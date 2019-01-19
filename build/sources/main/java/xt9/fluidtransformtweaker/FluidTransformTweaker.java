package xt9.fluidtransformtweaker;


import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xt9.fluidtransformtweaker.common.CommonProxy;

/**
 * Created by xt9 on 2019-01-12.
 */
@Mod(modid = FluidTransformTweaker.MODID, version = FluidTransformTweaker.VERSION, useMetadata = true)
@Mod.EventBusSubscriber
public class FluidTransformTweaker {
    public static final String MODID = "fluidtransformtweaker";
    public static final String VERSION = "@VERSION@";

    @Mod.Instance(MODID)
    public static FluidTransformTweaker instance;

    @SidedProxy(
        clientSide="xt9.fluidtransformtweaker.client.ClientProxy",
        serverSide="xt9.fluidtransformtweaker.common.CommonProxy"
    )
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit();
    }
}
