package xt9.inworldcrafting.common.crafttweaker;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import xt9.inworldcrafting.ModConfig;
import xt9.inworldcrafting.common.event.EntityReplacer;
import xt9.inworldcrafting.common.recipe.FluidToFluidRecipe;
import xt9.inworldcrafting.common.util.ModClashInfo;

/**
 * Created by xt9 on 2019-01-12.
 */
@SuppressWarnings("unused")
@ZenClass("mods.inworldcrafting.FluidToFluid")
public class FluidToFluid {

    @ZenMethod
    public static void transform(ILiquidStack outputFluid, ILiquidStack inputFluid, IItemStack inputItem) {
        transform(outputFluid, inputFluid, inputItem, true);
    }

    @ZenMethod
    public static void transform(ILiquidStack outputFluid, ILiquidStack inputFluid, IIngredient ingredient) {
        transform(outputFluid, inputFluid, ingredient, true);
    }

    @ZenMethod
    public static void transform(ILiquidStack outputFluid, ILiquidStack inputFluid, IIngredient ingredient, boolean consume) {
        /* Inputs should only be items or oredicts */
        if(ingredient.getLiquids().size() > 0) { return; }

        String outputFluidName = getFluidName(outputFluid);
        String inputFluidName = getFluidName(inputFluid);

        ModClashInfo info = new ModClashInfo();
        if(!ModConfig.blacklistDisabled) {
            info.checkForClashes(ingredient);
        }

        if(!info.isModClashed()) {
            EntityReplacer.allValidInputs.add(ingredient);
            FluidToFluidRecipe.addRecipe(outputFluidName, inputFluidName, ingredient, ingredient.getAmount(), consume);
        }
    }


    private static String getFluidName(ILiquidStack stack) {
        return stack.getDefinition().getName();
    }
}
