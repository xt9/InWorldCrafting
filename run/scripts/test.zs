import mods.fluidtransformtweaker.FluidToFluid;
import mods.fluidtransformtweaker.FluidToItem;

FluidToFluid.transform(<liquid:lava>, <liquid:water>, <ore:dustRedstone> * 4);

# Water + Mana Dust = Primal mana
FluidToFluid.transform(<liquid:mana>, <liquid:water>, <thermalfoundation:material:1028>, false);

# Planks in creosote = Treated wood
FluidToItem.transform(<immersiveengineering:treated_wood>, <liquid:creosote>, <ore:plankWood> * 4);

FluidToItem.transform(<ore:gemLapis>.firstItem, <liquid:cryotheum>, <ore:dyeRed>);

FluidToItem.transform(<minecraft:water_bucket>, <liquid:water>, <minecraft:bucket>, true);

