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

/**
 * Created by xt9 on 2019-01-20.
 */
public class BurnItemRecipeCategory implements IRecipeCategory {
    private IDrawable background;

    public BurnItemRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation base = new ResourceLocation(InWorldCrafting.MODID, "textures/gui/jei_burn.png");
        background = guiHelper.createDrawable(base,0, 0, 80, 36, 4, 0, 18, 18);
    }

    @Override
    public void setRecipe(IRecipeLayout layout, IRecipeWrapper wrapper, IIngredients ingredients) {
        IGuiItemStackGroup itemStacks = layout.getItemStacks();

        itemStacks.init(0, true, 19, 5);
        itemStacks.set(0, ingredients.getInputs(ItemStack.class).get(0));

        itemStacks.init(1, true, 79, 5);
        itemStacks.set(1, ingredients.getOutputs(ItemStack.class).get(0));
    }

    @Override
    public String getUid() {
        return InWorldCrafting.MODID + ".burn_item";
    }

    @Override
    public String getTitle() {
        return I18n.format(InWorldCrafting.MODID+".jei.burn_item.title");
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
