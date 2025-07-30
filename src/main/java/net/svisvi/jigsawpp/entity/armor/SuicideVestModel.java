package net.svisvi.jigsawpp.entity.armor;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class SuicideVestModel<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "suicidevestmodel"), "main");
	private final ModelPart body;
	private final ModelPart resolver;
	private final ModelPart bomb;
	private final ModelPart bomb2;
	private final ModelPart bomb3;
	private final ModelPart bomb4;
	private final ModelPart bomb5;
	private final ModelPart bomb6;

	public SuicideVestModel(ModelPart root) {
		this.body = root.getChild("body");
		this.resolver = this.body.getChild("resolver");
		this.bomb = this.body.getChild("bomb");
		this.bomb2 = this.body.getChild("bomb2");
		this.bomb3 = this.body.getChild("bomb3");
		this.bomb4 = this.body.getChild("bomb4");
		this.bomb5 = this.body.getChild("bomb5");
		this.bomb6 = this.body.getChild("bomb6");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(40, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.75F))
		.texOffs(0, 22).addBox(3.5F, 2.0F, 3.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 22).addBox(-4.5F, 2.0F, 3.5F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, -9).addBox(-1.0F, 0.0F, -1.0F, 0.0F, 9.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.75F, -1.0F, -3.5F, 0.0F, 0.4625F, 0.0F));

		PartDefinition cube_r2 = body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, -9).addBox(-1.0F, 0.0F, -1.0F, 0.0F, 9.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.25F, -1.0F, -2.0F, 0.0F, -0.4625F, 0.0F));

		PartDefinition resolver = body.addOrReplaceChild("resolver", CubeListBuilder.create().texOffs(46, 8).addBox(-4.0F, -5.0F, -0.999F, 8.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(46, 8).addBox(-4.0F, -5.0F, 0.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(53, 10).addBox(3.0F, -4.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(47, 10).addBox(-4.0F, -4.0F, 0.0F, 3.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(46, 8).addBox(-4.0F, 1.0F, 0.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 3.75F));

		PartDefinition cube_r3 = resolver.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(32, 29).addBox(-3.0F, 1.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(32, 29).addBox(-8.0F, 1.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 1.0F, 2.0F, -0.4363F, 0.0F, 0.0F));

		PartDefinition cube_r4 = resolver.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(6, 28).addBox(-4.0F, 1.0F, -1.0F, 8.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 4.0F, -2.7053F, 0.0F, -3.1416F));

		PartDefinition cube_r5 = resolver.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(32, 21).addBox(2.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.5F, -13.0F, 5.5F, -0.5236F, 0.0F, 0.0F));

		PartDefinition cube_r6 = resolver.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(32, 25).addBox(1.0F, -5.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, -6.0F, 1.5F, -0.5236F, 0.0F, 0.0F));

		PartDefinition cube_r7 = resolver.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(28, 23).addBox(2.0F, -6.0F, -1.0F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, -6.0F, 2.0F, -0.5236F, 0.0F, 0.0F));

		PartDefinition bomb = body.addOrReplaceChild("bomb", CubeListBuilder.create().texOffs(38, 7).addBox(-1.0F, -6.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 13).addBox(-0.5F, -7.25F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 4.0F, -3.75F, -0.1745F, 0.0F, 1.1345F));

		PartDefinition cube_r8 = bomb.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(19, 29).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(19, 29).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition bomb2 = body.addOrReplaceChild("bomb2", CubeListBuilder.create().texOffs(38, 7).addBox(-1.0F, -6.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 15).addBox(-0.5F, -7.25F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 7.0F, -3.75F, -0.1745F, 0.0F, 1.1345F));

		PartDefinition cube_r9 = bomb2.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(19, 29).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(19, 29).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition bomb3 = body.addOrReplaceChild("bomb3", CubeListBuilder.create().texOffs(38, 7).addBox(-1.0F, -6.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 17).addBox(-0.5F, -7.25F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 10.0F, -3.75F, -0.1745F, 0.0F, 1.1345F));

		PartDefinition cube_r10 = bomb3.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(19, 29).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(19, 29).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition bomb4 = body.addOrReplaceChild("bomb4", CubeListBuilder.create().texOffs(38, 7).addBox(-1.0F, -6.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 13).addBox(-0.5F, -7.25F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 4.0F, -3.75F, -2.9671F, 0.0F, 1.9635F));

		PartDefinition cube_r11 = bomb4.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(19, 29).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(19, 29).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition bomb5 = body.addOrReplaceChild("bomb5", CubeListBuilder.create().texOffs(38, 7).addBox(-1.0F, -6.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 15).addBox(-0.5F, -7.25F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 7.0F, -3.75F, -2.9671F, 0.0F, 1.9635F));

		PartDefinition cube_r12 = bomb5.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(19, 29).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(19, 29).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition bomb6 = body.addOrReplaceChild("bomb6", CubeListBuilder.create().texOffs(38, 7).addBox(-1.0F, -6.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 17).addBox(-0.5F, -7.25F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 10.0F, -3.75F, -2.9671F, 0.0F, 1.9635F));

		PartDefinition cube_r13 = bomb6.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(19, 29).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(19, 29).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}