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

        int inputlength = ingredients.length > 4 ? 4 : ingredients.length;
        IIngredient[] inputs = new IIngredient[inputlength];
        System.arraycopy(ingredients, 0, inputs, 0, inputs.length);

        ItemStack outputItemStack = CraftTweakerMC.getItemStack(outputItem);
        String inputFluidName = getFluidName(inputFluid);

        // Only the first ingredient on the list needs to be added because each craft only need one "Controller" item
        EntityMatcher.allValidInputs.add(ingredients[0]);
        FluidToItemRecipe.addRecipe(outputItemStack, inputFluidName, inputs, consume);
    }

    private static String getFluidName(ILiquidStack stack) {
        return stack.getDefinition().getName();
    }
}
