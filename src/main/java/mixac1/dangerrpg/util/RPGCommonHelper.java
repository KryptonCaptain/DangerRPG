package mixac1.dangerrpg.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import mixac1.dangerrpg.api.item.ILvlableItem;
import mixac1.dangerrpg.api.item.ILvlableItem.ILvlableItemArmor;
import mixac1.dangerrpg.api.item.ILvlableItem.ILvlableItemTool;
import mixac1.dangerrpg.capability.LvlableItem;
import mixac1.dangerrpg.capability.RPGEntityData;
import mixac1.dangerrpg.capability.ea.PlayerAttributes;
import mixac1.dangerrpg.capability.ia.ItemAttributes;
import mixac1.dangerrpg.init.RPGCapability;
import mixac1.dangerrpg.item.IMaterialSpecial;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public abstract class RPGCommonHelper
{
    public static void knockBack(EntityLivingBase entityliving, EntityLivingBase attacker, float knockback)
    {
        double i = Math.sqrt(knockback);
        double x = -MathHelper.sin(attacker.rotationYaw / 180.0F * (float)Math.PI)   * 0.4;
        double z =  MathHelper.cos(attacker.rotationYaw / 180.0F * (float)Math.PI)   * 0.4;
        double y = -MathHelper.sin(attacker.rotationPitch / 180.0F * (float)Math.PI) * 0.1;
        entityliving.addVelocity(x * i, y * i, z * i);
    }

    public static void rebuildPlayerExp(EntityPlayer player)
    {
        int lvl = player.experienceLevel;
        int exp = (int) (player.xpBarCap() * player.experience);
        player.experience = 0.0F;
        player.experienceTotal = 0;
        player.experienceLevel = 0;
        for (int i = 0; i < lvl; ++i) {
            player.addExperience(player.xpBarCap());
        }
        player.addExperience(exp);
    }

    public static void rebuildPlayerLvl(EntityPlayer player)
    {
        int exp = player.experienceTotal;
        player.experience = 0.0F;
        player.experienceTotal = 0;
        player.experienceLevel = 0;
        player.addExperience(exp);
    }

//    @SideOnly(Side.CLIENT)
//    public static void wheelScroll(int value)
//    {
//        if (value == 0) {
//            return;
//        }
//
//        if (value > 0) {
//            value = 1;
//        }
//
//        if (value < 0) {
//            value = -1;
//        }
//
//        for (i -= value; i < 0; i += 9) {
//            ;
//        }
//
//        while (i >= 9)
//        {
//            i -= 9;
//        }
//    }

    public static MovingObjectPosition getMouseOver(float frame, float dist)
    {
        Minecraft mc = Minecraft.getMinecraft();
        MovingObjectPosition mop = null;
        if (mc.renderViewEntity != null) {
            if (mc.theWorld != null) {
                mop = mc.renderViewEntity.rayTrace(dist, frame);
                Vec3 pos = mc.renderViewEntity.getPosition(frame);
                double calcDist = dist;
                if (mop != null) {
                    calcDist = mop.hitVec.distanceTo(pos);
                }

                Vec3 look = mc.renderViewEntity.getLook(frame);
                look = Vec3.createVectorHelper(look.xCoord * dist, look.yCoord * dist, look.zCoord * dist);
                Vec3 vec = pos.addVector(look.xCoord, look.yCoord, look.zCoord);
                Entity pointedEntity = null;
                @SuppressWarnings("unchecked")
                List<Entity> list = mc.theWorld.getEntitiesWithinAABBExcludingEntity(mc.renderViewEntity, mc.renderViewEntity.boundingBox.addCoord(look.xCoord, look.yCoord, look.zCoord).expand(1.0F, 1.0F, 1.0F));
                double d = calcDist;

                for (Entity entity : list) {
                    if (entity.canBeCollidedWith()) {
                        float borderSize = entity.getCollisionBorderSize();
                        AxisAlignedBB aabb = entity.boundingBox.expand(borderSize, borderSize, borderSize);
                        MovingObjectPosition mop0 = aabb.calculateIntercept(pos, vec);

                        if (aabb.isVecInside(pos)) {
                            if (0.0D <= d) {
                                pointedEntity = entity;
                                d = 0.0D;
                            }
                        }
                        else if (mop0 != null) {
                            double d1 = pos.distanceTo(mop0.hitVec);
                            if (d1 < d || d == 0.0D) {
                                pointedEntity = entity;
                                d = d1;
                            }
                        }
                    }
                }

                if (pointedEntity != null && (d < calcDist || mop == null)) {
                    mop = new MovingObjectPosition(pointedEntity);
                }
            }
        }
        return mop;
    }

    public static float getUsePower(EntityPlayer player, ItemStack stack, int useDuration, float defMaxPow, float defMinPow)
    {
        float power = getUsePower(player, stack, useDuration, defMaxPow);

        float minPower = ItemAttributes.MIN_CUST_TIME.hasIt(stack) ? ItemAttributes.MIN_CUST_TIME.get(stack, player) : defMinPow;
        if (power < minPower) {
            return -1f;
        }
        return power;
    }

    public static float getUsePower(EntityPlayer player, ItemStack stack, int useDuration, float defMaxPow)
    {
        float power = useDuration / (ItemAttributes.SHOT_SPEED.hasIt(stack) ? ItemAttributes.SHOT_SPEED.get(stack, player) : defMaxPow);
        power = (power * power + power * 2.0F) / 3.0F;

        if (power > 1.0F) {
            return 1f;
        }
        return power;
    }

    public static IMaterialSpecial getMaterialSpecial(ItemStack stack)
    {
        if (stack != null && LvlableItem.isLvlable(stack)) {
            ILvlableItem ilvl = RPGCapability.lvlItemRegistr.data.get(stack.getItem()).lvlComponent;
            if (ilvl instanceof ILvlableItemArmor) {
                return ((ILvlableItemArmor) ilvl).getArmorMaterial(stack.getItem());
            }
            else if (ilvl instanceof ILvlableItemTool) {
                return ((ILvlableItemTool) ilvl).getToolMaterial(stack.getItem());
            }
        }
        return null;
    }

    public static int getSpecialColor(ItemStack stack, int defaultColor)
    {
        IMaterialSpecial mat = getMaterialSpecial(stack);
        if (mat != null && mat.hasSpecialColor()) {
            return mat.getSpecialColor();
        }
        return defaultColor;
    }

    public static Vec3 getFirePoint(EntityLivingBase thrower)
    {
        Vec3 tmp = thrower.getLookVec();

        tmp.xCoord /= 2;
        tmp.yCoord /= 2;
        tmp.zCoord /= 2;

        tmp.xCoord += thrower.posX;
        tmp.yCoord += thrower.posY + thrower.getEyeHeight();
        tmp.zCoord += thrower.posZ;

        tmp.xCoord -= MathHelper.cos(thrower.rotationYaw / 180.0F * (float)Math.PI) * 0.22F;
        tmp.yCoord -= 0.3;
        tmp.zCoord -= MathHelper.sin(thrower.rotationYaw / 180.0F * (float)Math.PI) * 0.22F;

        return tmp;
    }

    public static boolean spendMana(EntityPlayer player, float mana)
    {
        if (!player.capabilities.isCreativeMode) {
            if (PlayerAttributes.CURR_MANA.getValue(player) >= mana) {
                PlayerAttributes.CURR_MANA.addValue(-mana, player);
                return true;
            }
            return false;
        }
        return true;
    }

    public static ArrayList<String> getItemNames(Set<Item> items, boolean needSort)
    {
        ArrayList<String> names = new ArrayList<String>();
        for (Item item : items) {
            names.add(item.unlocalizedName);
        }
        if (needSort) {
            Collections.sort(names);
        }
        return names;
    }

    public static ArrayList<String> getEntityNames(Set<Class<? extends EntityLivingBase>> set, boolean needSort)
    {
        ArrayList<String> names = new ArrayList<String>();
        for (Class item : set) {
            if (EntityList.classToStringMapping.containsKey(item)) {
                names.add((String) EntityList.classToStringMapping.get(item));
            }
        }
        if (needSort) {
            Collections.sort(names);
        }
        return names;
    }

    public static float getMeleeDamageHook(EntityLivingBase entity, float defaultDamage)
    {
        if (RPGEntityData.isRPGEntity(entity)) {
            return RPGCapability.rpgEntityRegistr.getAttributesSet(entity).rpgComponent.getEAMeleeDamage(entity).getValue(entity);
        }
        return defaultDamage;
    }

    public static float getRangeDamageHook(EntityLivingBase entity, float defaultDamage)
    {
        if (RPGEntityData.isRPGEntity(entity)) {
            return RPGCapability.rpgEntityRegistr.getAttributesSet(entity).rpgComponent.getEARangeDamage(entity).getValue(entity);
        }
        return defaultDamage;
    }
}

//if (worldObj.isRemote) {
//    int color = getColor();
//    double r = 0.3D;
//    double frec = Math.PI / 6;
//    double x, y, z, tmp;
//
//    for (double k = 0; k < Math.PI * 2; k += frec) {
//        y = posY + r * Math.cos(k);
//        tmp = Math.abs(r * Math.sin(k));
//        for (double l = 0; l < Math.PI * 2; l += frec) {
//            x = posX + tmp * Math.cos(l);
//            z = posZ + tmp * Math.sin(l);
//            DangerRPG.proxy.spawnEntityFX(RPGEntityFXManager.EntityAuraFXE, x, y, z, motionX, motionY, motionZ, color);
//        }
//    }
//}

//for (float l = 0F; l < 2 * Math.PI; l += Math.PI / 6) {
//    double px = entity.posX + radius * Math.cos(l);
//    double py = entity.posZ + radius * Math.sin(l);
//    Minecraft.getMinecraft().effectRenderer.addEffect(new EntitySplashFX(Minecraft.getMinecraft().theWorld,
//            px, entity.posY + 1.5D, py, 0F, 0.4F, 0F));
//}
