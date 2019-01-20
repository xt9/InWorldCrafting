package xt9.inworldcrafting.integrations.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import xt9.inworldcrafting.common.recipe.ExplodeItemRecipe;

/**
 * Created by xt9 on 2019-01-20.
 */
public class ExplodeItemRecipeWrapper implements IRecipeWrapper {
    private ExplodeItemRecipe recipe;

    public ExplodeItemRecipeWrapper(ExplodeItemRecipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(ItemStack.class, recipe.getInputs());
        ingredients.setOutput(ItemStack.class, recipe.getOutputStack());
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        FontRenderer renderer = minecraft.fontRenderer;
        renderer.drawStringWithShadow("Success chance: " + recipe.getSurviveChance() + "%", 1, 30, 0xFFFFFF);
    }
}
