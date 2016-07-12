package mixac1.dangerrpg.capability.playerattr;

import mixac1.dangerrpg.api.player.PlayerAttribute;
import mixac1.dangerrpg.capability.PlayerData;
import net.minecraft.entity.player.EntityPlayer;

public class PACurrMana extends PlayerAttribute
{
	public PACurrMana(String name)
	{
		super(name);
	}
	
	@Override
	public void init(EntityPlayer player)
	{
		init(PlayerAttributes.MANA.getValue(player), player);
	}
	
	@Override
	public float getValue(EntityPlayer player)
	{
		float value = PlayerData.get(player).attributeMap.get(hash).value;
		if (value > PlayerAttributes.MANA.getValue(player) || value < 0) {
			setValue(value, player);
			value = PlayerData.get(player).attributeMap.get(hash).value;
		}
		return value;
	}
	
	@Override
	public void setValue(float value, EntityPlayer player)
	{
		if (isValid(value)) {
		    PlayerData.get(player).attributeMap.get(hash).value = getValidValue(value, player);
			apply(player);
			sync(player);
		}
	}
	
	private float getValidValue(float value, EntityPlayer player)
	{
	    float tmp;
        if (value > (tmp = PlayerAttributes.MANA.getValue(player))) {
            return tmp;
        }
        else if (value < 0) {
            return 0;
        }
        return value;
	}
}
