package mixac1.dangerrpg.client.gui;

import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.entity.LvlEAProvider;
import mixac1.dangerrpg.capability.RPGEntityData;
import mixac1.dangerrpg.client.gui.GuiInfoBookContentEntity.LevelUpButton;
import mixac1.dangerrpg.init.RPGKeyBinds;
import mixac1.dangerrpg.util.RPGHelper;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GuiInfoBook extends GuiScreen
{
    public static final ResourceLocation TEXTURE = new ResourceLocation(DangerRPG.MODID, "textures/gui/info_book.png");

    public EntityPlayer player;
    public EntityLivingBase target;
    public boolean isTargetPlayer;
    public List<LvlEAProvider> attributes;
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

        MovingObjectPosition mop = RPGHelper.getMouseOver(0, 10);
        if (mop != null && mop.entityHit != null && mop.entityHit instanceof EntityPlayer && RPGEntityData.isRPGEntity((EntityLivingBase) mop.entityHit)) {
            target = (EntityLivingBase) mop.entityHit;
            isTargetPlayer = true;
        }
        else {
            target = player;
            isTargetPlayer = true;
        }

        attributes = RPGEntityData.get(target).getLvlProviders();
    }

    @Override
    public void setWorldAndResolution(Minecraft mc, int width, int height)
    {
        super.setWorldAndResolution(mc, width, height);
        currContent = 0;

        EntityPlayer tmp = null;
        if (isTargetPlayer) {
            tmp = (EntityPlayer) target;
            stacks = new ItemStack[] {
                tmp.getCurrentEquippedItem(),
                tmp.getCurrentArmor(3),
                tmp.getCurrentArmor(2),
                tmp.getCurrentArmor(1),
                tmp.getCurrentArmor(0)
            };
        }
        else {
            stacks = new ItemStack[5];
        }

        offsetX = (width  - bookImageWidth)  / 2;
        offsetY = (height - bookImageHeight) / 2;

        content[0] = new GuiInfoBookContentEntity(mc, bookImageWidth - contentOffsetX * 2, 0, offsetY + contentOffsetY, contentSize, offsetX + contentOffsetX, this);
        for (int i = 1; i < content.length; ++i) {
            content[i] = new GuiInfoBookContentStack(mc, bookImageWidth - contentOffsetX * 2, 0, offsetY + contentOffsetY, contentSize, offsetX + contentOffsetX, this, stacks[i - 1]);
        }
        buttonList.add(button = new LevelUpButton(100, offsetX + contentOffsetX + GuiInfoBookContentEntity.butOffsetX, offsetY + contentOffsetY + contentSize + GuiInfoBookContentEntity.butOffsetY - GuiInfoBookContentEntity.imageHeight, (GuiInfoBookContentEntity) content[0]));
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
        GL11.glPushMatrix();

        offsetX = (width  - bookImageWidth)  / 2;
        offsetY = (height - bookImageHeight) / 2;

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(TEXTURE);
        drawTexturedModalRect(offsetX, offsetY, 0, 0, bookImageWidth, bookImageHeight);

        String title = Utils.toString(DangerRPG.trans("rpgstr.info_about"), " ", target.getCommandSenderName());
        fontRendererObj.drawStringWithShadow(title, offsetX + (bookImageWidth - fontRendererObj.getStringWidth(title)) / 2, offsetY + (titleHeight - fontRendererObj.FONT_HEIGHT) / 2 + 2, 0xffffff);

        content[currContent].drawScreen(mouseX, mouseY, par3);

        super.drawScreen(mouseX, mouseY, par3);

        GL11.glPopMatrix();
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
                enabled = id == 0 || id != 0 && stacks[id - 1] != null;
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
                    RenderHelper.enableGUIStandardItemLighting();
                    GuiScreen.itemRender.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.getTextureManager(), stacks[id - 1], xPosition + 2, yPosition + 2);
                    GuiScreen.itemRender.renderItemOverlayIntoGUI(mc.fontRenderer, mc.getTextureManager(), stacks[id - 1], xPosition + 2, yPosition + 2);
                    GL11.glEnable(GL11.GL_BLEND);
                }
                else {
                    drawTexturedModalRect(xPosition + 2, yPosition + 2, emptyIconOffsetU + (isTargetPlayer ? 0 : 16), emptyIconOffsetV + id * 16, 16, 16);
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
