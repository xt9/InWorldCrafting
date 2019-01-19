package xt9.fluidtransformtweaker.common.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.ArrayList;

/**
 * Created by xt9 on 2019-01-12.
 */
public class FluidToFluidTransform {
    public static ArrayList<FluidToFluidTransform> recipes = new ArrayList<>();

    private String outputFluid;
    private String inputFluid;
    private NonNullList<ItemStack> inputs;
    private int inputAmount;
    private boolean consume;

    public FluidToFluidTransform(String outputFluid, String inputFluid, NonNullList<ItemStack> inputs, int inputAmount,  boolean consume) {
        this.outputFluid = outputFluid;
        this.inputFluid = inputFluid;
        this.inputs = inputs;
        this.inputAmount = inputAmount;
        this.consume = consume;
    }

    public static void addRecipe(String outputFluid, String inputFluid, NonNullList<ItemStack> inputs, int inputAmount, boolean consume) {
        recipes.add(new FluidToFluidTransform(outputFluid, inputFluid, inputs, inputAmount, consume));
    }

    public String getOutputFluid() {
        return outputFluid;
    }

    public String getInputFluid() {
        return inputFluid;
    }

    public boolean willConsume() {
        return consume;
    }

    public NonNullList<ItemStack> getInputs() {
        return inputs;
    }

    public int getInputAmount() {
        return inputAmount;
    }
}
