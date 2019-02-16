package xt9.inworldcrafting.common.event;

import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xt9.inworldcrafting.common.recipe.ExplodeBlockRecipe;
import xt9.inworldcrafting.common.recipe.ExplodeItemRecipe;
import xt9.inworldcrafting.common.util.ParticleMessageHelper;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by xt9 on 2019-01-19.
 */
@SuppressWarnings("unused")
@Mod.EventBusSubscriber
public class ExplosionCraft {

    @SubscribeEvent
    public static void somethingWentBoom(ExplosionEvent.Detonate event) {
        if(!event.getWorld().isRemote) {
            List<Entity> affectedEntities = event.getAffectedEntities();
            List<BlockPos> affectedBlocks = event.getAffectedBlocks();

            ExplodeItemRecipe.recipes.forEach(r -> affectedEntities.forEach(entity -> {
                if (entity instanceof EntityItem) {
                    ItemStack spawnedStack = ((EntityItem) entity).getItem();
                    if (r.getInputs().matches(CraftTweakerMC.getIItemStack(spawnedStack))) {
                        handleExplodeItemCraft(event, r, (EntityItem) entity);
                    }
                }
            }));

            ExplodeBlockRecipe.recipes.forEach(r -> affectedBlocks.forEach(blockPos -> {
                IBlockState currentState = event.getWorld().getBlockState(blockPos);
                if (currentState.getBlock() == Blocks.AIR) {
                    return;
                }

                if (Objects.equals(currentState.getBlock().getRegistryName(), r.getInputBlock().getRegistryName()) && currentState.getBlock().getMetaFromState(currentState) == r.getInputStack().getItemDamage()) {
                    handleExplodeBlockCraft(event, r, blockPos);
                }
            }));
        }
    }

    private static void handleExplodeBlockCraft(ExplosionEvent.Detonate event, ExplodeBlockRecipe recipe, BlockPos blockPos) {
        ThreadLocalRandom rand = ThreadLocalRandom.current();

        event.getWorld().setBlockState(blockPos, Blocks.AIR.getDefaultState(), 3);

        if(recipe.getItemSpawnChance() >= rand.nextInt(1, 100)) {
            EntityItem item = new EntityItem(event.getWorld(), blockPos.getX(), blockPos.getY(), blockPos.getZ(), recipe.getOutputStack().copy());
            item.motionX = 0;
            item.motionY = 0;
            item.motionZ = 0;
            item.setPickupDelay(10);

            ParticleMessageHelper.visualizeCrafting(event.getWorld(),"black", item.posX, item.posY, item.posZ, 80);
            event.getWorld().spawnEntity(item);
        }
    }

    private static void handleExplodeItemCraft(ExplosionEvent.Detonate event, ExplodeItemRecipe recipe, EntityItem entity) {
        ThreadLocalRandom rand = ThreadLocalRandom.current();

        if(entity.getItem().getCount() == 0) {
            return;
        }

        /* Number of recipes this entity can complete with it's current stacksize */
        int recipeNum = (int) Math.floor(entity.getItem().getCount() / recipe.getInputAmount());
        for (int i = 0; i < recipeNum; i++) {
            EntityItem item = new EntityItem(event.getWorld(), entity.posX, entity.posY, entity.posZ, recipe.getOutputStack().copy());
            item.motionX = 0;
            item.motionY = 0;
            item.motionZ = 0;
            item.setPickupDelay(10);

            if(recipe.getSurviveChance() >= rand.nextInt(1, 100)) {
                ParticleMessageHelper.visualizeCrafting(event.getWorld(),"black", item.posX, item.posY, item.posZ, 80);
                event.getWorld().spawnEntity(item);
                entity.getItem().shrink(recipe.getInputAmount());
            }
        }
    }
}
