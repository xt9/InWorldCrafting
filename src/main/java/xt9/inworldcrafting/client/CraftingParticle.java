package xt9.inworldcrafting.client;

import net.minecraft.client.particle.ParticleSmokeNormal;
import net.minecraft.world.World;

/**
 * Created by xt9 on 2019-02-16.
 */
public class CraftingParticle extends ParticleSmokeNormal {
    public CraftingParticle(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, float scale) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, scale);
    }
}
