package mixac1.dangerrpg.capability;

import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.item.gem.Gem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public enum GemType
{
    GEM_PASSIVE_STAT("passive_stat"),

    ;

    private String name;

    GemType(String name)
    {
        this.name = name;
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
        return DangerRPG.trans("gem.".concat(name));
    }
}
