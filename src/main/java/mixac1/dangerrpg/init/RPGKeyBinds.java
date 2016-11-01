package mixac1.dangerrpg.init;

import org.lwjgl.input.Keyboard;

import mixac1.dangerrpg.DangerRPG;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RPGKeyBinds
{
    public static KeyBinding specialItemKey = new KeyBinding(DangerRPG.trans("rpgkey.special_use"), Keyboard.KEY_F,
        DangerRPG.MODNAME);
    public static KeyBinding extraItemKey   = new KeyBinding(DangerRPG.trans("rpgkey.extra_use"), Keyboard.KEY_C,
        DangerRPG.MODNAME);
    public static KeyBinding infoBookKey    = new KeyBinding(DangerRPG.trans("rpgkey.info_book"), Keyboard.KEY_I,
        DangerRPG.MODNAME);
    public static KeyBinding guiModeKey     = new KeyBinding(DangerRPG.trans("rpgkey.gui_mode"), Keyboard.KEY_O,
        DangerRPG.MODNAME);

    public static void initClient(FMLPreInitializationEvent e)
    {
        ClientRegistry.registerKeyBinding(specialItemKey);
        ClientRegistry.registerKeyBinding(extraItemKey);
        ClientRegistry.registerKeyBinding(infoBookKey);
        ClientRegistry.registerKeyBinding(guiModeKey);
    }
}
