package xt9.inworldcrafting.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by xt9 on 2019-02-16.
 */
public class ParticleHelper {
    public static void spawnParticle(String type, double x, double y, double z) {
        World world = Minecraft.getMinecraft().world;
        ThreadLocalRandom rand = ThreadLocalRandom.current();

        Particle particle = new CraftingParticle(
            world,
            x + 0.0D,
            y + rand.nextDouble(0.15D, 0.6D),
            z + 0.0D,
            rand.nextDouble(-0.06D, 0.06D),
            rand.nextDouble(-0.0D, 0.15D),
            rand.nextDouble(-0.06D, 0.06D),
            1.0F
        );

        switch (type) {
            case "black": break;
            case "white": particle.setRBGColorF(0.92F, 0.92F, 0.92F); particle.setAlphaF(0.85F); break;
            default: break;
        }

        Minecraft.getMinecraft().effectRenderer.addEffect(particle);
    }
}
