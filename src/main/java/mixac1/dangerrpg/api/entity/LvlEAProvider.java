package mixac1.dangerrpg.api.entity;

import mixac1.dangerrpg.capability.CommonEntityData;
import mixac1.dangerrpg.capability.ea.EntityAttributes;
import mixac1.dangerrpg.init.RPGNetwork;
import mixac1.dangerrpg.network.MsgReqUpEA;
import mixac1.dangerrpg.util.Multiplier;
import mixac1.dangerrpg.util.Multiplier.MultiplierE;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class LvlEAProvider<Type>
{
    public EntityAttribute<Type> attr;

    protected MultiplierE<Type> mulValue;
    protected int maxLvl;
    protected int startExpCost;
    protected Multiplier<Integer> mulExpCost;

    public LvlEAProvider(int startExpCost, int maxLvl, MultiplierE<Type> mulValue, Multiplier<Integer> mulExpCost)
    {
        this.mulValue = mulValue;
        this.maxLvl = maxLvl;
        this.startExpCost = startExpCost;
        this.mulExpCost = mulExpCost;
    }

    public void init(EntityLivingBase entity)
    {
        attr.getEntityData(entity).lvlMap.put(attr.hash, 1);
    }

    public int getLvl(EntityLivingBase entity)
    {
        return attr.getEntityData(entity).lvlMap.get(attr.hash);
    }

    @Deprecated
    public int setLvl(int lvl, EntityLivingBase entity)
    {
        return attr.getEntityData(entity).lvlMap.put(attr.hash, lvl);
    }

    public int getExpUp(EntityLivingBase entity)
    {
        return startExpCost + mulExpCost.up(getLvl(entity));
    }

    public boolean canUp(EntityLivingBase entity)
    {
        if (entity instanceof EntityPlayer) {
            return ((EntityPlayer) entity).capabilities.isCreativeMode ||
                   ((EntityPlayer) entity).experienceLevel >= getExpUp(entity);
        }
        return true;
    }

    public boolean tryUp(EntityLivingBase entity)
    {
        if (entity instanceof EntityPlayer) {
            if (getLvl(entity) < maxLvl) {
                EntityPlayer player = (EntityPlayer) entity;
                if (player.capabilities.isCreativeMode) {
                    return up(player, true);
                }
                else {
                    int exp = getExpUp(player);
                    if (exp <= player.experienceLevel) {
                        if (CommonEntityData.isServerSide(entity)) {
                            player.addExperienceLevel(-exp);
                        }
                        return up(player, true);
                    }
                }
            }
            return false;
        }
        return up(entity, true);
    }

    /**
     * @param flag - if true, then lvl up, else down
     */
    @Deprecated
    public boolean up(EntityLivingBase entity, boolean flag)
    {
        if (CommonEntityData.isServerSide(entity)) {
            int lvl= getLvl(entity);
            if (flag) {
                if (lvl < maxLvl) {
                    setLvl(lvl + 1, entity);
                    EntityAttributes.LVL.addValue(1, entity);
                    attr.setValue(mulValue.up(attr.getValue(entity)), entity);
                    return true;
                }
            }
            else if (lvl > 1) {
                setLvl(lvl - 1, entity);
                EntityAttributes.LVL.addValue(-1, entity);
                attr.setValue(mulValue.down(attr.getValue(entity)), entity);
                return true;
            }
            return false;
        }
        else {
            if (flag) {
                RPGNetwork.net.sendToServer(new MsgReqUpEA(attr.hash, entity.getEntityId()));
            }
            return flag;
        }
    }
}
