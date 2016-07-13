package mixac1.dangerrpg.init;

import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.entity.projectile.EntityArrowRPG;
import mixac1.dangerrpg.entity.projectile.EntityMaterial;
import mixac1.dangerrpg.entity.projectile.EntityProjectile;
import mixac1.dangerrpg.entity.projectile.EntitySniperArrow;
import mixac1.dangerrpg.entity.projectile.EntityThrowKnife;
import mixac1.dangerrpg.entity.projectile.EntityThrowLvlItem;
import mixac1.dangerrpg.entity.projectile.EntityThrowTomahawk;
import mixac1.dangerrpg.tileentity.TileEntityModifyTable;

public class RPGEntities
{
	static int count = 0;
	
	public static void load()
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
		EntityRegistry.registerModEntity(EntityProjectile.class, "EntityProjectile", count++, DangerRPG.instance, 80, 3, true);
		EntityRegistry.registerModEntity(EntityMaterial.class, "EntityMaterial", count++, DangerRPG.instance, 80, 3, true);
		EntityRegistry.registerModEntity(EntityThrowLvlItem.class, "EntityThrowLvlItem", count++, DangerRPG.instance, 80, 3, true);
		
		EntityRegistry.registerModEntity(EntityThrowKnife.class, "EntityThrowKnife", count++, DangerRPG.instance, 80, 3, true);
		EntityRegistry.registerModEntity(EntityThrowTomahawk.class, "EntityThrowTomahawk", count++, DangerRPG.instance, 80, 3, true);
		EntityRegistry.registerModEntity(EntityArrowRPG.class, "EntityArrowRPG", count++, DangerRPG.instance, 80, 3, true);
		EntityRegistry.registerModEntity(EntitySniperArrow.class, "EntitySniperArrow", count++, DangerRPG.instance, 80, 3, true);
	}
}
