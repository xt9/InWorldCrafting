package xt9.inworldcrafting.common.util;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ExpensiveKoala on 2/13/2019
 */
public class WorldHelper {
    public static List<EntityItem> getAllItemEntitiesAtPosition(World world, BlockPos pos) {
        return getAllItemEntitiesAtPosition(world, pos, 1);
    }

    public static List<EntityItem> getAllItemEntitiesAtPosition(World world, BlockPos pos, int radius) {
        return world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos.getX() - radius, pos.getY() - radius, pos.getZ() - radius, pos.getX() + radius, pos.getY() + radius, pos.getZ() + radius));
    }

    public static List<ItemStack> getAllItemStacksAtPosition(World world, BlockPos pos){
        return getAllItemStacksAtPosition(world, pos, 1);
    }

    public static List<ItemStack> getAllItemStacksAtPosition(World world, BlockPos pos, int radius){
        return getAllItemEntitiesAtPosition(world, pos, radius).stream().map(EntityItem::getItem).collect(Collectors.toList());
    }

    public static List<Item> getAllItemsAtPosition(World world, BlockPos pos) {
        return getAllItemsAtPosition(world, pos, 1);
    }

    public static List<Item> getAllItemsAtPosition(World world, BlockPos pos, int radius) {
        return getAllItemStacksAtPosition(world, pos, radius).stream().map(ItemStack::getItem).collect(Collectors.toList());
    }
}
