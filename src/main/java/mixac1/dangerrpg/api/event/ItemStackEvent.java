package mixac1.dangerrpg.api.event;

import java.util.List;

import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemStackEvent extends Event
{
    public ItemStack stack;

    public ItemStackEvent(ItemStack stack)
    {
        this.stack = stack;
    }

    /**
     * It is fires whenever a {@link Item#hitEntity(ItemStack, EntityLivingBase, EntityLivingBase)} is processed
     * and stack is lvlable item
     */
    @Cancelable
    public static class HitEntityEvent extends ItemStackEvent
    {
        public EntityLivingBase entity;
        public EntityLivingBase attacker;

        public float oldDamage;
        public float newDamage;
        public float knockback;
        public boolean isRangeed;

        public HitEntityEvent(ItemStack stack, EntityLivingBase entity, EntityLivingBase attacker,
                              float damage, float knockback, boolean isRangeed)
        {
            super(stack);
            this.entity = entity;
            this.attacker = attacker;
            this.oldDamage = this.newDamage = damage;
            this.knockback = knockback;
            this.isRangeed = isRangeed;
        }
    }

    /**
     * It is fires whenever a {@link Item#addInformation(ItemStack, EntityPlayer, List, boolean)} is processed
     * and stack is lvlable item
     */
    @Cancelable
    public static class AddInformationEvent extends ItemStackEvent
    {
        public EntityPlayer player;
        public List list;
        public boolean par;

        public AddInformationEvent(ItemStack stack, EntityPlayer player, List list, boolean par)
        {
            super(stack);
            this.player = player;
            this.list = list;
            this.par = par;
        }
    }

    public static class StackChangedEvent extends ItemStackEvent
    {
        public ItemStack oldStack;
        public EntityPlayer player;
        public int slot;

        public StackChangedEvent(ItemStack newStack, ItemStack oldStack, int slot, EntityPlayer player)
        {
            super(newStack);
            this.oldStack = oldStack;
            this.player = player;
            this.slot = slot;
        }
    }

    public static class UpMaxLevelEvent extends ItemStackEvent
    {
        public UpMaxLevelEvent(ItemStack stack)
        {
            super(stack);
        }
    }
}
