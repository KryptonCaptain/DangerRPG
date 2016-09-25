package mixac1.dangerrpg.capability.data;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import mixac1.dangerrpg.api.entity.EntityAttribute;
import mixac1.dangerrpg.api.entity.IRPGEntity;
import mixac1.dangerrpg.api.entity.LvlEAProvider;
import mixac1.dangerrpg.capability.data.RPGDataRegister.ElementData;
import mixac1.dangerrpg.util.IMultiplier.IMulConfigurable;
import mixac1.dangerrpg.util.IMultiplier.MultiplierMul;

public class RPGEntityData extends ElementData<Object>
{
    public HashMap<EntityAttribute, EntityAttrParams> attributes = new LinkedHashMap<EntityAttribute, EntityAttrParams>();
    public List<LvlEAProvider> lvlProviders = new LinkedList<LvlEAProvider>();
    public IRPGEntity rpgComponent;

    public RPGEntityData(IRPGEntity rpgComponent, boolean isSupported)
    {
        this.rpgComponent = rpgComponent;
        this.isSupported = isSupported;
    }

    public <T> void addLvlableEntityAttribute(EntityAttribute<T> attr, T startvalue, LvlEAProvider<T> lvlProvider)
    {
        lvlProvider.attr = attr;
        attributes.put(attr, new EntityAttrParams(startvalue, lvlProvider));
        lvlProviders.add(lvlProvider);
    }

    public <T> void addEntityAttribute(EntityAttribute<T> attr, T startvalue)
    {
        attributes.put(attr, new EntityAttrParams(startvalue, null));
    }

    @Override
    public Object getTransferData()
    {
        return null;
    }

    @Override
    public void unpackTransferData(Object data)
    {

    }

    public static class EntityAttrParams<Type>
    {
        public Type startValue;
        public LvlEAProvider<Type> lvlProvider;

        /**
         * Used for static level up entity
         */
        public IMulConfigurable mulValue;

        private static final IMulConfigurable MUL_0d1 = new MultiplierMul(0.1f);

        public EntityAttrParams(Type startValue, LvlEAProvider<Type> lvlProvider)
        {
            this(startValue, lvlProvider, MUL_0d1);
        }

        public EntityAttrParams(Type startValue, LvlEAProvider<Type> lvlProvider, IMulConfigurable mulValue)
        {
            this.startValue = startValue;
            this.lvlProvider = lvlProvider;

            this.mulValue = mulValue;
        }
    }
}