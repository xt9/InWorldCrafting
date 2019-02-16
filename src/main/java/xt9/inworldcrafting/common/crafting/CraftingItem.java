package xt9.inworldcrafting.common.crafting;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import xt9.inworldcrafting.InWorldCrafting;
import xt9.inworldcrafting.common.recipe.BurnItemRecipe;
import xt9.inworldcrafting.common.recipe.FluidToFluidRecipe;
import xt9.inworldcrafting.common.recipe.FluidToItemRecipe;
import xt9.inworldcrafting.common.util.ItemStackHelper;
import xt9.inworldcrafting.common.util.WorldHelper;

import java.util.*;

import static net.minecraft.block.BlockLiquid.LEVEL;

/**
 * Created by xt9 on 2019-02-03.
 */
public class CraftingItem {
    private List<Integer> fluidToFluidRecipeIndexes = new ArrayList<>();
    private List<Integer> fluidToItemRecipeIndexes = new ArrayList<>();
    private World world;
    private EntityItem item;
    private List<EntityItem> nearbyItems = new ArrayList<>();
    private int burnItemRecipeIndex = -1;
    private int burnProgress = 0;

    public CraftingItem(NBTTagCompound tag) {
        deserialize(tag);
    }

    public void deserialize(NBTTagCompound tag) {
        fluidToFluidRecipeIndexes = tag.hasKey("fluidToFluidRecipeIndexes") ? getListFromArray(tag.getIntArray("fluidToFluidRecipeIndexes")) : new ArrayList<>();
        fluidToItemRecipeIndexes = tag.hasKey("fluidToItemRecipeIndexes") ? getListFromArray(tag.getIntArray("fluidToItemRecipeIndexes")) : new ArrayList<>();
        burnItemRecipeIndex = tag.hasKey("burnItemRecipeIndex") ? tag.getInteger("burnItemRecipeIndex") : -1;
        burnProgress = tag.getInteger("burnProgress");
    }

