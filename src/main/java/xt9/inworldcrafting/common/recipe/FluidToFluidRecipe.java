package xt9.inworldcrafting.common.recipe;

import crafttweaker.api.item.IIngredient;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.ArrayList;

/**
 * Created by xt9 on 2019-01-12.
 */
public class FluidToFluidRecipe {
    public static ArrayList<FluidToFluidRecipe> recipes = new ArrayList<>();

    private String outputFluid;
    private String inputFluid;
    private IIngredient inputs;
    private int inputAmount;
    private boolean consume;

    private FluidToFluidRecipe(String outputFluid, String inputFluid, IIngredient inputs, int inputAmount, boolean consume) {
        this.outputFluid = outputFluid;
        this.inputFluid = inputFluid;
        this.inputs = inputs;
        this.inputAmount = inputAmount;
        this.consume = consume;
    }

    public static void addRecipe(String outputFluid, String inputFluid, IIngredient inputs, int inputAmount, boolean consume) {
        recipes.add(new FluidToFluidRecipe(outputFluid, inputFluid, inputs, inputAmount, consume));
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

    public IIngredient getInputs() {
        return inputs;
    }

    public int getInputAmount() {
        return inputAmount;
    }
}
