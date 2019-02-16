package xt9.inworldcrafting.common.util;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.ArrayList;
import java.util.List;

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

    public static List getStacksFromIngredients(IIngredient[] ingredients) {
        List<List<ItemStack>> inputs = new ArrayList<>();
        for (IIngredient ingredient : ingredients) {
            List<ItemStack> stacks = new ArrayList<>();
            for (IItemStack iStack : ingredient.getItems()) {
                ItemStack stack = CraftTweakerMC.getItemStack(iStack);
                stack.setCount(ingredient.getAmount());
                stacks.add(stack);
            }
            inputs.add(stacks);
        }
        return inputs;
    }
}
