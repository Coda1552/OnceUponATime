package codyhuh.onceuponatime.common.entities;

import codyhuh.onceuponatime.util.networking.MultipartEntityMessage;
import codyhuh.onceuponatime.util.networking.OUATMessages;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.entity.PartEntity;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class HydraPart extends PartEntity<Hydra> {
    public final Hydra parentMob;
    public final String name;
    public final EntityDimensions size;

    public HydraPart(Hydra entity, String name, float x, float y) {
        super(entity);
        this.size = EntityDimensions.scalable(x, y);
        this.parentMob = entity;
        this.name = name;
        this.refreshDimensions();
    }

    public boolean is(Entity entity) {
        return this == entity || this.parentMob == entity;
    }

    public EntityDimensions getDimensions(Pose p_31023_) {
        return this.size;
    }

    public boolean shouldBeSaved() {
        return false;
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Nullable
    @Override
    public ItemStack getPickResult() {
        return this.parentMob.getPickResult();
    }

/*
    @Override
    public boolean hurt(@NotNull DamageSource source, float damage) {
        return parentMob.damagePart(this, source, damage);
    }
*/

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        Entity parent = parentMob;
        if (parent == null) {
            return InteractionResult.PASS;
        } else {
            if (player.level().isClientSide) {
                OUATMessages.sendToServer(new MultipartEntityMessage(parentMob.getId(), getId(), player.getId(), 0, 0));
            }
            return parent.interact(player, hand);
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        Entity parent = parentMob;
        if (!this.isInvulnerableTo(source) && parent != null) {
            Entity player = source.getEntity();
            if (player != null && player.level().isClientSide) {
                OUATMessages.sendToServer(new MultipartEntityMessage(parentMob.getId(), getId(), player.getId(), 1, amount));
            }
        }
        return false;
    }


    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
    }
}