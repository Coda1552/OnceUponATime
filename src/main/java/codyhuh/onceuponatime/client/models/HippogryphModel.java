package codyhuh.onceuponatime.client.models;

import codyhuh.onceuponatime.OnceUponATime;
import codyhuh.onceuponatime.client.animations.HippogryphAnimation;
import codyhuh.onceuponatime.common.entities.Hippogryph;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.AgeableHierarchicalModel;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

import java.util.Collections;

public class HippogryphModel<T extends Hippogryph> extends OUATModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(OnceUponATime.MOD_ID, "hippogryph"), "main");
	private final ModelPart root;
	public final ModelPart body;
	private final ModelPart l_wing_1;
	private final ModelPart l_wing_2;
	private final ModelPart r_wing_1;
	private final ModelPart r_wing_2;
	private final ModelPart ears;
	private final ModelPart l_leg_1;
	private final ModelPart r_leg_1;
	private final ModelPart l_leg_2a;
	private final ModelPart r_leg_2a;
	private final ModelPart tail;
	private final ModelPart head;
	private final ModelPart l_wing_1_feathers;
	private final ModelPart r_wing_1_feathers;

	public HippogryphModel(ModelPart base) {
        super(0.5F, 24.0F);
        this.root = base.getChild("root");
		this.l_leg_1 = root.getChild("l_leg_1");
		this.r_leg_1 = root.getChild("r_leg_1");
		this.l_leg_2a = root.getChild("l_tight");
		this.r_leg_2a = root.getChild("r_tight");
		this.body = root.getChild("body");
		this.tail = body.getChild("tail");
		this.head = root.getChild("head");
		this.ears = head.getChild("ears");
		this.l_wing_1 = root.getChild("l_wing_1");
		this.l_wing_2 = l_wing_1.getChild("l_wing_2");
		this.l_wing_1_feathers = l_wing_1.getChild("l_wing_1_feathers");
		this.r_wing_1 = root.getChild("r_wing_1");
		this.r_wing_2 = r_wing_1.getChild("r_wing_2");
		this.r_wing_1_feathers = r_wing_1.getChild("r_wing_1_feathers");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 5.0F, 0.0F));

		PartDefinition l_leg_1 = root.addOrReplaceChild("l_leg_1", CubeListBuilder.create().texOffs(16, 61).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 13.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(28, 36).addBox(-2.0F, 13.0F, -6.0F, 5.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(3.5F, 6.0F, -4.3776F));

		PartDefinition r_leg_1 = root.addOrReplaceChild("r_leg_1", CubeListBuilder.create().texOffs(16, 61).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 13.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(28, 36).addBox(-3.0F, 13.0F, -6.0F, 5.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.5F, 6.0F, -4.3776F));

		PartDefinition l_wing_1 = root.addOrReplaceChild("l_wing_1", CubeListBuilder.create().texOffs(62, 61).addBox(-1.0F, -10.0F, -1.6224F, 2.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(4.8F, -5.0F, 0.3724F));

		PartDefinition l_wing_1_feathers = l_wing_1.addOrReplaceChild("l_wing_1_feathers", CubeListBuilder.create().texOffs(58, 36).addBox(4.0F, -34.0F, 0.25F, 0.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.975F, 24.0F, 1.1276F));

		PartDefinition l_wing_2 = l_wing_1.addOrReplaceChild("l_wing_2", CubeListBuilder.create().texOffs(0, 79).addBox(-1.0F, -12.0F, 0.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(62, 0).addBox(0.0F, -22.0F, 0.0F, 0.0F, 22.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -10.0F, -1.6224F));

		PartDefinition r_wing_1 = root.addOrReplaceChild("r_wing_1", CubeListBuilder.create().texOffs(62, 61).mirror().addBox(-1.0F, -10.0F, -1.6224F, 2.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.8F, -5.0F, 0.3724F));

		PartDefinition r_wing_1_feathers = r_wing_1.addOrReplaceChild("r_wing_1_feathers", CubeListBuilder.create().texOffs(58, 36).mirror().addBox(0.0F, -10.0F, 1.3776F, 0.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-0.025F, 0.0F, 0.0F));

		PartDefinition r_wing_2 = r_wing_1.addOrReplaceChild("r_wing_2", CubeListBuilder.create().texOffs(62, 0).mirror().addBox(0.0F, -22.0F, 0.0F, 0.0F, 22.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 79).mirror().addBox(-1.0F, -12.0F, 0.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -10.0F, -1.6224F));

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 36).addBox(-3.5F, -14.5343F, -3.75F, 7.0F, 18.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(100, 72).addBox(-3.5F, -14.5343F, -3.75F, 7.0F, 18.0F, 7.0F, new CubeDeformation(0.1F))
				.texOffs(105, 68).addBox(-3.5F, -14.5343F, -5.95F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.1F))
				.texOffs(8, 79).addBox(-3.5F, -14.5343F, -5.75F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(46, 61).addBox(-1.5F, -12.5343F, -8.75F, 3.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(8, 83).addBox(-1.5F, -8.5343F, -8.75F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.4657F, -6.6276F));

		PartDefinition ears = head.addOrReplaceChild("ears", CubeListBuilder.create(), PartPose.offset(3.5F, -13.5343F, 0.25F));

		PartDefinition cube_r1 = ears.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(78, 36).addBox(-5.0F, 0.0F, -4.0F, 5.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.2618F));

		PartDefinition cube_r2 = ears.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(78, 36).mirror().addBox(0.0F, 0.0F, -4.0F, 5.0F, 0.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.2618F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-5.5F, -4.0F, -9.8724F, 11.0F, 11.0F, 20.0F, new CubeDeformation(0.05F))
				.texOffs(66, 97).addBox(-5.5F, -4.0F, -9.8724F, 11.0F, 11.0F, 20.0F, new CubeDeformation(0.1F))
				.texOffs(0, 87).addBox(-5.5F, -4.0F, -4.8724F, 11.0F, 11.0F, 10.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, -1.0F, 1.6224F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(32, 61).addBox(-1.5F, -4.0F, 0.0F, 3.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 8.3776F, 0.3927F, 0.0F, 0.0F));

		PartDefinition l_tight = root.addOrReplaceChild("l_tight", CubeListBuilder.create().texOffs(0, 61).mirror().addBox(-2.0F, 0.0F, -1.75F, 4.0F, 13.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.5F, 6.0F, 9.5F));

		PartDefinition r_tight = root.addOrReplaceChild("r_tight", CubeListBuilder.create().texOffs(0, 61).addBox(-2.0F, 0.0F, -1.75F, 4.0F, 13.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.5F, 6.0F, 9.5F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		boolean flying = !entity.onGround() && (entity.isFlying() || entity.isLanding());

		ears.visible = !entity.isWearingArmor();

		if (!flying) {
			if (entity.walkAnimation.isMoving()) {
				this.animateWalk(HippogryphAnimation.WALK, limbSwing, limbSwingAmount, 3.0F, 10.0F);
			}
		}
		else {
			if (entity.getDeltaMovement().y() > 0) {
				this.animate(entity.flyAnimationState, HippogryphAnimation.FLY, ageInTicks, 3.0F, 1.0F);
			}
			else {
				this.animate(entity.glideAnimationState, HippogryphAnimation.GLIDE, ageInTicks, 3.0F, 1.0F);
			}
		}

		this.animateIdle(entity.idleAnimationState, HippogryphAnimation.IDLE, ageInTicks, 1.0F, 1 - Math.abs(limbSwingAmount));

		this.head.xRot = headPitch * 0.017453292F;
		this.head.yRot = netHeadYaw * 0.017453292F;

		if (this.young) this.applyStatic(HippogryphAnimation.BABY_TRANSFORM);
	}

	@Override
	public ModelPart root() {
		return root;
	}
}