package mixac1.dangerrpg.init;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.FMLInjectionData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.client.gui.GuiMode;
import mixac1.dangerrpg.util.RPGHelper;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class RPGConfig
{
    public static File dir;

    private static ArrayList<RPGConfigCommon> configs = new ArrayList<RPGConfigCommon>();

    public static void load(FMLPreInitializationEvent e)
    {
        dir = new File((File) FMLInjectionData.data()[6], "config/".concat(DangerRPG.MODID));
        if (dir.exists()) {
            if (!dir.isDirectory()) {
                dir.delete();
            }
        }
        else {
            dir.mkdir();
        }

        configs.add(new MainConfig("MainConfig"));
        configs.add(new ItemConfig("ItemConfig"));
        configs.add(new EntityConfig("EntityConfig"));
    }

    @SideOnly(Side.CLIENT)
    public static void loadClient(FMLPreInitializationEvent e)
    {
        configs.add(new ClientConfig("ClientConfig"));
    }

    public static void postLoadPre(FMLPostInitializationEvent e)
    {
        for (RPGConfigCommon config : configs) {
            config.postLoadPre();
        }
    }

    public static void postLoadPost(FMLPostInitializationEvent e)
    {
        for (RPGConfigCommon config : configs) {
            config.postLoadPost();
        }
    }

    public static class MainConfig extends RPGConfigCommon
    {
        public static boolean   mainEnableInfoLog   = true;
        public static int       playerLoseLvlCount  = 3;

        public MainConfig(String fileName)
        {
            super(fileName);
        }

        @Override
        public void load()
        {
            mainEnableInfoLog = getBoolean("mainEnableInfoLog", mainEnableInfoLog,
                    "Enable writing info message to log (true/false)");

            playerLoseLvlCount = getInteger("playerLoseLvlCount", playerLoseLvlCount,
                    "Set number of lost points of level when player die");

            save();
        }
    }

    @SideOnly(Side.CLIENT)
    public static class ClientConfig extends RPGConfigCommon
    {
        public static boolean   guiIsEnableHUD          = true;
        public static int       guiPlayerHUDOffsetX     = 10;
        public static int       guiPlayerHUDOffsetY     = 10;
        public static boolean   guiPlayerHUDIsInvert    = false;
        public static int       guiEnemyHUDOffsetX      = 10;
        public static int       guiEnemyHUDOffsetY      = 10;
        public static boolean   guiEnemyHUDIsInvert     = true;
        public static int       guiChargeOffsetX        = 0;
        public static int       guiChargeOffsetY        = 45;
        public static boolean   guiChargeIsCentered     = true;
        public static boolean   guiTwiceHealthManaBar   = true;
        public static int       guiDafaultHUDMode       = 1;
        public static int       guiDamageForTestArmor   = 25;

        public static boolean   neiShowShapedRecipe     = false;

        public ClientConfig(String fileName)
        {
            super(fileName);
        }

        @Override
        public void load()
        {
            guiIsEnableHUD = getBoolean("guiIsEnableHUD", guiIsEnableHUD,
                    "Enable RPG HUD (true/false)");

            guiPlayerHUDOffsetX = getInteger("guiPlayerHUDOffsetX", guiPlayerHUDOffsetX,
                    "Change X offset of player's HUD");

            guiPlayerHUDOffsetY = getInteger("guiPlayerHUDOffsetY", guiPlayerHUDOffsetY,
                    "Change Y offset of player's HUD");

            guiPlayerHUDIsInvert = getBoolean("guiPlayerHUDIsInvert", guiPlayerHUDIsInvert,
                    "Change side of player's HUD (true/false)");

            guiEnemyHUDOffsetX = getInteger("guiEnemyHUDOffsetX", guiEnemyHUDOffsetX,
                    "Change X offset of enemy's HUD");

            guiEnemyHUDOffsetY = getInteger("guiEnemyHUDOffsetY", guiEnemyHUDOffsetY,
                    "Change Y offset of enemy's HUD");

            guiEnemyHUDIsInvert = getBoolean("guiEnemyHUDIsInvert", guiEnemyHUDIsInvert,
                    "Change side of enemy's HUD (true/false)");

            guiChargeOffsetX = getInteger("guiChargeOffsetX", guiChargeOffsetX,
                    "Change X offset of charge bar");

            guiChargeOffsetY = getInteger("guiChargeOffsetY", guiChargeOffsetY,
                    "Change Y offset of charge bar");

            guiChargeIsCentered = getBoolean("guiChargeIsCentered", guiChargeIsCentered,
                    "Charge bar need centering (true/false)");

            guiTwiceHealthManaBar = getBoolean("guiTwiceHealthManaBar", guiTwiceHealthManaBar,
                    "Twice health-mana bar (true/false)");

            guiDamageForTestArmor = getInteger("guiDamageForTestArmor", guiDamageForTestArmor,
                    "Default damage value for calculate resistance in armor bar.");

            guiDafaultHUDMode = getInteger("guiDafaultHUDMode", guiDafaultHUDMode,
                    "Set default HUD mode:\n[0] - normal\n[1] - normal digital\n[2] - simple\n[3] - simple digital");
            GuiMode.set(guiDafaultHUDMode);

            neiShowShapedRecipe = getBoolean("neiShowShapedRecipe", neiShowShapedRecipe,
                    "Is show default recipes in RPG workbench (need NEI) (true/false)");

            save();
        }
    }

    public static class ItemConfig extends RPGConfigCommon
    {
        public static boolean   isAllItemsRPGable   = false;
        public static boolean   canUpInTable        = true;
        public static int       maxLevel            = 100;
        public static int       startMaxExp         = 100;
        public static float     expMul              = 1.15f;

        public static HashSet<String> supportedRPGItems = new HashSet<String>();

        public ItemConfig(String fileName)
        {
            super(fileName);
        }

        @Override
        public void load()
        {
            isAllItemsRPGable = getBoolean("isAllItemsRPGable", isAllItemsRPGable,
                    "All weapons, tools , armors are RPGable (dangerous) (true/false)");

            canUpInTable = getBoolean("canUpInTable", canUpInTable,
                    "Items can be upgrade in LevelUp Table without creative mode (true/false) \nLevelUp Table is invisible now");

            maxLevel = getInteger("maxLevel", maxLevel,
                    "Set max level of RPG items");

            startMaxExp = getInteger("startMaxExp", startMaxExp,
                    "Set start needed expirience for RPG items");

            expMul = (float) getDouble("expMul", expMul,
                    "Set expirience multiplier for RPG items");

            save();
        }

        @Override
        public void postLoadPre()
        {
            ArrayList<String> names = RPGHelper.getItemNames(RPGCapability.rpgItemRegistr.keySet(), true);
            Property prop = getPropertyStrings("supportedRPGItems", names.toArray(new String[names.size()]),
                    "Set supported RPG items (activated if 'isAllItemsRPGable' is false) (true/false)", false);
            if (!isAllItemsRPGable) {
                supportedRPGItems = new HashSet<String>(Arrays.asList(prop.getStringList()));
            }

            save();
        }

        @Override
        public void postLoadPost()
        {
            ArrayList<String> names = RPGHelper.getItemNames(RPGCapability.rpgItemRegistr.getActiveElements().keySet(), true);
            getPropertyStrings("supportedRPGItems", names.toArray(new String[names.size()]), null, true);

            names = RPGHelper.getItemNames(RPGCapability.rpgItemRegistr.keySet(), true);
            getPropertyStrings("itemList", names.toArray(new String[names.size()]),
                    "List of all items, which can be RPGable", true);

            save();
        }
    }

    public static class EntityConfig extends RPGConfigCommon
    {
        public static boolean isAllEntitiesRPGable = false;

        public static HashSet<String> supportedRPGEntities = new HashSet<String>();

        public EntityConfig(String fileName)
        {
            super(fileName);
        }

        @Override
        public void load()
        {
            isAllEntitiesRPGable = getBoolean("isAllEntitiesRPGable", isAllEntitiesRPGable,
                    "All entities are RPGable (true/false)");

            save();
        }

        @Override
        public void postLoadPre()
        {
            ArrayList<String> names = RPGHelper.getEntityNames(RPGCapability.rpgEntityRegistr.keySet(), true);
            Property prop = getPropertyStrings("supportedRPGEntities", names.toArray(new String[names.size()]),
                    "Set supported RPG entities (activated if 'isAllEntitiesRPGable' is false) (true/false)", false);
            if (!isAllEntitiesRPGable) {
                supportedRPGEntities = new HashSet<String>(Arrays.asList(prop.getStringList()));
            }
            save();
        }

        @Override
        public void postLoadPost()
        {
            ArrayList<String> names = RPGHelper.getEntityNames(RPGCapability.rpgEntityRegistr.getActiveElements().keySet(), true);
            getPropertyStrings("supportedRPGEntities", names.toArray(new String[names.size()]), null, true);

            names = RPGHelper.getEntityNames(RPGCapability.rpgEntityRegistr.keySet(), true);
            getPropertyStrings("entityList", names.toArray(new String[names.size()]),
                    "List of all entities, which can be RPGable", true);

            save();
        }
    }

    public static abstract class RPGConfigCommon
    {
        protected Configuration config;
        protected ConfigCategory category;

        protected RPGConfigCommon(String fileName)
        {
            config = new Configuration(new File(dir, fileName.concat(".cfg")), DangerRPG.VERSION, true);

            category = config.getCategory(fileName);
            category.setRequiresMcRestart(true);
            category.setShowInGui(true);

            load();
        }

        protected void load() {}

        public void postLoadPre() {}

        public void postLoadPost() {}

        public void save()
        {
            if (config.hasChanged()) {
                config.save();
            }
        }

        protected Property getPropertyStrings(String categoryName, String[] defValue, String comment, boolean needClear)
        {

            ConfigCategory cat = config.getCategory(categoryName);
            if (needClear) {
                cat.clear();
            }

            Property prop = config.get(cat.getName(), "list", defValue);
            prop.comment = comment != null ? comment : needClear ? "" : prop.comment;
            return prop;
        }

        protected int getInteger(String field, int defValue, String comment)
        {
            Property prop = config.get(category.getName(), field, defValue);
            prop.comment = comment != null ? comment : "";
            return prop.getInt(defValue);
        }

        protected double getDouble(String field, double defValue, String comment)
        {
            Property prop = config.get(category.getName(), field, defValue);
            prop.comment = comment != null ? comment : "";
            return prop.getDouble(defValue);
        }

        protected boolean getBoolean(String field, boolean defValue, String comment)
        {
            Property prop = config.get(category.getName(), field, defValue);
            prop.comment = comment != null ? comment : "";
            return prop.getBoolean(defValue);
        }
    }
}
