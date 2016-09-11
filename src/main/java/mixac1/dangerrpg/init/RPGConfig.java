package mixac1.dangerrpg.init;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.FMLInjectionData;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.util.RPGCommonHelper;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public abstract class RPGConfig
{
    public static Configuration config;
    public static File dir;

    /* MAIN */
    public static boolean   mainEnableModGui;
    public static int       mainDamageForArmorBar;
    public static boolean   mainEnableInfoLog;
    public static boolean   mainShowShapedRecipe;

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

        dir = new File((File) FMLInjectionData.data()[6], "config/".concat(DangerRPG.MODID));
        if (dir.exists()) {
            if (!dir.isDirectory()) {
                dir.delete();
            }
        }
        else {
            dir.mkdir();
        }

        initMainCategory();
        initPlayerCategory();
        initEntityCategory();
        initItemCategory();

        if (config.hasChanged()) {
            config.save();
        }
    }

    public static void preLoadCapability(FMLPostInitializationEvent e)
    {
        Property prop;

        ArrayList<String> names = RPGCommonHelper.getItemNames(RPGCapability.lvlItemRegistr.data.keySet(), true);
        prop = getPropertyStrings("Supported Lvl items", "itemSupportedLvlItems", names.toArray(new String[names.size()]),
                "Set supported lvlable items (activated if 'itemAllItemsLvlable' is false)", false);
        if (!itemAllItemsLvlable) {
            itemSupportedLvlItems = new HashSet<String>(Arrays.asList(prop.getStringList()));
        }


        prop = getPropertyStrings("Supported RPG entities", "entitySupportedRPGEntities", new String[] {},
                "Set supported RPGable entities (activated if 'entityAllEntityRPG' is false)", false);
        if (!entityAllEntityRPG) {
            entitySupportedRPGEntities = new HashSet<String>(Arrays.asList(prop.getStringList()));
        }


        if (config.hasChanged()) {
            config.save();
        }
    }

    public static void postLoadCapability(FMLPostInitializationEvent e)
    {
        Property prop;

        ArrayList<String> names = RPGCommonHelper.getItemNames(RPGCapability.lvlItemRegistr.registr, true);
        RPGConfig.getPropertyStrings("Supported Lvl items", "itemSupportedLvlItems",
                names.toArray(new String[names.size()]), null, true);


        names = RPGCommonHelper.getEntityNames(RPGCapability.rpgEntityRegistr.registr, true);
        RPGConfig.getPropertyStrings("Supported RPG entities", "entitySupportedRPGEntities",
                names.toArray(new String[names.size()]), null, true);


        if (config.hasChanged()) {
            config.save();
        }

        PrintWriter file = createPrintWriter("AllEntityNames.txt");
        names = RPGCommonHelper.getEntityNames(RPGCapability.rpgEntityRegistr.data.keySet(), true);
        for (String str : names) {
            file.write(str.concat("\n"));
        }
        file.flush();
        file.close();

        file = createPrintWriter("AllItemNames.txt");
        names = RPGCommonHelper.getItemNames(RPGCapability.lvlItemRegistr.data.keySet(), true);
        for (String str : names) {
            file.write(str.concat("\n"));
        }
        file.flush();
        file.close();
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

        mainShowShapedRecipe = getBoolean(cat.getName(), "mainShowShapedRecipe", false,
                "Show default shape recipes in Shaped and Shapeless Crafting(need NEI)");

        mainDamageForArmorBar = getInteger(cat.getName(), "mainDamageForArmorBar", 25,
                "Damage count for calculate resistance in armor bar");
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
    }

    private static void initItemCategory()
    {
        ConfigCategory cat = config.getCategory("Item Category");
        cat.setRequiresMcRestart(true);
        cat.setShowInGui(true);
        Property prop;

        itemAllItemsLvlable = getBoolean(cat.getName(), "itemAllItemsLvlable", false,
                "Are all weapons, tools levelable?");

        itemCanUpInTable = getBoolean(cat.getName(), "itemCanUpInTable", true,
                "Can items upgrade in lvlup table without creative mode?");

        itemMaxLevel = getInteger(cat.getName(), "itemMaxLevel", 100,
                "Set items max level");

        itemStartMaxExp = getInteger(cat.getName(), "itemStartMaxExp", 100,
                "Set items start needed expirience");

        itemExpMul = (float) getDouble(cat.getName(), "itemExpMul", 1.15D,
                "Set items expirience multiplier");
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

    private static File createFile(String path)
    {
        File file = new File(dir, path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    private static PrintWriter createPrintWriter(String path)
    {
        try {
            return new PrintWriter(createFile(path));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}

