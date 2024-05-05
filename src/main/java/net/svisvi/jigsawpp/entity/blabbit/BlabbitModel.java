package net.svisvi.jigsawpp.entity.blabbit;

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
import net.svisvi.jigsawpp.entity.moss_elephant.MossElephantAnimationDefinitions;
import net.svisvi.jigsawpp.entity.moss_elephant.MossElephantEntity;

public class BlabbitModel<T extends Entity> extends HierarchicalModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor

    private final ModelPart main;

    public BlabbitModel(ModelPart root) {
        this.main = root.getChild("main");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = main.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 25).addBox(-6.0F, -29.0F, -3.0F, 12.0F, 7.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(77, 0).addBox(-1.0F, -26.5F, -3.75F, 2.0F, 1.5F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition l_ear = body.addOrReplaceChild("l_ear", CubeListBuilder.create().texOffs(0, 60).addBox(-2.0F, -11.0F, -1.0F, 4.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -28.5F, 1.0F, 0.258F, -0.045F, 0.1687F));

        PartDefinition rotator1 = l_ear.addOrReplaceChild("rotator1", CubeListBuilder.create(), PartPose.offset(0.0F, -10.5F, -0.25F));

        PartDefinition cube_r1 = rotator1.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(52, 14).addBox(-1.75F, 0.0F, -3.5F, 3.5F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -1.75F, 0.9163F, 0.0F, 0.0F));

        PartDefinition r_ear = body.addOrReplaceChild("r_ear", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-2.0F, -11.0F, -1.0F, 4.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.0F, -28.5F, 1.0F, 0.258F, 0.045F, -0.1687F));

        PartDefinition rotator2 = r_ear.addOrReplaceChild("rotator2", CubeListBuilder.create(), PartPose.offset(0.0F, -10.5F, -1.75F));

        PartDefinition cube_r2 = rotator2.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(52, 8).mirror().addBox(-1.75F, 0.0F, -3.5F, 3.5F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, -0.25F, 0.9163F, 0.0F, 0.0F));

        PartDefinition r_eye = body.addOrReplaceChild("r_eye", CubeListBuilder.create().texOffs(0, 25).addBox(-1.25F, -1.25F, -0.75F, 2.5F, 2.5F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.75F, -26.75F, -2.75F));

        PartDefinition l_eye = body.addOrReplaceChild("l_eye", CubeListBuilder.create().texOffs(0, 28).addBox(-1.25F, -1.25F, -0.75F, 2.5F, 2.5F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.75F, -26.75F, -2.75F));

        PartDefinition l_vibr = body.addOrReplaceChild("l_vibr", CubeListBuilder.create().texOffs(52, 20).addBox(0.0F, -2.0F, 0.0F, 7.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.25F, -25.0F, -3.0F, 0.0F, 0.3927F, 0.0F));

        PartDefinition r_vibr = body.addOrReplaceChild("r_vibr", CubeListBuilder.create().texOffs(52, 20).mirror().addBox(-7.0F, -2.0F, 0.0F, 7.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.25F, -25.0F, -3.0F, 0.0F, -0.3927F, 0.0F));

        PartDefinition belly = body.addOrReplaceChild("belly", CubeListBuilder.create(), PartPose.offset(0.0F, -15.0F, 0.0F));

        PartDefinition dummy = belly.addOrReplaceChild("dummy", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -7.0F, -4.5F, 14.0F, 13.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition egg = belly.addOrReplaceChild("egg", CubeListBuilder.create().texOffs(36, 51).addBox(-3.75F, -5.0F, -0.25F, 7.0F, 10.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.25F, 7.25F));

        PartDefinition l_ball = belly.addOrReplaceChild("l_ball", CubeListBuilder.create().texOffs(0, 42).addBox(-4.5F, -0.254F, -4.6854F, 9.0F, 9.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 6.25F, -1.5F, 0.0436F, 0.0F, 0.0F));

        PartDefinition r_ball = belly.addOrReplaceChild("r_ball", CubeListBuilder.create().texOffs(35, 33).mirror().addBox(-4.5F, -0.254F, -4.6854F, 9.0F, 9.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-5.0F, 6.25F, -1.5F, 0.0436F, 0.0F, 0.0F));

        PartDefinition l_leg = belly.addOrReplaceChild("l_leg", CubeListBuilder.create().texOffs(40, 0).mirror().addBox(0.0F, -1.0F, -3.0F, 9.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(6.0F, 5.0F, -3.0F, 0.4614F, 1.029F, 0.5258F));

        PartDefinition r_leg = belly.addOrReplaceChild("r_leg", CubeListBuilder.create().texOffs(34, 25).addBox(-9.0F, -1.0F, -3.0F, 9.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, 5.0F, -3.0F, 0.4614F, -1.029F, -0.5258F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        //this.animateWalk(BlabbitAnimationDefinitions.JUMP, limbSwing, limbSwingAmount, 2f, 2.5f);
        this.animate(((BlabbitEntity) entity).idleAnimationState, BlabbitAnimationDefinitions.IDLE, ageInTicks, 1f);
        this.animate(((BlabbitEntity) entity).jumpAnimationState, BlabbitAnimationDefinitions.JUMP, ageInTicks, 1f);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return main;
    }
}
