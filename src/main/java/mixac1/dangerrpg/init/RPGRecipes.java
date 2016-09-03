package mixac1.dangerrpg.init;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import mixac1.dangerrpg.recipe.CommonShapedRecipe;
import mixac1.dangerrpg.recipe.RecipeColorArmorDyes;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class RPGRecipes
{
    public static List<CommonShapedRecipe> recipes = new LinkedList<CommonShapedRecipe>();

    public static void load()
    {
        GameRegistry.addRecipe(new RecipeColorArmorDyes());
        registerCommonShapedRecipe(new ItemStack(Items.apple, 4), "QQQQ", 'Q', Items.apple);
        registerCommonShapedRecipe(new ItemStack(Items.apple, 7), " Q ", "QQQ", " Q ", " Q ", 'Q', Items.apple);
    }

    public static void registerCommonShapedRecipe(ItemStack stack, Object ... objs)
    {
        recipes.add(createRecipe(stack, objs));
    }

    public static ItemStack findMatchingRecipe(InventoryCrafting inv, World world)
    {
        int i = 0;
        ItemStack itemstack = null;
        ItemStack itemstack1 = null;
        int j;

        for (j = 0; j < inv.getSizeInventory(); ++j) {
            ItemStack itemstack2 = inv.getStackInSlot(j);

            if (itemstack2 != null) {
                if (i == 0) {
                    itemstack = itemstack2;
                }

                if (i == 1) {
                    itemstack1 = itemstack2;
                }

                ++i;
            }
        }

        if (i == 2 && itemstack.getItem() == itemstack1.getItem() && itemstack.stackSize == 1 && itemstack1.stackSize == 1 && itemstack.getItem().isRepairable()) {
            Item item = itemstack.getItem();
            int j1 = item.getMaxDamage() - itemstack.getItemDamageForDisplay();
            int k = item.getMaxDamage() - itemstack1.getItemDamageForDisplay();
            int l = j1 + k + item.getMaxDamage() * 5 / 100;
            int i1 = item.getMaxDamage() - l;

            if (i1 < 0) {
                i1 = 0;
            }

            return new ItemStack(itemstack.getItem(), 1, i1);
        }
        else {
            for (j = 0; j < recipes.size(); ++j) {
                IRecipe irecipe = recipes.get(j);

                if (irecipe.matches(inv, world)) {
                    return irecipe.getCraftingResult(inv);
                }
            }

            return null;
        }
    }

    public static CommonShapedRecipe createRecipe(ItemStack stack, Object ... objs)
    {
        String s = "";
        int i = 0;
        int j = 0;
        int k = 0;

        if (objs[i] instanceof String[]) {
            String[] astring = ((String[])objs[i++]);

            for (String s1 : astring) {
                ++k;
                j = s1.length();
                s = s + s1;
            }
        }
        else {
            while (objs[i] instanceof String) {
                String s2 = (String)objs[i++];
                ++k;
                j = s2.length();
                s = s + s2;
            }
        }

        HashMap hashmap;

        for (hashmap = new HashMap(); i < objs.length; i += 2) {
            Character character = (Character)objs[i];
            ItemStack itemstack1 = null;

            if (objs[i + 1] instanceof Item) {
                itemstack1 = new ItemStack((Item)objs[i + 1]);
            }
            else if (objs[i + 1] instanceof Block) {
                itemstack1 = new ItemStack((Block)objs[i + 1], 1, 32767);
            }
            else if (objs[i + 1] instanceof ItemStack) {
                itemstack1 = (ItemStack)objs[i + 1];
            }

            hashmap.put(character, itemstack1);
        }

        ItemStack[] aitemstack = new ItemStack[j * k];

        for (int i1 = 0; i1 < j * k; ++i1) {
            char c0 = s.charAt(i1);

            if (hashmap.containsKey(Character.valueOf(c0))) {
                aitemstack[i1] = ((ItemStack)hashmap.get(Character.valueOf(c0))).copy();
            }
            else {
                aitemstack[i1] = null;
            }
        }

        return new CommonShapedRecipe(j, k, aitemstack, stack);
    }
}
