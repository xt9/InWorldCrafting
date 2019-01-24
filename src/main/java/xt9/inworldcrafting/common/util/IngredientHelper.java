package xt9.inworldcrafting.common.util;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

/**
 * Created by xt9 on 2019-01-24.
 */
public class IngredientHelper {
    public static NonNullList<ItemStack> getStacksFromIngredient(IIngredient ingredient) {
        NonNullList<ItemStack> inputs = NonNullList.create();
        ingredient.getItems().forEach(iiStack -> {
            ItemStack stack = CraftTweakerMC.getItemStack(iiStack);
            stack.setCount(ingredient.getAmount());
            inputs.add(stack);
        });
        return inputs;
    }
}
