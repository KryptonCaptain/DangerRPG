package mixac1.dangerrpg.inventory;

import mixac1.dangerrpg.tileentity.TileEntityModifyTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.world.World;

public class ContainerModifyTable extends Container
{
    private World worldObj;
    private InventoryCraftingModifyTable craftMatrix;
    private IInventory craftResult = new InventoryCraftResult();
    private TileEntityModifyTable tileEntity;
    
    public ContainerModifyTable(IInventory playerInv, World world, int x, int y, int z)
    {
        tileEntity = (TileEntityModifyTable) world.getTileEntity(x, y, z);
        craftMatrix = new InventoryCraftingModifyTable(this, 3, 3, tileEntity.getCraftStacks());
        
        // Modify slots: 0 - 4
        addSlotToContainer(new SlotModifyTable(tileEntity, 0, 53, 90));
        for (int i = 0; i < 2; ++i) {
            for (int j = 0; j < 2; ++j) {
                addSlotToContainer(new SlotModifyTable(tileEntity, j + i * 2 + 1, 80 + j * 18, 90 + i * 18));
            }
        }
    
        // Player inventory: 5 - 31
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 145 + i * 18));
            }
        }
    
        // Player Inventory, Slot 32 - 40
        for (int i = 0; i < 9; ++i) {
            addSlotToContainer(new Slot(playerInv, i, 8 + i * 18, 203));
        }
        
        // Player armor's slots: 41 - 44
        for (int i = 0; i < 4; ++i) {
            final int k = i;
            addSlotToContainer(new Slot(playerInv, playerInv.getSizeInventory() - 1 - i, 17, 17 + i * 18)
            {
                @Override
                public int getSlotStackLimit()
                {
                    return 1;
                }
   
                @Override
                public boolean isItemValid(ItemStack stack)
                {
                    if (stack == null) {
                        return false;
                    }
                    return stack.getItem().isValidArmor(stack, k, null);
                }
            });
        }
        
        // Craft result slot: 45
        addSlotToContainer(new SlotCrafting(((InventoryPlayer) playerInv).player, craftMatrix, craftResult, 0, 138, 36));
        
        // Craft table slots: 46 - 54
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 3; ++j)
            {
                addSlotToContainer(new Slot(craftMatrix, j + i * 3, 44 + j * 18, 17 + i * 18));
            }
        }
        
        craftMatrix.init();
        onCraftMatrixChanged(craftMatrix);
    }
    
    /**
     * Callback for when the crafting matrix is changed.
     */
    @Override
    public void onCraftMatrixChanged(IInventory inv)
    {
        craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(craftMatrix, worldObj));
        craftMatrix.reload();
    }
    
    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return tileEntity.isUseableByPlayer(playerIn);
    }
    
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot)
    {
        ItemStack stack = null;
        Slot slot = (Slot)inventorySlots.get(fromSlot);

        if (slot != null && slot.getHasStack()) {
            ItemStack stack1 = slot.getStack();
            stack = stack1.copy();

             
            if (stack.getItem() instanceof ItemArmor && !((Slot)inventorySlots.get(41 + ((ItemArmor)stack.getItem()).armorType)).getHasStack()) {
                int j = 41 + ((ItemArmor)stack.getItem()).armorType;
                if (!mergeItemStack(stack1, j, j + 1, false)) {
                    return null;
                }
            }   
            else if (fromSlot == 45) {
                if (!mergeItemStack(stack1, 5, 41, false)) {
                    return null;
                }
                slot.onSlotChange(stack1, stack);
            }
            else if (fromSlot >= 5 && fromSlot < 32) {
                if (tryTransfer(0, 5, slot)) {    
                    return null;
                }
                else if (!mergeItemStack(stack1, 32, 41, false)) {
                    return null;
                }
            }
            else if (fromSlot >= 32 && fromSlot < 41) {
                if (tryTransfer(0, 5, slot)) {    
                    return null;
                }
                else if (!mergeItemStack(stack1, 5, 32, false)) {
                    return null;
                }
            }
            else if (fromSlot >= 41 && fromSlot < 45) {
                if (tryTransfer(0, 5, slot)) {    
                    return null;
                }
                else if (!mergeItemStack(stack1, 5, 41, false)) {
                    return null;
                }
            }
            else if (fromSlot >= 46 && fromSlot < 55) {
                if (!mergeItemStack(stack1, 5, 41, false)) {
                    return null;
                }
            }
            else if (fromSlot >= 0 && fromSlot < 5) {
                if (tryTransfer(5, 41, slot)) {    
                    return null;
                }
                return null;
            }
            else {
                return null;
            }

            if (stack1.stackSize == 0) {
                slot.putStack((ItemStack)null);
            }
            else {
                slot.onSlotChanged();
            }

            if (stack1.stackSize == stack.stackSize) {
                return null;
            }

            slot.onPickupFromSlot(playerIn, stack1);
        }

        return stack;
    }
    
    public boolean tryTransfer(int from, int to, Slot slot)
    {
        for (int i = from; i < to; ++i) {
            if (getSlot(i).getStack() == null &&
                getSlot(i).isItemValid(slot.getStack())) {
                getSlot(i).putStack(slot.getStack());
                slot.putStack(null);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Copy-pasted from ContainerWorkbench
     */
    @Override
    public boolean func_94530_a(ItemStack stack, Slot slot)
    {
        return slot.inventory != craftResult && super.func_94530_a(stack, slot);
    }
    
    public static class SlotModifyTable extends Slot
    {
        private int slotIndex;
        
        public SlotModifyTable(IInventory inv, int index, int x, int y)
        {
            super(inv, index, x, y);
            slotIndex = index;
        }
        
        @Override
        public int getSlotStackLimit()
        {
            return 1;
        }
        
        @Override
        public void putStack(ItemStack stack)
        {
            this.inventory.setInventorySlotContents(this.slotIndex, stack);
            this.onSlotChanged();
        }
        
        @Override
        public boolean isItemValid(ItemStack stack)
        {
            return inventory.isItemValidForSlot(getSlotIndex(), stack);
        }
    }
    
    public static class InventoryCraftingModifyTable extends InventoryCrafting
    {
        private ItemStack[] inventory;
        
        public InventoryCraftingModifyTable(Container container, int x, int y, ItemStack[] inventory)
        {
            super(container, x, y);
            this.inventory = inventory;
        }
        
        public void init()
        {
            ItemStack[] temp = new ItemStack[9];
            for (int i = 0; i < 9; ++i) {
                temp[i] = inventory[i];
            }
            for (int i = 0; i < 9; ++i) {
                setInventorySlotContents(i, temp[i]);
            }
        }
        
        public void reload()
        {
            for (int i = 0; i < 9; ++i) {
                inventory[i] = super.getStackInSlot(i);
            }
        }
    }
}
