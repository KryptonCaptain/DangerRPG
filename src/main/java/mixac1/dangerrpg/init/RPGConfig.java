package mixac1.dangerrpg.init;

import java.util.Arrays;
import java.util.HashSet;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import mixac1.dangerrpg.DangerRPG;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public abstract class RPGConfig
{
    public static Configuration config;

    /* MAIN */
    public static boolean   mainEnableModGui;
    public static boolean   mainEnableInfoLog;

    /* PLAYER */
    public static int       playerLoseLvlCount;

    /* ENTITY */
    public static boolean   entityAllEntityRPG;

    public static HashSet<String> entitySupportedRPGEntities = new HashSet<String>();

    /* ITEM */
    public static boolean   itemAllItemsLvlable;
    public static boolean   itemCanUpInTable;
    public static int       itemMaxLevel;
    public static int       itemStartMaxExp;
    public static float     itemExpMul;

    public static HashSet<String> itemSupportedLvlItems = new HashSet<String>();

    public static void load(FMLPreInitializationEvent e)
    {
        config = new Configuration(e.getSuggestedConfigurationFile(), DangerRPG.VERSION, true);
        config.load();

        initMainCategory();
        initPlayerCategory();
        initEntityCategory();
        initItemCategory();

        if (config.hasChanged()) {
            config.save();
        }
    }

    private static void initMainCategory()
    {
        ConfigCategory cat = config.getCategory("Main Category");
        cat.setRequiresMcRestart(true);
        cat.setShowInGui(true);

        mainEnableModGui = getBoolean(cat.getName(), "mainEnableModGui", true,
                "Enable Modify Gui");

        mainEnableInfoLog = getBoolean(cat.getName(), "mainEnableInfoLog", true,
                "Enable writing info message to log");
    }

    private static void initPlayerCategory()
    {
        ConfigCategory cat = config.getCategory("Player Category");
        cat.setRequiresMcRestart(true);
        cat.setShowInGui(true);
        Property prop;

        playerLoseLvlCount = getInteger(cat.getName(), "playerLoseLvlCount", 3,
                "Set number of lost points of level when player die");
    }

    private static void initEntityCategory()
    {
        ConfigCategory cat = config.getCategory("Entity Category");
        cat.setRequiresMcRestart(true);
        cat.setShowInGui(true);
        Property prop;

        entityAllEntityRPG = getBoolean(cat.getName(), "entityAllEntityRPG", true,
                "Are all entity RPGable?");

        prop = getPropertyStrings("Supported RPG entities", "entitySupportedRPGEntities", new String[] {},
                "Set supported RPGable entities (activated if 'entityAllEntityRPG' is false)", false);
        if (!entityAllEntityRPG) {
            entitySupportedRPGEntities = new HashSet<String>(Arrays.asList(prop.getStringList()));
        }
    }

    private static void initItemCategory()
    {
        ConfigCategory cat = config.getCategory("Item Category");
        cat.setRequiresMcRestart(true);
        cat.setShowInGui(true);
        Property prop;

        itemAllItemsLvlable = getBoolean(cat.getName(), "itemAllItemsLvlable", true,
                "Are all weapons, tools levelable?");

        itemCanUpInTable = getBoolean(cat.getName(), "itemCanUpInTable", true,
                "Can items upgrade in lvlup table without creative mode?");

        itemMaxLevel = getInteger(cat.getName(), "itemMaxLevel", 100,
                "Set items max level");

        itemStartMaxExp = getInteger(cat.getName(), "itemStartMaxExp", 100,
                "Set items start needed expirience");

        itemExpMul = (float) getDouble(cat.getName(), "itemExpMul", 1.15D,
                "Set items expirience multiplier");

        prop = getPropertyStrings("Supported Lvl items", "itemSupportedLvlItems", new String[] {},
                "Set supported lvlable items (activated if 'itemAllItemsLvlable' is false)", false);
        if (!itemAllItemsLvlable) {
            itemSupportedLvlItems = new HashSet<String>(Arrays.asList(prop.getStringList()));
        }
    }

    public static Property getPropertyStrings(String category, String field, String[] defValue, String comment, boolean needClear)
    {
        ConfigCategory cat = config.getCategory(category);
        if (needClear) {
            cat.clear();
        }
        Property prop = config.get(category, field, defValue);
        if (comment != null) {
            prop.comment = comment;
        }
        return prop;
    }

    private static int getInteger(String category, String field, int defValue, String comment)
    {
        Property prop = config.get(category, field, defValue);
        if (comment != null) {
            prop.comment = comment;
        }
        return prop.getInt(defValue);
    }

    private static double getDouble(String category, String field, double defValue, String comment)
    {
        Property prop = config.get(category, field, defValue);
        if (comment != null) {
            prop.comment = comment;
        }
        return prop.getDouble(defValue);
    }

    private static boolean getBoolean(String category, String field, boolean defValue, String comment)
    {
        Property prop = config.get(category, field, defValue);
        if (comment != null) {
            prop.comment = comment;
        }
        return prop.getBoolean(defValue);
    }
}

