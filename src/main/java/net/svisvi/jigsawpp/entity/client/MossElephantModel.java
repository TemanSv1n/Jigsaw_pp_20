package net.svisvi.jigsawpp.entity.client;// Made with Blockbench 4.9.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class MossElephantModel<T extends Entity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	private final ModelPart moss_elephant;
	private final ModelPart head;

	public MossElephantModel(ModelPart root) {
		this.moss_elephant = root.getChild("moss_elephant");
		this.head = moss_elephant.getChild("body").getChild("head");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition moss_elephant = partdefinition.addOrReplaceChild("moss_elephant", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = moss_elephant.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-14.0F, -42.0F, -18.0F, 28.0F, 21.0F, 36.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(68, 57).addBox(-9.0F, -9.0F, -12.0F, 18.0F, 16.0F, 13.0F, new CubeDeformation(0.0F))
		.texOffs(90, 114).addBox(-2.5F, 0.0F, -17.0F, 5.0F, 16.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -30.0F, -19.0F));

		PartDefinition cube_r1 = head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(110, 114).addBox(-2.499F, 0.0F, -1.0F, 5.0F, 12.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 16.0F, -16.0F, 0.3491F, 0.0F, 0.0F));

		PartDefinition cube_r2 = head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(36, 103).addBox(-14.0F, -8.0F, 14.0F, 2.0F, 15.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.0F, 0.0F, -3.0F, 0.0F, -0.9163F, 0.0F));

		PartDefinition cube_r3 = head.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(36, 103).addBox(0.0F, -8.0F, -2.0F, 2.0F, 15.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.0F, 0.0F, -3.0F, 0.0F, 0.9163F, 0.0F));

		PartDefinition leg_left_1 = body.addOrReplaceChild("leg_left_1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 0.0F, -4.0F, 9.0F, 21.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, -21.0F, -13.0F));

		PartDefinition leg_left_2 = body.addOrReplaceChild("leg_left_2", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 0.0F, -4.0F, 9.0F, 21.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, -21.0F, 12.0F));

		PartDefinition leg_right_2 = body.addOrReplaceChild("leg_right_2", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 0.0F, -3.0F, 9.0F, 21.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(-9.0F, -21.0F, 11.0F));

		PartDefinition leg_right_1 = body.addOrReplaceChild("leg_right_1", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, 0.0F, -4.0F, 9.0F, 21.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, -21.0F, -13.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {


	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		moss_elephant.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return moss_elephant;
	}
}