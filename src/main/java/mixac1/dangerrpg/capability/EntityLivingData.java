package mixac1.dangerrpg.capability;

import java.util.ArrayList;

import mixac1.dangerrpg.api.entity.EntityAttribute;
import mixac1.dangerrpg.api.entity.EntityAttributeE;
import mixac1.dangerrpg.capability.entityattr.EntityAttributes;
import net.minecraft.entity.EntityLivingBase;

public class EntityLivingData extends CommonEntityData
{
    public static ArrayList<EntityAttributeE> lvlableAttributes = new ArrayList<EntityAttributeE>(CommonEntityData.lvlableAttributes);
    public static ArrayList<EntityAttribute>  staticAttributes  = new ArrayList<EntityAttribute>(CommonEntityData.staticAttributes);

	static
    {
	    staticAttributes.add(EntityAttributes.HEALTH);
    }

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
	public ArrayList<EntityAttribute> getStaticAttributes()
    {
    	return staticAttributes;
    }

    @Override
	public ArrayList<EntityAttributeE> getLvlableAttributes()
    {
    	return lvlableAttributes;
    }
}
