package mixac1.dangerrpg.client.gui;

import org.lwjgl.opengl.GL11;

import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.entity.IRPGEntity;
import mixac1.dangerrpg.capability.RPGEntityData;
import mixac1.dangerrpg.capability.ea.EntityAttributes;
import mixac1.dangerrpg.capability.ea.PlayerAttributes;
import mixac1.dangerrpg.capability.ia.ItemAttributes;
import mixac1.dangerrpg.hook.HookArmorSystem;
import mixac1.dangerrpg.init.RPGCapability;
import mixac1.dangerrpg.util.RPGHelper;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;

public class RPGGuiIngame extends Gui
{
    public static Minecraft mc = Minecraft.getMinecraft();
    public static FontRenderer fr = mc.fontRenderer;
    public ScaledResolution res;

    public static final ResourceLocation TEXTURE = new ResourceLocation(DangerRPG.MODID, "textures/gui/gui_in_game.png");

    private static int textureWidth = 139;
    private static int textureHeight = 59;

    private static int barOffsetX = 42;
    private static int barOffsetY = 20;
    private static int barWidth = 93;
    private static int barHeight = 10;
    private static int barOffsetU = 163;
    private static int barOffsetV = 69;

    private static int healthBarOffsetX = 54;
    private static int healthBarOffsetY = 22;
    private static int healthBarIndent = 10;
    private static int healthBarWidth = 81;
    private static int healthBarHeight = 5;
    private static int healthBarOffsetU = 175;
    private static int healthBarOffsetV = 0;

    private static int hungerIconOffsetX = 6;
    private static int hungerIconOffsetY = 54;
    private static int hungerBarOffsetX = hungerIconOffsetX + 2;
    private static int hungerBarOffsetY = hungerIconOffsetY + 11;
    private static int hungerBarIndent = 14;
    private static int hungerBarWidth = 5;
    private static int hungerBarHeight = 31;
    private static int hungerBarOffsetU = 175;
    private static int hungerBarOffsetV = 35;
    private static int hungerIconOffsetU = 195;
    private static int hungerIconOffsetV = 35;
    private static int hungerIconWidth = 9;
    private static int hungerIconHeight = 9;

    private static int line1OffsetX = 39;
    private static int line1OffsetY = 3;
    private static int line1Width = 101;
    private static int line1Height = 16;

    private static int line2OffsetX = 2;
    private static int line2OffsetY = 40;
    private static int line2Width = 34;
    private static int line2Height = 13;

    private static int chargeWidth = 101;
    private static int chargeHeight = 5;
    private static int chargeOffsetU = 0;
    private static int chargeOffsetV = 68;

