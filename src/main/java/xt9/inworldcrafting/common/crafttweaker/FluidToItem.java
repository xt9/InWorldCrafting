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

import java.util.Arrays;

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
        transform(outputItem, inputFluid, new IIngredient[]{ingredient}, consume);
    }

    @ZenMethod
    public static void transform(IItemStack outputItem, ILiquidStack inputFluid, IIngredient[] ingredients) {
        transform(outputItem, inputFluid, ingredients, false);
    }

    @ZenMethod
    public static void transform(IItemStack outputItem, ILiquidStack inputFluid, IIngredient[] ingredients, boolean consume) {
        /* Inputs should only be items or oredicts */
        for (int i = 0; i < ingredients.length; i++) {
            if (ingredients[i].getLiquids().size() > 0) {
                return;
            }
        }

        ItemStack outputItemStack = CraftTweakerMC.getItemStack(outputItem);
        String inputFluidName = getFluidName(inputFluid);

        EntityMatcher.allValidInputs.add(ingredients[0]);
        FluidToItemRecipe.addRecipe(outputItemStack, inputFluidName, ingredients, consume);
    }

    private static String getFluidName(ILiquidStack stack) {
        return stack.getDefinition().getName();
    }
}
