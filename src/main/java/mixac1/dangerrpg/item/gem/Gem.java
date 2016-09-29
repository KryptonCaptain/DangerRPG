package mixac1.dangerrpg.item.gem;

import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.item.GemType;
import mixac1.dangerrpg.api.item.IRPGItem;
import mixac1.dangerrpg.capability.data.RPGItemRegister.RPGItemData;
import mixac1.dangerrpg.capability.ia.ItemAttributes;
import mixac1.dangerrpg.init.RPGConfig.ItemConfig;
import mixac1.dangerrpg.init.RPGOther.RPGCreativeTabs;
import mixac1.dangerrpg.item.IHasBooksInfo;
import mixac1.dangerrpg.util.IMultiplier.MultiplierAdd;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class Gem extends Item implements IRPGItem, IHasBooksInfo
{
    protected static final MultiplierAdd LVL_STEP = new MultiplierAdd((float) ItemConfig.d.gemLvlUpStep);

    public Gem(String name)
    {
        super();
        this.setTextureName(Utils.toString(DangerRPG.MODID, ":gems/", name));
        this.setUnlocalizedName(name);
        this.setMaxStackSize(1);
        this.setCreativeTab(RPGCreativeTabs.tabRPGAmunitions);
    }

    public abstract GemType getGemType();

    @Override
    public void registerAttributes(Item item, RPGItemData map)
    {
        map.addDynamicItemAttribute(ItemAttributes.LEVEL, ItemConfig.d.gemStartLvl, LVL_STEP);
    }

    @Override
    public String getInformationToInfoBook(ItemStack item, EntityPlayer player)
    {
        return null;
    }
}
