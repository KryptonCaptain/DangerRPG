package mixac1.dangerrpg.capability;

import java.util.ArrayList;

import mixac1.dangerrpg.api.entity.EntityAttribute;
import mixac1.dangerrpg.api.entity.EntityAttributeE;
import mixac1.dangerrpg.capability.entityattr.EntityAttributes;
import net.minecraft.entity.EntityLivingBase;

public class EntityLivingData extends CommonEntityData
{
	public static ArrayList<EntityAttributeE> entityAttributes = new ArrayList<EntityAttributeE>(CommonEntityData.entityAttributes);    
	public static ArrayList<EntityAttribute>  workAttributes   = new ArrayList<EntityAttribute>(CommonEntityData.workAttributes);
	
	static
    {
		workAttributes.add(EntityAttributes.HEALTH);
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
	public ArrayList<EntityAttribute> getWorkAttributes()
    {
    	return workAttributes;
    }
    
    @Override
	public ArrayList<EntityAttributeE> getEntityAttributes()
    {
    	return entityAttributes;
    }
}
