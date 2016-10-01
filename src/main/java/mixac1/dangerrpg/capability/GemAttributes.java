package mixac1.dangerrpg.capability;

import mixac1.dangerrpg.api.item.IADynamic;
import mixac1.dangerrpg.capability.ia.IAStrUUID;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public abstract class GemAttributes
{
    public static final IAStrUUID UUID = new IAStrUUID("uuid");

    public static final IADynamic VALUE = new IADynamic("value");

    public static final IADynamic PERCENT = new IADynamic("percent")
    {
        @Override
        public String getDispayValue(ItemStack stack, EntityPlayer player)
        {
            return ItemAttributes.getStringProcentage(get(stack, player));
        }
    };

    public static final IADynamic CHANCE = new IADynamic("chance")
    {
        @Override
        public String getDispayValue(ItemStack stack, EntityPlayer player)
        {
            return ItemAttributes.getStringProcentage(get(stack, player));
        }
    };
}
