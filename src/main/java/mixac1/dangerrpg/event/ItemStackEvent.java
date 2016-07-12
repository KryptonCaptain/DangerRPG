package mixac1.dangerrpg.event;

import java.util.List;

import com.google.common.collect.Multimap;

import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

@Cancelable
public class ItemStackEvent extends Event
{
	public ItemStack stack;
	
	public ItemStackEvent(ItemStack stack)
	{
		this.stack = stack;
	}
	
	@Cancelable
	public static class OnLeftClickEntityEvent extends ItemStackEvent
	{
		public EntityPlayer player;
		public Entity entity;
		
		public OnLeftClickEntityEvent(ItemStack stack, EntityPlayer player, Entity entity)
		{
			super(stack);
			this.player = player;
			this.entity = entity;
		}
	}
	
	@Cancelable
	public static class HitEntityEvent extends ItemStackEvent
	{
		public EntityLivingBase entity;
		public EntityLivingBase attacker;
		
		public HitEntityEvent(ItemStack stack, EntityLivingBase entity, EntityLivingBase attacker)
		{
			super(stack);
			this.entity = entity;
			this.attacker = attacker;
		}
	}
	
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
	
	@Cancelable
	public static class GetAttributeModifiers extends ItemStackEvent
	{
		public Multimap map;
		
		public GetAttributeModifiers(ItemStack stack, Multimap map)
		{
			super(stack);
			this.map = map;
		}
	}
}
