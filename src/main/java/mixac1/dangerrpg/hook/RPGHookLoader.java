package mixac1.dangerrpg.hook;

import gloomyfolken.hooklib.minecraft.HookLoader;
import gloomyfolken.hooklib.minecraft.PrimaryClassTransformer;

public class RPGHookLoader extends HookLoader
{
    @Override
    public String[] getASMTransformerClass()
    {
        return new String[] {PrimaryClassTransformer.class.getName()};
    }

    @Override
    public void registerHooks()
    {
        registerHookContainer(RPGHooks.class.getName());
        registerHookContainer(FixIncorrectMotionHooks.class.getName());
    }

    /**
     * MY CHANGES IN HOOKLIB
     * 1. {@link Hook}
     * boolean exceptionOnUnsuccess() default true;
     *
     * 2. {@link AsmHook}
     * public boolean exceptionOnUnsuccess = true;
     *
     * public void setNeedExcOnUnscs(boolean state) {
            AsmHook.this.exceptionOnUnsuccess = state;
       }
     *
     * 3. {@link HookContainerParser}
     * builder.setNeedExcOnUnscs(Boolean.TRUE.equals(annotationValues.get("exceptionOnUnsuccess")));
     *
     * 4. {@link HookClassTransformer#transform(String, byte[])}
     *
     */
}
