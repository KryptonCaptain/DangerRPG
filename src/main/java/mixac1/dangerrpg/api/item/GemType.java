package mixac1.dangerrpg.api.item;

import java.util.ArrayList;
import java.util.Arrays;
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

        RPGCapability.mapIntToGemType.put(hash, this);
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
        attach(dest, Arrays.asList(src));
    }

    public void attach(ItemStack dest, List<ItemStack> src)
    {
        NBTTagList tagList = new NBTTagList();
        int max = RPGCapability.rpgItemRegistr.get(dest.getItem()).gems.get(this);
        for (int i = 0; i < src.size() && i < max; ++i) {
            ItemStack stack = src.get(i);
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
        attach(dest, Collections.EMPTY_LIST);
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
        NBTTagList nbtList = stack.stackTagCompound.getTagList(name, 10);
        List<ItemStack> stacks = new ArrayList<ItemStack>(nbtList.tagCount());
        int max = RPGCapability.rpgItemRegistr.get(stack.getItem()).gems.get(this);
        for (int i = 0; i < nbtList.tagCount() && i < max; ++i) {
            stacks.add(i, ItemStack.loadItemStackFromNBT(nbtList.getCompoundTagAt(i)));
        }
        return stacks;
    }

    public abstract void activate(ItemStack stack, EntityPlayer player);

    public abstract void deactivate(ItemStack stack, EntityPlayer player);

    public void activateAll(ItemStack stack, EntityPlayer player)
    {
        List<ItemStack> stacks = get(stack);
        for (ItemStack it : stacks) {
            activate(it, player);
        }
    }

    public void deactivateAll(ItemStack stack, EntityPlayer player)
    {
        List<ItemStack> stacks = get(stack);
        for (ItemStack it : stacks) {
            deactivate(it, player);
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
