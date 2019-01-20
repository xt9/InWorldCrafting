package xt9.inworldcrafting.common.entity;

import crafttweaker.CraftTweakerAPI;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import xt9.inworldcrafting.InWorldCrafting;
import xt9.inworldcrafting.common.recipe.BurnItemRecipe;
import xt9.inworldcrafting.common.recipe.FluidToFluidRecipe;
import xt9.inworldcrafting.common.recipe.FluidToItemRecipe;

import static net.minecraft.block.BlockLiquid.LEVEL;

/**
 * Created by xt9 on 2019-01-19.
 */
public class EntityCrafterItem extends EntityItem {
    private boolean errored = false;
    private NonNullList<FluidToFluidRecipe> fluidToFluidRecipes = NonNullList.create();
    private NonNullList<FluidToItemRecipe> fluidToItemRecipes = NonNullList.create();
    private BurnItemRecipe burnItemRecipe = null;
    private int burnProgress = 0;

    public EntityCrafterItem(World worldIn) {
        super(worldIn);
    }

    public EntityCrafterItem(World worldIn, double x, double y, double z, ItemStack stack) {
        super(worldIn, x, y, z);
        setItem(stack);
        setPickupDelay(20);
        setEntityInvulnerable(true);
    }


    public void addFluidToFluidRecipe(FluidToFluidRecipe recipe) {
        fluidToFluidRecipes.add(recipe);
    }

    public void addFluidToItemRecipe(FluidToItemRecipe recipe) {
        fluidToItemRecipes.add(recipe);
    }

    public void setBurnItemRecipe(BurnItemRecipe recipe) {
        this.burnItemRecipe = recipe;
    }

    public boolean containsRecipes() {
        return fluidToItemRecipes.size() > 0 || fluidToFluidRecipes.size() > 0 || burnItemRecipe != null;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if(!world.isRemote) {
            if (containsRecipes()) {
                IBlockState state = world.getBlockState(getPosition());

                if(!fluidToFluidRecipes.isEmpty()) {
                    fluidToFluidRecipes.forEach(r -> {
                        Fluid inputFluid = FluidRegistry.getFluid(r.getInputFluid());
                        updateFluidToFluid(state, inputFluid.getBlock(), r);
                    });
                }
                
                if(!fluidToItemRecipes.isEmpty()) {
                    fluidToItemRecipes.forEach(r -> {
                        Fluid inputFluid = FluidRegistry.getFluid(r.getInputFluid());
                        updateFluidToItem(state, inputFluid.getBlock(), r);
                    });
                }

                if(burnItemRecipe != null) {
                    /* Reset progress if it stops burning */
                    if(!this.isBurning()) {
                        burnProgress = 0;
                    }

                    if(this.isBurning()) {
                        burnProgress++;
                        if(this.burnProgress == burnItemRecipe.getTicks()) {
                            updateBurnItem(this.burnItemRecipe);
                        }
                    }
                }
            }
        }
    }

    private void updateBurnItem(BurnItemRecipe recipe) {
        if(this.getItem().getCount() == 0) {
            this.setDead();
            return;
        }

        /* Number of recipes this entity can complete with it's current stacksize */
        int recipeNum = (int) Math.floor(this.getItem().getCount() / recipe.getInputAmount());
        int items = recipeNum * recipe.getOutputStack().getCount();
        int stacks = 0;
        int remainder = 0;

        while(items != 0) {
            if(items >= 64) {
                stacks = stacks + 1;
                items = items - 64;
            } else {
                remainder = items;
                items = 0;
            }
        }

        for (int i = 0; i < stacks; i++) {
            EntityItem item = new EntityItem(world, posX, posY, posZ, recipe.getOutputStack().copy());
            item.getItem().setCount(64);
            item.motionX = 0;
            item.motionY = 0;
            item.motionZ = 0;
            item.setPickupDelay(10);
            item.setEntityInvulnerable(true);

            world.spawnEntity(item);
        }

        EntityItem item = new EntityItem(world, posX, posY, posZ, recipe.getOutputStack().copy());
        item.getItem().setCount(remainder);
        item.motionX = 0;
        item.motionY = 0;
        item.motionZ = 0;
        item.setPickupDelay(10);
        item.setEntityInvulnerable(true);

        world.spawnEntity(item);
        this.getItem().shrink(recipeNum * recipe.getInputAmount());


        this.burnProgress = 0;
    }

    private void updateFluidToItem(IBlockState state, Block block, FluidToItemRecipe recipe) {
        if (state.getBlock() == block) {
            if (this.getItem().getCount() == 0) {
                this.setDead();
                return;
            }

            EntityItem item = new EntityItem(world, posX, posY, posZ, recipe.getOutputStack().copy());
            item.motionX = 0;
            item.motionY = 0;
            item.motionZ = 0;
            item.setPickupDelay(10);

            if (this.getItem().getCount() >= recipe.getInputAmount()) {
                if (recipe.willConsume()) {
                    // Fluid Sourceblocks has a level of 0
                    if (state.getValue(LEVEL) == 0) {
                        world.setBlockState(getPosition(), Blocks.AIR.getDefaultState(), 3);
                    }
                }
                world.spawnEntity(item);
                this.getItem().shrink(recipe.getInputAmount());
            }
        }
    }

    private void updateFluidToFluid(IBlockState state, Block block, FluidToFluidRecipe recipe) {
        if(state.getBlock() == block) {
            // Fluid Sourceblocks has a level of 0
            if (state.getValue(LEVEL) == 0 && this.getItem().getCount() >= recipe.getInputAmount()) {
                Fluid outFluid = FluidRegistry.getFluid(recipe.getOutputFluid());
                if(outFluid.getBlock() == null) {
                    if(!errored) {
                        CraftTweakerAPI.logError(InWorldCrafting.MODID + ": Output fluid has no Block, this usually happens when you try to use a fluid that's not meant to be placed in the world.");
                    }
                    errored = true;
                } else {
                    world.setBlockState(this.getPosition(), outFluid.getBlock().getDefaultState(), 3);
                    if(recipe.willConsume()) {
                        this.getItem().shrink(recipe.getInputAmount());
                    }
                }
            }
        }
    }
}
