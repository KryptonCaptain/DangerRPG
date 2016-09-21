package mixac1.hooklib.asm;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import mixac1.dangerrpg.DangerRPG;

public class HookClassTransformer {

    public HashMap<String, List<AsmHook>> hooksMap = new HashMap<String, List<AsmHook>>();
    private HookContainerParser containerParser = new HookContainerParser(this);

    public void registerHook(AsmHook hook) {
        if (hooksMap.containsKey(hook.getTargetClassName())) {
            hooksMap.get(hook.getTargetClassName()).add(hook);
        } else {
            List<AsmHook> list = new ArrayList<AsmHook>(2);
            list.add(hook);
            hooksMap.put(hook.getTargetClassName(), list);
        }
    }

    public void registerHookContainer(String className) {
        containerParser.parseHooks(className);
    }

    public void registerHookContainer(InputStream classData) {
        containerParser.parseHooks(classData);
    }

    public byte[] transform(String className, byte[] bytecode) {
        List<AsmHook> hooks = hooksMap.get(className);

        if (hooks != null) {
            try {
                Collections.sort(hooks);
                int numHooks = hooks.size();

                for (AsmHook hook : hooks) {
                    DangerRPG.infoLog(String.format("Hook: patching method %s#%s", hook.getTargetClassName(), hook.getTargetMethodName()));
                }

                /*
                 Начиная с седьмой версии джавы, сильно изменился процесс верификации байткода.
                 Ради этого приходится включать автоматическую генерацию stack map frame'ов.
                 На более старых версиях байткода это лишняя трата времени.
                 Подробнее здесь: http://stackoverflow.com/questions/25109942
                */
                int majorVersion = ((bytecode[6] & 0xFF) << 8) | (bytecode[7] & 0xFF);
                boolean java7 = majorVersion > 50;

                ClassReader cr = new ClassReader(bytecode);
                ClassWriter cw = createClassWriter(java7 ? ClassWriter.COMPUTE_FRAMES : ClassWriter.COMPUTE_MAXS);
                HookInjectorClassVisitor hooksWriter = createInjectorClassVisitor(cw, hooks);
                cr.accept(hooksWriter, java7 ? ClassReader.SKIP_FRAMES : ClassReader.EXPAND_FRAMES);

                int numInjectedHooks = numHooks - hooksWriter.hooks.size();
                for (AsmHook hook : hooks) {
                    DangerRPG.infoLog(String.format("Warning: unsuccesfull pathing method %s#%s", hook.getTargetClassName(), hook.getTargetMethodName()));
                }

                return cw.toByteArray();
            }
            catch (Exception e) {
                DangerRPG.logger.error("A problem has occured during transformation of class " + className + ".");
                DangerRPG.logger.error("Attached hooks:");
                for (AsmHook hook : hooks) {
                    DangerRPG.logger.error(hook.toString());
                }
                DangerRPG.logger.error("Stack trace:", e);
            }
        }
        return bytecode;
    }

    /**
     * Создает ClassVisitor для списка хуков.
     * Метод можно переопределить, если в ClassVisitor'e нужна своя логика для проверки,
     * является ли метод целевым (isTargetMethod())
     * @param cw ClassWriter, который должен стоять в цепочке после этого ClassVisitor'a
     * @param hooks Список хуков, вставляемых в класс
     * @return ClassVisitor, добавляющий хуки
     */
    protected HookInjectorClassVisitor createInjectorClassVisitor(ClassWriter cw, List<AsmHook> hooks) {
        return new HookInjectorClassVisitor(cw, hooks);
    }

    /**
     * Создает ClassWriter для сохранения трансформированного класса.
     * Метод можно переопределить, если в ClassWriter'e нужна своя реализация метода getCommonSuperClass().
     * Стандартная реализация работает для уже загруженных классов и для классов, .class файлы которых есть
     * в classpath, но они ещё не загружены. Во втором случае происходит загрузка (но не инициализация) классов.
     * Если загрузка классов является проблемой, то можно воспользоваться SafeClassWriter.
     * @param flags Список флагов, которые нужно передать в конструктор ClassWriter'a
     * @return ClassWriter, сохраняющий трансформированный класс
     */
    protected ClassWriter createClassWriter(int flags) {
        return new SafeClassWriter(flags);
    }
}
