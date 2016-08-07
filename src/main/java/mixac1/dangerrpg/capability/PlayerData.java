package mixac1.dangerrpg.capability;

import java.util.ArrayList;

import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.entity.EntityAttribute;
import mixac1.dangerrpg.api.entity.EntityAttributeE;
import mixac1.dangerrpg.capability.playerattr.PlayerAttributes;
import mixac1.dangerrpg.init.RPGConfig;
import net.minecraft.entity.player.EntityPlayer;

public class PlayerData extends CommonEntityData
{
	public final EntityPlayer player;

	public static ArrayList<EntityAttributeE> lvlableAttributes = new ArrayList<EntityAttributeE>(CommonEntityData.lvlableAttributes);
	public static ArrayList<EntityAttribute>  staticAttributes  = new ArrayList<EntityAttribute>(CommonEntityData.staticAttributes);

	static
	{
	    lvlableAttributes.add(PlayerAttributes.HEALTH);
	    lvlableAttributes.add(PlayerAttributes.MANA);
	    lvlableAttributes.add(PlayerAttributes.STRENGTH);
	    lvlableAttributes.add(PlayerAttributes.AGILITY);
	    lvlableAttributes.add(PlayerAttributes.INTELLIGENCE);
	    lvlableAttributes.add(PlayerAttributes.EFFICIENCY);
	    lvlableAttributes.add(PlayerAttributes.MANA_REGEN);

	    staticAttributes.add(PlayerAttributes.CURR_MANA);
	    staticAttributes.add(PlayerAttributes.SPEED_COUNTER);
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

    public void rebuildOnDeath()
    {
        int count = 0;
        int lvl;

        ArrayList<EntityAttributeE> pas = new ArrayList<EntityAttributeE>();
        for (EntityAttributeE it : getLvlableAttributes()) {
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
	public ArrayList<EntityAttribute> getStaticAttributes()
    {
    	return staticAttributes;
    }

    @Override
	public ArrayList<EntityAttributeE> getLvlableAttributes()
    {
    	return lvlableAttributes;
    }
}
