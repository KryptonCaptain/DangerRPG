package mixac1.dangerrpg.init;

import mixac1.dangerrpg.DangerRPG;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RPGEntities
{
    static int count = 0;

    public static void initCommon(FMLInitializationEvent e)
    {
        initTileEntities();
        initProjectileEntities();
    }

    private static void initTileEntities()
    {
    }

    private static void initProjectileEntities()
    {
    }

    private static void registerEntityProjectile(Class<? extends Entity> entityClass)
    {
        EntityRegistry.registerModEntity(entityClass, entityClass.getSimpleName(), count++, DangerRPG.INSTANCE, 64, 20,
            true);
    }

    private static void registerTileEntity(Class<? extends TileEntity> tielEntityClass)
    {
        GameRegistry.registerTileEntity(tielEntityClass, tielEntityClass.getSimpleName());
    }
}
