package xt9.inworldcrafting.common.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import xt9.inworldcrafting.client.ParticleHelper;

/**
 * Created by xt9 on 2019-02-16.
 * @ Received on the Client side
 */
public class ParticleMessage implements IMessage {
    private String type;
    private double posX;
    private double posY;
    private double posZ;
    private int particleCount = 0;

    public ParticleMessage() {

    }

    public ParticleMessage(String type, double x, double y, double z, int particleCount) {
        this.type = type;
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        this.particleCount = particleCount;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        type = ByteBufUtils.readUTF8String(buf);
        posX = buf.readDouble();
        posY = buf.readDouble();
        posZ = buf.readDouble();
        particleCount = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, type);
        buf.writeDouble(posX);
        buf.writeDouble(posY);
        buf.writeDouble(posZ);
        buf.writeInt(particleCount);
    }

    public static class Handler implements IMessageHandler<ParticleMessage, IMessage> {

        @Override
        public IMessage onMessage(ParticleMessage message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                for (int i = 0; i < message.particleCount; i++) {
                    ParticleHelper.spawnParticle(message.type, message.posX, message.posY, message.posZ);
                }
            });
            return null;
        }
    }
}
