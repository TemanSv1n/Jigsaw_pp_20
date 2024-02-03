package net.svisvi.jigsawpp.entity.client;

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
public class GasMaskModel<T extends Entity> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in
    // the entity renderer and passed into this model's constructor
    public final ModelPart head;

    public GasMaskModel(ModelPart root) {
        this.head = root.getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition mask = head.addOrReplaceChild("mask", CubeListBuilder.create().texOffs(27, 0).addBox(-3.0F, 0.0F, -2.0F, 6.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, -4.0F));
        PartDefinition cube_r1 = mask.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(24, 12).addBox(-4.0F, -2.5F, -1.0F, 8.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -1.5F, -1.0F, 0.3927F, 0.0F, 0.0F));
        PartDefinition cube_r2 = mask.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 5).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(2.5F, -1.6585F, -3.2304F, 0.3655F, -0.147F, 0.3655F));
        PartDefinition cube_r3 = mask.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 5).mirror().addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(-2.5F, -1.6585F, -3.2304F, 0.3655F, 0.147F, -0.3655F));
        PartDefinition central = mask.addOrReplaceChild("central", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, -1.0F));
        PartDefinition cube_r4 = central.addOrReplaceChild("cube_r4",
                CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.75F, -0.25F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 28).addBox(-1.5F, 1.25F, -0.75F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.5F, -2.0F, -0.7854F, 0.0F, 0.0F));
        PartDefinition left = mask.addOrReplaceChild("left", CubeListBuilder.create(), PartPose.offset(3.0F, 1.0F, -1.0F));
        PartDefinition cube_r5 = left.addOrReplaceChild("cube_r5",
                CubeListBuilder.create().texOffs(0, 16).addBox(0.0F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 12).addBox(3.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0692F, 0.2527F, 0.2706F));
        PartDefinition right = mask.addOrReplaceChild("right", CubeListBuilder.create(), PartPose.offset(-3.0F, 1.0F, -1.0F));
        PartDefinition cube_r6 = right.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 12).mirror().addBox(-4.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false).texOffs(0, 16).mirror()
                .addBox(-3.0F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, -0.2618F, -0.2618F));
        PartDefinition slaves = head.addOrReplaceChild("slaves",
                CubeListBuilder.create().texOffs(23, 19).addBox(-1.5F, -2.25F, -4.5F, 3.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)).texOffs(0, 0).addBox(-4.5F, 0.0F, -4.25F, 9.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -6.0F, 0.0F));
        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }
}
