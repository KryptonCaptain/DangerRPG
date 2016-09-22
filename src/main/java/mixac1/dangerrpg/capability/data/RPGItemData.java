package mixac1.dangerrpg.capability.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import mixac1.dangerrpg.api.item.IADynamic;
import mixac1.dangerrpg.api.item.IAStatic;
import mixac1.dangerrpg.api.item.IRPGItem;
import mixac1.dangerrpg.api.item.ItemAttribute;
import mixac1.dangerrpg.capability.data.RPGItemData.ItemAttrParams;
import mixac1.dangerrpg.capability.data.RPGDataRegister.ElementData;
import mixac1.dangerrpg.init.RPGCapability;
import mixac1.dangerrpg.util.IMultiplier.IMulConfigurable;

public class RPGItemData extends ElementData<HashMap<Integer, ItemAttrParams>>
{
    public HashMap<ItemAttribute, ItemAttrParams> map = new LinkedHashMap<ItemAttribute, ItemAttrParams>();
    public IRPGItem rpgComponent;

    public RPGItemData(IRPGItem lvlComponent, boolean isSupported)
    {
        this.rpgComponent = lvlComponent;
        this.isSupported = isSupported;
    }

    public void addStaticItemAttribute(IAStatic attr, float value)
    {
        map.put(attr, new ItemAttrParams(value, null));
    }

    public void addDynamicItemAttribute(IADynamic attr, float value, IMulConfigurable mul)
    {
        map.put(attr, new ItemAttrParams(value, mul));
    }

    @Override
    public HashMap<Integer, ItemAttrParams> getTransferData()
    {
        HashMap<Integer, ItemAttrParams> tmp = new HashMap<Integer, ItemAttrParams>();
        for (Entry<ItemAttribute, ItemAttrParams> entry : map.entrySet()) {
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
                if (map.containsKey(attr)) {
                    map.get(attr).value = entry.getValue().value;
                    map.get(attr).mul = entry.getValue().mul;
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
