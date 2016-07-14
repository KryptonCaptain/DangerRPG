package mixac1.dangerrpg.item.tool;

import java.util.HashMap;

import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.item.IGemableItem;
import mixac1.dangerrpg.api.item.ItemAttribute;
import mixac1.dangerrpg.api.item.ILvlableItem.ILvlableItemTool;
import mixac1.dangerrpg.capability.GemType;
import mixac1.dangerrpg.capability.ItemAttrParams;
import mixac1.dangerrpg.capability.LvlableItem;
import mixac1.dangerrpg.init.RPGOther;
import mixac1.dangerrpg.item.IHasBooksInfo;
import mixac1.dangerrpg.item.RPGToolComponent;
import mixac1.dangerrpg.util.Translator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class RPGWeapon extends ItemSword implements ILvlableItemTool, IGemableItem, IHasBooksInfo
{    
    private static final GemType[] gemTypes = new GemType[] {
            GemType.GEM_MODIFY_ATTACK_1,
            GemType.GEM_MODIFY_ATTACK_2,
            GemType.GEM_SPECIAL_ATTACK,
            GemType.GEM_DEFENCE
    };
    
    public ToolMaterial toolMaterial;
    public RPGToolComponent toolComponent;
    
    public RPGWeapon(ToolMaterial toolMaterial, RPGToolComponent toolComponent, String name)
    {
        super(toolMaterial);
        this.toolMaterial = toolMaterial;
        this.toolComponent = toolComponent;
        setUnlocalizedName(name);
        setTextureName(DangerRPG.MODID + ":weapons/melee/" + name);
        setCreativeTab(RPGOther.tabDangerRPG);
        setMaxStackSize(1);
    }

    @Override
    public GemType[] getGemTypes(ItemStack stack)
    {
        return gemTypes;
    }

    @Override
    public String getInformationToInfoBook(ItemStack item, EntityPlayer player)
    {
        return Translator.trans("rpgstr.no_info_yet");
    }

    @Override
    public RPGToolComponent getToolComponent(Item item)
    {
        return toolComponent;
    }

    @Override
    public ToolMaterial getToolMaterial(Item item)
    {
        return toolMaterial;
    }

    @Override
    public void registerAttributes(Item item, HashMap<ItemAttribute, ItemAttrParams> map)
    {
        LvlableItem.registerParamsItemSword(item, map);
    }
    
//    @Override
//    public void onUpdate(ItemStack stack, World world, Entity entity, int par1, boolean par2)
//    {
//        if (!world.isRemote && entity instanceof EntityLivingBase) {
//            if (((EntityLivingBase) entity).getEquipmentInSlot(0) != null &&
//                ((EntityLivingBase) entity).getEquipmentInSlot(0).getItem() == this) {
//                double radius = 0.37D;
//
//                for (float l = 0F; l < 2 * Math.PI; l += Math.PI / 6) {
//                    double px = entity.posX + radius * Math.cos(l);
//                    double py = entity.posZ + radius * Math.sin(l);
//                    Minecraft.getMinecraft().effectRenderer.addEffect(new EntitySplashFX(Minecraft.getMinecraft().theWorld, 
//                            px, entity.posY + 1.5D, py, 0F, 0.4F, 0F));
//                }
//            }
//        }
//    }
}
