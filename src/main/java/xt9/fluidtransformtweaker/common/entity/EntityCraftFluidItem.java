package xt9.fluidtransformtweaker.common.entity;

import crafttweaker.CraftTweakerAPI;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import xt9.fluidtransformtweaker.FluidTransformTweaker;
import xt9.fluidtransformtweaker.common.recipe.FluidToFluidTransform;

import static net.minecraft.block.BlockLiquid.LEVEL;

/**
 * Created by xt9 on 2019-01-12.
 */
public class EntityCraftFluidItem extends EntityItem {
    private FluidToFluidTransform recipe;

    public EntityCraftFluidItem(World worldIn) {
        super(worldIn);
    }

    public EntityCraftFluidItem(World worldIn, double x, double y, double z, ItemStack stack, FluidToFluidTransform recipe) {
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
            if (recipe != null) {
                IBlockState state = world.getBlockState(getPosition());
                Fluid inputFluid = FluidRegistry.getFluid(recipe.getInputFluid());

                updateFluid(state, inputFluid.getBlock());
            }
        }
    }

    private void updateFluid(IBlockState state, Block block) {
        if(state.getBlock() == block) {
            // Fluid Sourceblocks has a level of 0
            if (state.getValue(LEVEL) == 0 && this.getItem().getCount() >= this.recipe.getInputAmount()) {
                Fluid outFluid = FluidRegistry.getFluid(recipe.getOutputFluid());
                if(outFluid.getBlock() == null) {
                    CraftTweakerAPI.logError(FluidTransformTweaker.MODID + ": Output fluid has no Block, this usually happens when you try to use a fluid that's not meant to be placed in the world.");
                    this.recipe = null;
                } else {
                    world.setBlockState(this.getPosition(), outFluid.getBlock().getDefaultState(), 3);
                    if(recipe.willConsume()) {
                        this.getItem().shrink(this.recipe.getInputAmount());
                    }
                }
            }
        }
    }
}
