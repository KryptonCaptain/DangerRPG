package mixac1.dangerrpg.init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class RPGOther
{
    public static CreativeTabs tabDangerRPG = (new CreativeTabs("tabDangerRPG") {
        @Override
        public Item getTabIconItem() {
            return Items.arrow;
        }
    });
}
