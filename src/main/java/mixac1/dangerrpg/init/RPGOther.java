package mixac1.dangerrpg.init;

import java.util.Random;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.EnumHelper;

public abstract class RPGOther
{
    public static Random rand = new Random();

    public static class RPGCreativeTabs
    {
        public static CreativeTabs tabRPGAmunitions = (new CreativeTabs("tabRPGAmunitions")
        {
            @Override
            public Item getTabIconItem() {
                return RPGItems.scytheBlackMatter;
            }
        });

        public static CreativeTabs tabRPGGems = (new CreativeTabs("tabRPGGems")
        {
            @Override
            public Item getTabIconItem() {
                return RPGItems.scytheBlackMatter;
            }
        });

        public static CreativeTabs tabRPGBlocks = (new CreativeTabs("tabRPGBlocks")
        {
            @Override
            public Item getTabIconItem() {
                return Item.getItemFromBlock(RPGBlocks.syntheticBedrock);
            }
        });

        public static CreativeTabs tabRPGItems = (new CreativeTabs("tabRPGItems")
        {
            @Override
            public Item getTabIconItem() {
                return RPGItems.magicLeather;
            }
        });
    }

    public static class RPGDamageSource
    {
        public static DamageSource phisic = (new DamageSource("phisicRPG"));

        public static DamageSource magic = (new DamageSource("magicRPG")).setMagicDamage();

        public static DamageSource clear = (new DamageSource("clearRPG")).setDamageBypassesArmor();
    }

    public static class RPGItemRarity
    {
        public static EnumRarity common     = EnumHelper.addRarity("common", EnumChatFormatting.WHITE, "Common");

        public static EnumRarity uncommon   = EnumHelper.addRarity("uncommon", EnumChatFormatting.GREEN, "Uncommon");

        public static EnumRarity rare       = EnumHelper.addRarity("rare", EnumChatFormatting.BLUE, "Rare");

        public static EnumRarity mythic     = EnumHelper.addRarity("mythic", EnumChatFormatting.DARK_PURPLE, "Mythic");

        public static EnumRarity epic       = EnumHelper.addRarity("epic", EnumChatFormatting.DARK_RED, "Epic");

        public static EnumRarity legendary  = EnumHelper.addRarity("legendary", EnumChatFormatting.GOLD, "Legendary");
    }
}
