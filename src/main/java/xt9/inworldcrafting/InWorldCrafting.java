package xt9.inworldcrafting;


import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import xt9.inworldcrafting.common.CommonProxy;
import xt9.inworldcrafting.common.network.ParticleMessage;

/**
 * Created by xt9 on 2019-01-12.
 */
@Mod(modid = InWorldCrafting.MODID, version = InWorldCrafting.VERSION, useMetadata = true, dependencies = "required-after:crafttweaker")
@Mod.EventBusSubscriber
public class InWorldCrafting {
    public static final String MODID = "inworldcrafting";
    public static final String VERSION = "@VERSION@";
    public static SimpleNetworkWrapper network;
    @Mod.Instance(MODID)
    public static InWorldCrafting instance;

    @SidedProxy(
        clientSide="xt9.inworldcrafting.client.ClientProxy",
        serverSide="xt9.inworldcrafting.common.CommonProxy"
    )
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
        network.registerMessage(ParticleMessage.Handler.class, ParticleMessage.class, 0, Side.CLIENT);

        proxy.preInit();
    }
}
