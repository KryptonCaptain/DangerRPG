package mixac1.dangerrpg.item.weapon;

import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.item.IGemableItem;
import mixac1.dangerrpg.api.item.IRPGItem.IRPGItemTool;
import mixac1.dangerrpg.capability.GemType;
import mixac1.dangerrpg.capability.RPGableItem;
import mixac1.dangerrpg.capability.data.RPGItemRegister.RPGItemData;
import mixac1.dangerrpg.init.RPGItems;
import mixac1.dangerrpg.init.RPGOther.RPGCreativeTabs;
import mixac1.dangerrpg.item.IHasBooksInfo;
import mixac1.dangerrpg.item.RPGItemComponent.RPGToolComponent;
import mixac1.dangerrpg.item.RPGToolMaterial;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class ItemRPGWeapon extends ItemSword implements IRPGItemTool, IGemableItem, IHasBooksInfo
{
    private static final GemType[] gemTypes = new GemType[] {
            GemType.GEM_MODIFY_ATTACK_1,
            GemType.GEM_MODIFY_ATTACK_2,
            GemType.GEM_SPECIAL_ATTACK,
            GemType.GEM_DEFENCE
    };

    public RPGToolMaterial toolMaterial;
    public RPGToolComponent toolComponent;

    public ItemRPGWeapon(RPGToolMaterial toolMaterial, RPGToolComponent toolComponent)
    {
        super(toolMaterial.material);
        this.toolMaterial = toolMaterial;
        this.toolComponent = toolComponent;
        setUnlocalizedName(RPGItems.getRPGName(getItemComponent(this), getToolMaterial(this)));
        setTextureName(Utils.toString(DangerRPG.MODID, ":weapons/melee/", unlocalizedName));
        setCreativeTab(RPGCreativeTabs.tabRPGAmunitions);
        setMaxStackSize(1);
    }

    public ItemRPGWeapon(RPGToolMaterial toolMaterial, RPGToolComponent toolComponent, String name)
    {
        this(toolMaterial, toolComponent);
        setUnlocalizedName(name);
        setTextureName(DangerRPG.MODID + ":weapons/melee/" + unlocalizedName);
    }

    @Override
    public GemType[] getGemTypes(ItemStack stack)
    {
        return gemTypes;
    }

    @Override
    public String getInformationToInfoBook(ItemStack item, EntityPlayer player)
    {
        return DangerRPG.trans("rpgstr.no_info_yet");
    }

    @Override
    public RPGToolComponent getItemComponent(Item item)
    {
        return toolComponent;
    }

    @Override
    public RPGToolMaterial getToolMaterial(Item item)
    {
        return toolMaterial;
    }

    @Override
    public void registerAttributes(Item item, RPGItemData map)
    {
        RPGableItem.registerParamsItemSword(item, map);
    }
}
