package xt9.inworldcrafting;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by xt9 on 2019-01-21.
 */
@Mod.EventBusSubscriber
@Config(modid = InWorldCrafting.MODID)
public class ModConfig {
    @SubscribeEvent
    public static void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(InWorldCrafting.MODID)) {
            ConfigManager.sync(InWorldCrafting.MODID, Config.Type.INSTANCE);
        }
    }

    @Config.Comment({
        "Default: false",
        "Only override if you know what you're doing."
    })
    @Config.Name("Override the Input Blacklist")
    public static boolean blacklistDisabled = false;
}
