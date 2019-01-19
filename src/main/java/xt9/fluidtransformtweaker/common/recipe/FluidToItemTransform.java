package xt9.fluidtransformtweaker.common.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.ArrayList;

/**
 * Created by xt9 on 2019-01-12.
 */
public class FluidToItemTransform {
    public static ArrayList<FluidToItemTransform> recipes = new ArrayList<>();

    private ItemStack outputItem;
    private String inputFluid;
    private NonNullList<ItemStack> inputs;
    private int inputAmount;
    private boolean consume;

    public FluidToItemTransform(ItemStack outputItem, String inputFluid, NonNullList<ItemStack> inputs, int inputAmount, boolean consume) {
        this.outputItem = outputItem;
        this.inputFluid = inputFluid;
        this.inputs = inputs;
        this.consume = consume;
        this.inputAmount = inputAmount;
    }

    public static void addRecipe(ItemStack outputItem, String inputFluid, NonNullList<ItemStack> inputs, int inputAmount, boolean consume) {
        recipes.add(new FluidToItemTransform(outputItem, inputFluid, inputs, inputAmount, consume));
    }

    public ItemStack getOutputItem() {
        return outputItem;
    }

    public String getInputFluid() {
        return inputFluid;
    }

    public boolean willConsume() {
        return consume;
    }

    public int getInputAmount() {
        return inputAmount;
    }

    public NonNullList<ItemStack> getInputs() {
        return inputs;
    }
}
