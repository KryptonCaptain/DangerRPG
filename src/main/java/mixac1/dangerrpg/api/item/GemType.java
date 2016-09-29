package mixac1.dangerrpg.api.item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.init.RPGCapability;
import mixac1.dangerrpg.item.gem.Gem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public abstract class GemType
{
    public final String name;
    public final int hash;

    public GemType(String name)
    {
        this.name = name;
        hash = name.hashCode();
    }

    public boolean hasIt(ItemStack stack)
    {
        return RPGCapability.rpgItemRegistr.isActivated(stack.getItem()) && stack.stackTagCompound.hasKey(name);
    }

    public boolean isItGem(ItemStack stack)
    {
        return stack.getItem() instanceof Gem && getClass().isInstance(((Gem) stack.getItem()).getGemType());
    }

    public void attach(ItemStack dest, ItemStack... src)
    {
        NBTTagList tagList = new NBTTagList();
        for (ItemStack stack : src) {
            if (stack != null && isItGem(stack)) {
                NBTTagCompound nbt = new NBTTagCompound();
                stack.writeToNBT(nbt);
                tagList.appendTag(nbt);
            }
        }
        dest.stackTagCompound.setTag(name, tagList);
    }

    public List<ItemStack> detach(ItemStack dest)
    {
        List<ItemStack> stacks = get(dest);
        attach(dest, (ItemStack[]) null);
        return stacks;
    }

    public List<ItemStack> get(ItemStack stack)
    {
        if (hasIt(stack)) {
            List<ItemStack> stacks = getRaw(stack);
            for (ItemStack it : stacks) {
                if (!isItGem(it)) {
                    stacks.remove(it);
                }
            }
            return stacks;
        }
        return Collections.EMPTY_LIST;
    }

    public List<ItemStack> getRaw(ItemStack stack)
    {
        NBTTagList nbtList = stack.stackTagCompound.getTagList(name, 1);
        List<ItemStack> stacks = new ArrayList<ItemStack>(nbtList.tagCount());
        for (int i = 0; i < nbtList.tagCount(); ++i) {
            stacks.add(i, ItemStack.loadItemStackFromNBT(nbtList.getCompoundTagAt(i)));
        }
        return stacks;
    }

    public abstract void activate(Gem gem, EntityPlayer player);

    public void activate(ItemStack stack, EntityPlayer player)
    {
        List<ItemStack> stacks = get(stack);
        for (ItemStack it : stacks) {
            activate((Gem) it.getItem(), player);
        }
    }

    public String getDispayName()
    {
        return DangerRPG.trans("gem.".concat(name));
    }

    @Override
    public final int hashCode()
    {
        return hash;
    }
}
