package mixac1.dangerrpg.init;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.api.event.GuiModeChangeEvent;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
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

    @SideOnly(Side.CLIENT)
    public static class GuiMode
    {
        static int curr = 0;

        public static void change()
        {
            if (++curr >= GuiModeType.values().length) {
                curr = 0;
            }
            MinecraftForge.EVENT_BUS.post(new GuiModeChangeEvent(curr()));
        }

        public static GuiModeType curr()
        {
            return GuiModeType.values()[curr];
        }

        public static boolean isIt(GuiModeType type)
        {
            return type.equals(curr());
        }

        public static enum GuiModeType
        {
            NORMAL          (false, false),
            NORMAL_DIGITAL  (true,  false),
            SIMPLE          (false, true),
            SIMPLE_DIGITAL  (true,  true),

            ;

            public boolean isDigital;
            public boolean isSimple;

            GuiModeType(boolean isDigital, boolean isSimple)
            {
                this.isDigital = isDigital;
                this.isSimple = isSimple;
            }
        }
    }
}
