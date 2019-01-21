package xt9.inworldcrafting.common.crafttweaker;

import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import xt9.inworldcrafting.ModConfig;
import xt9.inworldcrafting.common.event.EntityReplacer;
import xt9.inworldcrafting.common.recipe.BurnItemRecipe;
import xt9.inworldcrafting.common.util.ModClashInfo;

/**
 * Created by xt9 on 2019-01-19.
 */
@SuppressWarnings("unused")
@ZenClass("mods.inworldcrafting.FireCrafting")
public class FireCrafting {
    @ZenMethod
    public static void addRecipe(IItemStack output, IIngredient ingredient) {
        addRecipe(output, ingredient, 40);
    }

    @ZenMethod
    public static void addRecipe(IItemStack output, IIngredient ingredient, int ticks) {
        /* Inputs should only be items or oredicts */
        if(ingredient.getLiquids().size() > 0) { return; }

        NonNullList<ItemStack> inputs = NonNullList.create();
        ingredient.getItems().forEach(iiStack -> {
            ItemStack stack = CraftTweakerMC.getItemStack(iiStack);
            stack.setCount(ingredient.getAmount());
            inputs.add(stack);
        });

        ModClashInfo info = new ModClashInfo();
        if(!ModConfig.blacklistDisabled) {
            info.checkForClashes(inputs);
        }

        if(!info.isModClashed()) {
            EntityReplacer.allValidInputs.addAll(inputs);
            BurnItemRecipe.addRecipe(CraftTweakerMC.getItemStack(output), inputs, ingredient.getAmount(), ticks);
        }
    }
}
