package mixac1.dangerrpg.block;

import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.init.RPGBlocks;
import mixac1.dangerrpg.init.RPGGuiHandlers;
import mixac1.dangerrpg.init.RPGOther;
import mixac1.dangerrpg.tileentity.TileEntityModifyTable;
import mixac1.dangerrpg.util.RPGCommonHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ModifyTable extends BlockContainer
{
    public static final String NAME  = "modify_table";
                                     
    public IIcon[]             icons = new IIcon[3];
                                     
    public ModifyTable()
    {
        super(Material.iron);
        setBlockName(NAME);
        setBlockTextureName(DangerRPG.MODID + ":" + NAME);
        setHardness(2.0f);
        setResistance(6.0f);
        this.setHarvestLevel("pickaxe", 2);
        setCreativeTab(RPGOther.tabDangerRPG);
    }
    
    @Override
    public void registerBlockIcons(IIconRegister reg)
    {
        icons[0] = reg.registerIcon(getTextureName() + "_side");
        icons[1] = reg.registerIcon(getTextureName() + "_top");
        icons[2] = reg.registerIcon(getTextureName() + "_front");
    }
    
    @Override
    public IIcon getIcon(int side, int meta)
    {
        return side == 1 ? icons[1]
                : side == 0 ? RPGBlocks.lvlupTable.getBlockTextureFromSide(side)
                        : side != 2 && side != 4 ? icons[0]
                                : icons[2];
    }
    
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityModifyTable();
    }
    
    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int par)
    {
        RPGCommonHelper.breakBlock(world, x, y, z, block, par);
        super.breakBlock(world, x, y, z, block, par);
    }
    
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par1, float par2,
                                    float par3, float par4)
    {
        if (!world.isRemote) {
            player.openGui(DangerRPG.instance, RPGGuiHandlers.GUI_MODIFY_TABLE, world, x, y, z);
        }
        return true;
    }
}
