package xt9.inworldcrafting.common.recipe;

import crafttweaker.api.item.IIngredient;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

/**
 * Created by xt9 on 2019-01-12.
 */
public class FluidToItemRecipe {
    public static ArrayList<FluidToItemRecipe> recipes = new ArrayList<>();

    private ItemStack outputStack;
    private String inputFluid;
    private IIngredient[] inputs;
    private boolean consume;

    private FluidToItemRecipe(ItemStack outputStack, String inputFluid, IIngredient[] inputs, boolean consume) {
        this.outputStack = outputStack;
        this.inputFluid = inputFluid;
        this.inputs = inputs;
        this.consume = consume;
    }

    public static void addRecipe(ItemStack outputItem, String inputFluid, IIngredient[] inputs, boolean consume) {
        recipes.add(new FluidToItemRecipe(outputItem, inputFluid, inputs, consume));
    }

    public ItemStack getOutputStack() {
        return outputStack;
    }

    public String getInputFluid() {
        return inputFluid;
    }

    public boolean willConsume() {
        return consume;
    }

    public IIngredient[] getInputs() {
        return inputs;
    }
}
