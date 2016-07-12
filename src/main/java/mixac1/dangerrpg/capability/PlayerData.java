package mixac1.dangerrpg.capability;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.player.PlayerAttribute;
import mixac1.dangerrpg.api.player.PlayerAttributeE;
import mixac1.dangerrpg.capability.playerattr.PlayerAttributes;
import mixac1.dangerrpg.init.RPGConfig;
import mixac1.dangerrpg.init.RPGNetwork;
import mixac1.dangerrpg.network.MsgSyncPlayerData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class PlayerData implements IExtendedEntityProperties
{
	private static final String ID = "RPGPlayerData";
	
	public final EntityPlayer player;
	
	public HashMap<Integer, PAEValues> attributeMapE = new HashMap<Integer, PAEValues>();
	public HashMap<Integer, PAValues> attributeMap   = new HashMap<Integer, PAValues>();
	
	public static ArrayList<PlayerAttributeE> playerAttributes = new ArrayList<PlayerAttributeE>();	
	public static ArrayList<PlayerAttribute> workAttributes = new ArrayList<PlayerAttribute>();
	
	static {
		playerAttributes.add(PlayerAttributes.HEALTH);
		playerAttributes.add(PlayerAttributes.MANA);
		playerAttributes.add(PlayerAttributes.STRENGTH);
		playerAttributes.add(PlayerAttributes.AGILITY);
		playerAttributes.add(PlayerAttributes.INTELLIGENCE);
		playerAttributes.add(PlayerAttributes.EFFICIENCY);
		playerAttributes.add(PlayerAttributes.MANA_REGEN);
		
		workAttributes.add(PlayerAttributes.CURR_MANA);
		workAttributes.add(PlayerAttributes.SPEED_COUNTER);
	}
	
	public PlayerData(EntityPlayer player)
	{
        this.player = player;
    }
	
	@Override
	public void init(Entity entity, World world)
	{
		for (PlayerAttribute iter : playerAttributes) {
			iter.init(player);
		}
		for (PlayerAttribute iter : workAttributes) {
			iter.init(player);
		}
	}
	
	public int getLvl()
	{
		int lvl = 1;
		for (PlayerAttributeE attr : playerAttributes) {
			lvl += attr.getLvl(player) - 1;
		}
		return lvl;
	}
	
	public static void register(EntityPlayer player)
	{
	    player.registerExtendedProperties(ID, new PlayerData(player));
	}
	
	public static PlayerData get(EntityPlayer player)
	{
	    return (PlayerData) player.getExtendedProperties(ID);
	}

	public static boolean isServerSide(EntityPlayer player)
	{
	    return player instanceof EntityPlayerMP;
	}
	
	private Object getObject(int hash, List list)
	{
	    for (Object it : list) {
            if (it.hashCode() == hash) {
                return it;
            }
        }
        return null;
	}
	
	public PlayerAttribute getPlayerAttribute(int hash)
	{
	    return (PlayerAttribute) getObject(hash, workAttributes);
	}
	
	public PlayerAttributeE getPlayerAttributeE(int hash)
    {
        return (PlayerAttributeE) getObject(hash, playerAttributes);
    }
	
	public void syncAll()
	{
	    if (isServerSide(player)) {
	    	RPGNetwork.net.sendTo(new MsgSyncPlayerData(this), (EntityPlayerMP) player);
	    }
	}

	public void requestSyncAll()
	{
	    if (!isServerSide(player)) {
	    	RPGNetwork.net.sendToServer(new MsgSyncPlayerData());
	    }
	}
	
	public void rebuildOnDeath()
	{
	    int count = 0;
	    int lvl;
	    
	    ArrayList<PlayerAttributeE> pas = new ArrayList<PlayerAttributeE>();
	    for (PlayerAttributeE it : playerAttributes) {
	        if ((lvl = it.getLvl(player)) > 1) {
	            pas.add(it);
	            count += lvl - 1;
	        }
	    }
	    
	    if (count > RPGConfig.playerLoseLvlCount) {
	        count = RPGConfig.playerLoseLvlCount;
	    }
	    
	    for (int i = 0; i < count; ++i) {
	        int rand = DangerRPG.rand.nextInt(pas.size());
	        pas.get(rand).up(player, false);
	        if (pas.get(rand).getLvl(player) <= 1) {
	            pas.remove(rand);
	        }
	    }
	}
	
	@Override
	public void saveNBTData(NBTTagCompound nbt)
	{
		for (PlayerAttributeE iter : playerAttributes) {
			NBTTagCompound tmp = new NBTTagCompound();
			tmp.setInteger("lvl", iter.getLvl(player));
			tmp.setFloat("value", iter.getValue(player));
			nbt.setTag(iter.name, tmp);
		}
		for (PlayerAttribute iter : workAttributes) {
			nbt.setFloat(iter.name, iter.getValue(player));
		}
	}

	@Override
	public void loadNBTData(NBTTagCompound nbt)
	{
		for (PlayerAttributeE iter : playerAttributes) {
			if (nbt.hasKey(iter.name)) {
				NBTTagCompound tmp = (NBTTagCompound) nbt.getTag(iter.name);
				iter.setLvl(tmp.getInteger("lvl"), player);
				iter.setValue(tmp.getFloat("value"), player, false);
		    }
		}
		for (PlayerAttribute iter : workAttributes) {
			if (nbt.hasKey(iter.name)) {
			    iter.setValue(nbt.getFloat(iter.name), player, false);
		    }
		}
	}
	
	public static class PAValues
	{
	    public float value;
	    
	    public PAValues(float value)
	    {
	        this.value = value;
	    }
	}
	
	public static class PAEValues extends PAValues
    {
        public int lvl;
        
        public PAEValues(int lvl, float value)
        {
            super(value);
            this.lvl = lvl;
        }
    }
}
