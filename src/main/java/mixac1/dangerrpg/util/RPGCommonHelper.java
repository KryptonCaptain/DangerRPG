package mixac1.dangerrpg.util;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.capability.ia.ItemAttributes;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public abstract class RPGCommonHelper
{
    static int i = 0;

    public static void knockBack(EntityLivingBase entityliving, EntityLivingBase attacker, float knockback)
    {
        double i = Math.sqrt(knockback);
        double x = -MathHelper.sin(attacker.rotationYaw / 180.0F * (float)Math.PI)   * 0.4;
        double z =  MathHelper.cos(attacker.rotationYaw / 180.0F * (float)Math.PI)   * 0.4;
        double y = -MathHelper.sin(attacker.rotationPitch / 180.0F * (float)Math.PI) * 0.1;
        entityliving.addVelocity(x * i, y * i, z * i);
    }

    public static float applyArmorCalculations(EntityLivingBase entity, DamageSource source, float damage)
    {
        if (!source.isUnblockable()) {
            if (entity instanceof EntityPlayer) {
                ((EntityPlayer) entity).inventory.damageArmor(damage);
            }
            damage *= (100F - RPGCommonHelper.calcTotalPhisicResistance(entity)) / 100;
        }
        else if (source == DamageSource.magic) {
            if (entity instanceof EntityPlayer) {
                ((EntityPlayer) entity).inventory.damageArmor(damage);
            }
            damage *= (100F - RPGCommonHelper.calcTotalMagicResistance(entity)) / 100;
        }
        return damage;
    }

    /**
     * @return % of phisical resistance from one armor item
     */
    public static float calcPhisicResistance(float phisicArmor)
    {
        return phisicArmor * 2.5F;
    }

    /**
     * @return % of magic resistance from one armor item
     */
    public static float calcMagicResistance(float magicArmor)
    {
        return (-2000F / (magicArmor + 20) + 100) / 4F;
    }

    /**
     * @return % of phisical resistance from full armors set
     */
    public static float calcTotalPhisicResistance(EntityLivingBase entity)
    {
        float value = calcPhisicResistance(entity.getTotalArmorValue());
        return value > 100F ? 100F : value;
    }

    /**
     * @return % of magic resistance from full armors set
     */
    public static float calcTotalMagicResistance(EntityLivingBase entity)
    {
        float i = 0;
        ItemStack[] stacks = entity instanceof EntityPlayer ?
                             ((EntityPlayer) entity).inventory.armorInventory :
                             entity.getLastActiveItems();
        for (ItemStack stack : stacks) {
            if (stack != null && stack.getItem() instanceof ItemArmor && ItemAttributes.MAGIC_ARMOR.hasIt(stack))
            {
                i += calcMagicResistance(ItemAttributes.MAGIC_ARMOR.get(stack));
            }
        }
        return i > 100F ? 100F : i;
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

    @SideOnly(Side.CLIENT)
    public static void wheelScroll(int value)
    {
        if (value == 0) {
            return;
        }

        if (value > 0) {
            value = 1;
        }

        if (value < 0) {
            value = -1;
        }

        for (i -= value; i < 0; i += 9) {
            ;
        }

        while (i >= 9)
        {
            i -= 9;
        }

        DangerRPG.log(i);
    }

    public static void breakBlock(World world, int x, int y, int z, Block block, int par)
    {
        TileEntity tileEntity = world.getTileEntity(x, y, z);

        if (tileEntity != null && tileEntity instanceof IInventory) {
        	IInventory inventory = (IInventory) tileEntity;
            for (int i = 0; i < inventory.getSizeInventory(); ++i) {
                ItemStack stack = inventory.getStackInSlot(i);

                if (stack != null) {
                    float f = DangerRPG.rand.nextFloat() * 0.8F + 0.1F;
                    float f1 = DangerRPG.rand.nextFloat() * 0.8F + 0.1F;
                    EntityItem entityItem;

                    for (float f2 = DangerRPG.rand.nextFloat() * 0.8F + 0.1F; stack.stackSize > 0; world.spawnEntityInWorld(entityItem)) {
                        int j = DangerRPG.rand.nextInt(21) + 10;

                        if (j > stack.stackSize) {
                            j = stack.stackSize;
                        }

                        stack.stackSize -= j;
                        entityItem = new EntityItem(world, x + f, y + f1, z + f2, new ItemStack(stack.getItem(), j, stack.getItemDamage()));
                        float f3 = 0.05F;
                        entityItem.motionX = (float)DangerRPG.rand.nextGaussian() * f3;
                        entityItem.motionY = (float)DangerRPG.rand.nextGaussian() * f3 + 0.2F;
                        entityItem.motionZ = (float)DangerRPG.rand.nextGaussian() * f3;

                        if (stack.hasTagCompound()) {
                            entityItem.getEntityItem().setTagCompound((NBTTagCompound) stack.getTagCompound().copy());
                        }
                    }
                }
            }
            world.func_147453_f(x, y, z, block);
        }
    }

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
}
