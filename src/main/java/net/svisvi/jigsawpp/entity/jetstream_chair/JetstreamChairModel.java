package net.svisvi.jigsawpp.entity.jetstream_chair;

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

// Made with Blockbench 4.5.1
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports
public class JetstreamChairModel<T extends Entity> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in
    // the entity renderer and passed into this model's constructor
    public final ModelPart bone;

    public JetstreamChairModel(ModelPart root) {
        this.bone = root.getChild("bone");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition bone = partdefinition.addOrReplaceChild("bone",
                CubeListBuilder.create().texOffs(17, 1).addBox(-5.0F, -8.0F, -5.0F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(15, 11).addBox(-5.0F, -18.0F, 4.25F, 10.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(40, 39)
                        .addBox(-3.5F, -14.0F, 4.25F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(32, 33).addBox(3.5F, -17.0F, 4.25F, 2.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(38, 35)
                        .addBox(-5.5F, -17.0F, 4.25F, 2.0F, 9.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 11).addBox(-2.5F, -17.0F, 4.25F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 11)
                        .addBox(1.5F, -17.0F, 4.25F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 11).addBox(-0.5F, -17.0F, 4.25F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(2, 0)
                        .addBox(-4.25F, -7.0F, -4.25F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(4, 1).addBox(2.25F, -7.0F, -4.25F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(4, 3)
                        .addBox(2.25F, -7.0F, 2.25F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(4, 4).addBox(-4.25F, -7.0F, 2.25F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(7, 52)
                        .addBox(-2.0F, -5.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(6, 51).addBox(-2.5F, -7.0F, -2.5F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition cube_r1 = bone.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(6, 2).addBox(-0.5F, -3.5F, -0.5F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -10.5F, 4.75F, 0.0F, 0.0F, 0.6109F));
        PartDefinition cube_r2 = bone.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(5, 8).addBox(-0.5F, -3.5F, -0.5F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-2.0F, -10.5F, 4.75F, 0.0F, 0.0F, -0.6109F));
        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }
}
