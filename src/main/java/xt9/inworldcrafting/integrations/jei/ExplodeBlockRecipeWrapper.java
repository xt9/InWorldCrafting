package xt9.inworldcrafting.integrations.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import xt9.inworldcrafting.InWorldCrafting;
import xt9.inworldcrafting.common.recipe.ExplodeBlockRecipe;

/**
 * Created by xt9 on 2019-01-20.
 */
public class ExplodeBlockRecipeWrapper implements IRecipeWrapper {
    private ExplodeBlockRecipe recipe;

    public ExplodeBlockRecipeWrapper(ExplodeBlockRecipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(ItemStack.class, recipe.getInputStack());
        ingredients.setOutput(ItemStack.class, recipe.getOutputStack());
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        FontRenderer renderer = minecraft.fontRenderer;
        renderer.drawStringWithShadow(I18n.format(InWorldCrafting.MODID+".jei.explode_blocks.description", String.valueOf(recipe.getItemSpawnChance())), 1, 30, 0xFFFFFF);
    }
}
