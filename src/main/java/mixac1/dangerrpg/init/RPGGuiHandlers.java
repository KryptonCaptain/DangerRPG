package mixac1.dangerrpg.init;

import mixac1.dangerrpg.DangerRPG;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class RPGGuiHandlers implements IGuiHandler
{
    public static final int GUI_MODIFICATION_TABLE = 0;
    public static final int GUI_LVLUP_TABLE        = 1;
    public static final int GUI_INFO_BOOK          = 2;
    public static final int GUI_RPG_WORKBENCH      = 3;

    public static void initCommon(FMLInitializationEvent e)
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(DangerRPG.INSTANCE, new RPGGuiHandlers());
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (ID) {
        default:
            return null;
        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (ID) {
        default:
            return null;
        }
    }
}
