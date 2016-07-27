package mixac1.dangerrpg.capability;

import java.util.ArrayList;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.entity.EntityAttribute;
import mixac1.dangerrpg.api.entity.EntityAttributeE;
import mixac1.dangerrpg.capability.playerattr.PlayerAttributes;
import mixac1.dangerrpg.init.RPGConfig;
import mixac1.dangerrpg.init.RPGNetwork;
import mixac1.dangerrpg.network.MsgSyncPlayerData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class PlayerData extends CommonEntityData
{
	public final EntityPlayer player;
	
	public static ArrayList<EntityAttributeE> entityAttributes = new ArrayList<EntityAttributeE>(CommonEntityData.entityAttributes);    
	public static ArrayList<EntityAttribute>  workAttributes   = new ArrayList<EntityAttribute>(CommonEntityData.workAttributes);
	
	static
	{
    	entityAttributes.add(PlayerAttributes.HEALTH);
    	entityAttributes.add(PlayerAttributes.MANA);
    	entityAttributes.add(PlayerAttributes.STRENGTH);
    	entityAttributes.add(PlayerAttributes.AGILITY);
    	entityAttributes.add(PlayerAttributes.INTELLIGENCE);
    	entityAttributes.add(PlayerAttributes.EFFICIENCY);
    	entityAttributes.add(PlayerAttributes.MANA_REGEN);
        
        workAttributes.add(PlayerAttributes.CURR_MANA);
        workAttributes.add(PlayerAttributes.SPEED_COUNTER);
    }
	
	public PlayerData(EntityPlayer player)
	{
		super(player);
		this.player = player;
	}
	
	public EntityPlayer getPlayer()
	{
		return (EntityPlayer) entity;
	}
	
	public static void register(EntityPlayer player)
    {
		player.registerExtendedProperties(ID, new PlayerData(player));
    }
    
    public static PlayerData get(EntityPlayer player)
    {
        return (PlayerData) player.getExtendedProperties(ID);
    }
	
	@Override
	public void sync(IMessage msg)
    {
    	if (isServerSide(entity)) {
            RPGNetwork.net.sendTo(msg, (EntityPlayerMP) getPlayer());
        }
    }
	
	@Override
	public void syncAll()
    {
        if (isServerSide(entity)) {
            RPGNetwork.net.sendTo(new MsgSyncPlayerData(this), (EntityPlayerMP) getPlayer());
        }
    }

    @Override
	public void requestAll()
    {
        if (!isServerSide(entity)) {
            RPGNetwork.net.sendToServer(new MsgSyncPlayerData());
        }
    }
    
    public void rebuildOnDeath()
    {
        int count = 0;
        int lvl;
        
        ArrayList<EntityAttributeE> pas = new ArrayList<EntityAttributeE>();
        for (EntityAttributeE it : entityAttributes) {
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
	public ArrayList<EntityAttribute> getWorkAttributes()
    {
    	return workAttributes;
    }
    
    @Override
	public ArrayList<EntityAttributeE> getEntityAttributes()
    {
    	return entityAttributes;
    }
}
