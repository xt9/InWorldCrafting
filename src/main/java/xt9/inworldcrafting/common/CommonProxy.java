package xt9.inworldcrafting.common;

import crafttweaker.CraftTweakerAPI;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import xt9.inworldcrafting.InWorldCrafting;
import xt9.inworldcrafting.common.crafttweaker.ExplosionCrafting;
import xt9.inworldcrafting.common.crafttweaker.FireCrafting;
import xt9.inworldcrafting.common.crafttweaker.FluidToFluid;
import xt9.inworldcrafting.common.crafttweaker.FluidToItem;
import xt9.inworldcrafting.common.entity.EntityCrafterItem;

/**
 * Created by xt9 on 2019-01-12.
 */
public class CommonProxy {
    
    public void preInit() {
        CraftTweakerAPI.registerClass(FluidToFluid.class);
        CraftTweakerAPI.registerClass(FluidToItem.class);
        CraftTweakerAPI.registerClass(ExplosionCrafting.class);
        CraftTweakerAPI.registerClass(FireCrafting.class);

        EntityRegistry.registerModEntity(
            new ResourceLocation(InWorldCrafting.MODID + ":crafter_item"),
            EntityCrafterItem.class,
            InWorldCrafting.MODID + ".crafter_item",
            0,
            InWorldCrafting.instance,
            64,
            1,
            true
        );
    }
}
