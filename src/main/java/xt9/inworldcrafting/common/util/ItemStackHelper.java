package xt9.inworldcrafting.common.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;
import java.util.Objects;
import java.util.Set;

/**
 * Created by xt9 on 2019-01-19.
 */
public class ItemStackHelper {
    public static boolean areItemsEqualWithWildcard(ItemStack stack1, ItemStack stack2) {
        if(ItemStack.areItemsEqual(stack1, stack2)) {
            if(stack1.hasTagCompound() && stack2.hasTagCompound()) {
                NBTTagCompound tag1 = (NBTTagCompound) stack1.serializeNBT().getTag("tag");
                NBTTagCompound tag2 = (NBTTagCompound) stack2.serializeNBT().getTag("tag");

                Set<String> keySet = tag1.getKeySet();
                for (String key : keySet) {
                    if(!tag2.hasKey(key)) {
                        return false;
                    } else if(!(tag2.getTag(key).equals(tag1.getTag(key)))) {
                        return false;
                    }
                }
                return true;
            }
            return true;
        } else if(stack1.getItemDamage() == OreDictionary.WILDCARD_VALUE || stack2.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
            return Objects.equals(stack1.getItem().getRegistryName(), stack2.getItem().getRegistryName());
        }
        return false;
    }
}
