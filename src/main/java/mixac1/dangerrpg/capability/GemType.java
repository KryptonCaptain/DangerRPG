package mixac1.dangerrpg.capability;

import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.item.gem.Gem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public enum GemType
{
    GEM_SPECIAL_ATTACK("wpn_gem_sa", 1),
    GEM_MODIFY_ATTACK_1("wpn_gem_ma1", 0),
    GEM_MODIFY_ATTACK_2("wpn_gem_ma2", 0),
    
    GEM_DEFENCE("wpn_gem_def", 0),
    
    GEM_MODIFY_HELMET("helmet_gem", 0),
    GEM_MODIFY_CHESTPLATE("chestplate_gem", 0),
    GEM_MODIFY_LEGGINGS("leggings_gem", 0),
    GEM_MODIFY_BOOTS("boots_gem", 0);
    
    private String name;
    public int guiId;
    
    GemType(String name, int guiId)
    {
        this.name = name;
        this.guiId = guiId;
    }
    
    public boolean hasIt(ItemStack stack)
    {
        return stack.hasTagCompound() && stack.stackTagCompound.hasKey(name);
    }
    
    public void attach(ItemStack src, ItemStack dest)
    {
        NBTTagCompound tag = new NBTTagCompound();
        if (src != null &&
            src.getItem() instanceof Gem &&
            ((Gem) src.getItem()).getGemType() == this) {
            src.writeToNBT(tag);
        }
        dest.stackTagCompound.setTag(name, tag);
    }
    
    public ItemStack detach(ItemStack stack)
    {
        ItemStack gem = get(stack);
        attach(null, stack);
        return gem;
    }
    
    public ItemStack get(ItemStack stack)
    {
        if (hasIt(stack)) {
            return ItemStack.loadItemStackFromNBT((NBTTagCompound) stack.stackTagCompound.getTag(name));
        }
        return null;
    }
    
    public String getDispayName()
    {
        return DangerRPG.trans("gem_type.".concat(name));
    }
}
