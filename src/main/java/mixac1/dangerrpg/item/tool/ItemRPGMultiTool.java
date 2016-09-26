package mixac1.dangerrpg.item.tool;

import java.util.Set;

import com.google.common.collect.Sets;

import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.item.IRPGItem.IRPGItemTool;
import mixac1.dangerrpg.capability.RPGableItem;
import mixac1.dangerrpg.capability.data.RPGItemRegister.RPGItemData;
import mixac1.dangerrpg.init.RPGItems;
import mixac1.dangerrpg.init.RPGOther.RPGCreativeTabs;
import mixac1.dangerrpg.item.IHasBooksInfo;
import mixac1.dangerrpg.item.RPGItemComponent;
import mixac1.dangerrpg.item.RPGItemComponent.RPGToolComponent;
import mixac1.dangerrpg.item.RPGToolMaterial;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.world.World;

public class ItemRPGMultiTool extends ItemTool implements IRPGItemTool, IHasBooksInfo
{
    private static final Set multitoolBlocks = Sets.newHashSet(
        Sets.newHashSet(new Block[] {Blocks.grass, Blocks.dirt, Blocks.sand, Blocks.gravel, Blocks.snow_layer, Blocks.snow, Blocks.clay, Blocks.farmland, Blocks.soul_sand, Blocks.mycelium}),
        Sets.newHashSet(new Block[] {Blocks.cobblestone, Blocks.double_stone_slab, Blocks.stone_slab, Blocks.stone, Blocks.sandstone, Blocks.mossy_cobblestone, Blocks.iron_ore, Blocks.iron_block, Blocks.coal_ore, Blocks.gold_block, Blocks.gold_ore, Blocks.diamond_ore, Blocks.diamond_block, Blocks.ice, Blocks.netherrack, Blocks.lapis_ore, Blocks.lapis_block, Blocks.redstone_ore, Blocks.lit_redstone_ore, Blocks.rail, Blocks.detector_rail, Blocks.golden_rail, Blocks.activator_rail}),
        Sets.newHashSet(new Block[] {Blocks.planks, Blocks.bookshelf, Blocks.log, Blocks.log2, Blocks.chest, Blocks.pumpkin, Blocks.lit_pumpkin})
    );

    RPGToolMaterial toolMaterial;

    public ItemRPGMultiTool(RPGToolMaterial toolMaterial)
    {
        super(3.0F, toolMaterial.material, multitoolBlocks);
        this.toolMaterial = toolMaterial;
        setUnlocalizedName(RPGItems.getRPGName(getItemComponent(this), getToolMaterial(this)));
        setTextureName(Utils.toString(DangerRPG.MODID, ":tools/", unlocalizedName));
        setCreativeTab(RPGCreativeTabs.tabRPGAmunitions);
        setMaxStackSize(1);
    }

    @Override
    public boolean func_150897_b(Block block)
    {
        return Items.iron_shovel.func_150897_b(block) && Items.iron_pickaxe.func_150897_b(block);
    }

    @Override
    public float func_150893_a(ItemStack stack, Block block)
    {
        return efficiencyOnProperMaterial;
    }

    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass)
    {
        return super.toolMaterial.getHarvestLevel();
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        return Items.iron_hoe.onItemUse(stack, player, world, par4, par5, par6, par7, par8, par9, par10);
    }

    @Override
    public String getInformationToInfoBook(ItemStack item, EntityPlayer player)
    {
        return DangerRPG.trans("rpgstr.no_info_yet");
    }

    @Override
    public RPGToolComponent getItemComponent(Item item)
    {
        return RPGItemComponent.MULTITOOL;
    }

    @Override
    public RPGToolMaterial getToolMaterial(Item item)
    {
        return toolMaterial;
    }

    @Override
    public void registerAttributes(Item item, RPGItemData map)
    {
        RPGableItem.registerParamsItemTool(item, map);
    }
}
