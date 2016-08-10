package mixac1.dangerrpg.item.armor;

import java.util.HashMap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.item.ItemAttribute;
import mixac1.dangerrpg.capability.LvlableItem;
import mixac1.dangerrpg.capability.LvlableItem.ItemAttrParams;
import mixac1.dangerrpg.client.RPGRenderHelper;
import mixac1.dangerrpg.client.model.ModelMageArmor;
import mixac1.dangerrpg.item.RPGItemComponent;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemMageArmor extends RPGItemArmor
{
    protected int DEFAULT_COLOR = 0x3371e4;

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
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        String tmp = DangerRPG.MODID.concat(":armors/");
        itemIcon = iconRegister.registerIcon(Utils.toString(tmp, name, ARMOR_TYPES[armorType]));
        overlayIcon = iconRegister.registerIcon(Utils.toString(tmp, unlocalizedName, "_overlay"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return true;
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
        ModelMageArmor model = slot == 2 ? ModelMageArmor.INSTANCE_LEGGINGS : ModelMageArmor.INSTANCE_ARMOR;
        if (type != null) {
            model.setColor(0xffffff);
            return Utils.toString(modelTexture, slot == 2 ? 2 : 1, "_", type, ".png");
        }
        else {
            model.setColor(getColor(stack));
            return Utils.toString("DangerRPG:textures/models/armors/", name, "_layer_", slot == 2 ? 2 : 1, ".png");
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entity, ItemStack stack, int slot)
    {
        ModelBiped tmp = RPGRenderHelper.modelBipedInit(entity, slot == 2 ? ModelMageArmor.INSTANCE_LEGGINGS : ModelMageArmor.INSTANCE_ARMOR, slot);
        return tmp;
    }

    @Override
    public int getColor(ItemStack stack)
    {
        NBTTagCompound nbt = stack.getTagCompound();

        if (nbt == null) {
            return DEFAULT_COLOR;
        }
        else {
            NBTTagCompound nbtColor = nbt.getCompoundTag("display");
            return nbtColor == null ? DEFAULT_COLOR : (nbtColor.hasKey("color", 3) ? nbtColor.getInteger("color") : DEFAULT_COLOR);
        }
    }
}
