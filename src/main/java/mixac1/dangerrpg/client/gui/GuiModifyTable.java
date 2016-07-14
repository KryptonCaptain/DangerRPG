package mixac1.dangerrpg.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.init.RPGBlocks;
import mixac1.dangerrpg.inventory.ContainerModifyTable;
import mixac1.dangerrpg.tileentity.TileEntityModifyTable;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class GuiModifyTable extends GuiContainer
{
    public static final ResourceLocation TEXTURE = new ResourceLocation("DangerRPG:textures/gui/container/gui_modify_table.png");
    
    private IInventory playerInv;
    private TileEntityModifyTable tileEntity;
    
    public GuiModifyTable(InventoryPlayer inventory, World world, int x, int y, int z)
    {
        super(new ContainerModifyTable(inventory, world, x, y, z));

        playerInv = inventory;
        tileEntity = (TileEntityModifyTable) world.getTileEntity(x, y, z);

        xSize = 176;
        ySize = 227;
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        mc.getTextureManager().bindTexture(TEXTURE);
        drawTexturedModalRect(this.guiLeft, guiTop, 0, 0, xSize, ySize);
        
        for (int i = 0, k = 0; i < 2; ++i) {
            for (int j = 0; j < 2; ++j, ++k) {
                drawTexturedModalRect(guiLeft + 80 + j * 18, guiTop + 90 + i * 18, 16 * tileEntity.guiIconIds[k], 227, 16, 16);
            }
        }
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String s1 = StatCollector.translateToLocal(RPGBlocks.modifyTable.getLocalizedName());
        String s2 = StatCollector.translateToLocal("key.inventory");
        fontRendererObj.drawString(s1, (xSize - fontRendererObj.getStringWidth(s1)) / 2, 5, 0x404040);
        fontRendererObj.drawString(s2, 8, 133, 0x404040);
    }
}
