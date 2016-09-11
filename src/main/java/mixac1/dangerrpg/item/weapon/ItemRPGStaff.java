package mixac1.dangerrpg.item.weapon;

import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.item.ILvlableItem.ILvlableItemStaff;
import mixac1.dangerrpg.capability.LvlableItem;
import mixac1.dangerrpg.capability.LvlableItem.ItemAttributesMap;
import mixac1.dangerrpg.capability.ia.ItemAttributes;
import mixac1.dangerrpg.entity.projectile.EntityMagicOrb;
import mixac1.dangerrpg.init.RPGItems;
import mixac1.dangerrpg.init.RPGOther.RPGCreativeTabs;
import mixac1.dangerrpg.item.IHasBooksInfo;
import mixac1.dangerrpg.item.RPGItemComponent.RPGStaffComponent;
import mixac1.dangerrpg.item.RPGToolMaterial;
import mixac1.dangerrpg.util.RPGCommonHelper;
import mixac1.dangerrpg.util.Utils;
import mixac1.dangerrpg.world.RPGEntityFXManager;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemRPGStaff extends ItemSword implements ILvlableItemStaff, IHasBooksInfo
{
    public RPGToolMaterial toolMaterial;
    public RPGStaffComponent staffComponent;

    public ItemRPGStaff(RPGToolMaterial toolMaterial, RPGStaffComponent staffComponent)
    {
        super(toolMaterial.material);
        this.toolMaterial = toolMaterial;
        this.staffComponent = staffComponent;
        setUnlocalizedName(RPGItems.getRPGName(getItemComponent(this), getToolMaterial(this)));
        setTextureName(Utils.toString(DangerRPG.MODID, ":weapons/range/", unlocalizedName));
        setCreativeTab(RPGCreativeTabs.tabRPGAmunitions);
        setMaxStackSize(1);
    }

    @Override
    public void registerAttributes(Item item, ItemAttributesMap map)
    {
        LvlableItem.registerParamsItemStaff(item, map);
    }

    @Override
    public RPGToolMaterial getToolMaterial(Item item)
    {
        return toolMaterial;
    }

    @Override
    public RPGStaffComponent getItemComponent(Item item)
    {
        return staffComponent;
    }

    @Override
    public String getInformationToInfoBook(ItemStack item, EntityPlayer player)
    {
        return DangerRPG.trans("rpgstr.no_info_yet");
    }

    @Override
    public float func_150893_a(ItemStack stack, Block block)
    {
        Material material = block.getMaterial();
        return material != Material.plants && material != Material.vine && material != Material.coral && material != Material.leaves && material != Material.gourd ? 1.0F : 1.5F;
    }

    @Override
    public boolean func_150897_b(Block block)
    {
        return false;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack p_77661_1_)
    {
        return EnumAction.bow;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        if (RPGCommonHelper.spendMana(player, ItemAttributes.MANA_COST.get(stack, player))) {
            player.setItemInUse(stack, getMaxItemUseDuration(stack));
        }
        return stack;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int useRemain)
    {
        if (!world.isRemote
            && RPGCommonHelper.getUsePower(player, stack, stack.getMaxItemUseDuration() - useRemain, 20F, 20F) > 0) {
            EntityMagicOrb entity = getEntityMagicOrb(stack, world, player);
            world.spawnEntityInWorld(entity);
            playShotSound(world, player);
        }
    }

    public EntityMagicOrb getEntityMagicOrb(ItemStack stack, World world, EntityPlayer player)
    {
        return new EntityMagicOrb(world, player, stack, 1f, 0F);
    }

    public void playShotSound(World world, EntityPlayer player)
    {
        world.playAuxSFXAtEntity(null, 1016, (int)player.posX, (int)player.posY, (int)player.posZ, 0);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int par, boolean isActive)
    {
        if (world.isRemote && isActive && entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            if (player.isUsingItem()) {
                double power = RPGCommonHelper.getUsePower(player, stack, stack.getMaxItemUseDuration() - player.getItemInUseCount(), 20F);
                int color = RPGCommonHelper.getSpecialColor(stack, EntityMagicOrb.DEFAULT_COLOR);
                Vec3 vec = RPGCommonHelper.getFirePoint(player);
                DangerRPG.proxy.spawnEntityFX(RPGEntityFXManager.EntityReddustFXE,
                                              vec.xCoord, vec.yCoord + 1 - 1 * power,vec.zCoord, 0f, 0f, 0f, color);
            }
        }
    }
}
