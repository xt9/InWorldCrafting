package xt9.inworldcrafting.integrations.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import xt9.inworldcrafting.InWorldCrafting;
import xt9.inworldcrafting.common.recipe.FluidToItemRecipe;
import xt9.inworldcrafting.common.util.IngredientHelper;

/**
 * Created by xt9 on 2019-01-13.
 */
public class FluidToItemRecipeWrapper implements IRecipeWrapper {
    private FluidToItemRecipe recipe;

    public FluidToItemRecipeWrapper(FluidToItemRecipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputs(ItemStack.class, IngredientHelper.getStacksFromIngredients(recipe.getInputs()));
        ingredients.setInput(FluidStack.class, new FluidStack(FluidRegistry.getFluid(recipe.getInputFluid()), 1000));
        ingredients.setOutput(ItemStack.class, recipe.getOutputStack());
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        FontRenderer renderer = minecraft.fontRenderer;
        renderer.drawStringWithShadow(I18n.format(InWorldCrafting.MODID+".jei.if_consumes_liquid"), 2, 26, 0xFFFFFF);

        if(recipe.willConsume()) {
            renderer.drawStringWithShadow(I18n.format(InWorldCrafting.MODID+".jei.yes"), 84, 26, 0xcc0036);
        } else {
            renderer.drawStringWithShadow(I18n.format(InWorldCrafting.MODID+".jei.no"), 84, 26, 0xc9f26a);
        }
    }
}
