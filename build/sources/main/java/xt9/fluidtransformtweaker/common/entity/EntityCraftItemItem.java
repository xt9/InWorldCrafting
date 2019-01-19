package xt9.fluidtransformtweaker.common.entity;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import xt9.fluidtransformtweaker.common.recipe.FluidToItemTransform;

import static net.minecraft.block.BlockLiquid.LEVEL;

/**
 * Created by xt9 on 2019-01-12.
 */
public class EntityCraftItemItem extends EntityItem {
    private FluidToItemTransform recipe;

    public EntityCraftItemItem(World worldIn) {
        super(worldIn);
    }

    public EntityCraftItemItem(World worldIn, double x, double y, double z, ItemStack stack, FluidToItemTransform recipe) {
        super(worldIn, x, y, z);
        setItem(stack);
        setPickupDelay(20);
        setEntityInvulnerable(true);
        this.recipe = recipe;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if(!world.isRemote) {
            if(recipe != null) {
                IBlockState state = world.getBlockState(getPosition());
                Fluid inputFluid = FluidRegistry.getFluid(recipe.getInputFluid());

                updateFluid(state, inputFluid.getBlock());
            }
        }
    }

    private void updateFluid(IBlockState state, Block block) {
        if(state.getBlock() == block) {
            if(this.getItem().getCount() == 0) {
                this.setDead();
                return;
            }

            EntityItem item = new EntityItem(world, posX, posY, posZ, recipe.getOutputItem().copy());
            item.motionX = 0;
            item.motionY = 0;
            item.motionZ = 0;
            item.setPickupDelay(10);

            if(this.getItem().getCount() >= this.recipe.getInputAmount()) {
                if (recipe.willConsume()) {
                    // Fluid Sourceblocks has a level of 0
                    if(state.getValue(LEVEL) == 0) {
                        world.setBlockState(getPosition(), Blocks.AIR.getDefaultState(), 3);
                        world.spawnEntity(item);
                        this.getItem().shrink(this.recipe.getInputAmount());
                    }
                } else {
                    world.spawnEntity(item);
                    this.getItem().shrink(this.recipe.getInputAmount());
                }
            }

        }
    }
}
