package xt9.inworldcrafting.common.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Objects;

/**
 * Created by xt9 on 2019-01-19.
 */
public class ItemStackHelper {
    public static boolean areItemsEqualWithWildcard(ItemStack stack1, ItemStack stack2) {
        if(ItemStack.areItemsEqual(stack1, stack2)) {
            return true;
        } else if(stack1.getItemDamage() == OreDictionary.WILDCARD_VALUE || stack2.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
            return Objects.equals(stack1.getItem().getRegistryName(), stack2.getItem().getRegistryName());
        }
        return false;
    }
}
