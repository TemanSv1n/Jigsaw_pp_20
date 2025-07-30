// Made with Blockbench 4.12.5
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports
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

public class BellyJunkModel<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "bellyjunkmodel"), "main");
	private final ModelPart Body;
	private final ModelPart belly;
	private final ModelPart junk;

	public BellyJunkModel(ModelPart root) {
		this.Body = root.getChild("Body");
		this.belly = this.Body.getChild("belly");
		this.junk = this.Body.getChild("junk");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(40, 28).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.75F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition belly = Body.addOrReplaceChild("belly", CubeListBuilder.create().texOffs(36, 50).addBox(-4.0F, -7.0F, -3.0F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(38, 52).addBox(-4.0F, -7.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(36, 50).addBox(-11.0F, -7.0F, -3.0F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(38, 52).addBox(-11.0F, -7.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(36, 50).addBox(-4.0F, -8.0F, -2.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(36, 50).addBox(-11.0F, -8.0F, -2.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(37, 50).addBox(-10.0F, -9.0F, -2.0F, 6.0F, 9.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(39, 52).addBox(-10.0F, -8.0F, -3.0F, 6.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(40, 52).addBox(-9.0F, -8.0F, -4.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(39, 51).addBox(-9.0F, -1.0F, -4.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(40, 52).addBox(-9.0F, -9.0F, -3.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(39, 56).addBox(-10.0F, -7.0F, -4.0F, 6.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(7.0F, 12.0F, -3.0F));

		PartDefinition junk = Body.addOrReplaceChild("junk", CubeListBuilder.create().texOffs(52, 16).addBox(-2.75F, 1.0F, -3.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(52, 22).addBox(0.75F, 1.0F, -3.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, 12.0F, -2.25F));

		PartDefinition cube_r1 = junk.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(52, 0).addBox(-1.0F, -13.0F, -2.0F, 3.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}