package mixac1.dangerrpg.client.gui;

import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.entity.LvlEAProvider;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GuiInfoBookContentPlayer extends GuiInfoBookContent
{
    public static final ResourceLocation TEXTURE = new ResourceLocation("DangerRPG:textures/gui/info_book_player_content.png");
    private LevelUpButton button;

    public static int imageWidth  = 176;
    public static int imageHeight = 84;

    public static int offset = 5;
    public static int sizeContent = 20;

    public static int butOffsetX = 111;
    public static int butOffsetY = 1;
    public static int butOffsetU = 176;
    public static int butOffsetV = 0;
    public static int butSizeX = 64;
    public static int butSizeY = 15;

    public static int titleSizeX = 109;
    public static int titleSizeY = 15;
    public static int titleOffsetX = 1;
    public static int titleOffsetY = 1;

    public static int infoOffsetX = 5;
    public static int infoOffsetY = 20;
    public static int infoIndent = 2;
    public static int infoWidth = imageWidth - infoOffsetX * 2;

    private int currIndex = -1;

    public GuiInfoBookContentPlayer(Minecraft mc, int width, int height, int top, int size, int left, GuiScreen parent, EntityPlayer player)
    {
        super(mc, width, height, top, size - imageHeight - offset, left, sizeContent, parent, player);
    }

    @Override
    public void init()
    {
        super.init();
        for (LvlEAProvider attr : parent.attributes) {
            list.add(new ContentItem(attr));
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float par3)
    {
        super.drawScreen(mouseX, mouseY, par3);
        String s;
        if (currIndex >= 0) {
            int offsetX = left;
            int offsetY = bottom + offset;
            int indent = infoIndent + mc.fontRenderer.FONT_HEIGHT;

            mc.getTextureManager().bindTexture(TEXTURE);
            parent.drawTexturedModalRect(offsetX, offsetY, 0, 0, imageWidth, imageHeight);

            s = mc.fontRenderer.trimStringToWidth(parent.attributes.get(currIndex).attr.getDisplayName().toUpperCase(), titleSizeX);
            mc.fontRenderer.drawStringWithShadow(s, offsetX + (titleSizeX - mc.fontRenderer.getStringWidth(s) + 4) / 2, offsetY + (titleSizeY - mc.fontRenderer.FONT_HEIGHT + 4) / 2, 0xffffff);

            s = Utils.toString(DangerRPG.trans("ia.lvl"), ": ", parent.attributes.get(currIndex).getLvl(player));
            mc.fontRenderer.drawStringWithShadow(s, offsetX + infoOffsetX, offsetY + infoOffsetY, 0xffffff);

            s = Utils.toString(DangerRPG.trans("rpgstr.value"), ": ", parent.attributes.get(currIndex).attr.displayValue(player));
            mc.fontRenderer.drawStringWithShadow(s, offsetX + infoOffsetX, offsetY + infoOffsetY + indent, 0xffffff);

            s = parent.attributes.get(currIndex).attr.getInfo();
            List list = mc.fontRenderer.listFormattedStringToWidth(s, infoWidth);
            for (int i = 0; i < list.size(); ++i) {
                mc.fontRenderer.drawStringWithShadow(list.get(i).toString(), offsetX + infoOffsetX, offsetY + infoOffsetY + indent * (2 + i), 0xffffff);
            }

            int value = parent.attributes.get(currIndex).getExpUp(player);
            s = String.format("%d/%d", player.experienceLevel, value);
            int tmp = offsetX + (infoWidth - mc.fontRenderer.getStringWidth(s)) / 2;
            s = String.valueOf(player.experienceLevel);
            mc.fontRenderer.drawStringWithShadow(s, tmp, offsetY + 73, parent.attributes.get(currIndex).canUp(player) ? 0x00bf00 : 0xbf0000);
            mc.fontRenderer.drawStringWithShadow("/".concat(String.valueOf(value)), tmp + mc.fontRenderer.getStringWidth(s), offsetY + 73, 0xffffff);
        }

        s = DangerRPG.trans("rpgstr.player_stats");
        mc.fontRenderer.drawStringWithShadow(s, left + (listWidth - mc.fontRenderer.getStringWidth(s)) / 2, top - mc.fontRenderer.FONT_HEIGHT - 4, 0xffffff);
    }

    @Override
    protected void elementClicked(int index, boolean doubleClick)
    {
        currIndex = index;
    }

    @Override
    protected boolean isSelected(int index)
    {
        return index == currIndex;
    }

    @Override
    protected void drawBackground() {}

    @Override
    protected void drawSlot(int var1, int var2, int var3, int var4, Tessellator var5)
    {
        ((ContentItem) list.get(var1)).draw(left, var3, 0xffffff, 0x00bf00);
    }

    public class ContentItem
    {
        private LvlEAProvider attr;

        public ContentItem(LvlEAProvider attr)
        {
            this.attr = attr;
        }

        public void draw(int x, int y, int color1, int color2)
        {
            String s = mc.fontRenderer.trimStringToWidth(attr.attr.getDisplayName(), listWidth - 20);
            mc.fontRenderer.drawStringWithShadow(s, x + (listWidth - mc.fontRenderer.getStringWidth(s)) / 2, y + (sizeContent - mc.fontRenderer.FONT_HEIGHT) / 2, attr.canUp(player) ? color2 : color1);

            s = String.valueOf(attr.getLvl(player));
            mc.fontRenderer.drawStringWithShadow(s, x + 5, y + (sizeContent - mc.fontRenderer.FONT_HEIGHT) / 2, color1);
        }
    }

    public static class LevelUpButton extends GuiButton
    {
        private GuiInfoBookContentPlayer parent;

        public LevelUpButton(int id, int x, int y, GuiInfoBookContentPlayer parent)
        {
            super(id, x, y, butSizeX, butSizeY, DangerRPG.trans("rpgstr.info_book.lvlup_but"));
            this.parent = parent;
        }

        @Override
        public void drawButton(Minecraft mc, int par1, int par2)
        {
            GL11.glDisable(GL11.GL_LIGHTING);
            if (visible && parent.currIndex >= 0) {
                boolean flag = par1 >= xPosition && par2 >= yPosition && par1 < xPosition + width && par2 < yPosition + height;
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                mc.getTextureManager().bindTexture(TEXTURE);

                enabled = parent.parent.attributes.get(parent.currIndex).canUp(parent.player);
                int color = 0x919191;

                if (enabled) {
                    if (flag) {
                        if (!Mouse.isButtonDown(0)) {
                            this.drawTexturedModalRect(xPosition, yPosition, butOffsetU, butOffsetV + butSizeY, width, height);
                            color = 0x00bf00;
                        }
                    }
                    else {
                        this.drawTexturedModalRect(xPosition, yPosition, butOffsetU, butOffsetV, width, height);
                        color = 0x00bf00;
                    }
                }

                mc.fontRenderer.drawStringWithShadow(displayString, xPosition + (width - mc.fontRenderer.getStringWidth(displayString)) / 2, yPosition + (height - mc.fontRenderer.FONT_HEIGHT) / 2, color);
            }
        }

        @Override
        public boolean mousePressed(Minecraft mc, int x, int y)
        {
            if (super.mousePressed(mc, x, y) && parent.parent.attributes.get(parent.currIndex).tryUp(parent.player)) {
                return true;
            }
            return false;
        }
    }
}
