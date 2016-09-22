package mixac1.dangerrpg.init;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.FMLInjectionData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.item.ItemAttribute;
import mixac1.dangerrpg.capability.RPGableItem.ItemData;
import mixac1.dangerrpg.capability.RPGableItem.ItemData.ItemAttrParams;
import mixac1.dangerrpg.client.gui.GuiMode;
import mixac1.dangerrpg.util.IMultiplier.IMulConfigurable;
import mixac1.dangerrpg.util.IMultiplier.MulType;
import mixac1.dangerrpg.util.RPGHelper;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
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

        public static HashSet<String> activeRPGItems = new HashSet<String>();

        public ItemConfig(String fileName)
        {
            super(fileName);
            category.setComment("FAQ:\n"
                    + "Q: How do activate RPG item?\n"
                    + "A: Take name of item frome the 'itemList' and put it to the 'activeRPGItems' list.\n"
                    + "Or you can enabled flag 'isAllItemsRPGable' for active all items.\n"
                    + "\n"
                    + "Q: How do congigure any item?\n"
                    + "A: Take name of item frome the 'itemList' and put it to the 'needCustomSetting' list.\n"
                    + "After this, run the game, close and reopen this config.\n"
                    + "You can find generated element for configue that item.");
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
            Property prop = getPropertyStrings("activeRPGItems", names.toArray(new String[names.size()]),
                    "Set active RPG items (activated if 'isAllItemsRPGable' is false) (true/false)", false);
            if (!isAllItemsRPGable) {
                activeRPGItems = new HashSet<String>(Arrays.asList(prop.getStringList()));
            }

            save();
        }

        @Override
        public void postLoadPost()
        {
            HashMap<Item, ItemData> map = RPGCapability.rpgItemRegistr.getActiveElements();

            customConfig(map);

            ArrayList<String> names = RPGHelper.getItemNames(map.keySet(), true);
            getPropertyStrings("activeRPGItems", names.toArray(new String[names.size()]),
                    "Set active RPG items (activated if 'isAllItemsRPGable' is false) (true/false)", true);

            names = RPGHelper.getItemNames(RPGCapability.rpgItemRegistr.keySet(), true);
            getPropertyStrings("itemList", names.toArray(new String[names.size()]),
                    "List of all items, which can be RPGable", true);

            save();
        }

        protected void customConfig(HashMap<Item, ItemData> map)
        {
            String str = "customSetting";

            Property prop = getPropertyStrings("needCustomSetting", new String[] {Items.diamond_sword.delegate.name()},
                    "Set items, which needs customization", true);
            HashSet<String> needCustomSetting = new HashSet<String>(Arrays.asList(prop.getStringList()));

            if (!needCustomSetting.isEmpty()) {
                for (Entry<Item, ItemData> item : map.entrySet()) {
                    if (needCustomSetting.contains(item.getKey().delegate.name())) {
                        for (Entry<ItemAttribute, ItemAttrParams> ia : item.getValue().map.entrySet()) {
                            String cat = Utils.toString(str, ".", item.getKey().delegate.name());
                            ia.getValue().value = getRPGAttributeValue(cat, ia);
                            if (ia.getValue().mul != null) {
                                ia.getValue().mul = getRPGMultiplier(cat, ia);
                            }
                        }
                    }
                }
            }
        }

        protected float getRPGAttributeValue(String category, Entry<ItemAttribute, ItemAttrParams> attr)
        {
            Property prop = config.get(category, attr.getKey().name, attr.getValue().value);
            float value = (float) prop.getDouble();
            if (attr.getKey().isValid(value)) {
                return value;
            }
            else {
                prop.set(attr.getValue().value);
                return attr.getValue().value;
            }
        }

        protected IMulConfigurable getRPGMultiplier(String category, Entry<ItemAttribute, ItemAttrParams> attr)
        {
            String def = attr.getValue().mul.toString();
            Property prop = config.get(category, attr.getKey().name.concat(".mul"), def);
            String str = prop.getString();

            if (!def.equals(str)) {
                String[] strs = str.split(" ");
                if (strs.length == 2) {
                    MulType type = MulType.valueOf(strs[0].toUpperCase());
                    Float value = Float.valueOf(strs[1]);
                    if (type != null && value != null) {
                        return type.getMul(value);
                    }
                }
            }

            prop.set(def);
            return attr.getValue().mul;
        }
    }

    public static class EntityConfig extends RPGConfigCommon
    {
        public static boolean isAllEntitiesRPGable = false;

        public static HashSet<String> activeRPGEntities = new HashSet<String>();

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
            Property prop = getPropertyStrings("activeRPGEntities", names.toArray(new String[names.size()]),
                    "Set active RPG entities (activated if 'isAllEntitiesRPGable' is false) (true/false)", false);
            if (!isAllEntitiesRPGable) {
                activeRPGEntities = new HashSet<String>(Arrays.asList(prop.getStringList()));
            }
            save();
        }

        @Override
        public void postLoadPost()
        {
            ArrayList<String> names = RPGHelper.getEntityNames(RPGCapability.rpgEntityRegistr.getActiveElements().keySet(), true);
            getPropertyStrings("activeRPGEntities", names.toArray(new String[names.size()]),
                    "Set active RPG entities (activated if 'isAllEntitiesRPGable' is false) (true/false)", true);

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

            Property prop = config.get(cat.getQualifiedName(), "list", defValue);
            prop.comment = comment != null ? comment : "";
            return prop;
        }

        protected int getInteger(String category, String field, int defValue, String comment)
        {
            Property prop = config.get(category, field, defValue);
            prop.comment = comment != null ? comment : "";
            return prop.getInt(defValue);
        }

        protected double getDouble(String category, String field, double defValue, String comment)
        {
            Property prop = config.get(category, field, defValue);
            prop.comment = comment != null ? comment : "";
            return prop.getDouble(defValue);
        }

        protected boolean getBoolean(String category, String field, boolean defValue, String comment)
        {
            Property prop = config.get(category, field, defValue);
            prop.comment = comment != null ? comment : "";
            return prop.getBoolean(defValue);
        }

        protected String getString(String category, String field, String defValue, String comment)
        {
            Property prop = config.get(category, field, defValue);
            prop.comment = comment != null ? comment : "";
            return prop.getString();
        }

        protected int getInteger(String field, int defValue, String comment)
        {
            return this.getInteger(category.getQualifiedName(), field, defValue, comment);
        }

        protected double getDouble(String field, double defValue, String comment)
        {
            return this.getDouble(category.getQualifiedName(), field, defValue, comment);
        }

        protected boolean getBoolean(String field, boolean defValue, String comment)
        {
            return this.getBoolean(category.getQualifiedName(), field, defValue, comment);
        }

        protected String getString(String field, String defValue, String comment)
        {
            return this.getString(field, defValue, comment);
        }
    }
}
