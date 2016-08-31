package mixac1.dangerrpg.init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;

public abstract class RPGOther
{
    public static CreativeTabs tabDangerRPG = (new CreativeTabs("tabDangerRPG")
    {
        @Override
        public Item getTabIconItem() {
            return Items.arrow;
        }
    });

    public static DamageSource phisic = (new DamageSource("phisicRPG"));

    public static DamageSource magic = (new DamageSource("magicRPG")).setMagicDamage();

    public static DamageSource clear = (new DamageSource("clearRPG")).setDamageBypassesArmor();
}
