package xt9.inworldcrafting.common.recipe;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import java.util.ArrayList;

/**s
 * Created by xt9 on 2019-01-19.
 */
public class ExplodeBlockRecipe {
    public static ArrayList<ExplodeBlockRecipe> recipes = new ArrayList<>();

    private ItemStack output;
    private ItemStack input;
    private int itemSpawnChance;

    private ExplodeBlockRecipe(ItemStack output, ItemStack input, int itemSpawnChance) {
        this.output = output;
        this.input = input;
        this.itemSpawnChance = itemSpawnChance;
    }

    public static void addRecipe(ItemStack output, ItemStack input, int itemSpawnChance) {
        recipes.add(new ExplodeBlockRecipe(output, input, itemSpawnChance));
    }

    public ItemStack getOutputStack() {
        return output;
    }

    public ItemStack getInputStack() {
        return input;
    }

    public Block getInputBlock() {
        return Block.getBlockFromItem(input.getItem());
    }

    public int getItemSpawnChance() {
        return itemSpawnChance;
    }
}
