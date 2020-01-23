package xt9.inworldcrafting.integrations.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import xt9.inworldcrafting.InWorldCrafting;

import java.util.List;

/**
 * Created by xt9 on 2019-01-13.
 */
public class FluidToFluidRecipeCategory implements IRecipeCategory {
    private IDrawable background;

    public FluidToFluidRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation base = new ResourceLocation(InWorldCrafting.MODID, "textures/gui/jei.png");
        background = guiHelper.createDrawable(base,0, 0, 177, 36, 0, 0, 0, 0);
    }

    @Override
    public void setRecipe(IRecipeLayout layout, IRecipeWrapper wrapper, IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = layout.getItemStacks();
        IGuiFluidStackGroup fluidStacks = layout.getFluidStacks();

        List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);

        int slotindex = 0;
        for (List<ItemStack> input : inputs) {
            itemStacks.init(slotindex, true, 64 - (slotindex) * 21, 1);
            itemStacks.set(slotindex, input);
            slotindex++;
        }

        slotindex++;
        fluidStacks.init(slotindex, true, 112, 2);
        fluidStacks.set(slotindex, ingredients.getInputs(FluidStack.class).get(0));

        slotindex++;
        fluidStacks.init(slotindex, true, 159, 2);
        fluidStacks.set(slotindex, ingredients.getOutputs(FluidStack.class).get(0));
    }

    @Override
    public String getUid() {
        return InWorldCrafting.MODID + ".fluid_to_fluid";
    }

    @Override
    public String getTitle() {
        return I18n.format(InWorldCrafting.MODID+".jei.fluid_to_fluid.title");
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
