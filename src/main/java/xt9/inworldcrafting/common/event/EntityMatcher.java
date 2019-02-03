package xt9.inworldcrafting.common.event;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xt9.inworldcrafting.common.crafting.CraftingItem;
import xt9.inworldcrafting.common.entity.EntityCrafterItem;
import xt9.inworldcrafting.common.recipe.BurnItemRecipe;
import xt9.inworldcrafting.common.recipe.FluidToFluidRecipe;
import xt9.inworldcrafting.common.recipe.FluidToItemRecipe;

/**
 * Created by xt9 on 2019-01-12.
 */
@Mod.EventBusSubscriber
public class EntityMatcher {
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
            EntityItem entity = (EntityItem) event.getEntity();
            ItemStack spawnedStack = ((EntityItem) event.getEntity()).getItem();

            boolean match = false;
            for (IIngredient input : allValidInputs) {
                if(input.matches(CraftTweakerMC.getIItemStack(spawnedStack))) {
                    match = true;
                }
            }

            if(!match) { return; }

            CraftingItem craftingItem = new CraftingItem(new NBTTagCompound());

            /* Matches for recipes, if a match a found the method will add the recipe to the entity */
            matchFluidToFluidRecipes(spawnedStack, craftingItem);
            matchFluidToItemRecipes(spawnedStack, craftingItem);
            matchBurnItemRecipes(spawnedStack, craftingItem);

            if(craftingItem.containsRecipes()) {
                entity.setPickupDelay(20);
                entity.setEntityInvulnerable(true);
                entity.getEntityData().setTag(CraftingItem.getNbtKey(), craftingItem.serialize());
            }
        }
    }

    private static void matchFluidToFluidRecipes(ItemStack spawnedStack, CraftingItem craftingItem) {
        for (int i = 0; i < FluidToFluidRecipe.recipes.size(); i++) {
            if(FluidToFluidRecipe.recipes.get(i).getInputs().matches(CraftTweakerMC.getIItemStack(spawnedStack))) {
                craftingItem.addFluidToFluidRecipeIndex(i);
            }
        }
    }

    private static void matchFluidToItemRecipes(ItemStack spawnedStack, CraftingItem craftingItem) {
        for (int i = 0; i < FluidToItemRecipe.recipes.size(); i++) {
            if(FluidToItemRecipe.recipes.get(i).getInputs().matches(CraftTweakerMC.getIItemStack(spawnedStack))) {
                craftingItem.addFluidToItemRecipeIndex(i);
            }
        }
    }

    private static void matchBurnItemRecipes(ItemStack spawnedStack, CraftingItem craftingItem) {
        for (int i = 0; i < BurnItemRecipe.recipes.size(); i++) {
            if(BurnItemRecipe.recipes.get(i).getInputs().matches(CraftTweakerMC.getIItemStack(spawnedStack))) {
                craftingItem.setBurnItemRecipe(i);
            }
        }
    }
}
