package mixac1.dangerrpg.api.event;

import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class DealtDamageEvent extends Event
{
    public EntityPlayer player;
    public EntityLivingBase target;
    public ItemStack stack;
    public float damage;

    public DealtDamageEvent(EntityPlayer player, EntityLivingBase target, ItemStack stack, float damage)
    {
        this.player = player;
        this.target = target;
        this.stack = stack;
        this.damage = damage;
    }
}
