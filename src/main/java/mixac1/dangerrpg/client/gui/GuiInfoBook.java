package mixac1.dangerrpg.client.gui;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.capability.EntityData;
import mixac1.dangerrpg.capability.LvlEAProvider;
import mixac1.dangerrpg.capability.LvlableItem;
import mixac1.dangerrpg.client.gui.GuiInfoBookContentPlayer.LevelUpButton;
import mixac1.dangerrpg.init.RPGKeyBinds;
import mixac1.dangerrpg.item.IHasBooksInfo;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GuiInfoBook extends GuiScreen
{
    public static final ResourceLocation TEXTURE = new ResourceLocation("DangerRPG:textures/gui/info_book.png");

    private EntityPlayer player;
    public ArrayList<LvlEAProvider> attributes;
    private ItemStack[] stacks;
    public int currContent;

    private GuiInfoBook.SelectContentButton[] butContent;
    public GuiInfoBookContent[] content = new GuiInfoBookContent[6];

    private int offsetX;
    private int offsetY;

    public static int bookImageWidth  = 186;
    public static int bookImageHeight = 254;
    public static int titleHeight = 16;

    public static int butContentOffsetX = 31;
    public static int butContentOffsetY = 16;
    public static int butContentOffsetU = 186;
    public static int butContentOffsetV = 0;
    public static int butContentIndent = 1;
    public static int butContentSize = 20;

    public static int emptyIconOffsetU = 206;
    public static int emptyIconOffsetV = 0;

    public static int contentOffsetX = 5;
    public static int contentOffsetY = 55;
    public static int contentSize = 190;

    private LevelUpButton button;

    public GuiInfoBook(EntityPlayer player)
    {
        this.player = player;
        attributes = EntityData.get(player).getLvlProviders();
    }

    @Override
    public void setWorldAndResolution(Minecraft mc, int width, int height)
    {
        super.setWorldAndResolution(mc, width, height);
        currContent = 0;

        stacks = new ItemStack[] {
            player.getCurrentEquippedItem(),
            player.getCurrentArmor(3),
            player.getCurrentArmor(2),
            player.getCurrentArmor(1),
            player.getCurrentArmor(0)
        };

        if (stacks[0] != null && !(LvlableItem.isLvlable(stacks[0]) || stacks[0].getItem() instanceof IHasBooksInfo)) {
            stacks[0] = null;
        }

        offsetX = (width  - bookImageWidth)  / 2;
        offsetY = (height - bookImageHeight) / 2;

        content[0] = new GuiInfoBookContentPlayer(mc, bookImageWidth - contentOffsetX * 2, 0, offsetY + contentOffsetY, contentSize, offsetX + contentOffsetX, this, player);
        for (int i = 1; i < content.length; ++i) {
            content[i] = new GuiInfoBookContentStack(mc, bookImageWidth - contentOffsetX * 2, 0, offsetY + contentOffsetY, contentSize, offsetX + contentOffsetX, this, player, stacks[i - 1]);
        }
        buttonList.add(button = new LevelUpButton(100, offsetX + contentOffsetX + GuiInfoBookContentPlayer.butOffsetX, offsetY + contentOffsetY + contentSize + GuiInfoBookContentPlayer.butOffsetY - GuiInfoBookContentPlayer.imageHeight, (GuiInfoBookContentPlayer) content[0]));
    }

    @Override
    public void initGui()
    {
        buttonList.clear();

        offsetX = (width  - bookImageWidth)  / 2;
        offsetY = (height - bookImageHeight) / 2;

        int i = 0;
        butContent = new GuiInfoBook.SelectContentButton[6];
        for (int k = 0; k < 6; ++k) {
            buttonList.add(butContent[k] = new GuiInfoBook.SelectContentButton(i++, offsetX + butContentOffsetX + (butContentSize + butContentIndent) * k, offsetY + butContentOffsetY));
        }
    }

    @Override
    protected void keyTyped(char c, int keyCode)
    {
        super.keyTyped(c, keyCode);
        if (keyCode == Keyboard.KEY_ESCAPE || keyCode == Keyboard.KEY_E || keyCode == RPGKeyBinds.infoBookKey.getKeyCode()) {
            mc.thePlayer.closeScreen();
        }
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float par3)
    {
        offsetX = (width  - bookImageWidth)  / 2;
        offsetY = (height - bookImageHeight) / 2;

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(TEXTURE);
        drawTexturedModalRect(offsetX, offsetY, 0, 0, bookImageWidth, bookImageHeight);

        String title = Utils.toString(DangerRPG.trans("rpgstr.info_about"), " ", player.getDisplayName());
        fontRendererObj.drawStringWithShadow(title, offsetX + (bookImageWidth - fontRendererObj.getStringWidth(title)) / 2, offsetY + (titleHeight - fontRendererObj.FONT_HEIGHT) / 2 + 2, 0xffffff);

        content[currContent].drawScreen(mouseX, mouseY, par3);

        super.drawScreen(mouseX, mouseY, par3);
    }

    class SelectContentButton extends GuiButton
    {
        public SelectContentButton(int id, int x, int y)
        {
            super(id, x, y, butContentSize, butContentSize, "");
        }

        @Override
        public void drawButton(Minecraft mc, int par1, int par2)
        {
            if (this.visible) {
                boolean flag = par1 >= xPosition && par2 >= yPosition && par1 < xPosition + width && par2 < yPosition + height;
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                mc.getTextureManager().bindTexture(TEXTURE);

                if (id == currContent) {
                    this.drawTexturedModalRect(xPosition, yPosition, butContentOffsetU, butContentOffsetV + butContentSize, butContentSize, butContentSize);
                }
                else if (flag && this.enabled) {
                    this.drawTexturedModalRect(xPosition, yPosition, butContentOffsetU, butContentOffsetV, butContentSize, butContentSize);
                }

                if (id != 0 && stacks[id - 1] != null) {
                    mc.getTextureManager().bindTexture(mc.getTextureManager().getResourceLocation(1));
                    drawTexturedModelRectFromIcon(xPosition + 2, yPosition + 2, stacks[id - 1].getItem().getIconFromDamage(0), 16, 16);
                }
                else {
                    drawTexturedModalRect(xPosition + 2, yPosition + 2, emptyIconOffsetU, emptyIconOffsetV + id * 16, 16, 16);
                }
            }
        }

        @Override
        public boolean mousePressed(Minecraft mc, int x, int y)
        {
            if (super.mousePressed(mc, x, y)) {
                currContent = id;
                content[id].init();
                button.visible = id == 0;
                return true;
            }
            return false;
        }
    }
}
