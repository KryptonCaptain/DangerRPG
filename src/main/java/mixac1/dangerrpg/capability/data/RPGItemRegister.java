package mixac1.dangerrpg.capability.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;

import mixac1.dangerrpg.api.item.IADynamic;
import mixac1.dangerrpg.api.item.IAStatic;
import mixac1.dangerrpg.api.item.IRPGItem;
import mixac1.dangerrpg.api.item.ItemAttribute;
import mixac1.dangerrpg.capability.data.RPGDataRegister.ElementData;
import mixac1.dangerrpg.capability.data.RPGItemRegister.ItemAttrParams;
import mixac1.dangerrpg.capability.data.RPGItemRegister.RPGItemData;
import mixac1.dangerrpg.init.RPGCapability;
import mixac1.dangerrpg.util.IMultiplier.IMulConfigurable;
import net.minecraft.item.Item;

public class RPGItemRegister extends RPGDataRegister<Item, RPGItemData, Integer, HashMap<Integer, ItemAttrParams>>
{
    @Override
    protected Integer codingKey(Item key)
    {
        return Item.getIdFromItem(key);
    }

    @Override
    protected Item decodingKey(Integer key)
    {
        return Item.getItemById(key);
    }

    /******************************************************************************************************/

    public static class RPGItemData extends ElementData<Item, HashMap<Integer, ItemAttrParams>>
    {
        public HashMap<ItemAttribute, ItemAttrParams> attributes = new LinkedHashMap<ItemAttribute, ItemAttrParams>();
        public IRPGItem rpgComponent;

        public RPGItemData(IRPGItem lvlComponent, boolean isSupported)
        {
            this.rpgComponent = lvlComponent;
            this.isSupported = isSupported;
        }

        public void addStaticItemAttribute(IAStatic attr, float value)
        {
            attributes.put(attr, new ItemAttrParams(value, null));
        }

        public void addDynamicItemAttribute(IADynamic attr, float value, IMulConfigurable mul)
        {
            attributes.put(attr, new ItemAttrParams(value, mul));
        }

        @Override
        public HashMap<Integer, ItemAttrParams> getTransferData(Item key)
        {
            HashMap<Integer, ItemAttrParams> tmp = new HashMap<Integer, ItemAttrParams>();
            for (Entry<ItemAttribute, ItemAttrParams> entry : attributes.entrySet()) {
                tmp.put(entry.getKey().hash, entry.getValue());
            }
            return tmp;
        }

        @Override
        public void unpackTransferData(HashMap<Integer, ItemAttrParams> data)
        {
            for (Entry<Integer, ItemAttrParams> entry : data.entrySet()) {
                if (RPGCapability.mapIntToItemAttribute.containsKey(entry.getKey())) {
                    ItemAttribute attr = RPGCapability.mapIntToItemAttribute.get(entry.getKey());
                    if (attributes.containsKey(attr)) {
                        ItemAttrParams tmp = attributes.get(attr);
                        tmp.value = entry.getValue().value;
                        tmp.mul = entry.getValue().mul;
                    }
                }
            }
        }
    }

    public static class ItemAttrParams implements Serializable
    {
        public float value;
        public IMulConfigurable mul;

        public ItemAttrParams(float value, IMulConfigurable mul)
        {
            this.value = value;
            this.mul = mul;
        }

        public float up(float value)
        {
            return mul.up(value);
        }
    }
}