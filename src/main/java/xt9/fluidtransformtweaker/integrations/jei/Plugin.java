package xt9.fluidtransformtweaker.integrations.jei;

import mezz.jei.api.*;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import xt9.fluidtransformtweaker.common.recipe.FluidToFluidTransform;
import xt9.fluidtransformtweaker.common.recipe.FluidToItemTransform;

/**
 * Created by xt9 on 2019-01-13.
 */
@JEIPlugin
public class Plugin implements IModPlugin {
    private static IJeiHelpers jeiHelpers;

    private static FluidToFluidRecipeCategory fluidCategory;
    private static FluidToItemRecipeCategory itemCategory;

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        jeiHelpers = registry.getJeiHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

        fluidCategory = new FluidToFluidRecipeCategory(guiHelper);
        registry.addRecipeCategories(fluidCategory);

        itemCategory = new FluidToItemRecipeCategory(guiHelper);
        registry.addRecipeCategories(itemCategory);
    }

    @Override
    public void register(IModRegistry registry) {
        jeiHelpers = registry.getJeiHelpers();

        registry.handleRecipes(FluidToFluidTransform.class, FluidToFluidRecipeWrapper::new, fluidCategory.getUid());
        addFluidToFluidRecipes(registry);

        registry.handleRecipes(FluidToItemTransform.class, FluidToItemRecipeWrapper::new, itemCategory.getUid());
        addFluidToItemRecipes(registry);
    }

    private void addFluidToFluidRecipes(IModRegistry registry) {
        registry.addRecipes(FluidToFluidTransform.recipes, fluidCategory.getUid());
    }

    private void addFluidToItemRecipes(IModRegistry registry) {
        registry.addRecipes(FluidToItemTransform.recipes, itemCategory.getUid());
    }
}
