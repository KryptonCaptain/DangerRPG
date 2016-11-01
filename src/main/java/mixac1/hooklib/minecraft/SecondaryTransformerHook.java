package mixac1.hooklib.minecraft;

import mixac1.hooklib.asm.Hook;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraftforge.fml.common.Loader;

public class SecondaryTransformerHook
{
    @Hook
    public static void injectData(Loader loader, Object... data)
    {
        LaunchClassLoader classLoader = (LaunchClassLoader) SecondaryTransformerHook.class.getClassLoader();
        classLoader.registerTransformer(MinecraftClassTransformer.class.getName());
    }
}
