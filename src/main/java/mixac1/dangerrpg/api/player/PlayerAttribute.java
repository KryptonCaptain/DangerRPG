package mixac1.dangerrpg.api.player;

import mixac1.dangerrpg.capability.PlayerData;
import mixac1.dangerrpg.capability.PlayerData.PAValues;
import mixac1.dangerrpg.init.RPGNetwork;
import mixac1.dangerrpg.network.MsgSyncPA;
import mixac1.dangerrpg.util.Translator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class PlayerAttribute
{
    public final String name;
    public final int    hash;
                        
    protected float     startValue = 0F;
                                   
    public PlayerAttribute(String name)
    {
        this.name = name;
        hash = name.hashCode();
    }
    
    public boolean isValid(float value)
    {
        return value >= 0F;
    }
    
    public float getValue(EntityPlayer player)
    {
        float value = PlayerData.get(player).attributeMap.get(hash).value;
        if (!isValid(value)) {
            init(player);
            value = PlayerData.get(player).attributeMap.get(hash).value;
        }
        return value;
    }
    
    public void setValue(float value, EntityPlayer player, boolean needSync)
    {
        if (isValid(value)) {
            PlayerData.get(player).attributeMap.get(hash).value = value;
            apply(player);
            if (needSync) {
                sync(player);
            }
        }
    }
    
    public void setValue(float value, EntityPlayer player)
    {
        setValue(value, player, true);
    }
    
    public void addValue(float value, EntityPlayer player)
    {
        setValue(getValue(player) + value, player, true);
    }
    
    public void init(float value, EntityPlayer player)
    {
        PlayerData.get(player).attributeMap.put(hash, new PAValues(value));
    }
    
    public void init(EntityPlayer player)
    {
        init(startValue, player);
    }
    
    public void sync(EntityPlayer player)
    {
        if (PlayerData.isServerSide(player)) {
            RPGNetwork.net.sendTo(new MsgSyncPA(hash, getValue(player)), (EntityPlayerMP) player);
        }
    }
    
    public void apply(EntityPlayer player)
    {
    }
    
    public float displayValue(EntityPlayer player)
    {
        return getValue(player);
    }
    
    public String getDispayName()
    {
        return Translator.trans("pl_atr.".concat(name));
    }
    
    @Override
    public int hashCode()
    {
        return hash;
    }
}
