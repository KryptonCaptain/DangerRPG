package mixac1.dangerrpg.capability;

import java.util.ArrayList;

import mixac1.dangerrpg.api.entity.EntityAttribute;
import mixac1.dangerrpg.api.entity.LvlEAProvider;
import net.minecraft.entity.EntityLivingBase;

public class EntityLivingData extends CommonEntityData
{
    public static ArrayList<LvlEAProvider>   lvlProviders     = new ArrayList<LvlEAProvider>(CommonEntityData.lvlProviders);
    public static ArrayList<EntityAttribute> entityAttributes = new ArrayList<EntityAttribute>(CommonEntityData.entityAttributes);

    public EntityLivingData(EntityLivingBase entity)
    {
        super(entity);
    }

    public static void register(EntityLivingBase entity)
    {
        entity.registerExtendedProperties(ID, new EntityLivingData(entity));
    }

    public static EntityLivingData get(EntityLivingBase entity)
    {
        return (EntityLivingData) entity.getExtendedProperties(ID);
    }

    @Override
    public ArrayList<EntityAttribute> getEntityAttributes()
    {
        return entityAttributes;
    }

    @Override
    public ArrayList<LvlEAProvider> getLvlProviders()
    {
        return lvlProviders;
    }
}
