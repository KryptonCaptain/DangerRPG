package mixac1.dangerrpg.init;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RPGItems
{
    public static void initCommon(FMLPreInitializationEvent e)
    {
    }

    public static void registerItem(Item item)
    {
        GameRegistry.register(item);
    }

    public static void registerItemArray(Item[] array)
    {
        for (Item item : array) {
            registerItem(item);
        }
    }
}
