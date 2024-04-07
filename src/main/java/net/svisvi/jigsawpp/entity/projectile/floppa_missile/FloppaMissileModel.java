package net.svisvi.jigsawpp.entity.projectile.floppa_missile;

import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.EntityModel;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

// Made with Blockbench 4.9.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports
public class FloppaMissileModel<T extends Entity> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in
    // the entity renderer and passed into this model's constructor

    public final ModelPart masmsam;

    public FloppaMissileModel(ModelPart root) {
        this.masmsam = root.getChild("masmsam");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition masmsam = partdefinition.addOrReplaceChild("masmsam", CubeListBuilder.create(), PartPose.offset(0.0F, 36.0F, 11.0F));
        PartDefinition body = masmsam.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(2.0F, -15.0F, 12.0F));
        PartDefinition rotation = body.addOrReplaceChild("rotation", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));
        PartDefinition body_sub_1 = rotation.addOrReplaceChild("body_sub_1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition leg2_r1 = body_sub_1.addOrReplaceChild("leg2_r1", CubeListBuilder.create().texOffs(30, 13).addBox(-1.5F, -22.0F, 6.0F, 3.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 15.0F, -12.0F, -0.1745F, 0.0F, 0.0F));
        PartDefinition body_sub_1_r1 = body_sub_1.addOrReplaceChild("body_sub_1_r1", CubeListBuilder.create().texOffs(0, 97).addBox(-6.0F, -40.0F, 5.0F, 12.0F, 21.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 15.0F, -12.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(-2.0F, 1.0F, -28.0F));
        PartDefinition head_r1 = head.addOrReplaceChild("head_r1",
                CubeListBuilder.create().texOffs(61, 126).addBox(3.5F, -12.0F, -22.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(53, 115).addBox(-5.5F, -12.0F, -22.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(68, 123)
                        .addBox(-4.5F, -11.0F, -22.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(76, 123).addBox(2.5F, -11.0F, -22.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 14.0F, 16.0F, -0.48F, 0.0F, 0.0F));
        PartDefinition head_r2 = head.addOrReplaceChild("head_r2",
                CubeListBuilder.create().texOffs(84, 123).addBox(-2.5F, -13.0F, -21.0F, 5.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(100, 114).addBox(-3.5F, -17.0F, -19.0F, 7.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 14.0F, 16.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition leg1 = body.addOrReplaceChild("leg1", CubeListBuilder.create(), PartPose.offsetAndRotation(2.5F, 5.0F, -6.0F, 1.2654F, 0.0F, 0.0F));
        PartDefinition leg1_r1 = leg1.addOrReplaceChild("leg1_r1", CubeListBuilder.create().texOffs(51, 16).addBox(1.5F, -10.0F, 4.0F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, 10.0F, -6.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition leg2 = body.addOrReplaceChild("leg2", CubeListBuilder.create(), PartPose.offsetAndRotation(-6.5F, 5.0F, -6.0F, 1.2654F, 0.0F, 0.0F));
        PartDefinition leg2_r2 = leg2.addOrReplaceChild("leg2_r2", CubeListBuilder.create().texOffs(51, 15).addBox(-4.5F, -10.0F, 4.0F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, 10.0F, -6.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition leg3 = body.addOrReplaceChild("leg3", CubeListBuilder.create(), PartPose.offsetAndRotation(1.5F, 5.0F, -20.0F, 1.0472F, 0.0F, 0.0F));
        PartDefinition leg3_r1 = leg3.addOrReplaceChild("leg3_r1", CubeListBuilder.create().texOffs(51, 15).addBox(1.5F, -10.0F, -10.0F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, 10.0F, 8.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition leg4 = body.addOrReplaceChild("leg4", CubeListBuilder.create(), PartPose.offsetAndRotation(-5.5F, 5.0F, -20.0F, 1.0472F, 0.0F, 0.0F));
        PartDefinition leg4_r1 = leg4.addOrReplaceChild("leg4_r1", CubeListBuilder.create().texOffs(51, 15).addBox(-4.5F, -10.0F, -10.0F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5F, 10.0F, 8.0F, 0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        masmsam.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }
}
