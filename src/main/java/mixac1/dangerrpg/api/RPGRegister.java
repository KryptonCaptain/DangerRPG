package mixac1.dangerrpg.api;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.api.entity.IRPGEntity;
import mixac1.dangerrpg.api.item.ILvlableItem;
import mixac1.dangerrpg.capability.LvlableItem.ItemAttributesMap;
import mixac1.dangerrpg.capability.RPGEntityData.EntityAttributesSet;
import mixac1.dangerrpg.client.render.item.RenderRPGItemModel;
import mixac1.dangerrpg.init.RPGCapability;
import mixac1.dangerrpg.init.RPGRenderers;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;

public abstract class RPGRegister
{
    /**
     * Advanced method model registering.<br>
     * Also, it is registering {@link ResourceLocation} of texture for model.<br>
     * This texture will be used, if you extends your model class from {@link RenderRPGItemModel}
     */
    @SideOnly(Side.CLIENT)
    public static void registerItemRendererModel(Item item, RenderRPGItemModel model, String resDomain, String resPath)
    {
        MinecraftForgeClient.registerItemRenderer(item, model);
        RPGRenderers.modelTextures.put(item,
                new ResourceLocation(resDomain, Utils.toString(resPath, item.unlocalizedName, ".png")));
    }

    /**
     * Advanced method model registering.<br>
     * Also, it is registering {@link ResourceLocation} of texture for model.<br>
     * This texture will be used, if you extends your model class from {@link RenderRPGItemModel}
     */
    @SideOnly(Side.CLIENT)
    public static void registerItemRendererModel(Item item, RenderRPGItemModel model, String resFullPath)
    {
        MinecraftForgeClient.registerItemRenderer(item, model);
        RPGRenderers.modelTextures.put(item,
                new ResourceLocation(Utils.toString(resFullPath, item.unlocalizedName, ".png")));
    }

    /**
     * Register supported Lvlable Item<br>
     * It may be used, if {@link Item} not instance of {@link ILvlableItem}
     * Must be fired before postInit
     */
    public static void registerLvlableItem(Item item, ILvlableItem ilvl)
    {
        RPGCapability.lvlItemRegistr.data.put(item, new ItemAttributesMap(ilvl, true));
    }

    /**
     * Register supported RPG Entity<br>
     * It may be used, if entityClass not instance of {@link IRPGEntity}
     * Must be fired before postInit
     */
    public static void registerRPGEntity(Class<? extends EntityLivingBase> entityClass, IRPGEntity iRPG)
    {
        RPGCapability.rpgEntityRegistr.data.put(entityClass, new EntityAttributesSet(iRPG, true));
    }
}
