package mixac1.dangerrpg.init;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.DangerRPG;
import net.minecraft.client.settings.KeyBinding;

@SideOnly(Side.CLIENT)
public class RPGKeyBinds
{
    public static KeyBinding specialItemKey = new KeyBinding(DangerRPG.trans("rpgstr.key.special_use"), Keyboard.KEY_F, DangerRPG.MODNAME);
    public static KeyBinding extraItemKey = new KeyBinding(DangerRPG.trans("rpgstr.key.extra_use"), Keyboard.KEY_C, DangerRPG.MODNAME);
    public static KeyBinding infoBookKey = new KeyBinding(DangerRPG.trans("rpgstr.key.info_book"), Keyboard.KEY_I, DangerRPG.MODNAME);
    
    public static void load()
    {
        ClientRegistry.registerKeyBinding(specialItemKey);
        ClientRegistry.registerKeyBinding(extraItemKey);
        ClientRegistry.registerKeyBinding(infoBookKey);
    }
}
