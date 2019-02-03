package xt9.inworldcrafting.common;

import crafttweaker.CraftTweakerAPI;
import xt9.inworldcrafting.common.crafttweaker.ExplosionCrafting;
import xt9.inworldcrafting.common.crafttweaker.FireCrafting;
import xt9.inworldcrafting.common.crafttweaker.FluidToFluid;
import xt9.inworldcrafting.common.crafttweaker.FluidToItem;

/**
 * Created by xt9 on 2019-01-12.
 */
public class CommonProxy {
    
    public void preInit() {
        CraftTweakerAPI.registerClass(FluidToFluid.class);
        CraftTweakerAPI.registerClass(FluidToItem.class);
        CraftTweakerAPI.registerClass(ExplosionCrafting.class);
        CraftTweakerAPI.registerClass(FireCrafting.class);
    }
}
