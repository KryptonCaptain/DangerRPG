package mixac1.dangerrpg.capability.data;

import java.util.HashMap;
import java.util.LinkedList;

import mixac1.dangerrpg.capability.data.RPGDataRegister.ElementData;
import mixac1.dangerrpg.capability.data.RPGItemData.ItemAttrParams;
import mixac1.dangerrpg.util.Tuple.Pair;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;

public abstract class RPGDataRegister<Key, Data extends ElementData<TransferData>, TransferKey, TransferData> extends HashMap<Key, Data>
{
    private byte[] tranferData;

    public boolean isActivated(Key key)
    {
        return containsKey(key) && get(key).isActivated;
    }

    public boolean isSupported(Key key)
    {
        return containsKey(key) && get(key).isSupported;
    }

    public HashMap<Key, Data> getActiveElements()
    {
        HashMap<Key, Data> map = new HashMap<Key, Data>();
        for (Entry<Key, Data> entry : entrySet()) {
            if (entry.getValue().isActivated) {
                map.put(entry.getKey(), entry.getValue());
            }
        }
        return map;
    }

    protected abstract TransferKey codingKey(Key key);

    protected abstract Key decodingKey(TransferKey key);

    public void createTransferData()
    {
        LinkedList<Pair<TransferKey, TransferData>> list = new LinkedList<Pair<TransferKey, TransferData>>();
        for (Entry<Key, Data> entry : getActiveElements().entrySet()) {
            TransferKey key = codingKey(entry.getKey());
            if (key != null) {
                list.add(new Pair<TransferKey, TransferData>(key, entry.getValue().getTransferData()));
            }
        }

        tranferData = Utils.serialize(list);
    }

    public byte[] getTransferData()
    {
        return tranferData;
    }

    public void extractTransferData(byte[] tranferData)
    {
        for (Entry<Key, Data> entry : entrySet()) {
            entry.getValue().isActivated = false;
        }

        LinkedList<Pair<TransferKey, TransferData>> list = Utils.deserialize(tranferData);
        for (Pair<TransferKey, TransferData> data : list) {
            Key key = decodingKey(data.value1);
            if (key != null && containsKey(key)) {
                get(key).unpackTransferData(data.value2);
                get(key).isActivated = true;
            }
        }
    }

    public static abstract class ElementData<TransferData>
    {
        public boolean isActivated;
        public boolean isSupported;

        public abstract TransferData getTransferData();

        public abstract void unpackTransferData(TransferData data);
    }

    public static class RPGItemRegister extends RPGDataRegister<Item, RPGItemData, Integer, HashMap<Integer, ItemAttrParams>>
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
    }

    public static class RPGEntityRegister extends RPGDataRegister<Class<? extends EntityLivingBase>, RPGEntityData, String, Object>
    {
        public Class<? extends EntityLivingBase> getClass(EntityLivingBase entity)
        {
            return entity instanceof EntityPlayer ? EntityPlayer.class : entity.getClass();
        }

        public boolean isActivated(EntityLivingBase entity)
        {
            return super.isActivated(getClass(entity));
        }

        public RPGEntityData get(EntityLivingBase entity)
        {
            return super.get(getClass(entity));
        }

        public void put(EntityLivingBase entity, RPGEntityData data)
        {
            super.put(getClass(entity), data);
        }

        @Override
        protected String codingKey(Class<? extends EntityLivingBase> key)
        {
            return (String) (EntityList.classToStringMapping.containsKey(key) ?
                    EntityList.classToStringMapping.get(key) : EntityPlayer.class.isAssignableFrom(key) ?
                            "player" : null);
        }

        @Override
        protected Class<? extends EntityLivingBase> decodingKey(String key)
        {
            return (Class<? extends EntityLivingBase>) (EntityList.stringToClassMapping.containsKey(key) ?
                    EntityList.stringToClassMapping.get(key) : "player".equals(key) ?
                            EntityPlayer.class : null);
        }
    }
}
