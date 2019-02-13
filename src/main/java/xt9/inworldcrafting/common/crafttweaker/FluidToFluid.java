package xt9.inworldcrafting.common.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import xt9.inworldcrafting.InWorldCrafting;
import xt9.inworldcrafting.common.event.EntityMatcher;
import xt9.inworldcrafting.common.recipe.FluidToFluidRecipe;

import java.util.Arrays;

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
        transform(outputFluid, inputFluid, new IIngredient[]{ingredient}, consume);
    }

    @ZenMethod
    public static void transform(ILiquidStack outputFluid, ILiquidStack inputFluid, IIngredient[] ingredients) {
        transform(outputFluid, inputFluid, ingredients, true);
    }

    @ZenMethod
    public static void transform(ILiquidStack outputFluid, ILiquidStack inputFluid, IIngredient[] ingredients, boolean consume) {
        /* Inputs should only be items or oredicts */
        for (int i = 0; i < ingredients.length; i++) {
            if (ingredients[i].getLiquids().size() > 0) {
                return;
            }
        }

        String outputFluidName = getFluidName(outputFluid);
        String inputFluidName = getFluidName(inputFluid);

        boolean isValidOutBlock = true;
        Fluid outFluid = FluidRegistry.getFluid(outputFluidName);
        if (outFluid.getBlock() == null) {
            isValidOutBlock = false;
            CraftTweakerAPI.logError(InWorldCrafting.MODID + ": <liquid:" + outFluid.getName() + "> has no registered Block, it cannot be used as an Output for FluidToFluid crafting.");
        }

        if (isValidOutBlock) {
            EntityMatcher.allValidInputs.addAll(Arrays.asList(ingredients));
            FluidToFluidRecipe.addRecipe(outputFluidName, inputFluidName, ingredients, consume);
        }
    }

    private static String getFluidName(ILiquidStack stack) {
        return stack.getDefinition().getName();
    }
}