    public void renderGameOverlay()
    {
        if (mc.playerController.gameIsSurvivalOrAdventure() || true) {
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_ALPHA_TEST);

            res = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
            int width = res.getScaledWidth();
            int height = res.getScaledHeight();

            renderEntityBar(mc.thePlayer, 10, 10, false, res);
            renderChargeBar((width - chargeWidth) / 2, height - 40 - chargeHeight);
            renderEnemyBar(10, 10, res);

            renderTestString(250, 10,
                    "");

            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glEnable(GL11.GL_BLEND);
        }
    }

    private void renderEntityBar(EntityLivingBase entity, int offsetX, int offsetY, boolean isInverted, ScaledResolution res)
    {
        this.isInvert = isInverted;
        float tmpX, tmpY;

        if (isInverted) {
            offsetX = res.getScaledWidth() - offsetX;
        }

        GuiInventory.func_147046_a(offsetX + invert(18), offsetY + 37, 16, invert(30), 00f, entity);

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(TEXTURE);
        drawTexturedModalRect(offsetX, offsetY, 0, 0, textureWidth, textureHeight, isInverted);

        int yFal = 0, proc;
        float curr, max;
        String s;

        IRPGEntity iRPG = null;
        if (RPGEntityData.isRPGEntity(entity)) {
            RPGEntityData data = RPGEntityData.get(entity);
            if (data != null && data.checkValid()) {
                iRPG = RPGCapability.rpgEntityRegistr.getAttributesSet(entity).rpgComponent;
            }
        }

        if (!(entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.isCreativeMode)) {

            // T0D0: health
            {
                drawTexturedModalRect(offsetX + invert(barOffsetX), offsetY + barOffsetY + yFal, barOffsetU, barOffsetV, barWidth, barHeight, isInverted);

                curr = entity.getHealth();
                float absorbHealth = entity.getAbsorptionAmount();
                max = entity.getMaxHealth() + absorbHealth;
                if (max > 0) {
                    proc = getProcent(curr, max, healthBarWidth);
                    int procAbsorb = getProcent(absorbHealth, max, healthBarWidth);
                    if (entity.isPotionActive(Potion.wither)) {
                        drawTexturedModalRect(offsetX + invert(healthBarOffsetX), offsetY + healthBarOffsetY + yFal, healthBarOffsetU, healthBarOffsetV + healthBarHeight * 3, healthBarWidth, healthBarHeight, isInverted);
                    }
                    else {
                        if (proc > 0) {
                            if (entity.isPotionActive(Potion.poison)) {
                                drawTexturedModalRect(offsetX + invert(healthBarOffsetX), offsetY + healthBarOffsetY + yFal, healthBarOffsetU, healthBarOffsetV + healthBarHeight * 2, proc, healthBarHeight, isInverted);
                            }
                            else {
                                drawTexturedModalRect(offsetX + invert(healthBarOffsetX), offsetY + healthBarOffsetY + yFal, healthBarOffsetU, healthBarOffsetV, proc, healthBarHeight, isInverted);
                            }
                        }
                        if (procAbsorb > 0) {
                            drawTexturedModalRect(offsetX + invert(healthBarOffsetX) + proc, offsetY + healthBarOffsetY + yFal, healthBarOffsetU + proc, healthBarOffsetV + healthBarHeight, procAbsorb, healthBarHeight, isInverted);
                        }
                    }
                }

                yFal += barHeight;
            }

            // T0D0: mana
            if (entity instanceof EntityPlayer) {
                drawTexturedModalRect(offsetX + invert(barOffsetX), offsetY + barOffsetY + yFal, barOffsetU, barOffsetV + barHeight, barWidth, barHeight, isInverted);

                curr = PlayerAttributes.CURR_MANA.getValue(entity);
                max = PlayerAttributes.MANA.getValue(entity);
                if (max > 0) {
                    proc = getProcent(curr, max, healthBarWidth);
                    if (proc > 0) {
                        drawTexturedModalRect(offsetX + invert(healthBarOffsetX), offsetY + healthBarOffsetY + yFal, healthBarOffsetU, healthBarOffsetV + healthBarHeight * 4, proc, healthBarHeight, isInverted);
                    }
                }

                yFal += barHeight;
            }

            // T0D0: phisicArmor
            if (entity instanceof EntityPlayer) {
                drawTexturedModalRect(offsetX + invert(barOffsetX), offsetY + barOffsetY + yFal, barOffsetU, barOffsetV + barHeight * 2, barWidth, barHeight, isInverted);

                curr = HookArmorSystem.getTotalPhisicArmor();
                proc = getProcent(curr, 100F, healthBarWidth);
                if (proc > 0) {
                    drawTexturedModalRect(offsetX + invert(healthBarOffsetX), offsetY + healthBarOffsetY + yFal, healthBarOffsetU, healthBarOffsetV + healthBarHeight * 5, proc, healthBarHeight, isInverted);
                }

                yFal += barHeight;
            }

            // T0D0: magicArmor
            if (entity instanceof EntityPlayer) {
                drawTexturedModalRect(offsetX + invert(barOffsetX), offsetY + barOffsetY + yFal, barOffsetU, barOffsetV + barHeight * 3, barWidth, barHeight, isInverted);

                curr = HookArmorSystem.getTotalMagicArmor();
                proc = getProcent(curr, 100F, healthBarWidth);
                if (proc > 0) {
                    drawTexturedModalRect(offsetX + invert(healthBarOffsetX), offsetY + healthBarOffsetY + yFal, healthBarOffsetU, healthBarOffsetV + healthBarHeight * 6, proc, healthBarHeight, isInverted);
                }

                yFal += barHeight;
            }

            // T0D0: melee damage
            if (iRPG != null && iRPG.getEAMeleeDamage(entity) != null) {
                drawTexturedModalRect(offsetX + invert(barOffsetX), offsetY + barOffsetY + yFal, barOffsetU, barOffsetV + barHeight * 4, barWidth, barHeight, isInverted);

                yFal += barHeight;
            }

            // T0D0: range damage
            if (iRPG != null && iRPG.getEARangeDamage(entity) != null) {
                drawTexturedModalRect(offsetX + invert(barOffsetX), offsetY + barOffsetY + yFal, barOffsetU, barOffsetV + barHeight * 5, barWidth, barHeight, isInverted);

                yFal += barHeight;
            }

            // T0D0: food
            if (entity == mc.thePlayer) {
                curr = mc.thePlayer.getFoodStats().getFoodLevel();
                proc = getProcent(curr, 20F, hungerBarHeight);
                if (curr < 20) {
                    drawTexturedModalRect(offsetX + invert(hungerBarOffsetX), offsetY + hungerBarOffsetY, hungerBarOffsetU, hungerBarOffsetV, hungerBarWidth, hungerBarHeight, isInverted);
                    if (mc.thePlayer.isPotionActive(Potion.hunger)) {
                        drawTexturedModalRect(offsetX + invert(hungerIconOffsetX), offsetY + hungerIconOffsetY, hungerIconOffsetU + hungerIconWidth, hungerIconOffsetV, hungerIconWidth, hungerIconHeight, isInverted);
                        drawTexturedModalRect(offsetX + invert(hungerBarOffsetX), offsetY + hungerBarOffsetY, hungerBarOffsetU + hungerBarWidth * 2, hungerBarOffsetV, hungerBarWidth, proc, isInverted);
                    }
                    else {
                        drawTexturedModalRect(offsetX + invert(hungerIconOffsetX), offsetY + hungerIconOffsetY, hungerIconOffsetU, hungerIconOffsetV, hungerIconWidth, hungerIconHeight, isInverted);
                        drawTexturedModalRect(offsetX + invert(hungerBarOffsetX), offsetY + hungerBarOffsetY, hungerBarOffsetU + hungerBarWidth, hungerBarOffsetV, hungerBarWidth, proc, isInverted);
                    }
                }
            }

            // T0D0: air
            if (entity == mc.thePlayer) {
                curr = mc.thePlayer.getAir();
                proc = getProcent(curr, 300F, hungerBarHeight);
                if (curr < 300) {
                    drawTexturedModalRect(offsetX + invert(hungerIconOffsetX + hungerBarIndent), offsetY + hungerIconOffsetY, hungerIconOffsetU + hungerIconWidth * 2, hungerIconOffsetV, hungerIconWidth, hungerIconHeight, isInverted);
                    drawTexturedModalRect(offsetX + invert(hungerBarOffsetX + hungerBarIndent), offsetY + hungerBarOffsetY, hungerBarOffsetU, hungerBarOffsetV, hungerBarWidth, hungerBarHeight, isInverted);
                    drawTexturedModalRect(offsetX + invert(hungerBarOffsetX + hungerBarIndent), offsetY + hungerBarOffsetY, hungerBarOffsetU + hungerBarWidth * 3, hungerBarOffsetV, hungerBarWidth, proc, isInverted);
                }
            }

            // T0D0: heath value
            {
                s = String.format("%.1f", entity.getHealth());
                fr.drawStringWithShadow(s, offsetX + getOffsetX(s, healthBarOffsetX + healthBarWidth + 4, isInverted), offsetY + healthBarOffsetY + (healthBarHeight - fr.FONT_HEIGHT) / 2 + 1, 0xFFFFFF);
            }

            // T0D0: mana value
            if (entity instanceof EntityPlayer) {
                s = PlayerAttributes.CURR_MANA.getDisplayValue(entity);
                fr.drawStringWithShadow(s, offsetX + getOffsetX(s, healthBarOffsetX + healthBarWidth + 4, isInverted), offsetY + healthBarOffsetY + barHeight + (healthBarHeight - fr.FONT_HEIGHT) / 2 + 1, 0xFFFFFF);
            }

            // T0D0: melee damage value
            if (iRPG != null && iRPG.getEAMeleeDamage(entity) != null) {
                s = iRPG.getEAMeleeDamage(entity).getDisplayValue(entity);
                fr.drawStringWithShadow(s, offsetX + getOffsetX(s, healthBarOffsetX, isInverted), offsetY + healthBarOffsetY + barHeight * 1 + (healthBarHeight - fr.FONT_HEIGHT) / 2 + 1, 0xFFFFFF);
            }

            // T0D0: range damage value
            if (iRPG != null && iRPG.getEARangeDamage(entity) != null) {
                s = iRPG.getEARangeDamage(entity).getDisplayValue(entity);
                fr.drawStringWithShadow(s, offsetX + getOffsetX(s, healthBarOffsetX, isInverted), offsetY + healthBarOffsetY + barHeight * 2 + (healthBarHeight - fr.FONT_HEIGHT) / 2 + 1, 0xFFFFFF);
            }

        }

        // T0D0: name
        {
            s = entity.getCommandSenderName();
            fr.drawStringWithShadow(s, offsetX + getOffsetX(s, line1OffsetX, line1Width - 6, isInverted), offsetY + line1OffsetY + (line1Height - fr.FONT_HEIGHT) / 2, 0xFFFFFF);
        }

        // T0D0: lvl
        if (iRPG != null) {
            s = String.valueOf((int) EntityAttributes.LVL.getValue(entity));
            fr.drawStringWithShadow(s, offsetX + getOffsetX(s, line2OffsetX, line2Width, isInverted), offsetY + line2OffsetY + (line2Height - fr.FONT_HEIGHT) / 2, 0xFFFFFF);
        }
    }

    public void renderChargeBar(int offsetX, int offsetY)
    {
        ItemStack stack;
        if ((stack = mc.thePlayer.getItemInUse()) != null && stack.getItemUseAction() == EnumAction.bow && ItemAttributes.SHOT_SPEED.hasIt(stack)) {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            mc.getTextureManager().bindTexture(TEXTURE);
            drawTexturedModalRect(offsetX, offsetY, chargeOffsetU, chargeOffsetV, chargeWidth, chargeHeight);

            int useDuration = mc.thePlayer.getItemInUseDuration();
            float maxCharge = ItemAttributes.SHOT_SPEED.get(stack, mc.thePlayer);
            float power = useDuration / maxCharge;
            power = (power * power + power * 2.0F) / 3.0F;
            int proc = getProcent(power, 1f, chargeWidth);
            if (proc > 0) {
                drawTexturedModalRect(offsetX, offsetY, chargeOffsetU, chargeOffsetV + chargeHeight, proc, chargeHeight);
            }

            if (ItemAttributes.MIN_CUST_TIME.hasIt(stack)) {
                float tmp = ItemAttributes.MIN_CUST_TIME.get(stack, mc.thePlayer);
                proc = getProcent(maxCharge * tmp, maxCharge, chargeWidth);
                drawTexturedModalRect(offsetX + proc, offsetY, chargeOffsetU, chargeOffsetV + chargeHeight * 2, 1, chargeHeight);
            }
        }
    }

    private void renderEnemyBar(int offsetX, int offsetY, ScaledResolution res)
    {
        MovingObjectPosition mop = RPGHelper.getMouseOver(0, 10);
        if (mop != null && mop.entityHit != null) {
            if (mop.entityHit instanceof EntityDragonPart) {
                if (((EntityDragonPart) mop.entityHit).entityDragonObj instanceof EntityDragon) {
                    renderEntityBar((EntityDragon) ((EntityDragonPart) mop.entityHit).entityDragonObj, offsetX, offsetY, true, res);
                }
            }
            else if (mop.entityHit instanceof EntityLivingBase) {
                renderEntityBar((EntityLivingBase) mop.entityHit, offsetX, offsetY, true, res);
            }
        }
    }

    private int getProcent(float curr, float max, int width)
    {
        int value = (int) (curr /  max * width);
        return value <= 1 && curr != 0 ? 1 : value > width ? width : value;
    }

    private boolean isInvert = true;

    private double invert(double value)
    {
        return Utils.invert(value, isInvert);
    }

    private int invert(int value)
    {
        return (int) Utils.invert(value, isInvert);
    }

    private int getOffsetX(String s, int offset, int width, boolean isInverted)
    {
        s = fr.trimStringToWidth(s, width);
        int size = fr.getStringWidth(s);
        int value = (offset + (width - size) / 2);
        if (isInverted) {
            return - (value + size);
        }
        return value;
    }

    private int getOffsetX(String s, int offset, boolean isInverted)
    {
        if (isInverted) {
            int size = fr.getStringWidth(s);
            return - (offset + size);
        }
        return offset;
    }

    private void renderTestString(int x, int y, Object... str)
    {
        int i = 0;
        for (Object tmp : str) {
            fr.drawStringWithShadow(tmp.toString(), x, y + fr.FONT_HEIGHT * i++, 0xffffff);
        }
    }

    public void drawTexturedModalRect(int x, int y, int u, int v, int width, int heght, boolean isInverted)
    {
        float f = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();

        if (!isInverted) {
            tessellator.addVertexWithUV(x + 0,      y + heght,  this.zLevel, (u + 0) * f,       (v + heght) * f);
            tessellator.addVertexWithUV(x + width,  y + heght,  this.zLevel, (u + width) * f,   (v + heght) * f);
            tessellator.addVertexWithUV(x + width,  y + 0,      this.zLevel, (u + width) * f,   (v + 0) * f);
            tessellator.addVertexWithUV(x + 0,      y + 0,      this.zLevel, (u + 0) * f,       (v + 0) * f);
        }
        else {
            tessellator.addVertexWithUV(x - width,  y + heght,  this.zLevel, (u + width) * f,   (v + heght) * f);
            tessellator.addVertexWithUV(x + 0,      y + heght,  this.zLevel, (u + 0) * f,       (v + heght) * f);
            tessellator.addVertexWithUV(x + 0,      y + 0,      this.zLevel, (u + 0) * f,       (v + 0) * f);
            tessellator.addVertexWithUV(x - width,  y + 0,      this.zLevel, (u + width) * f,   (v + 0) * f);
        }

        tessellator.draw();
    }
}
