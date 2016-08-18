package mixac1.dangerrpg.item.armor;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.client.RPGRenderHelper;
import mixac1.dangerrpg.client.model.ModelMageArmor;
import mixac1.dangerrpg.item.RPGArmorMaterial;
import mixac1.dangerrpg.item.RPGItemComponent.RPGArmorComponent;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemMageArmor extends RPGItemArmor
{
    protected int DEFAULT_COLOR = 0x3371e4;

    public ItemMageArmor(RPGArmorMaterial armorMaterial, RPGArmorComponent armorComponent, int armorType)
    {
        super(armorMaterial, armorComponent, 0, armorType);
    }

    public static ItemMageArmor[] createFullSet(RPGArmorMaterial armorMaterial, RPGArmorComponent armorComponent)
    {
        return new ItemMageArmor[] {
            new ItemMageArmor(armorMaterial, armorComponent, 0),
            new ItemMageArmor(armorMaterial, armorComponent, 1),
            new ItemMageArmor(armorMaterial, armorComponent, 2),
            new ItemMageArmor(armorMaterial, armorComponent, 3)
        };
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        String tmp = DangerRPG.MODID.concat(":armors/");
        itemIcon = iconRegister.registerIcon(Utils.toString(tmp, armorComponent.name, ARMOR_TYPES[armorType]));
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
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
    {
        ModelMageArmor model = slot == 2 ? ModelMageArmor.INSTANCE_LEGGINGS : ModelMageArmor.INSTANCE_ARMOR;
        if (type != null) {
            model.setColor(0xffffff);
            return Utils.toString(modelTexture, slot == 2 ? 2 : 1, "_", type, ".png");
        }
        else {
            model.setColor(getColor(stack));
            return Utils.toString("DangerRPG:textures/models/armors/", armorComponent.name, "_layer_", slot == 2 ? 2 : 1, ".png");
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
