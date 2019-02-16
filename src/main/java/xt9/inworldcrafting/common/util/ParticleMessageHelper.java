package xt9.inworldcrafting.common.util;

import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import xt9.inworldcrafting.InWorldCrafting;
import xt9.inworldcrafting.common.network.ParticleMessage;

/**
 * Created by xt9 on 2019-02-16.
 */
public class ParticleMessageHelper {
    public static void visualizeCrafting(World world, String type, double x, double y, double z, int particleCount) {
        InWorldCrafting.network.sendToAllAround(
            new ParticleMessage(type, x, y, z, particleCount),
            new NetworkRegistry.TargetPoint(world.provider.getDimension(), x, y, z, 100)
        );
    }
}
