package mixac1.dangerrpg.item.armor;

import java.util.HashMap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.item.ILvlableItem.ILvlableItemArmor;
import mixac1.dangerrpg.api.item.ItemAttribute;
import mixac1.dangerrpg.capability.ItemAttrParams;
import mixac1.dangerrpg.capability.LvlableItem;
import mixac1.dangerrpg.init.RPGOther;
import mixac1.dangerrpg.item.IHasBooksInfo;
import mixac1.dangerrpg.item.RPGItemComponent;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class RPGItemArmor extends ItemArmor implements ILvlableItemArmor, IHasBooksInfo
{
    protected ArmorMaterial armorMaterial;
    private String texture;

    public RPGItemArmor(ArmorMaterial armorMaterial, int renderIndex, int armorType, String name)
    {
        super(armorMaterial, renderIndex, armorType);
        this.armorMaterial = armorMaterial;
        texture = "DangerRPG:textures/models/armors/".concat(name).concat("_layer_");
        name = (armorType == 0 ? "helmet_" :
                armorType == 1 ? "chestplate_" :
                armorType == 2 ? "leggings_" :
                armorType == 3 ? "boots_" : "").concat(name);
        setUnlocalizedName(name);
        setTextureName(DangerRPG.MODID + ":armors/" + name);
        setCreativeTab(RPGOther.tabDangerRPG);
    }

    public static RPGItemArmor[] createFullSet(ArmorMaterial armorMaterial, String name)
    {
        return new RPGItemArmor[] {
            new RPGItemArmor(armorMaterial, 0, 0, name),
            new RPGItemArmor(armorMaterial, 0, 1, name),
            new RPGItemArmor(armorMaterial, 0, 2, name),
            new RPGItemArmor(armorMaterial, 0, 3, name)
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
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
    {
        return texture.concat(String.valueOf(slot == 2 ? 2 : 1)).concat(".png");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot)
    {
        return null;
    }
}
