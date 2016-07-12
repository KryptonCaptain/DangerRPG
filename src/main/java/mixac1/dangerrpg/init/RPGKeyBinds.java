package mixac1.dangerrpg.init;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.util.Translator;
import net.minecraft.client.settings.KeyBinding;

public class RPGKeyBinds
{
	public static KeyBinding specialItemKey = new KeyBinding(Translator.trans("rpgstr.key.special_use"), Keyboard.KEY_F, DangerRPG.MODNAME);
	public static KeyBinding extraItemKey = new KeyBinding(Translator.trans("rpgstr.key.extra_use"), Keyboard.KEY_C, DangerRPG.MODNAME);
	public static KeyBinding infoBookKey = new KeyBinding(Translator.trans("rpgstr.key.info_book"), Keyboard.KEY_I, DangerRPG.MODNAME);
	
	public static void load()
	{
		ClientRegistry.registerKeyBinding(specialItemKey);
		ClientRegistry.registerKeyBinding(extraItemKey);
		ClientRegistry.registerKeyBinding(infoBookKey);
	}
}
