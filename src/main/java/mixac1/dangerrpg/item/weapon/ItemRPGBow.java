package mixac1.dangerrpg.item.weapon;

import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.item.ILvlableItem;
import mixac1.dangerrpg.api.item.ILvlableItem.ILvlableItemBow;
import mixac1.dangerrpg.capability.LvlableItem;
import mixac1.dangerrpg.capability.LvlableItem.ItemAttributesMap;
import mixac1.dangerrpg.init.RPGOther.RPGCreativeTabs;
import mixac1.dangerrpg.item.IHasBooksInfo;
import mixac1.dangerrpg.item.RPGItemComponent.RPGBowComponent;
import mixac1.dangerrpg.item.RPGToolMaterial;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemRPGBow extends ItemBow implements ILvlableItemBow, IHasBooksInfo
{
    public RPGBowComponent bowComponent;

    public ItemRPGBow(RPGBowComponent bowComponent)
    {
        setUnlocalizedName(bowComponent.name);
        setTextureName(Utils.toString(DangerRPG.MODID, ":weapons/range/", unlocalizedName));
        setCreativeTab(RPGCreativeTabs.tabRPGAmunitions);
        this.bowComponent = bowComponent;
    }

    @Override
    public void registerAttributes(Item item, ItemAttributesMap map)
    {
        LvlableItem.registerParamsItemBow(item, map);
    }

    @Override
    public String getInformationToInfoBook(ItemStack item, EntityPlayer player)
    {
        return null;
    }

    @Override
    public RPGBowComponent getItemComponent(Item item)
    {
        return bowComponent;
    }

    @Override
    public RPGToolMaterial getToolMaterial(Item item)
    {
        return null;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, EntityPlayer player, int useDuration)
    {
        ILvlableItem.DEFAULT_BOW.onStoppedUsing(stack, world, player, useDuration);
    }
}
