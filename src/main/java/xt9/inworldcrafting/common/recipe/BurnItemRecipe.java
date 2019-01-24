package xt9.inworldcrafting.common.recipe;

import crafttweaker.api.item.IIngredient;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.ArrayList;

/**
 * Created by xt9 on 2019-01-19.
 */
public class BurnItemRecipe {
    public static ArrayList<BurnItemRecipe> recipes = new ArrayList<>();

    private ItemStack output;
    private IIngredient inputs;
    private int inputAmount;
    private int ticks;

    private BurnItemRecipe(ItemStack output, IIngredient inputs, int inputAmount, int ticks) {
        this.output = output;
        this.inputs = inputs;
        this.inputAmount = inputAmount;
        this.ticks = ticks;
    }

    public static void addRecipe(ItemStack output, IIngredient inputs, int inputAmount, int ticks) {
        recipes.add(new BurnItemRecipe(output, inputs, inputAmount, ticks));
    }

    public ItemStack getOutputStack() {
        return output;
    }

    public IIngredient getInputs() {
        return inputs;
    }

    public int getTicks() {
        return ticks;
    }

    public int getInputAmount() {
        return inputAmount;
    }
}
