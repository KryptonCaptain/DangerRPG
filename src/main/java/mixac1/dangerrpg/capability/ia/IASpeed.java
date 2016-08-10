package mixac1.dangerrpg.capability.ia;

import mixac1.dangerrpg.api.item.IAStatic;
import mixac1.dangerrpg.capability.LvlableItem;
import mixac1.dangerrpg.capability.ea.PlayerAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class IASpeed extends IAStatic
{
    float normalValue;
    
    public IASpeed(String name, float normalValue)
    {
        super(name);
        this.normalValue = normalValue;
    }
    
    @Override
    public float get(ItemStack stack)
    {
        return LvlableItem.itemsAttrebutes.get(stack.getItem()).get(this).value;
    }
    
    @Override
    public float get(ItemStack stack, EntityPlayer player)
    {
        float value = get(stack) - PlayerAttributes.AGILITY.getValue(player) * ItemAttributes.AGI_MUL.get(stack) * normalValue / 100F;
        return value >= 1 ? value : 1F;
    }
}
