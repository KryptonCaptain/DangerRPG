package mixac1.dangerrpg.event;

import java.util.HashMap;

import cpw.mods.fml.common.eventhandler.Event;
import mixac1.dangerrpg.api.item.ItemAttribute;
import mixac1.dangerrpg.capability.LvlableItem.ItemAttrParams;
import net.minecraft.item.Item;

public class RegIAEvent extends Event
{
    public Item item;
    public HashMap<ItemAttribute, mixac1.dangerrpg.capability.LvlableItem.ItemAttrParams> map;

    public RegIAEvent(Item item, HashMap<ItemAttribute, ItemAttrParams> map)
    {
        this.item = item;
        this.map = map;
    }

    public static class DefaultIAEvent extends RegIAEvent
    {
        public DefaultIAEvent(Item item, HashMap<ItemAttribute, ItemAttrParams> map)
        {
            super(item, map);
        }
    }

    public static class ItemModIAEvent extends RegIAEvent
    {
        public ItemModIAEvent(Item item, HashMap<ItemAttribute, ItemAttrParams> map)
        {
            super(item, map);
        }
    }

    public static class ItemSwordIAEvent extends RegIAEvent
    {
        public ItemSwordIAEvent(Item item, HashMap<ItemAttribute, ItemAttrParams> map)
        {
            super(item, map);
        }
    }

    public static class ItemToolIAEvent extends RegIAEvent
    {
        public ItemToolIAEvent(Item item, HashMap<ItemAttribute, ItemAttrParams> map)
        {
            super(item, map);
        }
    }

    public static class ItemArmorIAEvent extends RegIAEvent
    {
        public ItemArmorIAEvent(Item item, HashMap<ItemAttribute, ItemAttrParams> map)
        {
            super(item, map);
        }
    }

    public static class ItemBowIAEvent extends RegIAEvent
    {
        public ItemBowIAEvent(Item item, HashMap<ItemAttribute, ItemAttrParams> map)
        {
            super(item, map);
        }
    }
}
