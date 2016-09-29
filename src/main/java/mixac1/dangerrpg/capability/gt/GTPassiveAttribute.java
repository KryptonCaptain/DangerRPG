package mixac1.dangerrpg.capability.gt;

import mixac1.dangerrpg.api.item.GemType;
import mixac1.dangerrpg.item.gem.Gem;
import net.minecraft.entity.player.EntityPlayer;

public class GTPassiveAttribute extends GemType
{
    public GTPassiveAttribute(String name)
    {
        super(name);
    }

    @Override
    public void activate(Gem gem, EntityPlayer player)
    {

    }

    public static class GTPAWeapon extends GTPassiveAttribute
    {
        public GTPAWeapon(String name)
        {
            super(name);
        }
    }

    public static class GTPAArmor extends GTPassiveAttribute
    {
        public GTPAArmor(String name)
        {
            super(name);
        }
    }
}
