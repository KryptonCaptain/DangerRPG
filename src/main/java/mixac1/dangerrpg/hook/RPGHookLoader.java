package mixac1.dangerrpg.hook;

import mixac1.hooklib.minecraft.HookLoader;
import mixac1.hooklib.minecraft.PrimaryClassTransformer;

public class RPGHookLoader extends HookLoader
{
    @Override
    public String[] getASMTransformerClass()
    {
        return new String[] {
            PrimaryClassTransformer.class.getName()
        };
    }

    @Override
    public void registerHooks()
    {

    }
}
