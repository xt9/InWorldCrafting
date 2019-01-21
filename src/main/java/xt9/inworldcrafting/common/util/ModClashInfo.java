package xt9.inworldcrafting.common.util;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import javafx.util.Pair;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreIngredient;
import xt9.inworldcrafting.InWorldCrafting;

/**
 * Created by xt9 on 2019-01-21.
 */
public class ModClashInfo {
    private NonNullList<Pair<String, Object>> blacklist = NonNullList.create();
    private boolean modClashed = false;

    public ModClashInfo() {
        blacklist.add(new Pair<>("astralsorcery", "logWood"));
        blacklist.add(new Pair<>("astralsorcery", CraftTweakerMC.getItemStack(CraftTweakerAPI.itemUtils.getItem("astralsorcery:itemrockcrystalsimple", 0))));
        blacklist.add(new Pair<>("astralsorcery", CraftTweakerMC.getItemStack(CraftTweakerAPI.itemUtils.getItem("astralsorcery:itemcelestialcrystal", 0))));
        blacklist.add(new Pair<>("appliedenergistics2", CraftTweakerMC.getItemStack(CraftTweakerAPI.itemUtils.getItem("appliedenergistics2:material", 1))));
        blacklist.add(new Pair<>("appliedenergistics2", CraftTweakerMC.getItemStack(CraftTweakerAPI.itemUtils.getItem("appliedenergistics2:crystal_seed", OreDictionary.WILDCARD_VALUE))));
        blacklist.add(new Pair<>("actuallyadditions", CraftTweakerMC.getItemStack(CraftTweakerAPI.itemUtils.getItem("actuallyadditions:item_misc", 23))));
        blacklist.add(new Pair<>("actuallyadditions", CraftTweakerMC.getItemStack(CraftTweakerAPI.itemUtils.getItem("actuallyadditions:item_misc", 24))));
        blacklist.add(new Pair<>("deepmoblearning", CraftTweakerMC.getItemStack(CraftTweakerAPI.itemUtils.getItem("deepmoblearning:glitch_fragment", 0))));
    }

    public boolean isModClashed() {
        return modClashed;
    }

    public void setModClashed(boolean modClashed) {
        this.modClashed = modClashed;
    }

    public void checkForClashes(NonNullList<ItemStack> inputs) {
        for (ItemStack input : inputs) {
            for (Pair<String, Object> pair : blacklist) {

                if (Loader.isModLoaded(pair.getKey())) {
                    /* Make oredicts out of strings, otherwise get the Object as is*/
                    IIngredient blIngredients = CraftTweakerMC.getIIngredient(pair.getValue() instanceof String ? new OreIngredient((String) pair.getValue()) : pair.getValue());

                    if(blIngredients != null) {
                        for (IItemStack blacklistedIItemStack : blIngredients.getItems()) {
                            ItemStack blacklistedStack = CraftTweakerMC.getItemStack(blacklistedIItemStack);

                            if (ItemStackHelper.areItemsEqualWithWildcard(input, blacklistedStack)) {
                                setModClashed(true);
                                if(pair.getValue() instanceof String) {
                                    CraftTweakerAPI.logError("[" + InWorldCrafting.MODID+ "]: Could not add recipe with " + pair.getValue() + " as an input, " + pair.getKey() + " already uses that OreDict for In-world crafting.");
                                } else {
                                    CraftTweakerAPI.logError("[" + InWorldCrafting.MODID+ "]: Could not add recipe with " + blacklistedStack.getItem().getRegistryName() + " as an input, " + pair.getKey() + " already uses " + blacklistedStack.getDisplayName() + " for In-world crafting.");
                                }
                                return;
                            }
                        }
                    }
                }
            }
        }
    }
}
