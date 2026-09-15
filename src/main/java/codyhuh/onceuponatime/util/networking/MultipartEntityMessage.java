package codyhuh.onceuponatime.util.networking;

import codyhuh.onceuponatime.OnceUponATime;
import codyhuh.onceuponatime.common.entities.Hydra;
import codyhuh.onceuponatime.common.entities.HydraPart;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.entity.PartEntity;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

// todo - this class is currently hardcoded to only work for the hydra, generify it
public class MultipartEntityMessage {

    public int parentId;
    public int partId;
    public int playerId;
    public int type;
    public double damage;

    public MultipartEntityMessage(int parentId, int partId, int playerId, int type, double damage) {
        this.parentId = parentId;
        this.partId = partId;
        this.playerId = playerId;
        this.type = type;
        this.damage = damage;
    }

    public MultipartEntityMessage() {}

    public static MultipartEntityMessage read(FriendlyByteBuf buf) {
        return new MultipartEntityMessage(buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readDouble());
    }

    public static void write(MultipartEntityMessage message, FriendlyByteBuf buf) {
        buf.writeInt(message.parentId);
        buf.writeInt(message.partId);
        buf.writeInt(message.playerId);
        buf.writeInt(message.type);
        buf.writeDouble(message.damage);
    }

    public static class Handler {
        public static void handle(MultipartEntityMessage message, Supplier<NetworkEvent.Context> context) {
            context.get().enqueueWork(() -> {
                Player playerSided = context.get().getSender();
                if (context.get().getDirection().getReceptionSide() == LogicalSide.CLIENT) {
                    playerSided = OnceUponATime.PROXY.getClientSidePlayer();
                }

                Entity interacter = playerSided.level().getEntity(message.playerId);
                Entity parent = playerSided.level().getEntity(message.parentId);

                if (interacter != null && parent != null && parent.isMultipartEntity()) {
                    if (message.type == 0) {
                        if (interacter instanceof Player player) {
                            parent.interact(player, player.getUsedItemHand());
                        }
                    }
                    else if (message.type == 1) {
                        if (parent instanceof Hydra hydra) {

                            List<PartEntity<?>> list = Arrays.stream(parent.getParts()).toList();
                            HydraPart part = (HydraPart) playerSided.level().getEntity(message.partId);

                            if (list.contains(part)) {
                                hydra.damagePart(part, parent.damageSources().generic(), (float) message.damage);
                            }
                        }
                    }
                }
            });
            context.get().setPacketHandled(true);
        }
    }
}