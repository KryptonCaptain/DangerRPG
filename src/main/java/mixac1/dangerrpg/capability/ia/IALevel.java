package mixac1.dangerrpg.capability.ia;

import mixac1.dangerrpg.api.item.IADynamic;
import mixac1.dangerrpg.init.RPGConfig;
import net.minecraft.item.ItemStack;

public class IALevel extends IADynamic
{
    public IALevel(String name)
    {
        super(name);
    }
    
    @Override
    public boolean isValid(float value)
    {
        return super.isValid(value) && value <= RPGConfig.itemMaxLevel;
    }
    
    @Override
    public void init(ItemStack stack) {}
    
    @Override
    public void lvlUp(ItemStack stack) {}
}
