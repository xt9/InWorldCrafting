package xt9.fluidtransformtweaker.integrations.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import xt9.fluidtransformtweaker.common.recipe.FluidToItemTransform;

/**
 * Created by xt9 on 2019-01-13.
 */
public class FluidToItemRecipeWrapper implements IRecipeWrapper {
    private FluidToItemTransform recipe;

    public FluidToItemRecipeWrapper(FluidToItemTransform recipe) {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(ItemStack.class, recipe.getInputs());
        ingredients.setInput(FluidStack.class, new FluidStack(FluidRegistry.getFluid(recipe.getInputFluid()), 1000));
        ingredients.setOutput(ItemStack.class, recipe.getOutputItem());
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        FontRenderer renderer = minecraft.fontRenderer;
        renderer.drawStringWithShadow("Consumes Fluid: ", 2, 26, 0xFFFFFF);

        if(recipe.willConsume()) {
            renderer.drawStringWithShadow("Yes", 84, 26, 0xcc0036);
        } else {
            renderer.drawStringWithShadow("No", 84, 26, 0xc9f26a);
        }
    }
}
