package mixac1.dangerrpg.capability.playerattr;

import mixac1.dangerrpg.api.player.PlayerAttributeE;
import mixac1.dangerrpg.capability.PlayerData.PAEValues;
import mixac1.dangerrpg.util.Multiplier;
import mixac1.dangerrpg.util.Multiplier.MultiplierE;
import net.minecraft.entity.player.EntityPlayer;

public class PAMana extends PlayerAttributeE
{
    public PAMana(String name, float startValue, float startExpCost, float maxLvl, MultiplierE mulValue, Multiplier mulExpCost)
    {
        super(name, startValue, startExpCost, maxLvl, mulValue, mulExpCost);
    }

    @Override
    public void setValue(float value, EntityPlayer player)
    {
        if (isValid(value)) {
            PAEValues tmp = getPAEValues(player);
            float max = tmp.value;
            tmp.value = value;
            PlayerAttributes.CURR_MANA.setValue(value * PlayerAttributes.CURR_MANA.getValue(player) / max, player);
            apply(player);
            sync(player);
        }
    }
}