    public NBTTagCompound serialize() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setIntArray("fluidToFluidRecipeIndexes", getArrayFromList(fluidToFluidRecipeIndexes));
        tag.setIntArray("fluidToItemRecipeIndexes", getArrayFromList(fluidToItemRecipeIndexes));
        tag.setInteger("burnItemRecipeIndex", burnItemRecipeIndex);
        tag.setInteger("burnProgress", burnProgress);
        return tag;
    }

    public void addFluidToFluidRecipeIndex(int index) {
        fluidToFluidRecipeIndexes.add(index);
    }

    public void addFluidToItemRecipeIndex(int index) {
        fluidToItemRecipeIndexes.add(index);
    }

    public void setBurnItemRecipe(int index) {
        this.burnItemRecipeIndex = index;
    }

    public boolean containsRecipes() {
        return fluidToItemRecipeIndexes.size() > 0 || fluidToFluidRecipeIndexes.size() > 0 || burnItemRecipeIndex != -1;
    }

    public void update(EntityItem item, World world) {
        this.world = world;
        this.item = item;

        if (containsRecipes()) {
            IBlockState state = world.getBlockState(item.getPosition());
            nearbyItems.clear();
            nearbyItems.addAll(WorldHelper.getAllItemEntitiesAtPosition(world, item.getPosition()));
            nearbyItems.removeIf(entityItem -> world.getBlockState(entityItem.getPosition()) != state);

            if (!fluidToFluidRecipeIndexes.isEmpty()) {
                fluidToFluidRecipeIndexes.forEach(i -> {
                    if (i < FluidToFluidRecipe.recipes.size()) {
                        FluidToFluidRecipe r = FluidToFluidRecipe.recipes.get(i);
                        Fluid inputFluid = FluidRegistry.getFluid(r.getInputFluid());
                        updateFluidToFluid(state, inputFluid.getBlock(), r);
                    }
                });
            }

            if (!fluidToItemRecipeIndexes.isEmpty()) {
                fluidToItemRecipeIndexes.forEach(i -> {
                    if (i < FluidToItemRecipe.recipes.size()) {
                        FluidToItemRecipe r = FluidToItemRecipe.recipes.get(i);
                        Fluid inputFluid = FluidRegistry.getFluid(r.getInputFluid());
                        updateFluidToItem(state, inputFluid.getBlock(), r);
                    }
                });
            }

            if (burnItemRecipeIndex != -1) {
                if (burnItemRecipeIndex < BurnItemRecipe.recipes.size()) {
                    BurnItemRecipe r = BurnItemRecipe.recipes.get(burnItemRecipeIndex);
                    /* Reset progress if it stops burning */
                    if (!item.isBurning()) {
                        burnProgress = 0;
                    }

                    if (item.isBurning()) {
                        burnProgress++;
                        if (this.burnProgress == r.getTicks()) {
                            updateBurnItem(r);
                        }
                    }
                }
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    private void updateFluidToFluid(IBlockState state, Block block, FluidToFluidRecipe recipe) {
        if (state.getBlock() == block) {
            // Fluid Sourceblocks has a level of 0
            if (state.getValue(LEVEL) == 0) {
                NonNullList<IIngredient> required = NonNullList.create();
                required.addAll(Arrays.asList(recipe.getInputs()));

                Map<EntityItem, Integer> used = new HashMap<>();

                // Iterate through the nearby item entities.
                // Depending on if there are a ton of items lying around, this could get laggy as it's O(n^2)
                for (EntityItem entityItem : nearbyItems) {
                    Iterator<IIngredient> req = required.iterator();
                    while (req.hasNext()) {
                        IIngredient ingredient = req.next();
                        if (ingredient.matches(CraftTweakerMC.getIItemStack(entityItem.getItem()))) {
                            // If our entity doesn't have enough to fulfill the requirement
                            if (entityItem.getItem().getCount() < ingredient.getAmount()) {
                                used.put(entityItem, entityItem.getItem().getCount());
                                required.add(ingredient.amount(ingredient.getAmount() - entityItem.getItem().getCount()));
                            } else {
                                used.put(entityItem, ingredient.getAmount());
                            }
                            req.remove();
                            break;
                        }
                    }
                }
                // If we found all required ingredients
                if (required.isEmpty()) {
                    Fluid outFluid = FluidRegistry.getFluid(recipe.getOutputFluid());
                    if (outFluid.getBlock() != null) {
                        world.setBlockState(item.getPosition(), outFluid.getBlock().getDefaultState(), 3);
                        if (recipe.willConsume()) {
                            shrinkAndUpdateItems(used);
                        }
                    }
                }
            }
        }
    }

    private void updateFluidToItem(IBlockState state, Block block, FluidToItemRecipe recipe) {
        if (state.getBlock() == block) {
            if (item.getItem().getCount() == 0) {
                item.setDead();
                return;
            }

            EntityItem output = new EntityItem(world, item.posX, item.posY, item.posZ, recipe.getOutputStack().copy());
            output.motionX = 0;
            output.motionY = 0;
            output.motionZ = 0;
            output.setPickupDelay(10);

            NonNullList<IIngredient> required = NonNullList.create();
            required.addAll(Arrays.asList(recipe.getInputs()));

            Map<EntityItem, Integer> used = new HashMap<>();

            // Iterate through the nearby item entities.
            // Depending on if there are a ton of items lying around, this could get laggy as it's O(n^2)
            for (EntityItem entityItem : nearbyItems) {
                Iterator<IIngredient> req = required.iterator();
                while (req.hasNext()) {
                    IIngredient ingredient = req.next();
                    if (ingredient.amount(1).matches(CraftTweakerMC.getIItemStack(entityItem.getItem()))) {
                        // If our entity doesn't have enough to fulfill the requirement
                        if (entityItem.getItem().getCount() < ingredient.getAmount()) {
                            used.put(entityItem, entityItem.getItem().getCount());
                            required.add(ingredient.amount(ingredient.getAmount() - entityItem.getItem().getCount()));
                        } else {
                            used.put(entityItem, ingredient.getAmount());
                        }
                        req.remove();
                        break;
                    }
                }
            }

            if (required.isEmpty()) {
                if (recipe.willConsume()) {
                    // Fluid Sourceblocks has a level of 0
                    if (state.getValue(LEVEL) == 0) {
                        world.setBlockState(item.getPosition(), Blocks.AIR.getDefaultState(), 3);

                        world.spawnEntity(output);
                        shrinkAndUpdateItems(used);
                    }
                } else {
                    world.spawnEntity(output);
                    shrinkAndUpdateItems(used);
                }
            }
        }
    }

    private void updateBurnItem(BurnItemRecipe recipe) {
        if (item.getItem().getCount() == 0) {
            item.setDead();
            return;
        }

        /* Number of recipes this entity can complete with it's current stacksize */
        int recipeNum = (int) Math.floor(item.getItem().getCount() / recipe.getInputAmount());
        int items = recipeNum * recipe.getOutputStack().getCount();
        int stacks = 0;
        int remainder = 0;

        while (items != 0) {
            if (items >= 64) {
                stacks = stacks + 1;
                items = items - 64;
            } else {
                remainder = items;
                items = 0;
            }
        }

        for (int i = 0; i < stacks; i++) {
            EntityItem newItem = new EntityItem(world, item.posX, item.posY, item.posZ, recipe.getOutputStack().copy());
            newItem.getItem().setCount(64);
            newItem.motionX = 0;
            newItem.motionY = 0;
            newItem.motionZ = 0;
            newItem.setPickupDelay(10);
            newItem.setEntityInvulnerable(true);

            world.spawnEntity(newItem);
        }

        EntityItem newItem = new EntityItem(world, item.posX, item.posY, item.posZ, recipe.getOutputStack().copy());
        newItem.getItem().setCount(remainder);
        newItem.motionX = 0;
        newItem.motionY = 0;
        newItem.motionZ = 0;
        newItem.setPickupDelay(10);
        newItem.setEntityInvulnerable(true);

        world.spawnEntity(newItem);
        shrinkAndUpdateItem(recipeNum * recipe.getInputAmount());
        this.burnProgress = 0;
    }

    private void shrinkAndUpdateItem(int inputAmount) {
        ItemStack updatedItem = item.getItem().copy();
        updatedItem.shrink(inputAmount);
        item.setItem(updatedItem);
    }

    private void shrinkAndUpdateItems(Map<EntityItem, Integer> items) {
        for (Map.Entry<EntityItem, Integer> entry : items.entrySet()) {
            ItemStack updatedItem = entry.getKey().getItem().copy();
            updatedItem.shrink(entry.getValue());
            entry.getKey().setItem(updatedItem);
        }
    }

    private List<Integer> getListFromArray(int[] arr) {
        List<Integer> list = new ArrayList<>();
        for (int index : arr) {
            list.add(index);
        }
        return list;
    }

    private int[] getArrayFromList(List<Integer> list) {
        int[] indexes = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            indexes[i] = list.get(i);
        }
        return indexes;
    }

    public static String getNbtKey() {
        return InWorldCrafting.MODID + ":crafting_item";
    }
}
