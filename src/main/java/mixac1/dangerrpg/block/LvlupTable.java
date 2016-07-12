package mixac1.dangerrpg.block;

import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.init.RPGGuiHandlers;
import mixac1.dangerrpg.init.RPGOther;
import mixac1.dangerrpg.tileentity.TileEntityLvlupTable;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class LvlupTable extends BlockContainer
{
    public static final String NAME  = "lvlup_table";
                                     
    public IIcon[]             icons = new IIcon[2];
                                     
    public LvlupTable()
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
        icons[0] = reg.registerIcon(textureName + "_" + 0);
        icons[1] = reg.registerIcon(textureName + "_" + 1);
    }
    
    @Override
    public IIcon getIcon(int side, int meta)
    {
        return icons[side == 1 ? 1 : 0];
    }
    
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par1, float par2,
                                    float par3, float par4)
    {
        if (!world.isRemote) {
            player.openGui(DangerRPG.instance, RPGGuiHandlers.GUI_LVLUP_TABLE, world, x, y, z);
        }
        return true;
    }
    
    @Override
    public TileEntity createNewTileEntity(World world, int par)
    {
        return new TileEntityLvlupTable();
    }
    
    // @Override
    // public int getRenderType()
    // {
    // return -1;
    // }
    //
    // @Override
    // public boolean isOpaqueCube()
    // {
    // return false;
    // }
    //
    // @Override
    // public boolean renderAsNormalBlock()
    // {
    // return false;
    // }
}
