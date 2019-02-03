package xt9.inworldcrafting.common.util;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreIngredient;
import xt9.inworldcrafting.InWorldCrafting;

import java.util.AbstractMap;

/**
 * Created by xt9 on 2019-01-21.
 */
public class ModClashInfo {
    private NonNullList<ModClashEntry> blacklist = NonNullList.create();
    private boolean modClashed = false;

    public ModClashInfo() {
        blacklist.add(new ModClashEntry("astralsorcery", "logWood", "Wood is used to make Infused Wood."));
        blacklist.add(new ModClashEntry("astralsorcery", CraftTweakerMC.getItemStack(CraftTweakerAPI.itemUtils.getItem("astralsorcery:itemrockcrystalsimple", 0)), "Rock Crystals are used to improve it's own stats and also to create Celstial Crystal Clusters."));
        blacklist.add(new ModClashEntry("astralsorcery", CraftTweakerMC.getItemStack(CraftTweakerAPI.itemUtils.getItem("astralsorcery:itemcelestialcrystal", 0)), "Celestial Rock Crystals are used to improve it's own stats."));
        blacklist.add(new ModClashEntry("appliedenergistics2", CraftTweakerMC.getItemStack(CraftTweakerAPI.itemUtils.getItem("appliedenergistics2:material", 1)), "Charged Certus Quartz is used to create Fluix."));
        blacklist.add(new ModClashEntry("appliedenergistics2", CraftTweakerMC.getItemStack(CraftTweakerAPI.itemUtils.getItem("appliedenergistics2:crystal_seed", OreDictionary.WILDCARD_VALUE)), "AE2 Seeds are used for growing Pure crystals."));
        blacklist.add(new ModClashEntry("actuallyadditions", CraftTweakerMC.getItemStack(CraftTweakerAPI.itemUtils.getItem("actuallyadditions:item_misc", 23)), "Crystallized Canola Seeds are used to improve Refined Canola Oil."));
        blacklist.add(new ModClashEntry("actuallyadditions", CraftTweakerMC.getItemStack(CraftTweakerAPI.itemUtils.getItem("actuallyadditions:item_misc", 24)), "Empowered Canola Seeds are used to improve Crystallized Oil."));
        blacklist.add(new ModClashEntry("deepmoblearning", CraftTweakerMC.getItemStack(CraftTweakerAPI.itemUtils.getItem("deepmoblearning:glitch_fragment", 0)), "Unstable Glitch Fragments are used to create Glitch Infused Ingots."));
        blacklist.add(new ModClashEntry("extrautils2", CraftTweakerMC.getItemStack(CraftTweakerAPI.itemUtils.getItem("minecraft:gold_ingot", 0)), "Gold is used to create Demon Ingots."));
        blacklist.add(new ModClashEntry("fluxnetworks", CraftTweakerMC.getItemStack(CraftTweakerAPI.itemUtils.getItem("minecraft:redstone", 0)), "Redstone is used to create Flux."));
        blacklist.add(new ModClashEntry("evilcraft", CraftTweakerMC.getItemStack(CraftTweakerAPI.itemUtils.getItem("evilcraft:dark_gem", 0)), "Dark Gems are used to create Dark Power Gem."));
        blacklist.add(new ModClashEntry("thaumcraft", CraftTweakerMC.getItemStack(CraftTweakerAPI.itemUtils.getItem("thaumcraft:bath_salts", 0)), "Purifying Bath Salts are used to create Purifying Fluid."));
    }


    public boolean hasModClashed() {
        return modClashed;
    }

    public void setModClashed(boolean modClashed) {
        this.modClashed = modClashed;
    }

    public void checkForClashes(IIngredient ingredient) {
        for (ModClashEntry entry : blacklist) {
            if (entry.isModLoaded()) {
                /* Make oredicts out of strings, otherwise get the Object as is*/
                IIngredient blIngredients = CraftTweakerMC.getIIngredient(entry.getIngredient() instanceof String ? new OreIngredient((String) entry.getIngredient()) : entry.getIngredient());

                if(blIngredients != null) {
                    for (IItemStack blacklistedIItemStack : blIngredients.getItems()) {
                        ItemStack blacklistedStack = CraftTweakerMC.getItemStack(blacklistedIItemStack);

                        if (ingredient.matches(CraftTweakerMC.getIItemStack(blacklistedStack))) {
                            setModClashed(true);
                            if(entry.getIngredient() instanceof String) {
                                CraftTweakerAPI.logError("[" + InWorldCrafting.MODID+ "]: Could not add recipe with <ore:" + entry.getIngredient() + "> as an input, " + entry.getModid() + " already uses that OreDict for In-world crafting.");
                            } else {
                                CraftTweakerAPI.logError("[" + InWorldCrafting.MODID+ "]: Could not add recipe with <" + blacklistedStack.getItem().getRegistryName() + "> as an input, " + entry.getModid() + " already uses " + blacklistedStack.getDisplayName() + " for In-world crafting.");
                            }
                            CraftTweakerAPI.logError("[" + InWorldCrafting.MODID+ "]: Additional Information: " + entry.getDescription());
                            return;
                        }
                    }
                }
            }
        }
    }
}
