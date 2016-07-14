package mixac1.dangerrpg.item.gem;

import mixac1.dangerrpg.capability.GemType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class GemWeaponSpecialAttack extends Gem
{
    public GemWeaponSpecialAttack(String name) {
        super(name);
    }

    public abstract void upgrade(ItemStack stack, World world, EntityPlayer player);
    
    @Override
    public GemType getGemType()
    {
        return GemType.GEM_SPECIAL_ATTACK;
    }
}
