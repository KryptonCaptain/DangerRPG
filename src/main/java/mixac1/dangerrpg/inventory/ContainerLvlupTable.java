package mixac1.dangerrpg.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mixac1.dangerrpg.capability.RPGableItem;
import mixac1.dangerrpg.capability.ia.ItemAttributes;
import mixac1.dangerrpg.init.RPGConfig;
import mixac1.dangerrpg.util.RPGHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerLvlupTable extends Container
{
    private World worldPointer;
    private int posX;
    private int posY;
    private int posZ;
    private boolean firstUse = true;
    public int expToUp;

    public IInventory tableInventory = new InventoryBasic("Lvlup", true, 1)
    {
        @Override
        public int getInventoryStackLimit()
        {
            return 1;
        }

        @Override
        public void markDirty()
        {
            super.markDirty();
            ContainerLvlupTable.this.onCraftMatrixChanged(this);
        }

        @Override
        public boolean isItemValidForSlot(int index, ItemStack stack)
        {
            return RPGableItem.isRPGable(stack);
        }
    };

    public ContainerLvlupTable(IInventory playerv, World world, int x, int y, int z)
    {
        worldPointer = world;
        posX = x;
        posY = y;
        posZ = z;

        addSlotToContainer(new Slot(tableInventory, 0, 34, 63)
        {
            @Override
            public boolean isItemValid(ItemStack stack)
            {
                return inventory.isItemValidForSlot(slotNumber, stack);
            }
        });

        // Player inventory: 1 - 27
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                addSlotToContainer(new Slot(playerv, j + i * 9 + 9, 8 + j * 18, 92 + i * 18));
            }
        }

        // Player Inventory, Slot 28 - 36
        for (int i = 0; i < 9; ++i) {
            addSlotToContainer(new Slot(playerv, i, 8 + i * 18, 150));
        }

        // Player armor's slots: 37 - 40
        for (int i = 0; i < 4; ++i) {
            final int k = i;
            addSlotToContainer(new Slot(playerv, playerv.getSizeInventory() - 1 - i, 8, 9 + i * 18)
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
    }

    @Override
    public void addCraftingToCrafters(ICrafting craft)
    {
        super.addCraftingToCrafters(craft);
        craft.sendProgressBarUpdate(this, 0, expToUp);
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < crafters.size(); ++i) {
            ICrafting iCrafting = (ICrafting) crafters.get(i);
            iCrafting.sendProgressBarUpdate(this, 0, expToUp);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2)
    {
        if (par1 == 0) {
            expToUp = par2;
        }
        else {
            super.updateProgressBar(par1, par2);
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        if (firstUse) {
            RPGHelper.rebuildPlayerExp(player);
            firstUse = false;
        }
        return player.getDistance(posX + 0.5D, posY + 0.5D, posZ + 0.5D) <= 64.0D;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int fromSlot)
    {
        ItemStack stack = null;
        Slot slot = (Slot)inventorySlots.get(fromSlot);

        if (slot != null && slot.getHasStack()) {
            ItemStack stack1 = slot.getStack();
            stack = stack1.copy();

            if (RPGableItem.isRPGable(stack) && !((Slot)inventorySlots.get(0)).getHasStack()) {
                if (!mergeItemStack(stack1, 0, 1, false)) {
                    return null;
                }
            }
            else if (stack.getItem() instanceof ItemArmor && !((Slot)inventorySlots.get(37 + ((ItemArmor)stack.getItem()).armorType)).getHasStack()) {
                int j = 37 + ((ItemArmor)stack.getItem()).armorType;
                if (!mergeItemStack(stack1, j, j + 1, false)) {
                    return null;
                }
            }
            else if (fromSlot >= 1 && fromSlot < 28) {
                if (!mergeItemStack(stack1, 28, 37, false)) {
                    return null;
                }
            }
            else if (fromSlot >= 28 && fromSlot < 37) {
                if (!mergeItemStack(stack1, 1, 29, false)) {
                    return null;
                }
            }
            else if (fromSlot >= 37 && fromSlot < 41) {
                if (!mergeItemStack(stack1, 1, 37, false)) {
                    return null;
                }
            }
            else if (fromSlot == 0) {
                if (!mergeItemStack(stack1, 1, 37, false)) {
                    return null;
                }
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
            slot.onPickupFromSlot(player, stack1);
        }
        return stack;
    }

    @Override
    public void onContainerClosed(EntityPlayer player)
    {
        super.onContainerClosed(player);

        if (!worldPointer.isRemote) {
            ItemStack stack = tableInventory.getStackInSlotOnClosing(0);

            if (stack != null) {
                player.dropPlayerItemWithRandomChoice(stack, false);
            }
        }
    }

    @Override
    public void onCraftMatrixChanged(IInventory inventory)
    {
        if (inventory == tableInventory) {
            ItemStack stack = inventory.getStackInSlot(0);
            if (stack != null && RPGableItem.isRPGable(stack)) {
                if (!worldPointer.isRemote) {
                    int currExp = (int) ItemAttributes.CURR_EXP.get(stack);
                    int maxExp  = (int) ItemAttributes.MAX_EXP.get(stack);
                    expToUp = (maxExp - currExp);
                    detectAndSendChanges();
                }
            }
            else {
                expToUp = -1;
            }
        }
    }

    @Override
    public boolean enchantItem(EntityPlayer player, int flag)
    {
        ItemStack stack = tableInventory.getStackInSlot(0);
        if (stack != null) {
            if (flag == 0) {
                if (player.capabilities.isCreativeMode) {
                    if (!worldPointer.isRemote) {
                        RPGableItem.addExp(stack, expToUp);
                        onCraftMatrixChanged(tableInventory);
                    }
                    return true;
                }
                else if (RPGConfig.ItemConfig.canUpInTable && expToUp <= player.experienceTotal) {
                    if (!worldPointer.isRemote) {
                        RPGableItem.addExp(stack, expToUp);
                        player.addExperience(-expToUp);
                        RPGHelper.rebuildPlayerLvl(player);
                        onCraftMatrixChanged(tableInventory);
                    }
                    return true;
                }

            }
            else {
                if (RPGConfig.ItemConfig.canUpInTable && player.experienceTotal > 0) {
                    if (!worldPointer.isRemote) {
                        while (player.experienceTotal > 0 && ItemAttributes.LEVEL.get(stack) < RPGConfig.ItemConfig.maxLevel) {
                            float temp = ItemAttributes.MAX_EXP.get(stack) - ItemAttributes.CURR_EXP.get(stack);
                            int needToUp = (int) ((temp > (int) temp) ? temp + 1 : temp);
                            if (player.experienceTotal > needToUp) {
                                RPGableItem.addExp(stack, needToUp);
                                player.experienceTotal -= needToUp;
                            }
                            else {
                                RPGableItem.addExp(stack, player.experienceTotal);
                                player.experienceTotal = 0;
                            }
                        }
                        RPGHelper.rebuildPlayerLvl(player);
                        onCraftMatrixChanged(tableInventory);
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
