package xt9.inworldcrafting;


import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xt9.inworldcrafting.common.CommonProxy;

/**
 * Created by xt9 on 2019-01-12.
 */
@Mod(modid = InWorldCrafting.MODID, version = InWorldCrafting.VERSION, useMetadata = true, dependencies = "required-after:crafttweaker")
@Mod.EventBusSubscriber
public class InWorldCrafting {
    public static final String MODID = "inworldcrafting";
    public static final String VERSION = "@VERSION@";

    @Mod.Instance(MODID)
    public static InWorldCrafting instance;

    @SidedProxy(
        clientSide="xt9.inworldcrafting.client.ClientProxy",
        serverSide="xt9.inworldcrafting.common.CommonProxy"
    )
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit();
    }
}
