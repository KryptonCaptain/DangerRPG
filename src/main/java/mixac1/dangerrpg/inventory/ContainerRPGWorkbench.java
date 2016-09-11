package mixac1.dangerrpg.inventory;

import mixac1.dangerrpg.client.gui.GuiRPGWorkbench;
import mixac1.dangerrpg.init.RPGRecipes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.world.World;

public class ContainerRPGWorkbench extends Container
{
    public static int craftSize = 5;

    public InventoryRPGCrafting craftMatrix = new InventoryRPGCrafting(this, craftSize, craftSize);
    public IInventory craftResult           = new InventoryCraftResult();

    protected World worldObj;
    protected int posX;
    protected int posY;
    protected int posZ;

    public ContainerRPGWorkbench(InventoryPlayer inv, World world, int x, int y, int z)
    {
        worldObj = world;
        posX = x;
        posY = y;
        posZ = z;

        /* 0 - 26 */
        for (int m = 0; m < 3; ++m) {
            for (int n = 0; n < 9; ++n) {
                addSlotToContainer(new Slot(inv, m * 9 + n + 9, GuiRPGWorkbench.playerInvX + n * 18, GuiRPGWorkbench.playerInvY + m * 18));
            }
        }

        /* 27 - 35 */
        for (int m = 0; m < 9; ++m) {
            addSlotToContainer(new Slot(inv, m, GuiRPGWorkbench.fastInvX + m * 18, GuiRPGWorkbench.fastInvY));
        }

        /* 36 - 60 */
        for (int m = 0; m < craftSize; ++m) {
            for (int n = 0; n < craftSize; ++n) {
                addSlotToContainer(new Slot(craftMatrix, craftSize * m + n, GuiRPGWorkbench.craftX + n * 18, GuiRPGWorkbench.craftY + m * 18));
            }
        }

        /* 61 */
        addSlotToContainer(new SlotCrafting(inv.player, craftMatrix, craftResult, 0, GuiRPGWorkbench.craftResX, GuiRPGWorkbench.craftResY));

        onCraftMatrixChanged(craftMatrix);
    }

    @Override
    public void onCraftMatrixChanged(IInventory inv)
    {
        ItemStack stack = RPGRecipes.ownFindMatchingRecipe(craftMatrix, worldObj, craftSize, craftSize);
        craftResult.setInventorySlotContents(0, stack);

        if (stack != null) {
            return;
        }

        for (int i = 0; i < craftSize - 2; ++i) {
            for (int j = 0; j < craftSize - 2; ++j) {
                if (craftMatrix.isValidCrafting(j, i)) {
                    stack = CraftingManager.getInstance().findMatchingRecipe(craftMatrix.getCrafting(j, i), worldObj);
                    craftResult.setInventorySlotContents(0, stack);
                    return;
                }
            }
        }
    }

    @Override
    public void onContainerClosed(EntityPlayer player)
    {
        super.onContainerClosed(player);

        if (!worldObj.isRemote) {
            for (int i = 0; i < craftSize * craftSize; ++i) {
                ItemStack itemstack = craftMatrix.getStackInSlotOnClosing(i);

                if (itemstack != null) {
                    player.dropPlayerItemWithRandomChoice(itemstack, false);
                }
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return player.getDistanceSq(posX + 0.5D, posY + 0.5D, posZ + 0.5D) <= 64.0D;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index)
    {
        ItemStack stack = null;
        Slot slot = (Slot) inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack newStack = slot.getStack();
            stack = newStack.copy();

            if (index == 61) {
                if (!mergeItemStack(newStack, 0, 36, true)) {
                    return null;
                }
                slot.onSlotChange(newStack, stack);
            }
            else if (index >= 0 && index < 27) {
                if (!mergeItemStack(newStack, 27, 36, false)) {
                    return null;
                }
            }
            else if (index >= 27 && index < 36) {
                if (!mergeItemStack(newStack, 0, 27, false)) {
                    return null;
                }
            }
            else if (!mergeItemStack(newStack, 0, 36, false)) {
                return null;
            }

            if (newStack.stackSize == 0) {
                slot.putStack(null);
            }
            else {
                slot.onSlotChanged();
            }

            if (newStack.stackSize == stack.stackSize) {
                return null;
            }

            slot.onPickupFromSlot(player, newStack);
        }

        return stack;
    }

    @Override
    public boolean func_94530_a(ItemStack stack, Slot slot)
    {
        return slot.inventory != craftResult && super.func_94530_a(stack, slot);
    }
}