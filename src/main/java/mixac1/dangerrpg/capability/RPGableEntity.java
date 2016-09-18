package mixac1.dangerrpg.capability;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import mixac1.dangerrpg.api.entity.EntityAttribute;
import mixac1.dangerrpg.api.entity.IRPGEntity;
import mixac1.dangerrpg.api.entity.LvlEAProvider;
import mixac1.dangerrpg.api.entity.LvlEAProvider.DafailtLvlEAProvider;
import mixac1.dangerrpg.api.event.RegEAEvent;
import mixac1.dangerrpg.capability.ea.EntityAttributes;
import mixac1.dangerrpg.capability.ea.PlayerAttributes;
import mixac1.dangerrpg.init.RPGCapability;
import mixac1.dangerrpg.util.IMultiplier;
import mixac1.dangerrpg.util.IMultiplier.IMultiplierE;
import mixac1.dangerrpg.util.IMultiplier.MultiplierAdd;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;

public abstract class RPGableEntity
{
    public static boolean isRPGable(EntityLivingBase entity)
    {
        return RPGCapability.rpgEntityRegistr.isRegistered(entity);
    }

    public static boolean registerEntity(Class entityClass)
    {
        if (EntityLivingBase.class.isAssignableFrom(entityClass)) {
            if (RPGCapability.rpgEntityRegistr.data.containsKey(entityClass)) {
                return true;
            }

            IRPGEntity iRPG = EntityPlayer.class.isAssignableFrom(entityClass) ? IRPGEntity.DEFAULT_PLAYER:
                              EntityMob.class.isAssignableFrom(entityClass)    ? IRPGEntity.DEFAULT_MOB:
                                                                                 IRPGEntity.DEFAULT_LIVING;

            RPGCapability.rpgEntityRegistr.data.put(entityClass, new EntityAttributesMap(iRPG, false));
            return true;
        }
        return false;
    }

    public static void registerEntityDefault(Class<? extends EntityLivingBase> entityClass, EntityAttributesMap map)
    {
        map.addEntityAttribute(EntityAttributes.LVL, 1);
        MinecraftForge.EVENT_BUS.post(new RegEAEvent.DefaultEAEvent(entityClass, map));
    }

    public static void registerEntityLiving(Class<? extends EntityLiving> entityClass, EntityAttributesMap map)
    {
        map.addEntityAttribute(EntityAttributes.HEALTH, 0f);
        MinecraftForge.EVENT_BUS.post(new RegEAEvent.EntytyLivingEAEvent(entityClass, map));
    }

    public static void registerEntityMob(Class<? extends EntityMob> entityClass, EntityAttributesMap map)
    {
        map.addEntityAttribute(EntityAttributes.MELEE_DAMAGE, 0f);
        MinecraftForge.EVENT_BUS.post(new RegEAEvent.EntytyMobEAEvent(entityClass, map));
    }

