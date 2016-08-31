package mixac1.dangerrpg.hook;

import gloomyfolken.hooklib.minecraft.HookLoader;
import gloomyfolken.hooklib.minecraft.PrimaryClassTransformer;

public class RPGHookLoader extends HookLoader
{
    @Override
    public String[] getASMTransformerClass()
    {
        return new String[] { PrimaryClassTransformer.class.getName() };
    }

    @Override
    public void registerHooks()
    {
        registerHookContainer(HookItems.class.getName());

        registerHookContainer(HookEntities.class.getName());

        registerHookContainer(HookArmorSystem.class.getName());

        registerHookContainer(HookItemBow.class.getName());

        registerHookContainer(HookFixEntityMotion.class.getName());
    }
}
