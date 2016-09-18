package mixac1.dangerrpg.init;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.entity.projectile.EntityRPGArrow;
import mixac1.dangerrpg.entity.projectile.EntityMagicOrb;
import mixac1.dangerrpg.entity.projectile.EntityMaterial;
import mixac1.dangerrpg.entity.projectile.EntityPowerMagicOrb;
import mixac1.dangerrpg.entity.projectile.EntityProjectile;
import mixac1.dangerrpg.entity.projectile.EntitySniperArrow;
import mixac1.dangerrpg.entity.projectile.EntityThrowKnife;
import mixac1.dangerrpg.entity.projectile.EntityThrowRPGItem;
import mixac1.dangerrpg.entity.projectile.EntityThrowTomahawk;
import mixac1.dangerrpg.tileentity.TileEntityModifyTable;
import net.minecraft.entity.Entity;

public abstract class RPGEntities
{
    static int count = 0;

    public static void load(FMLInitializationEvent e)
    {
        loadTileEntities();
        loadProjectileEntities();
    }

    private static void loadTileEntities()
    {
        GameRegistry.registerTileEntity(TileEntityModifyTable.class, TileEntityModifyTable.NAME);
    }

    private static void loadProjectileEntities()
    {
        registerEntityProjecttile(EntityProjectile.class);
        registerEntityProjecttile(EntityMaterial.class);
        registerEntityProjecttile(EntityThrowRPGItem.class);

        registerEntityProjecttile(EntityThrowKnife.class);
        registerEntityProjecttile(EntityThrowTomahawk.class);
        registerEntityProjecttile(EntityRPGArrow.class);
        registerEntityProjecttile(EntitySniperArrow.class);

        registerEntityProjecttile(EntityMagicOrb.class);
        registerEntityProjecttile(EntityPowerMagicOrb.class);
    }

    private static void registerEntityProjecttile(Class<? extends Entity> entityClass)
    {
        EntityRegistry.registerModEntity(entityClass, entityClass.getName(), count++, DangerRPG.instance, 64, 20, true);
    }
}
