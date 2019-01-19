package xt9.fluidtransformtweaker.common;

import crafttweaker.CraftTweakerAPI;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import xt9.fluidtransformtweaker.FluidTransformTweaker;
import xt9.fluidtransformtweaker.common.crafttweaker.FluidToFluid;
import xt9.fluidtransformtweaker.common.crafttweaker.FluidToItem;
import xt9.fluidtransformtweaker.common.entity.EntityCraftFluidItem;
import xt9.fluidtransformtweaker.common.entity.EntityCraftItemItem;

/**
 * Created by xt9 on 2019-01-12.
 */
public class CommonProxy {

    public void preInit() {
        CraftTweakerAPI.registerClass(FluidToFluid.class);
        CraftTweakerAPI.registerClass(FluidToItem.class);

        EntityRegistry.registerModEntity(
            new ResourceLocation(FluidTransformTweaker.MODID + ":crafting_fluid_item"),
            EntityCraftFluidItem.class,
            FluidTransformTweaker.MODID + ".crafting_fluid_item",
            0,
            FluidTransformTweaker.instance,
            64,
            1,
            true
        );

        EntityRegistry.registerModEntity(
            new ResourceLocation(FluidTransformTweaker.MODID + ":crafting_item_item"),
            EntityCraftItemItem.class,
            FluidTransformTweaker.MODID + ".crafting_item_item",
            1,
            FluidTransformTweaker.instance,
            64,
            1,
            true
        );
    }
}
