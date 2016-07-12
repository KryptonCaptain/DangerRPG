package mixac1.dangerrpg.init;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.client.gui.GuiInfoBook;
import mixac1.dangerrpg.client.gui.GuiLvlupTable;
import mixac1.dangerrpg.client.gui.GuiModifyTable;
import mixac1.dangerrpg.inventory.ContainerLvlupTable;
import mixac1.dangerrpg.inventory.ContainerModifyTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class RPGGuiHandlers implements IGuiHandler
{
	public static final int GUI_MODIFY_TABLE = 0;
	public static final int GUI_LVLUP_TABLE  = 1;
	public static final int GUI_INFO_BOOK    = 2;
	
	public static void load()
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(DangerRPG.instance, new RPGGuiHandlers());
	}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		switch(ID) {
		case GUI_MODIFY_TABLE:
			return new ContainerModifyTable(player.inventory, world, x, y, z);
		case GUI_LVLUP_TABLE:
			return new ContainerLvlupTable(player.inventory, world, x, y, z);
		default:
			return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		switch(ID) {
		case GUI_MODIFY_TABLE:
			return new GuiModifyTable(player.inventory, world, x, y, z);
		case GUI_LVLUP_TABLE:
			return new GuiLvlupTable(player.inventory, world, x, y, z);
		case GUI_INFO_BOOK:
			return new GuiInfoBook(player);
		default:
			return null;
		}
	}
}
