package mixac1.dangerrpg.entity.projectile;

import java.util.List;

import cpw.mods.fml.common.registry.IThrowableEntity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.DangerRPG;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityProjectile extends Entity implements IProjectile, IThrowableEntity
{
    protected int xTile;
    protected int yTile;
    protected int zTile;
    protected Block    inTile;
    protected int inData;
    protected boolean inGround;
    protected boolean beenInGround;
    protected int ticksAlive;
    public int untouch;

    public EntityLivingBase thrower;
    protected String throwerName;
    public float magicDamage;

    public EntityProjectile(World world)
    {
        super(world);
        renderDistanceWeight = 10.0D;
        setSize(0.5F, 0.5F);
        ticksExisted = 1200;
        untouch = getMaxUntouchability();
    }

    public EntityProjectile(World world, double x, double y, double z)
    {
        this(world);
        setPosition(x, y, z);
        yOffset = 0.0F;
    }

    public EntityProjectile(World world, EntityLivingBase thrower, float speed, float deviation)
    {
        this(world);
        this.thrower = thrower;

        setLocationAndAngles(thrower.posX, thrower.posY + thrower.getEyeHeight(), thrower.posZ, thrower.rotationYaw, thrower.rotationPitch);
        posX -= MathHelper.cos(rotationYaw / 180.0F * (float)Math.PI) * 0.16F;
        posY -= 0.1;
        posZ -= MathHelper.sin(rotationYaw / 180.0F * (float)Math.PI) * 0.16F;

        Vec3 vec = thrower.getLook(1F);
        setPosition(posX + vec.xCoord, posY + vec.yCoord, posZ + vec.zCoord);

        yOffset = 0.0F;
        motionX = -MathHelper.sin(rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float)Math.PI);
        motionZ =  MathHelper.cos(rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float)Math.PI);
        motionY = -MathHelper.sin(rotationPitch / 180.0F * (float)Math.PI);
        setThrowableHeading(motionX, motionY, motionZ, speed, deviation);
    }

    public EntityProjectile(World world, EntityLivingBase thrower, EntityLivingBase target, float speed, float deviation)
    {
        this(world);
        this.thrower = thrower;

        posY = thrower.posY + thrower.getEyeHeight() - 0.1;
        double d0 = target.posX - thrower.posX;
        double d1 = target.boundingBox.minY + target.height / 3.0F - posY;
        double d2 = target.posZ - thrower.posZ;
        double d3 = MathHelper.sqrt_double(d0 * d0 + d2 * d2);

        if (d3 >= 1.0E-7D) {
            float f2 = (float)(Math.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
            float f3 = (float)-(Math.atan2(d1, d3) * 180.0D / Math.PI);
            double d4 = d0 / d3;
            double d5 = d2 / d3;
            setLocationAndAngles(thrower.posX + d4, posY, thrower.posZ + d5, f2, f3);
            yOffset = 0.0F;
            float f4 = (float)d3 * 0.2F;
            setThrowableHeading(d0, d1 + f4, d2, speed, deviation);
        }
    }

    @Override
    public void entityInit() {}

    @Override
    public void setThrowableHeading(double x, double y, double z, float speed, float deviation)
    {
        float f2 = MathHelper.sqrt_double(x * x + y * y + z * z);
        x /= f2;
        y /= f2;
        z /= f2;
        x += rand.nextGaussian() * (rand.nextBoolean() ? -1 : 1) * 0.0075 * deviation;
        y += rand.nextGaussian() * (rand.nextBoolean() ? -1 : 1) * 0.0075 * deviation;
        z += rand.nextGaussian() * (rand.nextBoolean() ? -1 : 1) * 0.0075 * deviation;
        x *= speed;
        y *= speed;
        z *= speed;
        motionX = x;
        motionY = y;
        motionZ = z;
        float f3 = MathHelper.sqrt_double(x * x + z * z);
        prevRotationYaw = rotationYaw = (float)(Math.atan2(x, z) * 180.0D / Math.PI);
        prevRotationPitch = rotationPitch = (float)(Math.atan2(y, f3) * 180.0D / Math.PI);
        ticksAlive = 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int par)
    {
        //super.setPositionAndRotation2(x, y, z, yaw, pitch, par);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void setVelocity(double x, double y, double z)
    {
        motionX = x;
        motionY = y;
        motionZ = z;

        if (prevRotationPitch == 0.0F && prevRotationYaw == 0.0F) {
            float f = MathHelper.sqrt_double(x * x + z * z);
            prevRotationYaw = rotationYaw = (float)(Math.atan2(x, z) * 180.0D / Math.PI);
            prevRotationPitch = rotationPitch = (float)(Math.atan2(y, f) * 180.0D / Math.PI);
            prevRotationPitch = rotationPitch;
            prevRotationYaw = rotationYaw;
            setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
        }
    }

    @Override
    public void onUpdate()
    {
        onEntityUpdate();
    }

    @Override
    public void onEntityUpdate()
    {
        DangerRPG.log(motionX + " " + motionY + " " + motionZ);

        lastTickPosX = posX;
        lastTickPosY = posY;
        lastTickPosZ = posZ;

        super.onEntityUpdate();

        if (needAimRotation()) {
            makeAimRotation();
        }

        if (canRotation()) {
            rotationYaw += getRotationOnYaw();
            rotationPitch += getRotationOnPitch();
        }

        Block i = worldObj.getBlock(xTile, yTile, zTile);
        if (i != null) {
            i.setBlockBoundsBasedOnState(worldObj, xTile, yTile, zTile);
            AxisAlignedBB axisalignedbb = i.getCollisionBoundingBoxFromPool(worldObj, xTile, yTile, zTile);
            if (axisalignedbb != null && axisalignedbb.isVecInside(Vec3.createVectorHelper(posX, posY, posZ))) {
                inGround = true;
            }
        }

        if (untouch > 0) {
            untouch--;
        }

        if (inGround) {
            Block j = worldObj.getBlock(xTile, yTile, zTile);
            int k = worldObj.getBlockMetadata(xTile, yTile, zTile);
            if (j == inTile && k == inData) {}
            else {
                inGround = false;
                motionX *= rand.nextFloat() * 0.2F;
                motionY *= rand.nextFloat() * 0.2F;
                motionZ *= rand.nextFloat() * 0.2F;
            }
            return;
        }

        if (++ticksAlive >= ticksExisted) {
            setDead();
            return;
        }

        MovingObjectPosition mop = findMovingObjectPosition();
        if (mop != null) {
            if (mop.entityHit != null) {
                onEntityHit((EntityLivingBase) mop.entityHit);
                if (dieAfterEntityHit()) {
                    setDead();
                }
            }
            else {
                onGroundHit(mop);
                if (dieAfterGroundHit()) {
                    setDead();
                }
            }
            untouch = getMaxUntouchability();
        }

        posX += motionX;
        posY += motionY;
        posZ += motionZ;

        double r = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
        rotationYaw = (float) (Math.atan2(motionX, motionZ) * 180.0 / Math.PI);

        for (rotationPitch = (float) (Math.atan2(motionY, r) * 180.0D / Math.PI);
             rotationPitch - prevRotationPitch < -180.0F;
             prevRotationPitch -= 360.0F) {}

        for (; rotationPitch - prevRotationPitch >= 180.0F;
               prevRotationPitch += 360.0F) {}

        for (; rotationYaw - prevRotationYaw < -180.0F;
               prevRotationYaw -= 360.0F) {}

        for (; rotationYaw - prevRotationYaw >= 180.0F;
               prevRotationYaw += 360.0F) {}

        rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
        rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;

        motionX *= getAirResistance();
        motionY *= getAirResistance();
        motionZ *= getAirResistance();
        motionY -= getGravity();

        if (isWet()) {
            extinguish();
        }

        if (isInWater()) {
            onCollideWithWater();
        }

        setPosition(posX, posY, posZ);
        func_145775_I();
    }

    public void makeAimRotation()
    {
        float f = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
        prevRotationYaw = rotationYaw = (float) (Math.atan2(motionX, motionZ) * 180D / Math.PI);
        prevRotationPitch = rotationPitch = (float) (Math.atan2(motionY, f) * 180D / Math.PI);
    }

    public MovingObjectPosition findMovingObjectPosition()
    {
        Vec3 vec31 = Vec3.createVectorHelper(posX, posY, posZ);
        Vec3 vec32 = Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ);
        MovingObjectPosition mop = worldObj.func_147447_a(vec31, vec32, false, true, false);
        vec31 = Vec3.createVectorHelper(posX, posY, posZ);
        vec32 = Vec3.createVectorHelper(posX + motionX, posY + motionY, posZ + motionZ);
        if (mop != null) {
            vec32 = Vec3.createVectorHelper(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
        }

        Entity entity = null;
        @SuppressWarnings("unchecked")
        List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
        double d = 0.0D;
        for (Entity iterEntity : list) {
            if (!iterEntity.canBeCollidedWith() || !(iterEntity instanceof EntityLivingBase) || iterEntity == thrower && ticksAlive < 5) {
                continue;
            }
            float f4 = 0.3F;
            AxisAlignedBB aabb = iterEntity.boundingBox.expand(f4, f4, f4);
            MovingObjectPosition newMop = aabb.calculateIntercept(vec31, vec32);
            if (newMop == null) {
                continue;
            }
            double d1 = vec31.distanceTo(newMop.hitVec);
            if (d1 < d || d == 0.0D) {
                entity = iterEntity;
                d = d1;
            }
        }

        if (entity != null) {
            mop = new MovingObjectPosition(entity);
            if (mop.entityHit instanceof EntityPlayer) {
                EntityPlayer entityplayer = (EntityPlayer) mop.entityHit;
                if (entityplayer.capabilities.disableDamage || thrower instanceof EntityPlayer && thrower != null && !((EntityPlayer)thrower).canAttackPlayer(entityplayer)) {
                    mop = null;
                }
            }
        }

        return mop;
    }

    public void onEntityHit(EntityLivingBase entity)
    {
        if (!worldObj.isRemote) {
            applyEntityHitEffects(entity);
        }
        bounceBack();
    }

    public void applyEntityHitEffects(EntityLivingBase entity)
    {
        if (isBurning() && !(entity instanceof EntityEnderman)) {
            entity.setFire(5);
        }

        if (thrower != null) {
            EnchantmentHelper.func_151384_a(entity, thrower);
            EnchantmentHelper.func_151385_b(thrower, entity);

            if (thrower instanceof EntityPlayerMP && thrower != entity && entity instanceof EntityPlayer) {
                ((EntityPlayerMP) thrower).playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(6, 0));
            }

            int knockback = EnchantmentHelper.getKnockbackModifier(thrower, entity);
            if (knockback != 0) {
                entity.addVelocity(-MathHelper.sin(rotationYaw * (float) Math.PI / 180.0F) * knockback * 0.5F, 0.1D, MathHelper.cos(rotationYaw * (float) Math.PI / 180.0F) * knockback * 0.5F);
                this.motionX *= 0.6D;
                this.motionZ *= 0.6D;
                this.setSprinting(false);
            }

            int fire = EnchantmentHelper.getFireAspectModifier(thrower);
            if (fire > 0 && !entity.isBurning()) {
                entity.setFire(1);
            }
        }

        DamageSource dmgSource = DamageSource.causeIndirectMagicDamage(this, thrower == null ? this : thrower);
        entity.attackEntityFrom(dmgSource, magicDamage);
    }

    public void onGroundHit(MovingObjectPosition mop)
    {
        xTile = mop.blockX;
        yTile = mop.blockY;
        zTile = mop.blockZ;
        inTile = worldObj.getBlock(xTile, yTile, zTile);
        inData = worldObj.getBlockMetadata(xTile, yTile, zTile);
        motionX = mop.hitVec.xCoord - posX;
        motionY = mop.hitVec.yCoord - posY;
        motionZ = mop.hitVec.zCoord - posZ;
        float f1 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
        posX -= motionX / f1 * 0.05D;
        posY -= motionY / f1 * 0.05D;
        posZ -= motionZ / f1 * 0.05D;
        inGround = true;
        beenInGround = true;
        untouch = getMaxUntouchability();
        playHitSound();

        if (inTile != null) {
            inTile.onEntityCollidedWithBlock(worldObj, xTile, yTile, zTile, this);
        }
    }

    public void onCollideWithWater()
    {
        for (int i = 0; i < 4; ++i) {
            worldObj.spawnParticle("bubble", posX - motionX * 0.25F, posY - motionY * 0.25F, posZ - motionZ * 0.25F, motionX, motionY, motionZ);
        }
        motionX *= getWaterResistance();
        motionY *= getWaterResistance();
        motionZ *= getWaterResistance();
        beenInGround = true;
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer player) {}

    protected void bounceBack()
    {
        motionX *= -0.05D;
        motionY *= -0.05D;
        motionZ *= -0.05D;
    }

    @Override
    public Entity getThrower()
    {
        if (thrower == null && throwerName != null && throwerName.length() > 0) {
            thrower = worldObj.getPlayerEntityByName(throwerName);
        }
        return thrower;
    }

    @Override
    public void setThrower(Entity entity) {
        if (entity instanceof EntityLivingBase) {
            thrower = (EntityLivingBase) entity;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getShadowSize()
    {
        return 0.0F;
    }

    @Override
    protected boolean canTriggerWalking()
    {
        return false;
    }

    public float getAirResistance()
    {
        return 1F;
    }

    public float getWaterResistance()
    {
        return 1F;
    }

    public float getGravity()
    {
        return 0F;
    }

    public int getMaxUntouchability()
    {
        return 7;
    }

    public boolean dieAfterEntityHit()
    {
        return true;
    }

    public boolean dieAfterGroundHit()
    {
        return true;
    }

    public boolean canRotation()
    {
        return !beenInGround;
    }

    public float getRotationOnPitch()
    {
        return 0.0F;
    }

    public float getRotationOnYaw()
    {
        return 0.0F;
    }

    public boolean needAimRotation()
    {
        return !canRotation() || getRotationOnPitch() == 0 && getRotationOnYaw() == 0;
    }

    public void playHitSound() {}

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt)
    {
        nbt.setShort("xTile", (short) xTile);
        nbt.setShort("yTile", (short) yTile);
        nbt.setShort("zTile", (short) zTile);
        nbt.setByte("inTile", (byte) Block.getIdFromBlock(inTile));
        nbt.setByte("inData", (byte) inData);
        nbt.setBoolean("inGround", inGround);
        nbt.setBoolean("beenInGround", beenInGround);
        nbt.setInteger("ticksAlive", ticksAlive);
        nbt.setByte("untouch", (byte) untouch);
        nbt.setFloat("magicDamage", magicDamage);

        if ((throwerName == null || throwerName.length() == 0) && thrower != null && thrower instanceof EntityPlayer) {
            throwerName = thrower.getCommandSenderName();
        }
        nbt.setString("throwerName", throwerName == null ? "" : throwerName);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt)
    {
        xTile = nbt.getShort("xTile");
        yTile = nbt.getShort("yTile");
        zTile = nbt.getShort("zTile");
        inTile = Block.getBlockById(nbt.getByte("inTile") & 0xFF);
        inData = nbt.getByte("inData") & 0xFF;
        inGround = nbt.getBoolean("inGround");
        beenInGround = nbt.getBoolean("beenInGrond");
        ticksAlive = nbt.getInteger("ticksAlive");
        untouch = nbt.getByte("untouch") & 0xFF;
        magicDamage = nbt.getFloat("magicDamage");

        throwerName = nbt.getString("throwerName");
        if (throwerName != null && throwerName.length() == 0) {
            throwerName = null;
        }
        thrower = (EntityLivingBase) getThrower();
    }
}