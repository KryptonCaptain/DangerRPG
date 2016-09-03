package mixac1.dangerrpg.nei;

import static codechicken.lib.gui.GuiDraw.changeTexture;
import static codechicken.lib.gui.GuiDraw.drawTexturedModalRect;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import mixac1.dangerrpg.DangerRPG;
import mixac1.dangerrpg.client.gui.GuiRPGWorkbench;
import mixac1.dangerrpg.init.RPGRecipes;
import mixac1.dangerrpg.recipe.CommonShapedRecipe;
import mixac1.dangerrpg.util.Utils;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

public class CommonShapedRecipeHandler extends TemplateRecipeHandler
{
    @Override
    public Class<? extends GuiContainer> getGuiClass()
    {
        return GuiRPGWorkbench.class;
    }

    @Override
    public String getRecipeName()
    {
        return DangerRPG.trans(CommonShapedRecipe.NAME);
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results)
    {
        if (outputId.equals("crafting") && getClass() == CommonShapedRecipeHandler.class) {
            for (CommonShapedRecipe irecipe : RPGRecipes.recipes) {
                CachedCommonShapedRecipe recipe = new CachedCommonShapedRecipe(irecipe);
                recipe.computeVisuals();
                arecipes.add(recipe);
            }
        }
        else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result)
    {
        for (CommonShapedRecipe irecipe : RPGRecipes.recipes) {
            if (NEIServerUtils.areStacksSameTypeCrafting(irecipe.getRecipeOutput(), result)) {
                CachedCommonShapedRecipe recipe = new CachedCommonShapedRecipe(irecipe);
                recipe.computeVisuals();
                arecipes.add(recipe);
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        for (CommonShapedRecipe irecipe : RPGRecipes.recipes)
        {
            CachedCommonShapedRecipe recipe = new CachedCommonShapedRecipe(irecipe);
            recipe.computeVisuals();
            if (recipe.contains(recipe.ingredients, ingredient)) {
                recipe.setIngredientPermutation(recipe.ingredients, ingredient);
                arecipes.add(recipe);
            }
        }
    }

    @Override
    public String getGuiTexture()
    {
        return Utils.toString(GuiRPGWorkbench.TEXTURE.getResourceDomain(), ":", GuiRPGWorkbench.TEXTURE.getResourcePath());
    }

    public static int offsetX = 5;
    public static int offsetY = 11;

    @Override
    public void drawBackground(int recipe)
    {
        GL11.glColor4f(1, 1, 1, 1);
        changeTexture(getGuiTexture());
        drawTexturedModalRect(0, 0, offsetX, offsetY, 166, 100);
    }

    @Override
    public int recipiesPerPage()
    {
        return 1;
    }

    public class CachedCommonShapedRecipe extends CachedRecipe
    {
        public ArrayList<PositionedStack> ingredients;
        public PositionedStack result;

        public CachedCommonShapedRecipe(int width, int height, Object[] items, ItemStack out)
        {
            result = new PositionedStack(out, GuiRPGWorkbench.craftResX - offsetX, GuiRPGWorkbench.craftResY - offsetY);
            ingredients = new ArrayList<PositionedStack>();
            setIngredients(width, height, items);
        }

        public CachedCommonShapedRecipe(CommonShapedRecipe recipe)
        {
            this(recipe.recipeWidth, recipe.recipeHeight, recipe.recipeItems, recipe.getRecipeOutput());
        }

        public void setIngredients(int width, int height, Object[] items)
        {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    if (items[y * width + x] == null) {
                        continue;
                    }

                    PositionedStack stack = new PositionedStack(items[y * width + x],
                            GuiRPGWorkbench.craftX + x * 18 - offsetX, GuiRPGWorkbench.craftY + y * 18 - offsetY, false);
                    stack.setMaxSize(1);
                    ingredients.add(stack);
                }
            }
        }

        @Override
        public List<PositionedStack> getIngredients()
        {
            return getCycledIngredients(cycleticks / 20, ingredients);
        }

        @Override
        public PositionedStack getResult()
        {
            return result;
        }

        public void computeVisuals()
        {
            for (PositionedStack p : ingredients) {
                p.generatePermutations();
            }
        }
    }
}
