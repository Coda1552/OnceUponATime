package codyhuh.onceuponatime.common.entities;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.PartEntity;

public class Hydra extends PathfinderMob {
    private static final EntityDataAccessor<Boolean> LEFT_KILLED = SynchedEntityData.defineId(Hydra.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> MIDDLE_KILLED = SynchedEntityData.defineId(Hydra.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> RIGHT_KILLED = SynchedEntityData.defineId(Hydra.class, EntityDataSerializers.BOOLEAN);
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    private final HydraPart[] subEntities;
    private final HydraPart body;
    private final HydraPart headLeft;
    private final HydraPart headMiddle;
    private final HydraPart headRight;
    public float leftHealth = 30;
    public float middleHealth = 30;
    public float rightHealth = 30;

    public Hydra(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.body = new HydraPart(this, "body", 1.15F, 0.95F);
        this.headLeft = new HydraPart(this, "headLeft", 0.4F, 1.5F);
        this.headMiddle = new HydraPart(this, "headMiddle", 0.4F, 1.5F);
        this.headRight = new HydraPart(this, "headRight", 0.4F, 1.5F);
        this.subEntities = new HydraPart[]{this.body, this.headLeft, this.headMiddle, this.headRight};
        this.setId(ENTITY_COUNTER.getAndAdd(this.subEntities.length + 1) + 1);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.7D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createHydraAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 60.0D).add(Attributes.MOVEMENT_SPEED, 0.25F).add(Attributes.KNOCKBACK_RESISTANCE, 0.5F);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(LEFT_KILLED, false);
        this.entityData.define(MIDDLE_KILLED, false);
        this.entityData.define(RIGHT_KILLED, false);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setLeftHeadKilled(pCompound.getBoolean("LeftHeadKilled"));
        this.setMiddleHeadKilled(pCompound.getBoolean("MiddleHeadKilled"));
        this.setRightHeadKilled(pCompound.getBoolean("RightHeadKilled"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("LeftHeadKilled", this.isLeftHeadKilled());
        pCompound.putBoolean("MiddleHeadKilled", this.isMiddleHeadKilled());
        pCompound.putBoolean("RightHeadKilled", this.isRightHeadKilled());
    }

    @Override
    public void setId(int pId) {
        super.setId(pId);
        for (int i = 0; i < this.subEntities.length; i++) // Forge: Fix MC-158205: Set part ids to successors of parent mob id
            this.subEntities[i].setId(pId + i + 1);
    }

    @Override
    public void recreateFromPacket(ClientboundAddEntityPacket packet) {
        super.recreateFromPacket(packet);
        HydraPart[] parts = this.getSubEntities();

        for(int i = 0; i < parts.length; ++i) {
            parts[i].setId(i + packet.getId());
        }
    }

    @Override
    public boolean isMultipartEntity() {
        return true;
    }

    @Override
    public PartEntity<?>[] getParts() {
        return this.subEntities;
    }

    public HydraPart[] getSubEntities() {
        return this.subEntities;
    }

    protected void movePart(HydraPart part, double dX, double dY, double dZ) {
        Vec3 lastPos = new Vec3(part.getX(), part.getY(), part.getZ());

        part.setPos(this.getX() + dX, this.getY() + dY, this.getZ() + dZ);

        part.xo = lastPos.x;
        part.yo = lastPos.y;
        part.zo = lastPos.z;
        part.xOld = lastPos.x;
        part.yOld = lastPos.y;
        part.zOld = lastPos.z;
    }

    protected void updateParts() {
        Vec3 body = Vec3.ZERO;
        Vec3 headLeft = new Vec3(0.6D, 0.4D, 0.8D).yRot(-yBodyRot * ((float) Math.PI / 180f));
        Vec3 headMiddle = new Vec3(0.0D, 0.4D, 0.8D).yRot(-yBodyRot * ((float) Math.PI / 180f));
        Vec3 headRight = new Vec3(-0.6D, 0.4D, 0.8D).yRot(-yBodyRot * ((float) Math.PI / 180f));

        movePart(this.body, body.x, body.y, body.z);
        movePart(this.headLeft, headLeft.x, headLeft.y, headLeft.z);
        movePart(this.headMiddle, headMiddle.x, headMiddle.y, headMiddle.z);
        movePart(this.headRight, headRight.x, headRight.y, headRight.z);
    }

    public float getLeftHeadHealth() {
        return this.leftHealth;
    }

    private void setLeftHeadHealth(float health) {
        this.leftHealth = health;
    }

    public float getMiddleHeadHealth() {
        return this.middleHealth;
    }

    private void setMiddleHeadHealth(float health) {
        this.middleHealth = health;
    }

    public float getRightHeadHealth() {
        return this.rightHealth;
    }

    private void setRightHeadHealth(float health) {
        this.rightHealth = health;
    }

    public boolean isLeftHeadKilled() {
        return this.entityData.get(LEFT_KILLED);
    }

    private void setLeftHeadKilled(boolean killed) {
        this.entityData.set(LEFT_KILLED, killed);
    }

    public boolean isMiddleHeadKilled() {
        return this.entityData.get(MIDDLE_KILLED);
    }

    private void setMiddleHeadKilled(boolean killed) {
        this.entityData.set(MIDDLE_KILLED, killed);
    }

    public boolean isRightHeadKilled() {
        return this.entityData.get(RIGHT_KILLED);
    }

    private void setRightHeadKilled(boolean killed) {
        this.entityData.set(RIGHT_KILLED, killed);
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        return super.hurt(pSource, pAmount);

        /*if (!isInvulnerableTo(pSource)) {
            if (pSource.equals(damageSources().genericKill())) {
                return super.hurt(pSource, pAmount);
            }
            else if (isRightHeadKilled() && isLeftHeadKilled() && isMiddleHeadKilled()) {
                return super.hurt(pSource, pAmount);
            }
        }
        return super.hurt(pSource, 0.0F);*/
    }

    public boolean damagePart(HydraPart part, DamageSource source, float damage) {
        if (!level().isClientSide()) System.out.println(part.name);
        if (part.equals(this.headRight)) {
            if (this.getRightHeadHealth() - damage <= 0) {
                this.setRightHeadHealth(0);
                this.setRightHeadKilled(true);
                //super.hurt(source, 0.0F);
            } else {
                this.setRightHeadHealth(this.getRightHeadHealth() - damage);
                return hurt(source, 0.0F);
            }
        }
        if (part.equals(this.headMiddle)) {
            if (this.getMiddleHeadHealth() - damage <= 0) {
                this.setMiddleHeadHealth(0);
                this.setMiddleHeadKilled(true);
                //super.hurt(source, 0.0F);
            } else {
                this.setMiddleHeadHealth(this.getMiddleHeadHealth() - damage);
                return hurt(source, 0.0F);
            }
        }
        if (part.equals(this.headLeft)) {
            if (this.getLeftHeadHealth() - damage <= 0) {
                this.setLeftHeadHealth(0);
                this.setLeftHeadKilled(true);
                //super.hurt(source, 0.0F);
            } else {
                this.setLeftHeadHealth(this.getLeftHeadHealth() - damage);
                return hurt(source, 0.0F);
            }
        }
        return false;
    }

    @Override
    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize) {
        return pSize.height - 0.1F;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        updateParts();
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }
    }

    private void setupAnimationStates() {
        if (walkAnimation.speed() >  0.005F) {
            idleAnimationState.stop();
        }
        else {
            idleAnimationState.startIfStopped(this.tickCount);
        }

        if (this.idleAnimationTimeout == 0) {
            this.idleAnimationTimeout = 160;
            this.idleAnimationState.start(this.tickCount);
        }
        else {
            --this.idleAnimationTimeout;
        }
    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        return super.mobInteract(pPlayer, pHand);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.EMPTY;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.EMPTY;
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.EMPTY;
    }
}
