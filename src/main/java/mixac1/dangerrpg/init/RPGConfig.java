package mixac1.dangerrpg.init;

import java.util.Arrays;
import java.util.HashSet;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import mixac1.dangerrpg.DangerRPG;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class RPGConfig
{
    public static Configuration config;
    
    /* MAIN */
    public static boolean mainEnableModGui;
    
    /* PLAYER */
    public static int playerLoseLvlCount;
    
    /* ITEM */
    public static boolean itemAllItemsLvlable;
    public static boolean itemCanUpInTable;
    public static int itemMaxLevel;
    public static int itemStartMaxExp;
    public static float itemExpMul;
    public static int itemStartMagicArmor;
    public static HashSet<String> itemSupportedLvlItems = new HashSet<String>();
    
    public static void load(FMLPreInitializationEvent e)
    {
        config = new Configuration(e.getSuggestedConfigurationFile(), DangerRPG.VERSION, true);    
        config.load();
            
        initMainCategory();
        initPlayerCategory();
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
        Property prop;
        
        prop = config.get(cat.getName(), "mainEnableModGui", true);
        prop.comment = "Enable Modify Gui";
        mainEnableModGui = prop.getBoolean(true);
    }

    private static void initPlayerCategory()
    {
        ConfigCategory cat = config.getCategory("Player Category");
        cat.setRequiresMcRestart(true);
        cat.setShowInGui(true);
        Property prop;
        
        prop = config.get(cat.getName(), "playerLoseLvlCount", 3);
        prop.comment = "Set number of lost points of level when player die";
        playerLoseLvlCount = prop.getInt(3);
    }
    
    private static void initItemCategory()
    {
        ConfigCategory cat = config.getCategory("Item Category");
        cat.setRequiresMcRestart(true);
        cat.setShowInGui(true);
        Property prop;
        
        prop = config.get(cat.getName(), "itemAllItemsLvlable", true);
        prop.comment = "Are all weapons, tools levelable and gemable";
        itemAllItemsLvlable = prop.getBoolean(true);
        
        prop = config.get(cat.getName(), "itemCanUpInTable", true);
        prop.comment = "Can items upgrade in lvlup table without creative mode";
        itemCanUpInTable = prop.getBoolean(true);
        
        prop = config.get(cat.getName(), "itemMaxLevel", 100);
        prop.comment = "Set items max level";
        itemMaxLevel = prop.getInt(100);
        
        prop = config.get(cat.getName(), "itemStartMaxExp", 100);
        prop.comment = "Set items start needed expirience";
        itemStartMaxExp = prop.getInt(100);
        
        prop = config.get(cat.getName(), "itemExpMul", 1.15F);
        prop.comment = "Set items expirience multiplier";
        itemExpMul = (float) prop.getDouble(1.15F);
        
        prop = config.get(cat.getName(), "itemStartMagicArmor", 1);
        prop.comment = "Set default magic resistance";
        itemStartMagicArmor = prop.getInt(1);
        
        ConfigCategory cat1 = RPGConfig.config.getCategory("Supported Lvl items");
        prop = config.get(cat1.getName(), "itemSupportedLvlItems", new String[] {});
        prop.comment = "Set supported lvlable items (activated if 'itemAllItemsLvlable' is false)";
        if (!RPGConfig.itemAllItemsLvlable) {
            itemSupportedLvlItems = new HashSet<String>(Arrays.asList(prop.getStringList()));
        }
    }
}

