package xt9.inworldcrafting.integrations.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import xt9.inworldcrafting.InWorldCrafting;

/**
 * Created by xt9 on 2019-01-13.
 */
public class FluidToItemRecipeCategory implements IRecipeCategory {
    private IDrawable background;

    public FluidToItemRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation base = new ResourceLocation(InWorldCrafting.MODID, "textures/gui/jei.png");
        background = guiHelper.createDrawable(base,0, 0, 116, 36, 0, 0, 0, 0);
    }

    @Override
    public void setRecipe(IRecipeLayout layout, IRecipeWrapper wrapper, IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = layout.getItemStacks();
        IGuiFluidStackGroup fluidStacks = layout.getFluidStacks();

        itemStacks.init(0, true, 1, 1);
        itemStacks.set(0, ingredients.getInputs(ItemStack.class).get(0));

        fluidStacks.init(1, true, 50, 2);
        fluidStacks.set(1, ingredients.getInputs(FluidStack.class).get(0));

        itemStacks.init(2, true, 97, 1);
        itemStacks.set(2, ingredients.getOutputs(ItemStack.class).get(0));
    }

    @Override
    public String getUid() {
        return InWorldCrafting.MODID + ".itemtransform";
    }

    @Override
    public String getTitle() {
        return "Fluid to Item Transformation";
    }

    @Override
    public String getModName() {
        return InWorldCrafting.MODID;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }
}
