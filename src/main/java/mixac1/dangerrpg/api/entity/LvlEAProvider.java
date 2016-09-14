package mixac1.dangerrpg.api.entity;

import mixac1.dangerrpg.capability.RPGEntityData;
import mixac1.dangerrpg.capability.ea.EntityAttributes;
import mixac1.dangerrpg.init.RPGNetwork;
import mixac1.dangerrpg.network.MsgReqUpEA;
import mixac1.dangerrpg.util.IMultiplier;
import mixac1.dangerrpg.util.IMultiplier.IMultiplierE;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Class provides lvl for {@link EntityAttribute}
 */
public class LvlEAProvider<Type>
{
    public EntityAttribute<Type> attr;

    public IMultiplierE<Type> mulValue;
    public int maxLvl;
    public int startExpCost;
    public IMultiplier<Integer> mulExpCost;

    public LvlEAProvider(int startExpCost, int maxLvl, IMultiplierE<Type> mulValue, IMultiplier<Integer> mulExpCost)
    {
        this.mulValue = mulValue;
        this.maxLvl = maxLvl;
        this.startExpCost = startExpCost;
        this.mulExpCost = mulExpCost;
    }

    public void init(EntityLivingBase entity)
    {
        attr.getEntityData(entity).lvlMap.put(attr.hash, 0);
    }

    public int getLvl(EntityLivingBase entity)
    {
        int t = attr.getEntityData(entity).lvlMap.get(attr.hash);
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

    public boolean isMaxLvl(EntityLivingBase entity)
    {
        return getLvl(entity) >= maxLvl;
    }

    public boolean canUp(EntityLivingBase target, EntityPlayer upper)
    {
        return (upper.capabilities.isCreativeMode || upper.experienceLevel >= getExpUp(target))
               && !isMaxLvl(target);
    }

    public boolean tryUp(EntityLivingBase target, EntityPlayer upper)
    {
        if (!isMaxLvl(target)) {
            if (upper.capabilities.isCreativeMode) {
                return up(target, upper, true);
            }
            else {
                int exp = getExpUp(target);
                if (exp <= upper.experienceLevel) {
                    if (RPGEntityData.isServerSide(target)) {
                        upper.addExperienceLevel(-exp);
                    }
                    return up(target, upper, true);
                }
            }
        }
        return false;
    }

    /**
     * @param flag - if true, then lvl up, else down
     */
    @Deprecated
    public boolean up(EntityLivingBase target, EntityPlayer upper, boolean flag)
    {
        if (RPGEntityData.isServerSide(target)) {
            int lvl = getLvl(target);
            if (flag) {
                if (lvl < maxLvl) {
                    setLvl(lvl + 1, target);
                    EntityAttributes.LVL.addValue(1, target);
                    attr.setValue(mulValue.up(attr.getValue(target), target), target);
                    return true;
                }
            }
            else if (lvl > 1) {
                setLvl(lvl - 1, target);
                EntityAttributes.LVL.addValue(-1, target);
                attr.setValue(mulValue.down(attr.getValue(target), target), target);
                return true;
            }
            return false;
        }
        else {
            if (flag) {
                RPGNetwork.net.sendToServer(new MsgReqUpEA(attr.hash, target.getEntityId(), upper.getEntityId()));
            }
            return true;
        }
    }

    @Override
    public final int hashCode()
    {
        return attr.hash;
    }
}
