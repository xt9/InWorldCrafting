package xt9.inworldcrafting.integrations.jei;

import mezz.jei.api.*;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import xt9.inworldcrafting.common.recipe.*;

/**
 * Created by xt9 on 2019-01-13.
 */
@JEIPlugin
public class Plugin implements IModPlugin {
    private static IJeiHelpers jeiHelpers;

    private static FluidToFluidRecipeCategory fluidCategory;
    private static FluidToItemRecipeCategory itemCategory;
    private static ExplodeItemRecipeCategory explodeItemCategory;
    private static ExplodeBlockRecipeCategory explodeBlockCategory;
    private static BurnItemRecipeCategory burnItemCategory;

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        jeiHelpers = registry.getJeiHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

        fluidCategory = new FluidToFluidRecipeCategory(guiHelper);
        registry.addRecipeCategories(fluidCategory);

        itemCategory = new FluidToItemRecipeCategory(guiHelper);
        registry.addRecipeCategories(itemCategory);

        explodeItemCategory = new ExplodeItemRecipeCategory(guiHelper);
        registry.addRecipeCategories(explodeItemCategory);

        explodeBlockCategory = new ExplodeBlockRecipeCategory(guiHelper);
        registry.addRecipeCategories(explodeBlockCategory);

        burnItemCategory = new BurnItemRecipeCategory(guiHelper);
        registry.addRecipeCategories(burnItemCategory);
    }

    @Override
    public void register(IModRegistry registry) {
        jeiHelpers = registry.getJeiHelpers();

        registry.handleRecipes(FluidToFluidRecipe.class, FluidToFluidRecipeWrapper::new, fluidCategory.getUid());
        addFluidToFluidRecipes(registry);

        registry.handleRecipes(FluidToItemRecipe.class, FluidToItemRecipeWrapper::new, itemCategory.getUid());
        addFluidToItemRecipes(registry);

        registry.handleRecipes(ExplodeItemRecipe.class, ExplodeItemRecipeWrapper::new, explodeItemCategory.getUid());
        addExplodeItemRecipes(registry);

        registry.handleRecipes(ExplodeBlockRecipe.class, ExplodeBlockRecipeWrapper::new, explodeBlockCategory.getUid());
        addExplodeBlocksRecipes(registry);

        registry.handleRecipes(BurnItemRecipe.class, BurnItemRecipeWrapper::new, burnItemCategory.getUid());
        addBurnItemRecipes(registry);
    }

    private void addBurnItemRecipes(IModRegistry registry) {
        registry.addRecipes(BurnItemRecipe.recipes, burnItemCategory.getUid());
        registry.addRecipeCatalyst(new ItemStack(Items.FLINT_AND_STEEL), burnItemCategory.getUid());
    }

    private void addExplodeBlocksRecipes(IModRegistry registry) {
        registry.addRecipes(ExplodeBlockRecipe.recipes, explodeBlockCategory.getUid());
        registry.addRecipeCatalyst(new ItemStack(Blocks.TNT), explodeBlockCategory.getUid());

        ItemStack creeperSkull = new ItemStack(Items.SKULL);
        creeperSkull.setItemDamage(4);
        registry.addRecipeCatalyst(creeperSkull, explodeBlockCategory.getUid());
    }

    private void addExplodeItemRecipes(IModRegistry registry) {
        registry.addRecipes(ExplodeItemRecipe.recipes, explodeItemCategory.getUid());
        registry.addRecipeCatalyst(new ItemStack(Blocks.TNT), explodeItemCategory.getUid());

        ItemStack creeperSkull = new ItemStack(Items.SKULL);
        creeperSkull.setItemDamage(4);
        registry.addRecipeCatalyst(creeperSkull, explodeItemCategory.getUid());
    }

    private void addFluidToFluidRecipes(IModRegistry registry) {
        registry.addRecipes(FluidToFluidRecipe.recipes, fluidCategory.getUid());
    }

    private void addFluidToItemRecipes(IModRegistry registry) {
        registry.addRecipes(FluidToItemRecipe.recipes, itemCategory.getUid());
    }
}
