package mixac1.dangerrpg.item.armor;

import java.util.HashMap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.api.item.ItemAttribute;
import mixac1.dangerrpg.capability.ItemAttrParams;
import mixac1.dangerrpg.capability.LvlableItem;
import mixac1.dangerrpg.client.model.ModelMageArmor;
import mixac1.dangerrpg.item.RPGItemComponent;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemMageArmor extends RPGItemArmor
{
    public ItemMageArmor(ArmorMaterial armorMaterial, int armorType, String name)
    {
        super(armorMaterial, 0, armorType, name);
    }

    public static ItemMageArmor[] createFullSet(ArmorMaterial armorMaterial, String name)
    {
        return new ItemMageArmor[] {
            new ItemMageArmor(armorMaterial, 0, name),
            new ItemMageArmor(armorMaterial, 1, name),
            new ItemMageArmor(armorMaterial, 2, name),
            new ItemMageArmor(armorMaterial, 3, name)
        };
    }

    @Override
    public String getInformationToInfoBook(ItemStack item, EntityPlayer player)
    {
        return null;
    }

    @Override
    public void registerAttributes(Item item, HashMap<ItemAttribute, ItemAttrParams> map)
    {
        LvlableItem.registerParamsItemArmor(item, map);
    }

    @Override
    public RPGItemComponent getItemComponent(Item item)
    {
        return null;
    }

    @Override
    public ArmorMaterial getArmorMaterial(Item item)
    {
        return armorMaterial;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entity, ItemStack itemStack, int slot)
    {
        ModelMageArmor model = slot == 2 ? ModelMageArmor.INSTANCE_LEGGINGS : ModelMageArmor.INSTANCE_ARMOR;
        model.setColor(0xaabc56);
        return model.init(entity, itemStack, slot);
    }

    @Override
    public int getColor(ItemStack stack)
    {
        return 10511680;
    }
}
