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

/**
 * Created by xt9 on 2019-01-12.
 */
@SuppressWarnings("unused")
@ZenClass("mods.inworldcrafting.FluidToFluid")
public class FluidToFluid {
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

        int inputlength = ingredients.length > 4 ? 4 : ingredients.length;
        IIngredient[] inputs = new IIngredient[inputlength];
        System.arraycopy(ingredients, 0, inputs, 0, inputs.length);

        String outputFluidName = getFluidName(outputFluid);
        String inputFluidName = getFluidName(inputFluid);

        boolean isValidOutBlock = true;
        Fluid outFluid = FluidRegistry.getFluid(outputFluidName);
        if (outFluid.getBlock() == null) {
            isValidOutBlock = false;
            CraftTweakerAPI.logError(InWorldCrafting.MODID + ": <liquid:" + outFluid.getName() + "> has no registered Block, it cannot be used as an Output for FluidToFluid crafting.");
        }

        if (isValidOutBlock) {
            // Only the first ingredient on the list needs to be added because each craft only need one "Controller" item
            EntityMatcher.allValidInputs.add(ingredients[0]);
            FluidToFluidRecipe.addRecipe(outputFluidName, inputFluidName, inputs, consume);
        }
    }

    private static String getFluidName(ILiquidStack stack) {
        return stack.getDefinition().getName();
    }
}
