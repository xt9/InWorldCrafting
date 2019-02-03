package xt9.inworldcrafting.common.event;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import xt9.inworldcrafting.common.crafting.CraftingItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xt9 on 2019-02-03.
 */
@Mod.EventBusSubscriber
public class WorldTick {
    @SubscribeEvent
    public static void doCraft(TickEvent.WorldTickEvent event) {
        if (event.side.isServer()) {
            List<EntityItem> items = new ArrayList<>();
            for (Entity e : event.world.loadedEntityList) {
                if (e instanceof EntityItem && e.getEntityData().hasKey(CraftingItem.getNbtKey())) {
                    items.add((EntityItem) e);
                }
            }

            for (EntityItem item : items) {
                CraftingItem craftingItem = new CraftingItem((NBTTagCompound) item.getEntityData().getTag(CraftingItem.getNbtKey()));
                craftingItem.update(item, event.world);
                item.getEntityData().setTag(CraftingItem.getNbtKey(), craftingItem.serialize());
            }
        }
    }
}
