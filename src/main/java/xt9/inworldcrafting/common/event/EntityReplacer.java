package xt9.inworldcrafting.common.event;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xt9.inworldcrafting.common.entity.EntityCrafterItem;
import xt9.inworldcrafting.common.recipe.BurnItemRecipe;
import xt9.inworldcrafting.common.recipe.FluidToFluidRecipe;
import xt9.inworldcrafting.common.recipe.FluidToItemRecipe;
import xt9.inworldcrafting.common.util.ItemStackHelper;

/**
 * Created by xt9 on 2019-01-12.
 */
@Mod.EventBusSubscriber
public class EntityReplacer {
    // TODO, REMOVE DUPLICATES in validInputs
    public static NonNullList<IIngredient> allValidInputs = NonNullList.create();

    @SubscribeEvent
    public static void itemSpawnInWorld(EntityJoinWorldEvent event) {

        if(!(event.getEntity() instanceof EntityItem)) {
            return;
        }

        if(event.getEntity() instanceof EntityCrafterItem) {
            return;
        }


        if(!event.getWorld().isRemote) {
            ItemStack spawnedStack = ((EntityItem) event.getEntity()).getItem();

            boolean match = false;
            for (IIngredient input : allValidInputs) {
                if(input.matches(CraftTweakerMC.getIItemStack(spawnedStack))) {
                    match = true;
                }
            }

            if(!match) { return; }

            EntityCrafterItem e = new EntityCrafterItem(event.getWorld(), event.getEntity().posX, event.getEntity().posY, event.getEntity().posZ, ((EntityItem) event.getEntity()).getItem());

            /* Matches for recipes, if a match a found the method will add the recipe to the entity */
            matchFluidToFluidRecipes(spawnedStack, e);
            matchFluidToItemRecipes(spawnedStack, e);
            matchBurnItemRecipes(spawnedStack, e);

            if(e.containsRecipes()) {
                spawnTheDamnThing(event, e);
            }
        }
    }

    private static void matchFluidToFluidRecipes(ItemStack spawnedStack, EntityCrafterItem e) {
        for (FluidToFluidRecipe r : FluidToFluidRecipe.recipes) {
            if(r.getInputs().matches(CraftTweakerMC.getIItemStack(spawnedStack))) {
                e.addFluidToFluidRecipe(r);
            }
        }
    }

    private static void matchFluidToItemRecipes(ItemStack spawnedStack, EntityCrafterItem e) {
        for (FluidToItemRecipe r : FluidToItemRecipe.recipes) {
            if(r.getInputs().matches(CraftTweakerMC.getIItemStack(spawnedStack))) {
                e.addFluidToItemRecipe(r);
            }
        }
    }

    private static void matchBurnItemRecipes(ItemStack spawnedStack, EntityCrafterItem e) {
        for (BurnItemRecipe r : BurnItemRecipe.recipes) {
            if(r.getInputs().matches(CraftTweakerMC.getIItemStack(spawnedStack))) {
                e.setBurnItemRecipe(r);
            }
        }
    }


    private static void spawnTheDamnThing(EntityJoinWorldEvent event, EntityItem entity) {
        entity.motionX = event.getEntity().motionX;
        entity.motionY = event.getEntity().motionY;
        entity.motionZ = event.getEntity().motionZ;
        event.getEntity().setDead();
        event.getWorld().spawnEntity(entity);
        event.setCanceled(true);
    }
}
