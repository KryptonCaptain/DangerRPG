package mixac1.dangerrpg.capability.playerattr;

import mixac1.dangerrpg.api.player.PlayerAttributeE;
import mixac1.dangerrpg.capability.PlayerData;
import net.minecraft.entity.player.EntityPlayer;

public class PAMana extends PlayerAttributeE
{
	public PAMana(String name, float startValue, float upValue, float startExpCost)
	{
		super(name, startValue, upValue, startExpCost);
	}
	
	@Override
	public void setValue(float value, EntityPlayer player)
	{
		if (isValid(value)) {
			float max = PlayerData.get(player).attributeMapE.get(hash).value;
			PlayerData.get(player).attributeMapE.get(hash).value = value;
			PlayerAttributes.CURR_MANA.setValue(value * PlayerAttributes.CURR_MANA.getValue(player) / max, player);
			apply(player);
			sync(player);
		}
	}
}
