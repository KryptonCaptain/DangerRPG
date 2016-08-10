package mixac1.dangerrpg.item.tool;

import java.util.HashMap;

import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.api.item.ILvlableItem.ILvlableItemTool;
import mixac1.dangerrpg.api.item.ItemAttribute;
import mixac1.dangerrpg.capability.LvlableItem;
import mixac1.dangerrpg.capability.LvlableItem.ItemAttrParams;
import mixac1.dangerrpg.init.RPGItems;
import mixac1.dangerrpg.init.RPGOther;
import mixac1.dangerrpg.item.IHasBooksInfo;
import mixac1.dangerrpg.item.RPGItemComponent;
import mixac1.dangerrpg.item.RPGItemComponent.RPGToolComponent;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;

public class RPGSpade extends ItemSpade implements ILvlableItemTool, IHasBooksInfo
{
    public RPGSpade(ToolMaterial toolMaterial)
    {
        super(toolMaterial);
        setUnlocalizedName(RPGItems.getRPGName(getItemComponent(this), getToolMaterial(this)));
        setTextureName(Utils.toString(DangerRPG.MODID, ":tools/", unlocalizedName));
        setCreativeTab(RPGOther.tabDangerRPG);
        setMaxStackSize(1);
    }

    @Override
    public String getInformationToInfoBook(ItemStack item, EntityPlayer player)
    {
        return DangerRPG.trans("rpgstr.no_info_yet");
    }

    @Override
    public RPGToolComponent getItemComponent(Item item)
    {
        return RPGItemComponent.SHOVEL;
    }

    @Override
    public ToolMaterial getToolMaterial(Item item)
    {
        return toolMaterial;
    }

    @Override
    public void registerAttributes(Item item, HashMap<ItemAttribute, ItemAttrParams> map)
    {
        LvlableItem.registerParamsItemTool(item, map);
    }
}
