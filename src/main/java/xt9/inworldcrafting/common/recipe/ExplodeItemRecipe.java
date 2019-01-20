package xt9.inworldcrafting.common.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.ArrayList;

/**
 * Created by xt9 on 2019-01-19.
 */
public class ExplodeItemRecipe {
    public static ArrayList<ExplodeItemRecipe> recipes = new ArrayList<>();

    private ItemStack outputStack;
    private NonNullList<ItemStack> inputs;
    private int inputAmount;
    private int surviveChance;

    private ExplodeItemRecipe(ItemStack stack, NonNullList<ItemStack> inputs, int inputAmount, int surviveChance) {
        this.outputStack = stack;
        this.inputs = inputs;
        this.surviveChance = surviveChance;
        this.inputAmount = inputAmount;
    }

    public static void addRecipe(ItemStack stack, NonNullList<ItemStack> inputs, int inputAmount, int surviveChance) {
        recipes.add(new ExplodeItemRecipe(stack, inputs, inputAmount, surviveChance));
    }

    public NonNullList<ItemStack> getInputs() {
        return inputs;
    }

    public int getSurviveChance() {
        return surviveChance;
    }

    public ItemStack getOutputStack() {
        return outputStack;
    }

    public int getInputAmount() {
        return inputAmount;
    }
}
