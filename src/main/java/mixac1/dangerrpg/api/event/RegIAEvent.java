package mixac1.dangerrpg.api.event;

import cpw.mods.fml.common.eventhandler.Event;
import mixac1.dangerrpg.capability.LvlableItem.ItemAttributesMap;
import net.minecraft.item.Item;

public class RegIAEvent extends Event
{
    public Item item;
    public ItemAttributesMap map;

    public RegIAEvent(Item item, ItemAttributesMap map)
    {
        this.item = item;
        this.map = map;
    }

    public static class DefaultIAEvent extends RegIAEvent
    {
        public DefaultIAEvent(Item item, ItemAttributesMap map)
        {
            super(item, map);
        }
    }

    public static class ItemModIAEvent extends RegIAEvent
    {
        public ItemModIAEvent(Item item, ItemAttributesMap map)
        {
            super(item, map);
        }
    }

    public static class ItemSwordIAEvent extends RegIAEvent
    {
        public ItemSwordIAEvent(Item item, ItemAttributesMap map)
        {
            super(item, map);
        }
    }

    public static class ItemToolIAEvent extends RegIAEvent
    {
        public ItemToolIAEvent(Item item, ItemAttributesMap map)
        {
            super(item, map);
        }
    }

    public static class ItemArmorIAEvent extends RegIAEvent
    {
        public ItemArmorIAEvent(Item item, ItemAttributesMap map)
        {
            super(item, map);
        }
    }

    public static class ItemBowIAEvent extends RegIAEvent
    {
        public ItemBowIAEvent(Item item, ItemAttributesMap map)
        {
            super(item, map);
        }
    }
}
