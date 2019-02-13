package xt9.inworldcrafting.common.recipe;

import crafttweaker.api.item.IIngredient;

import java.util.ArrayList;

/**
 * Created by xt9 on 2019-01-12.
 */
public class FluidToFluidRecipe {
    public static ArrayList<FluidToFluidRecipe> recipes = new ArrayList<>();

    private String outputFluid;
    private String inputFluid;
    private IIngredient[] inputs;
    private boolean consume;

    private FluidToFluidRecipe(String outputFluid, String inputFluid, IIngredient[] inputs, boolean consume) {
        this.outputFluid = outputFluid;
        this.inputFluid = inputFluid;
        this.inputs = inputs;
        this.consume = consume;
    }

    public static void addRecipe(String outputFluid, String inputFluid, IIngredient[] inputs, boolean consume) {
        recipes.add(new FluidToFluidRecipe(outputFluid, inputFluid, inputs, consume));
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

    public IIngredient[] getInputs() {
        return inputs;
    }
}
