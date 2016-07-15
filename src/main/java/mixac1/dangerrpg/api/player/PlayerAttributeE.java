package mixac1.dangerrpg.api.player;

import mixac1.dangerrpg.capability.PlayerData;
import mixac1.dangerrpg.capability.PlayerData.PAEValues;
import mixac1.dangerrpg.init.RPGNetwork;
import mixac1.dangerrpg.network.MsgSyncPAE;
import mixac1.dangerrpg.util.Multiplier;
import mixac1.dangerrpg.util.Multiplier.MultiplierE;
import mixac1.dangerrpg.util.Translator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class PlayerAttributeE extends PlayerAttribute
{
    protected MultiplierE mulValue;
    protected float maxLvl;
    protected float startExpCost;
    protected Multiplier mulExpCost;

    public PlayerAttributeE(String name, float startValue, float startExpCost, float maxLvl, MultiplierE mulValue, Multiplier mulExpCost)
    {
        super(name);
        this.startValue = startValue;
        this.mulValue = mulValue;
        this.maxLvl = maxLvl;
        this.startExpCost = startExpCost;
        this.mulExpCost = mulExpCost;
    }

    public PAEValues getPAEValues(EntityPlayer player)
    {
        return PlayerData.get(player).attributeMapE.get(hash);
    }

    public int getLvl(EntityPlayer player)
    {
        return getPAEValues(player).lvl;
    }

    @Deprecated
    public void setLvl(int lvl, EntityPlayer player)
    {
        getPAEValues(player).lvl = lvl;
    }

    @Override
    public float getValue(EntityPlayer player)
    {
        float value = getPAEValues(player).value;
        if (!isValid(value)) {
            init(player);
            value = getPAEValues(player).value;
        }
        return value;
    }

    @Deprecated
    @Override
    public void setValue(float value, EntityPlayer player, boolean needSync)
    {
        if (isValid(value)) {
            getPAEValues(player).value = value;
            apply(player);
            if (needSync) {
                sync(player);
            }
        }
    }

    @Override
    public void init(float value, EntityPlayer player)
    {
        PlayerData.get(player).attributeMapE.put(hash, new PAEValues(1, value));
    }

    public int getExpUp(EntityPlayer player)
    {
        return (int) (startExpCost + mulExpCost.up(getLvl(player)));
    }

    public boolean canUp(EntityPlayer player)
    {
        return player.capabilities.isCreativeMode || player.experienceLevel >= getExpUp(player);
    }

    /**
     * @param flag - if true, then lvl up, else down
     */
    @Deprecated
    public boolean up(EntityPlayer player, boolean flag)
    {
        PAEValues tmp = getPAEValues(player);
        if (flag) {
            if (tmp.lvl < maxLvl) {
                tmp.lvl += 1;
                setValue(mulValue.up(tmp.value), player);
                return true;
            }
        }
        else if (tmp.lvl > 1) {
            tmp.lvl -= 1;
            setValue(mulValue.down(tmp.value), player);
            return true;
        }
        return false;
    }

    public boolean tryUp(EntityPlayer player)
    {
        if (getLvl(player) < maxLvl) {
            if (player.capabilities.isCreativeMode) {
                if (!player.worldObj.isRemote) {
                    up(player, true);
                }
                return true;
            }
            else {
                int exp = getExpUp(player);
                if (exp >= getExpUp(player)) {
                    if (!player.worldObj.isRemote) {
                        player.addExperienceLevel(-exp);
                        up(player, true);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void sync(EntityPlayer player)
    {
        if (PlayerData.isServerSide(player)) {
            RPGNetwork.net.sendTo(new MsgSyncPAE(hash, getLvl(player), getValue(player)), (EntityPlayerMP) player);
        }
    }

    public String getInfo(EntityPlayer player)
    {
        return Translator.trans("pl_atr.".concat(name).concat(".info"));
    }
}
