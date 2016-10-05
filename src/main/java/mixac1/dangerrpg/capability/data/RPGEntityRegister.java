package mixac1.dangerrpg.capability.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import mixac1.dangerrpg.api.entity.EntityAttribute;
import mixac1.dangerrpg.api.entity.IRPGEntity;
import mixac1.dangerrpg.api.entity.LvlEAProvider;
import mixac1.dangerrpg.capability.data.RPGEntityRegister.EntityTransferData;
import mixac1.dangerrpg.capability.data.RPGEntityRegister.RPGEntityData;
import mixac1.dangerrpg.init.RPGCapability;
import mixac1.dangerrpg.util.IMultiplier.IMultiplierE;
import mixac1.dangerrpg.util.IMultiplier.Multiplier;
import mixac1.dangerrpg.util.IMultiplier.MultiplierMul;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class RPGEntityRegister extends RPGDataRegister<Class<? extends EntityLivingBase>, RPGEntityData, String, HashMap<Integer, EntityTransferData>>
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

    /******************************************************************************************************/

    public static class RPGEntityData extends RPGDataRegister.ElementData<Class<? extends EntityLivingBase>, HashMap<Integer, EntityTransferData>>
    {
        public HashMap<EntityAttribute, EntityAttrParams> attributes = new LinkedHashMap<EntityAttribute, EntityAttrParams>();
        public List<LvlEAProvider> lvlProviders = new LinkedList<LvlEAProvider>();
        public IRPGEntity rpgComponent;

        public RPGEntityData(IRPGEntity rpgComponent, boolean isSupported)
        {
            this.rpgComponent = rpgComponent;
            this.isSupported = isSupported;
        }

        public <T> void registerEALvlable(EntityAttribute<T> attr, T startvalue, LvlEAProvider<T> lvlProvider)
        {
            lvlProvider.attr = attr;
            attributes.put(attr, new EntityAttrParams(startvalue, lvlProvider));
            lvlProviders.add(lvlProvider);
        }

        public <T> void registerEA(EntityAttribute<T> attr, T startvalue)
        {
            attributes.put(attr, new EntityAttrParams(startvalue, null));
        }

        @Override
        public HashMap<Integer, EntityTransferData> getTransferData(Class<? extends EntityLivingBase> key)
        {
            if (EntityPlayer.class.isAssignableFrom(key)) {
                HashMap<Integer, EntityTransferData> tmp = new HashMap<Integer, EntityTransferData>();
                for (Entry<EntityAttribute, EntityAttrParams> entry : attributes.entrySet()) {
                    if (entry.getValue().lvlProvider != null) {
                        tmp.put(entry.getKey().hash, new EntityTransferData(entry.getValue().lvlProvider));
                    }
                }
                return tmp;
            }
            return null;
        }

        @Override
        public void unpackTransferData(HashMap<Integer, EntityTransferData> data)
        {
            for (Entry<Integer, EntityTransferData> entry : data.entrySet()) {
                if (RPGCapability.mapIntToEntityAttribute.containsKey(entry.getKey())) {
                    EntityAttribute attr = RPGCapability.mapIntToEntityAttribute.get(entry.getKey());
                    if (attributes.containsKey(attr)) {
                        EntityAttrParams tmp = attributes.get(attr);
                        tmp.lvlProvider.mulValue = entry.getValue().mulValue;
                        tmp.lvlProvider.startExpCost  = entry.getValue().startExpCost;
                        tmp.lvlProvider.maxLvl = entry.getValue().maxLvl;
                        tmp.lvlProvider.mulExpCost = entry.getValue().mulExpCost;
                    }
                }
            }
        }
    }

    public static class EntityTransferData implements Serializable
    {
        public IMultiplierE mulValue;
        public int maxLvl;
        public int startExpCost;
        public Multiplier mulExpCost;

        public EntityTransferData(LvlEAProvider lvlProv)
        {
            this.mulValue = lvlProv.mulValue;
            this.maxLvl = lvlProv.maxLvl;
            this.startExpCost = lvlProv.startExpCost;
            this.mulExpCost = lvlProv.mulExpCost;
        }
    }

    public static class EntityAttrParams<Type>
    {
        public Type startValue;
        public LvlEAProvider<Type> lvlProvider;

        /**
         * Used for static level up entity
         */
        public Multiplier mulValue;

        private static final Multiplier MUL_0d1 = new MultiplierMul(0.1f);

        public EntityAttrParams(Type startValue, LvlEAProvider<Type> lvlProvider)
        {
            this(startValue, lvlProvider, MUL_0d1);
        }

        public EntityAttrParams(Type startValue, LvlEAProvider<Type> lvlProvider, Multiplier mulValue)
        {
            this.startValue = startValue;
            this.lvlProvider = lvlProvider;

            this.mulValue = mulValue;
        }
    }
}
