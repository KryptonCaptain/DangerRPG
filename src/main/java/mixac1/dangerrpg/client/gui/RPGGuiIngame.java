package mixac1.dangerrpg.client.gui;

import org.lwjgl.opengl.GL11;

import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.entity.IRPGEntity;
import mixac1.dangerrpg.capability.RPGEntityProperties;
import mixac1.dangerrpg.capability.RPGableEntity;
import mixac1.dangerrpg.capability.ea.EntityAttributes;
import mixac1.dangerrpg.capability.ea.PlayerAttributes;
import mixac1.dangerrpg.capability.ia.ItemAttributes;
import mixac1.dangerrpg.client.gui.GuiMode.GuiModeType;
import mixac1.dangerrpg.hook.HookArmorSystem;
import mixac1.dangerrpg.init.RPGCapability;
import mixac1.dangerrpg.init.RPGConfig.ClientConfig;
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
    public static final RPGGuiIngame INSTANCE = new RPGGuiIngame();

    public Minecraft mc = Minecraft.getMinecraft();
    public FontRenderer fr = mc.fontRenderer;

    public static final ResourceLocation TEXTURE = new ResourceLocation(DangerRPG.MODID, "textures/gui/gui_in_game.png");

    private int textureWidth = 139;
    private int textureHeight = 59;

    private int part1OffsetX;
    private int part1OffsetY;
    private int part1OffsetU;
    private int part1OffsetV;
    private int part1Width;
    private int part1Height;

    private int part2OffsetX;
    private int part2OffsetY;
    private int part2OffsetU;
    private int part2OffsetV;
    private int part2Width;
    private int part2Height;

    private int part3OffsetX;
    private int part3OffsetY;
    private int part3OffsetU;
    private int part3OffsetV;
    private int part3Width;
    private int part3Height;

    private int barIconOffsetX;
    private int barIconOffsetY;
    private int barIconWidth;
    private int barIconHeight;
    private int barIconOffsetU;
    private int barIconOffsetV;

    private int barOffsetX;
    private int barOffsetY;
    private int barWidth;
    private int barHeight;
    private int barOffsetU;
    private int barOffsetV;

    private int chargeWidth;
    private int chargeHeight;
    private int chargeOffsetU;
    private int chargeOffsetV;

    private GuiModeType mode;

    public RPGGuiIngame()
    {
        update(GuiMode.curr());
    }

    public void update(GuiModeType type)
    {
        mode = type;
        if (!mode.isSimple) {
            part1OffsetX = 0;
            part1OffsetY = 0;
            part1OffsetU = 0;
            part1OffsetV = 0;
            part1Width = 36;
            part1Height = 40;

            part2OffsetX = 38;
            part2OffsetY = 2;
            part2OffsetU = 36;
            part2OffsetV = 0;
            part2Width = 101;
            part2Height = 16;

            part3OffsetX = 1;
            part3OffsetY = 39;
            part3OffsetU = 36;
            part3OffsetV = 16;
            part3Width = 34;
            part3Height = 13;

            barIconOffsetX = 41;
            barIconOffsetY = 20;
            barIconWidth = 10;
            barIconHeight = 10;
            barIconOffsetU = 165;
            barIconOffsetV = 0;

            barOffsetX = barIconOffsetX + 12;
            barOffsetY = barIconOffsetY + 2;
            barWidth = 81;
            barHeight = 5;
            barOffsetU = 175;
            barOffsetV = 0;

            chargeWidth = 101;
            chargeHeight = 5;
            chargeOffsetU = 0;
            chargeOffsetV = 68;
        }
        else {
            part2OffsetX = part1OffsetX;
            part2OffsetY = part1OffsetY;
            part3OffsetX = part2Width + 1;
            part3OffsetY = part2OffsetY + 1;

            barIconOffsetX = part1OffsetX;

            barOffsetX = barIconOffsetX + 12;
            barOffsetY = barIconOffsetY + 2;
        }
    }

    public void renderGameOverlay(ScaledResolution res)
    {
        mc.mcProfiler.startSection("rpgBar");
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);

        int width = res.getScaledWidth();
        int height = res.getScaledHeight();

        renderEntityBar(mc.thePlayer, ClientConfig.guiPlayerHUDOffsetX, ClientConfig.guiPlayerHUDOffsetY, ClientConfig.guiPlayerHUDIsInvert, res);
        renderChargeBar(ClientConfig.guiChargeIsCentered ? (width - chargeWidth) / 2 : ClientConfig.guiChargeOffsetX, height - ClientConfig.guiChargeOffsetY);
        renderEnemyBar(ClientConfig.guiEnemyHUDOffsetX, ClientConfig.guiEnemyHUDOffsetY, ClientConfig.guiEnemyHUDIsInvert, res);

        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glPopMatrix();
        mc.mcProfiler.endSection();
    }

    private void renderEntityBar(EntityLivingBase entity, int offsetX, int offsetY, boolean isInverted, ScaledResolution res)
    {
        this.isInvert = isInverted;
        float tmpX, tmpY;

        if (isInverted) {
            offsetX = res.getScaledWidth() - offsetX;
        }

        if (!mode.isSimple) {
            GuiInventory.func_147046_a(offsetX + invert(18), offsetY + 37, 16, invert(30), 00f, entity);

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            mc.getTextureManager().bindTexture(TEXTURE);

            drawTexturedModalRect(offsetX + invert(part1OffsetX), offsetY + part1OffsetY, part1OffsetU, part1OffsetV, part1Width, part1Height, isInverted);
            drawTexturedModalRect(offsetX + invert(part2OffsetX), offsetY + part2OffsetY, part2OffsetU, part2OffsetV, part2Width, part2Height, isInverted);
            drawTexturedModalRect(offsetX + invert(part3OffsetX), offsetY + part3OffsetY, part3OffsetU, part3OffsetV, part3Width, part3Height, isInverted);
        }
        else {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            mc.getTextureManager().bindTexture(TEXTURE);

            drawTexturedModalRect(offsetX + invert(part2OffsetX), offsetY + part2OffsetY, part2OffsetU, part2OffsetV, part2Width, part2Height, isInverted);
            drawTexturedModalRect(offsetX + invert(part3OffsetX), offsetY + part3OffsetY, part3OffsetU, part3OffsetV, part3Width, part3Height, isInverted);
        }

        int yFal = 0, proc;
        float curr, max;
        String s;

        IRPGEntity iRPG = null;
        if (RPGableEntity.isRPGable(entity)) {
            RPGEntityProperties data = RPGEntityProperties.get(entity);
            if (data != null && data.checkValid()) {
                iRPG = RPGCapability.rpgEntityRegistr.get(entity).rpgComponent;
            }
        }

        if (!(entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.isCreativeMode)) {

            boolean hasHealth = true;
            boolean hasMana = entity instanceof EntityPlayer;
            boolean hasArmor = entity instanceof EntityPlayer;
            boolean hasMeleeDamage = iRPG != null && iRPG.getEAMeleeDamage(entity) != null;
            boolean hasRangeDamage = iRPG != null && iRPG.getEARangeDamage(entity) != null;
            boolean hasFood = entity == mc.thePlayer && mc.thePlayer.getFoodStats().getFoodLevel() < 20;
            boolean hasAir = entity == mc.thePlayer && mc.thePlayer.getAir() < 300;

            int offsetHealth = 0, offsetMana = 0, offsetMeleeDmg = 0, offsetRangeDmg = 0;

            if (hasMana && hasHealth && ClientConfig.guiTwiceHealthManaBar) {
                drawTexturedModalRect(offsetX + invert(barIconOffsetX), offsetY + barIconOffsetY + yFal, barIconOffsetU, barIconOffsetV + barIconHeight * 2, barIconWidth, barIconHeight, isInverted);
                drawTexturedModalRect(offsetX + invert(barOffsetX), offsetY + barOffsetY + yFal - 2, barOffsetU, barOffsetV, barWidth, barHeight, isInverted);
                drawTexturedModalRect(offsetX + invert(barOffsetX), offsetY + barOffsetY + yFal + 2, barOffsetU, barOffsetV, barWidth, barHeight, isInverted);

                renderHealthBar(entity, offsetX, offsetY + yFal - 2, isInverted);
                renderManaBar(entity, offsetX, offsetY + yFal + 2, isInverted);

                offsetHealth = offsetMana = offsetY + barOffsetY + yFal;
                yFal += barIconHeight;
            }
            else {
                if (hasHealth) {
                    drawTexturedModalRect(offsetX + invert(barIconOffsetX), offsetY + barIconOffsetY + yFal, barIconOffsetU, barIconOffsetV + barIconHeight * 0, barIconWidth, barIconHeight, isInverted);
                    drawTexturedModalRect(offsetX + invert(barOffsetX), offsetY + barOffsetY + yFal, barOffsetU, barOffsetV, barWidth, barHeight, isInverted);
                    renderHealthBar(entity, offsetX, offsetY + yFal, isInverted);

                    offsetHealth = offsetY + barOffsetY + yFal;
                    yFal += barIconHeight;
                }
                if (hasMana) {
                    drawTexturedModalRect(offsetX + invert(barIconOffsetX), offsetY + barIconOffsetY + yFal, barIconOffsetU, barIconOffsetV + barIconHeight * 1, barIconWidth, barIconHeight, isInverted);
                    drawTexturedModalRect(offsetX + invert(barOffsetX), offsetY + barOffsetY + yFal, barOffsetU, barOffsetV, barWidth, barHeight, isInverted);
                    renderManaBar(entity, offsetX, offsetY + yFal, isInverted);

                    offsetMana = offsetY + barOffsetY + yFal;
                    yFal += barIconHeight;
                }
            }

            if (hasArmor) {
                drawTexturedModalRect(offsetX + invert(barIconOffsetX), offsetY + barIconOffsetY + yFal, barIconOffsetU, barIconOffsetV + barIconHeight * 3, barIconWidth, barIconHeight, isInverted);
                drawTexturedModalRect(offsetX + invert(barOffsetX), offsetY + barOffsetY + yFal - 2, barOffsetU, barOffsetV, barWidth, barHeight, isInverted);
                drawTexturedModalRect(offsetX + invert(barOffsetX), offsetY + barOffsetY + yFal + 2, barOffsetU, barOffsetV, barWidth, barHeight, isInverted);

                curr = HookArmorSystem.getTotalPhisicArmor();
                proc = getProcent(curr, 100F, barWidth);
                if (proc > 0) {
                    drawTexturedModalRect(offsetX + invert(barOffsetX), offsetY + barOffsetY + yFal - 2, barOffsetU, barOffsetV + barHeight * 6, proc, barHeight, isInverted);
                }

                curr = HookArmorSystem.getTotalMagicArmor();
                proc = getProcent(curr, 100F, barWidth);
                if (proc > 0) {
                    drawTexturedModalRect(offsetX + invert(barOffsetX), offsetY + barOffsetY + yFal + 2, barOffsetU, barOffsetV + barHeight * 7, proc, barHeight, isInverted);
                }

                yFal += barIconHeight;
            }


            if (hasMeleeDamage) {
                drawTexturedModalRect(offsetX + invert(barIconOffsetX), offsetY + barIconOffsetY + yFal, barIconOffsetU, barIconOffsetV + barIconHeight * 7, barIconWidth, barIconHeight, isInverted);

                offsetMeleeDmg = offsetY + barIconOffsetY + yFal;
                yFal += barIconHeight;
            }

            if (hasRangeDamage) {
                drawTexturedModalRect(offsetX + invert(barIconOffsetX), offsetY + barIconOffsetY + yFal, barIconOffsetU, barIconOffsetV + barIconHeight * 8, barIconWidth, barIconHeight, isInverted);

                offsetRangeDmg = offsetY + barIconOffsetY + yFal;
                yFal += barIconHeight;
            }

            if (hasFood && hasAir) {
                drawTexturedModalRect(offsetX + invert(barIconOffsetX), offsetY + barIconOffsetY + yFal, barIconOffsetU, barIconOffsetV + barIconHeight * 6, barIconWidth, barIconHeight, isInverted);
                if (mc.thePlayer.isPotionActive(Potion.hunger)) {
                    drawTexturedModalRect(offsetX + invert(barIconOffsetX), offsetY + barIconOffsetY + yFal, barIconOffsetU, barIconOffsetV + barIconHeight * 5, barIconWidth, barIconHeight, isInverted);
                }
                else {
                    drawTexturedModalRect(offsetX + invert(barIconOffsetX), offsetY + barIconOffsetY + yFal, barIconOffsetU, barIconOffsetV + barIconHeight * 4, barIconWidth, barIconHeight, isInverted);
                }
                renderFoodBar(entity, offsetX, offsetY + yFal - 2, isInverted);
                renderAirBar(entity, offsetX, offsetY + yFal + 2, isInverted);
                yFal += barIconHeight;
            }
            else {
                if (hasFood) {
                    if (mc.thePlayer.isPotionActive(Potion.hunger)) {
                        drawTexturedModalRect(offsetX + invert(barIconOffsetX), offsetY + barIconOffsetY + yFal, barIconOffsetU, barIconOffsetV + barIconHeight * 5, barIconWidth, barIconHeight, isInverted);
                    }
                    else {
                        drawTexturedModalRect(offsetX + invert(barIconOffsetX), offsetY + barIconOffsetY + yFal, barIconOffsetU, barIconOffsetV + barIconHeight * 4, barIconWidth, barIconHeight, isInverted);
                    }
                    renderFoodBar(entity, offsetX, offsetY + yFal, isInverted);
                    yFal += barIconHeight;
                }
                if (hasAir) {
                    drawTexturedModalRect(offsetX + invert(barIconOffsetX), offsetY + barIconOffsetY + yFal, barIconOffsetU, barIconOffsetV + barIconHeight * 6, barIconWidth, barIconHeight, isInverted);
                    renderAirBar(entity, offsetX, offsetY + yFal + 2, isInverted);
                    yFal += barIconHeight;
                }
            }

            if (mode.isDigital) {
                if (hasMana && hasHealth && ClientConfig.guiTwiceHealthManaBar) {
                    s = Utils.toString(genValueStr(entity.getHealth()), "/", genValueStr(PlayerAttributes.CURR_MANA.getValue(entity)));
                    fr.drawStringWithShadow(s, offsetX + getOffsetX(s, barOffsetX + barWidth + 4, isInverted), offsetHealth + (barIconHeight - fr.FONT_HEIGHT) / 2 - 1, 0xFFFFFF);
                }
                else {
                    if (hasHealth) {
                        s = genValueStr(entity.getHealth());
                        fr.drawStringWithShadow(s, offsetX + getOffsetX(s, barOffsetX + barWidth + 4, isInverted), offsetHealth + (barIconHeight - fr.FONT_HEIGHT) / 2 - 1, 0xFFFFFF);
                    }
                    if (hasMana) {
                        s = genValueStr(PlayerAttributes.CURR_MANA.getValue(entity));
                        fr.drawStringWithShadow(s, offsetX + getOffsetX(s, barOffsetX + barWidth + 4, isInverted), offsetMana + (barIconHeight - fr.FONT_HEIGHT) / 2 - 1, 0xFFFFFF);
                    }
                }
            }

            if (hasMeleeDamage) {
                s = genValueStr(iRPG.getEAMeleeDamage(entity).getValue(entity));
                fr.drawStringWithShadow(s, offsetX + getOffsetX(s, barOffsetX, isInverted), offsetMeleeDmg + (barIconHeight - fr.FONT_HEIGHT) / 2 + 1, 0xFFFFFF);
            }

            if (hasRangeDamage) {
                s = genValueStr(iRPG.getEARangeDamage(entity).getValue(entity));
                fr.drawStringWithShadow(s, offsetX + getOffsetX(s, barOffsetX, isInverted), offsetRangeDmg + (barIconHeight - fr.FONT_HEIGHT) / 2 + 1, 0xFFFFFF);
            }
        }

        // T0D0: name
        {
            s = entity.getCommandSenderName();
            fr.drawStringWithShadow(s, offsetX + getOffsetX(s, part2OffsetX + 1, part2Width - 6, isInverted), offsetY + part2OffsetY + (part2Height - fr.FONT_HEIGHT) / 2 + 2, 0xFFFFFF);
        }

        // T0D0: lvl
        if (iRPG != null) {
            s = String.valueOf((int) EntityAttributes.LVL.getValue(entity));
            fr.drawStringWithShadow(s, offsetX + getOffsetX(s, part3OffsetX, part3Width, isInverted), offsetY + part3OffsetY + (part3Height - fr.FONT_HEIGHT) / 2 + 1, 0xFFFFFF);
        }
    }

    private void renderHealthBar(EntityLivingBase entity, int offsetX, int offsetY, boolean isInverted)
    {
        float curr = entity.getHealth();
        float absorbHealth = entity.getAbsorptionAmount();
        float max = entity.getMaxHealth() + absorbHealth;
        if (max > 0) {
            int proc = getProcent(curr, max, barWidth);
            int procAbsorb = getProcent(absorbHealth, max, barWidth);
            if (entity.isPotionActive(Potion.wither)) {
                drawTexturedModalRect(offsetX + invert(barOffsetX), offsetY + barOffsetY, barOffsetU, barOffsetV + barHeight * 4, barWidth, barHeight, isInverted);
            }
            else {
                if (proc > 0) {
                    if (entity.isPotionActive(Potion.poison)) {
                        drawTexturedModalRect(offsetX + invert(barOffsetX), offsetY + barOffsetY, barOffsetU, barOffsetV + barHeight * 3, proc, barHeight, isInverted);
                    }
                    else {
                        drawTexturedModalRect(offsetX + invert(barOffsetX), offsetY + barOffsetY, barOffsetU, barOffsetV + barHeight * 1, proc, barHeight, isInverted);
                    }
                }
                if (procAbsorb > 0) {
                    drawTexturedModalRect(offsetX + invert(barOffsetX) + proc, offsetY + barOffsetY, barOffsetU + proc, barOffsetV + barHeight * 2, procAbsorb, barHeight, isInverted);
                }
            }
        }
    }

    private void renderManaBar(EntityLivingBase entity, int offsetX, int offsetY, boolean isInverted)
    {
        float curr = PlayerAttributes.CURR_MANA.getValue(entity);
        float max = PlayerAttributes.MANA.getValue(entity);
        if (max > 0) {
            int proc = getProcent(curr, max, barWidth);
            if (proc > 0) {
                drawTexturedModalRect(offsetX + invert(barOffsetX), offsetY + barOffsetY, barOffsetU, barOffsetV + barHeight * 5, proc, barHeight, isInverted);
            }
        }
    }

    private void renderFoodBar(EntityLivingBase entity, int offsetX, int offsetY, boolean isInverted)
    {
        float curr = mc.thePlayer.getFoodStats().getFoodLevel();
        int proc = getProcent(curr, 20F, barWidth);
        if (curr < 20) {
            drawTexturedModalRect(offsetX + invert(barOffsetX), offsetY + barOffsetY, barOffsetU, barOffsetV, barWidth, barHeight, isInverted);
            if (mc.thePlayer.isPotionActive(Potion.hunger)) {
                drawTexturedModalRect(offsetX + invert(barOffsetX), offsetY + barOffsetY, barOffsetU, barOffsetV + barHeight * 9, proc, barHeight, isInverted);
            }
            else {
                drawTexturedModalRect(offsetX + invert(barOffsetX), offsetY + barOffsetY, barOffsetU, barOffsetV + barHeight * 8, proc, barHeight, isInverted);
            }
        }
    }

    private void renderAirBar(EntityLivingBase entity, int offsetX, int offsetY, boolean isInverted)
    {
        float curr = mc.thePlayer.getAir();
        int proc = getProcent(curr, 300F, barWidth);
        if (curr < 300) {
            drawTexturedModalRect(offsetX + invert(barOffsetX), offsetY + barOffsetY, barOffsetU, barOffsetV, barWidth, barHeight, isInverted);
            drawTexturedModalRect(offsetX + invert(barOffsetX), offsetY + barOffsetY, barOffsetU, barOffsetV + barHeight * 10, proc, barHeight, isInverted);
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

    private void renderEnemyBar(int offsetX, int offsetY, boolean isInverted, ScaledResolution res)
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

    private String genValueStr(float value)
    {
        if (value < 100) {
            return String.format("%.1f", value);
        }
        else if (value < 1000) {
            return String.format("%d", Math.round(value));
        }
        else if (value < 10000) {
            return String.format("%.2fK", Math.round(value) / 1000f);
        }
        else if (value < 100000) {
            return String.format("%.1fK", Math.round(value) / 1000f);
        }
        else if (value < 1000000) {
            return String.format("%dK", Math.round(value) / 1000);
        }
        else if (value < 10000) {
            return String.format("%.2fM", Math.round(value) / 1000000f);
        }
        else if (value < 100000) {
            return String.format("%.1fM", Math.round(value) / 1000000f);
        }
        else if (value < 1000000) {
            return String.format("%dM", Math.round(value) / 1000000);
        }
        else {
            return String.format("%dM", Math.round(value) / 1000000);
        }
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
