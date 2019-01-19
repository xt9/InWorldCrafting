package xt9.fluidtransformtweaker.common.event;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import xt9.fluidtransformtweaker.common.entity.EntityCraftFluidItem;
import xt9.fluidtransformtweaker.common.entity.EntityCraftItemItem;
import xt9.fluidtransformtweaker.common.recipe.FluidToFluidTransform;
import xt9.fluidtransformtweaker.common.recipe.FluidToItemTransform;

import java.util.Objects;

/**
 * Created by xt9 on 2019-01-12.
 */
@Mod.EventBusSubscriber
public class EntityReplacer {
    @SubscribeEvent
    public static void itemSpawnInWorld(EntityJoinWorldEvent event) {

        if(!(event.getEntity() instanceof EntityItem)) {
            return;
        }

        if(event.getEntity() instanceof EntityCraftFluidItem || event.getEntity() instanceof  EntityCraftItemItem) {
            return;
        }

        if(!event.getWorld().isRemote) {
            ItemStack spawnedStack = ((EntityItem) event.getEntity()).getItem();

            for (FluidToFluidTransform r : FluidToFluidTransform.recipes) {
                r.getInputs().forEach(stack -> {
                    if(ItemStack.areItemsEqual(stack, spawnedStack)) {
                        spawnCraftFluidItem(event, r);
                    } else if(stack.getItemDamage() == OreDictionary.WILDCARD_VALUE && Objects.equals(stack.getItem().getRegistryName(), spawnedStack.getItem().getRegistryName())) {
                        spawnCraftFluidItem(event, r);
                    }
                });
            }

            for (FluidToItemTransform r : FluidToItemTransform.recipes) {
                r.getInputs().forEach(stack -> {
                    if(ItemStack.areItemsEqual(stack, spawnedStack)) {
                        spawnCraftItemItem(event, r);
                    } else if(stack.getItemDamage() == OreDictionary.WILDCARD_VALUE && Objects.equals(stack.getItem().getRegistryName(), spawnedStack.getItem().getRegistryName())) {
                        spawnCraftItemItem(event, r);
                    }
                });
            }
        }
    }

    private static void spawnCraftFluidItem(EntityJoinWorldEvent event, FluidToFluidTransform recipe) {
        EntityCraftFluidItem entity = new EntityCraftFluidItem(
            event.getWorld(),
            event.getEntity().posX,
            event.getEntity().posY,
            event.getEntity().posZ,
            ((EntityItem) event.getEntity()).getItem(),
            recipe
        );
        spawnTheDamnThing(event, entity);
    }

    private static void spawnCraftItemItem(EntityJoinWorldEvent event, FluidToItemTransform recipe) {
        EntityCraftItemItem entity = new EntityCraftItemItem(
            event.getWorld(),
            event.getEntity().posX,
            event.getEntity().posY,
            event.getEntity().posZ,
            ((EntityItem) event.getEntity()).getItem(),
            recipe
        );
        spawnTheDamnThing(event, entity);
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