    public static void registerEntityPlayer(Class<? extends EntityPlayer> entityClass, EntityAttributesMap map)
    {
        IMultiplierE<Float> ADD_1     = IMultiplier.ADD_1;
        IMultiplierE<Float> ADD_2     = new MultiplierAdd(2F);
        IMultiplierE<Float> ADD_0d001 = new MultiplierAdd(0.001F);
        IMultiplierE<Float> ADD_0d01  = new MultiplierAdd(0.01F);
        IMultiplierE<Float> ADD_0d014 = new MultiplierAdd(0.014F);
        IMultiplierE<Float> ADD_0d025 = new MultiplierAdd(0.025F);
        IMultiplierE<Float> ADD_0d2   = new MultiplierAdd(0.2F);

        map.addLvlableEntityAttribute(PlayerAttributes.HEALTH,        0f,  new DafailtLvlEAProvider(2, 1000, ADD_2));
        map.addLvlableEntityAttribute(PlayerAttributes.MANA,          10f, new DafailtLvlEAProvider(2, 1000, ADD_2));
        map.addLvlableEntityAttribute(PlayerAttributes.STRENGTH,      0f,  new DafailtLvlEAProvider(2, 1000, ADD_1));
        map.addLvlableEntityAttribute(PlayerAttributes.AGILITY,       0f,  new DafailtLvlEAProvider(2, 1000, ADD_1));
        map.addLvlableEntityAttribute(PlayerAttributes.INTELLIGENCE,  0f,  new DafailtLvlEAProvider(2, 1000, ADD_1));
        map.addLvlableEntityAttribute(PlayerAttributes.EFFICIENCY,    0f,  new DafailtLvlEAProvider(2, 1000, ADD_2));
        map.addLvlableEntityAttribute(PlayerAttributes.MANA_REGEN,    1f,  new DafailtLvlEAProvider(2, 1000, ADD_0d2));
        map.addLvlableEntityAttribute(PlayerAttributes.HEALTH_REGEN,  0f,  new DafailtLvlEAProvider(2, 1000, ADD_0d2));

        map.addLvlableEntityAttribute(PlayerAttributes.MOVE_SPEED,    0f,  new DafailtLvlEAProvider(2, 20,   ADD_0d001));
        map.addLvlableEntityAttribute(PlayerAttributes.SNEAK_SPEED,   0f,  new DafailtLvlEAProvider(2, 20,   ADD_0d001));
        map.addLvlableEntityAttribute(PlayerAttributes.FLY_SPEED,     0f,  new DafailtLvlEAProvider(2, 20,   ADD_0d001));
        map.addLvlableEntityAttribute(PlayerAttributes.SWIM_SPEED,    0f,  new DafailtLvlEAProvider(2, 20,   ADD_0d001));
        map.addLvlableEntityAttribute(PlayerAttributes.JUMP_HEIGHT,   0f,  new DafailtLvlEAProvider(2, 20,   ADD_0d014));
        map.addLvlableEntityAttribute(PlayerAttributes.JUMP_RANGE,    0f,  new DafailtLvlEAProvider(2, 20,   ADD_0d001));

        map.addLvlableEntityAttribute(PlayerAttributes.PHISIC_RESIST, 0f,  new DafailtLvlEAProvider(2, 20,   ADD_0d01));
        map.addLvlableEntityAttribute(PlayerAttributes.MAGIC_RESIST,  0f,  new DafailtLvlEAProvider(2, 20,   ADD_0d01));
        map.addLvlableEntityAttribute(PlayerAttributes.FALL_RESIST,   0f,  new DafailtLvlEAProvider(2, 20,   ADD_0d025));
        map.addLvlableEntityAttribute(PlayerAttributes.FIRE_RESIST,   0f,  new DafailtLvlEAProvider(2, 20,   ADD_0d025));
        map.addLvlableEntityAttribute(PlayerAttributes.LAVA_RESIST,   0f,  new DafailtLvlEAProvider(2, 20,   ADD_0d025));

        map.addEntityAttribute(PlayerAttributes.CURR_MANA, 0f);
        map.addEntityAttribute(PlayerAttributes.SPEED_COUNTER, 0f);

        MinecraftForge.EVENT_BUS.post(new RegEAEvent.PlayerEAEvent(entityClass, map));
}

    public static class EntityAttributesMap
    {
        public Map<EntityAttribute, EntityAttrParams> attributes = new LinkedHashMap<EntityAttribute, EntityAttrParams>();
        public List<LvlEAProvider> lvlProviders = new LinkedList<LvlEAProvider>();
        public IRPGEntity rpgComponent;
        public boolean isSupported;

        public EntityAttributesMap(IRPGEntity rpgComponent, boolean isSupported)
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

        public static class EntityAttrParams<Type>
        {
            public Type startValue;
            public LvlEAProvider<Type> lvlProvider;

            public EntityAttrParams(Type startValue, LvlEAProvider<Type> lvlProvider)
            {
                this.startValue = startValue;
                this.lvlProvider = lvlProvider;
            }
        }
    }
}
