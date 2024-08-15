// Made with Beaver
package net.svisvi.jigsawpp.entity.beaverSpider;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;



public class BeaverSpiderModel<T extends Entity> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	private final ModelPart head;
	private final ModelPart neck;
	private final ModelPart body;
	private final ModelPart rightHindLeg;
	private final ModelPart leftHindLeg;
	private final ModelPart rightMiddleHindLeg;
	private final ModelPart leftMiddleHindLeg;
	private final ModelPart rightMiddleFrontLeg;
	private final ModelPart leftMiddleFrontLeg;
	private final ModelPart rightFrontLeg;
	private final ModelPart leftFrontLeg;

	public BeaverSpiderModel(ModelPart root) {
		this.head = root.getChild("head");
		this.neck = root.getChild("neck");
		this.body = root.getChild("body");
		this.rightHindLeg = root.getChild("rightHindLeg");
		this.leftHindLeg = root.getChild("leftHindLeg");
		this.rightMiddleHindLeg = root.getChild("rightMiddleHindLeg");
		this.leftMiddleHindLeg = root.getChild("leftMiddleHindLeg");
		this.rightMiddleFrontLeg = root.getChild("rightMiddleFrontLeg");
		this.leftMiddleFrontLeg = root.getChild("leftMiddleFrontLeg");
		this.rightFrontLeg = root.getChild("rightFrontLeg");
		this.leftFrontLeg = root.getChild("leftFrontLeg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(32, 4).addBox(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 15.0F, -3.0F));

		PartDefinition neck = partdefinition.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 15.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 12).addBox(-5.0F, -4.0F, -6.0F, 10.0F, 8.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 15.0F, 9.0F));

		PartDefinition rightHindLeg = partdefinition.addOrReplaceChild("rightHindLeg", CubeListBuilder.create().texOffs(18, 0).addBox(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 15.0F, 4.0F));

		PartDefinition leftHindLeg = partdefinition.addOrReplaceChild("leftHindLeg", CubeListBuilder.create().texOffs(18, 0).addBox(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 15.0F, 4.0F));

		PartDefinition rightMiddleHindLeg = partdefinition.addOrReplaceChild("rightMiddleHindLeg", CubeListBuilder.create().texOffs(18, 0).addBox(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 15.0F, 1.0F));

		PartDefinition leftMiddleHindLeg = partdefinition.addOrReplaceChild("leftMiddleHindLeg", CubeListBuilder.create().texOffs(18, 0).addBox(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 15.0F, 1.0F));

		PartDefinition rightMiddleFrontLeg = partdefinition.addOrReplaceChild("rightMiddleFrontLeg", CubeListBuilder.create().texOffs(18, 0).addBox(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 15.0F, -2.0F));

		PartDefinition leftMiddleFrontLeg = partdefinition.addOrReplaceChild("leftMiddleFrontLeg", CubeListBuilder.create().texOffs(18, 0).addBox(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 15.0F, -2.0F));

		PartDefinition rightFrontLeg = partdefinition.addOrReplaceChild("rightFrontLeg", CubeListBuilder.create().texOffs(18, 0).addBox(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 15.0F, -5.0F));

		PartDefinition leftFrontLeg = partdefinition.addOrReplaceChild("leftFrontLeg", CubeListBuilder.create().texOffs(18, 0).addBox(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 15.0F, -5.0F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}

  public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
    this.head.yRot = pNetHeadYaw * 0.017453292F;
    this.head.xRot = pHeadPitch * 0.017453292F;
    float f = 0.7853982F;
    this.rightHindLeg.zRot = -0.7853982F;
    this.leftHindLeg.zRot = 0.7853982F;
    this.rightMiddleHindLeg.zRot = -0.58119464F;
    this.leftMiddleHindLeg.zRot = 0.58119464F;
    this.rightMiddleFrontLeg.zRot = -0.58119464F;
    this.leftMiddleFrontLeg.zRot = 0.58119464F;
    this.rightFrontLeg.zRot = -0.7853982F;
    this.leftFrontLeg.zRot = 0.7853982F;
    float f1 = -0.0F;
    float f2 = 0.3926991F;
    this.rightHindLeg.yRot = 0.7853982F;
    this.leftHindLeg.yRot = -0.7853982F;
    this.rightMiddleHindLeg.yRot = 0.3926991F;
    this.leftMiddleHindLeg.yRot = -0.3926991F;
    this.rightMiddleFrontLeg.yRot = -0.3926991F;
    this.leftMiddleFrontLeg.yRot = 0.3926991F;
    this.rightFrontLeg.yRot = -0.7853982F;
    this.leftFrontLeg.yRot = 0.7853982F;
    float f3 = -(Mth.cos(pLimbSwing * 0.6662F * 2.0F + 0.0F) * 0.4F) * pLimbSwingAmount;
    float f4 = -(Mth.cos(pLimbSwing * 0.6662F * 2.0F + 3.1415927F) * 0.4F) * pLimbSwingAmount;
    float f5 = -(Mth.cos(pLimbSwing * 0.6662F * 2.0F + 1.5707964F) * 0.4F) * pLimbSwingAmount;
    float f6 = -(Mth.cos(pLimbSwing * 0.6662F * 2.0F + 4.712389F) * 0.4F) * pLimbSwingAmount;
    float f7 = Math.abs(Mth.sin(pLimbSwing * 0.6662F + 0.0F) * 0.4F) * pLimbSwingAmount;
    float f8 = Math.abs(Mth.sin(pLimbSwing * 0.6662F + 3.1415927F) * 0.4F) * pLimbSwingAmount;
    float f9 = Math.abs(Mth.sin(pLimbSwing * 0.6662F + 1.5707964F) * 0.4F) * pLimbSwingAmount;
    float f10 = Math.abs(Mth.sin(pLimbSwing * 0.6662F + 4.712389F) * 0.4F) * pLimbSwingAmount;
    ModelPart var10000 = this.rightHindLeg;
    var10000.yRot += f3;
    var10000 = this.leftHindLeg;
    var10000.yRot += -f3;
    var10000 = this.rightMiddleHindLeg;
    var10000.yRot += f4;
    var10000 = this.leftMiddleHindLeg;
    var10000.yRot += -f4;
    var10000 = this.rightMiddleFrontLeg;
    var10000.yRot += f5;
    var10000 = this.leftMiddleFrontLeg;
    var10000.yRot += -f5;
    var10000 = this.rightFrontLeg;
    var10000.yRot += f6;
    var10000 = this.leftFrontLeg;
    var10000.yRot += -f6;
    var10000 = this.rightHindLeg;
    var10000.zRot += f7;
    var10000 = this.leftHindLeg;
    var10000.zRot += -f7;
    var10000 = this.rightMiddleHindLeg;
    var10000.zRot += f8;
    var10000 = this.leftMiddleHindLeg;
    var10000.zRot += -f8;
    var10000 = this.rightMiddleFrontLeg;
    var10000.zRot += f9;
    var10000 = this.leftMiddleFrontLeg;
    var10000.zRot += -f9;
    var10000 = this.rightFrontLeg;
    var10000.zRot += f10;
    var10000 = this.leftFrontLeg;
    var10000.zRot += -f10;
  }

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		neck.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		rightHindLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leftHindLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		rightMiddleHindLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leftMiddleHindLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		rightMiddleFrontLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leftMiddleFrontLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		rightFrontLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leftFrontLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		// TODO Auto-generated method stub
    return root();
	}
}
