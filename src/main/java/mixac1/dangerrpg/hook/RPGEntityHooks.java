package mixac1.dangerrpg.hook;

import gloomyfolken.hooklib.asm.Hook;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.BaseAttribute;

public class RPGEntityHooks
{
    @Hook(injectOnExit = true, targetMethod = "<clinit>")
    public static void SharedMonsterAttributes(SharedMonsterAttributes attributes)
    {
        ((BaseAttribute) SharedMonsterAttributes.attackDamage).setShouldWatch(true);
    }
}
