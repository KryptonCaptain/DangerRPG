package mixac1.dangerrpg.capability.playerattr;

import mixac1.dangerrpg.api.player.PlayerAttribute;
import mixac1.dangerrpg.capability.PlayerData;
import net.minecraft.entity.player.EntityPlayer;

public class PASpeedCounter extends PlayerAttribute
{
    public PASpeedCounter(String name)
    {
        super(name);
    }
    
    @Override
    public void setValue(float value, EntityPlayer player)
    {
        if (isValid(value)) {
            PlayerData.get(player).attributeMap.get(hash).value = value;
            apply(player);
            if (value == 0) {
                sync(player);
            }
        }
    }
}
