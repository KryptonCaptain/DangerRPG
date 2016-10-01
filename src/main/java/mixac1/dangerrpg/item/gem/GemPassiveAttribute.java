package mixac1.dangerrpg.item.gem;

import mixac1.dangerrpg.api.entity.EAWithIAttr;
import mixac1.dangerrpg.api.item.GemType;
import mixac1.dangerrpg.capability.GemAttributes;
import mixac1.dangerrpg.capability.data.RPGItemRegister.RPGItemData;
import mixac1.dangerrpg.util.IMultiplier.Multiplier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class GemPassiveAttribute extends Gem
{
    protected final EAWithIAttr stat;
    protected final GemType gemType;

    protected final float startValue;
    protected final Multiplier mul;

    public GemPassiveAttribute(String name, EAWithIAttr stat, GemType gemType, float startValue, Multiplier mul)
    {
        super(name);
        this.stat = stat;
        this.gemType = gemType;
        this.startValue = startValue;
        this.mul = mul;
    }

    @Override
    public GemType getGemType()
    {
        return gemType;
    }

    @Override
    public void registerAttributes(Item item, RPGItemData map)
    {
        super.registerAttributes(item, map);
        map.registerIADynamic(GemAttributes.VALUE, startValue, mul);
    }

    public void activate(ItemStack gem, EntityPlayer player)
    {
        if (GemAttributes.VALUE.hasIt(gem) && GemAttributes.UUID.hasIt(gem)) {
            stat.setModificatorValue(GemAttributes.VALUE.get(gem, player), player, GemAttributes.UUID.getUUID(gem));
        }
    }

    public void deactivate(ItemStack gem, EntityPlayer player)
    {
        if (GemAttributes.UUID.hasIt(gem)) {
            stat.removeModificator(player, GemAttributes.UUID.getUUID(gem));
        }
    }
}
