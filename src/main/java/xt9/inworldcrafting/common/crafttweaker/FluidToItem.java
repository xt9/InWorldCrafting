package xt9.inworldcrafting.common.crafttweaker;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import xt9.inworldcrafting.common.event.EntityMatcher;
import xt9.inworldcrafting.common.recipe.FluidToItemRecipe;

/**
 * Created by xt9 on 2019-01-12.
 */
@SuppressWarnings("unused")
@ZenClass("mods.inworldcrafting.FluidToItem")
public class FluidToItem {
    @ZenMethod
    public static void transform(IItemStack outputItem, ILiquidStack inputFluid, IItemStack inputItem) {
        transform(outputItem, inputFluid, inputItem, false);
    }

    @ZenMethod
    public static void transform(IItemStack outputItem, ILiquidStack inputFluid, IIngredient ingredient) {
        transform(outputItem, inputFluid, ingredient, false);
    }

    @ZenMethod
    public static void transform(IItemStack outputItem, ILiquidStack inputFluid, IIngredient ingredient, boolean consume) {
        /* Inputs should only be items or oredicts */
        if(ingredient.getLiquids().size() > 0) { return; }

        ItemStack outputItemStack = CraftTweakerMC.getItemStack(outputItem);
        String inputFluidName = getFluidName(inputFluid);


        EntityMatcher.allValidInputs.add(ingredient);
        FluidToItemRecipe.addRecipe(outputItemStack, inputFluidName, ingredient, ingredient.getAmount(), consume);
    }

    private static String getFluidName(ILiquidStack stack) {
        return stack.getDefinition().getName();
    }
}
