package mixac1.dangerrpg.capability;

import mixac1.dangerrpg.capability.entityattr.EntityAttributes;
import net.minecraft.entity.EntityLivingBase;

public class EntityLivingData extends CommonEntityData
{
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
}
