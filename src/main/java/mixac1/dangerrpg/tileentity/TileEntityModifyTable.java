package mixac1.dangerrpg.tileentity;

import mixac1.dangerrpg.capability.GemType;
import mixac1.dangerrpg.capability.GemableItem;
import mixac1.dangerrpg.item.gem.Gem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityModifyTable extends TileEntity implements IInventory
{
	public static final String NAME = "modify_table_tile_entity";
	public static final int WORK_SIZE = 5;
	public static final int CRAFT_SIZE = 9;

	private ItemStack[] workPlace;
	private ItemStack[] craftTable;
	
	public int[] guiIconIds = new int[WORK_SIZE - 1];
    
    public TileEntityModifyTable()
    {
        this.workPlace = new ItemStack[WORK_SIZE];
        this.craftTable = new ItemStack[CRAFT_SIZE];
    }
    
	@Override
	public int getSizeInventory()
	{
		return WORK_SIZE + CRAFT_SIZE;
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		if (index < 0 || index >= this.getSizeInventory()) {
			return null;
		}
		else if (index < WORK_SIZE) {
			return workPlace[index];
		}
		return craftTable[index - WORK_SIZE];
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		ItemStack stack = getStackInSlot(index);
		if (stack != null) {
            ItemStack itemstack;

            if (stack.stackSize <= count) {
                itemstack = stack;
                this.setInventorySlotContents(index, null);
            }
            else {
                itemstack = stack.splitStack(count);
                if (stack.stackSize == 0) {
                	this.setInventorySlotContents(index, null);
                }
            }
            return itemstack;
        }
        return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int index)
	{
	    ItemStack stack = getStackInSlot(index);
	    if (stack != null) {
	    	this.setInventorySlotContents(index, null);
	    }
	    return stack;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		if (index < 0 || index >= this.getSizeInventory()) {
	        return;
	    }
		
		ItemStack temp;
		if (index == 0) {
			if (workPlace[0] == null) {
				if (stack != null) {
					detachGems(stack);
				}
			}
			else {
				if (stack == null) {
					attachGems(workPlace[0]);
				}
			}
		}
		
		if (index < WORK_SIZE) {
			workPlace[index] = stack;
		}
		else {
			craftTable[index - WORK_SIZE] = stack;
		}
	    this.markDirty();
	}
	
	private void detachGems(ItemStack stack)
	{
		GemType[] keys = GemableItem.getGemTypes(stack);
		int count = (keys.length < WORK_SIZE) ? keys.length : WORK_SIZE - 1;
		ItemStack temp;
		for (int i = 0; i < count; ++i) {
			if (keys[i] != null && keys[i].name() != "") {
				temp = keys[i].detach(stack);
		    	workPlace[i + 1] = temp;
		    	guiIconIds[i] = keys[i].guiId;
			}
		}
	}
	
	private void attachGems(ItemStack stack)
	{
		GemType[] keys = GemableItem.getGemTypes(stack);
		int count = (keys.length < WORK_SIZE) ? keys.length : WORK_SIZE - 1;
		for (int i = 0; i < count; ++i) {
			if (keys[i] != null && keys[i].name() != "") {
				keys[i].attach(workPlace[i + 1], workPlace[0]);
	    		workPlace[i + 1] = null;
	    		guiIconIds[i] = 0;
			}
		}
	}

	@Override
	public String getInventoryName()
	{
		return NAME;
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return true;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer p)
	{
		return this.getDistanceFrom(p.posX, p.posY, p.posZ) <= 25;
	}

	@Override
	public void openInventory()
	{
		
	}

	@Override
	public void closeInventory()
	{
		
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		if (stack == null) {
			return true;
		}
		
		if (index == 0) {
			if (GemableItem.isGemable(stack)) {
				return true;
			}	
		}
		else if (index > 0 && index < WORK_SIZE) {
			if (workPlace[0] != null &&
				stack.getItem() instanceof Gem) {
				GemType[] gems = GemableItem.getGemTypes(workPlace[0]);
				if (gems != null && gems.length > 0 &&
					((Gem) stack.getItem()).getGemType() == gems[index - 1]) {
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
	    super.writeToNBT(nbt);

	    NBTTagList list = new NBTTagList();
	    for (int i = 0; i < getSizeInventory(); ++i) {
	        if (getStackInSlot(i) != null) {
	            NBTTagCompound stackTag = new NBTTagCompound();
	            stackTag.setByte("Slot", (byte) i);
	            getStackInSlot(i).writeToNBT(stackTag);
	            list.appendTag(stackTag);
	        }
	    }
	    nbt.setTag("Items", list);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
	    super.readFromNBT(nbt);

	    NBTTagList list = nbt.getTagList("Items", 10);
	    for (int i = 0; i < list.tagCount(); ++i) {
	        NBTTagCompound stackTag = list.getCompoundTagAt(i);
	        int slot = stackTag.getByte("Slot") & 255;
	        setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(stackTag));
	    }
	}
	
	public ItemStack[] getCraftStacks()
	{
		return craftTable;
	}
}
