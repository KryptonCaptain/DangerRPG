package mixac1.dangerrpg.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.capability.LvlableItem;
import mixac1.dangerrpg.init.RPGBlocks;
import mixac1.dangerrpg.inventory.ContainerLvlupTable;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;


@SideOnly(Side.CLIENT)
public class GuiLvlupTable extends GuiContainer
{
    public static final ResourceLocation TEXTURE = new ResourceLocation("DangerRPG:textures/gui/container/gui_lvlup_table.png");
    
    private int buttonNumber;
    private boolean buttonFlag = false;
    
    private static int butOffsetX = 60;
    private static int butOffsetY = 41;
    private static int butOffsetU = 0;
    private static int butOffsetV = 174;
    private static int butSizeX = 108;
    private static int butSizeY = 19;
    
    public GuiLvlupTable(InventoryPlayer inventory, World world, int x, int y, int z)
    {
        super(new ContainerLvlupTable(inventory, world, x, y, z));
        xSize = 176;
        ySize = 174;
    }
    
    @Override
    public void updateScreen()
    {
        super.updateScreen();
    }
    
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int par3)
    {
        super.mouseClicked(mouseX, mouseY, par3);

        for (int i = 0; i < 2; ++i) {
            int x = mouseX - (guiLeft + butOffsetX);
            int y = mouseY - (guiTop + butOffsetY + butSizeY * i);

            if (x >= 0 && y >= 0 && x < butSizeX && y < butSizeY && ((ContainerLvlupTable) inventorySlots).enchantItem(mc.thePlayer, i)) {
                mc.playerController.sendEnchantPacket(this.inventorySlots.windowId, i);
                buttonFlag = true;
                buttonNumber = i;
            }
        }
    }
    
    @Override
    protected void mouseMovedOrUp(int mouseX, int mouseY, int par3)
    {
        super.mouseMovedOrUp(mouseX, mouseY, par3);
        buttonFlag = false;
    }
    
    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int par3, long par4)
    {
        super.mouseClickMove(mouseX, mouseY, par3, par4);
        
        int x = mouseX - (guiLeft + butOffsetX);
        int y = mouseY - (guiTop + butOffsetY + butSizeY * buttonNumber);
        if (!(x >= 0 && y >= 0 && x < butSizeX && y < butSizeY)) {
            buttonFlag = false;
        }
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        this.mc.getTextureManager().bindTexture(TEXTURE);
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        ItemStack stack;
        stack = inventorySlots.getSlot(0).getStack();
        int exp;
        if (stack != null && LvlableItem.isLvlable(stack)) {
            for (int i = 0; i < 2; ++i) {
                if ((exp = ((ContainerLvlupTable) inventorySlots).expToUp) != 0 && exp != -1 &&
                    (i == 0 && (((ContainerLvlupTable) inventorySlots).expToUp <= mc.thePlayer.experienceTotal) ||
                    (i == 0 && mc.thePlayer.capabilities.isCreativeMode) ||
                    (i == 1 && mc.thePlayer.experienceTotal > 0))) {
                    int x = mouseX - (guiLeft + butOffsetX);
                    int y = mouseY - (guiTop + butOffsetY + butSizeY * i);
                    if (x >= 0 && y >= 0 && x < butSizeX && y < butSizeY) {
                        if (!(i == buttonNumber && buttonFlag)) {
                            this.drawTexturedModalRect(guiLeft + butOffsetX, guiTop + butOffsetY + butSizeY * i, butOffsetU, butOffsetV + butSizeY, butSizeX, butSizeY);
                        }
                    }
                    else {
                        this.drawTexturedModalRect(guiLeft + butOffsetX, guiTop + butOffsetY + butSizeY * i, butOffsetU, butOffsetV, butSizeX, butSizeY);
                    }
                }
            } 
            mc.fontRenderer.drawSplitString(DangerRPG.trans("rpgstr.lvlup_table.1button"), guiLeft + 65, guiTop + 47, 100, 0x322D23);
            mc.fontRenderer.drawSplitString(DangerRPG.trans("rpgstr.lvlup_table.2button"), guiLeft + 65, guiTop + 47 + butSizeY, butSizeX, 0x322D23);
        }
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String s1 = StatCollector.translateToLocal(RPGBlocks.lvlupTable.getLocalizedName());
        String s2 = StatCollector.translateToLocal("key.inventory");
        fontRendererObj.drawString(s1, 88 - fontRendererObj.getStringWidth(s1) / 2, 5, 0x404040);
        fontRendererObj.drawString(s2, 8, 82, 0x404040);
    }
}
