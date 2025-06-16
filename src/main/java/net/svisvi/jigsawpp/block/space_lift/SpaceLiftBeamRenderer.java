//package net.svisvi.jigsawpp.block.space_lift;
//
//import com.mojang.blaze3d.vertex.PoseStack;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.renderer.MultiBufferSource;
//import net.minecraft.client.renderer.blockentity.BeaconRenderer;
//import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
//import net.minecraft.world.level.block.entity.BeaconBlockEntity;
//import net.minecraft.world.phys.Vec3;
//import net.svisvi.jigsawpp.block.entity.SpaceLiftBlockEntity;
//
//import java.util.List;
//
//public class SpaceLiftBeamRenderer implements BlockEntityRenderer<SpaceLiftBlockEntity>
//
//{
//    private static int ticks;
//    private static int pausedTicks;
//
//    @Override
//    public void render(SpaceLiftBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay)
//    {
//        pPoseStack.pushPose();
//        pPoseStack.translate(-0.5D, 0, -0.5D);
//
//        long i = pBlockEntity.getLevel().getGameTime();
//        List<BeaconBlockEntity.BeaconBeamSection> list = pBlockEntity.getBeamSections();
//        int j = 0;
//
//        for(int k = 0; k < list.size(); ++k) {
//            BeaconBlockEntity.BeaconBeamSection beaconblockentity$beaconbeamsection = list.get(k);
//            BeaconRenderer.renderBeaconBeam(pPoseStack, pBufferSource, getPartialTicks(), getTicks(false), j, k == list.size() - 1 ? 1024 : beaconblockentity$beaconbeamsection.getHeight(), beaconblockentity$beaconbeamsection.getColor());
//            j += beaconblockentity$beaconbeamsection.getHeight();
//        }
//
//
//        pPoseStack.popPose();
//    }
//
//    public static float getPartialTicks() {
//        Minecraft mc = Minecraft.getInstance();
//        return (mc.isPaused() ? mc.pausePartialTick : mc.getFrameTime());
//    }
//    public static int getTicks(boolean includePaused) {
//        return includePaused ? ticks + pausedTicks : ticks;
//    }
//
//    public boolean shouldRenderOffScreen(SpaceLiftBlockEntity pBlockEntity) {
//        return true;
//    }
//
//    public int getViewDistance() {
//        return 256;
//    }
//
//    public boolean shouldRender(SpaceLiftBlockEntity pBlockEntity, Vec3 pCameraPos) {
//        return Vec3.atCenterOf(pBlockEntity.getBlockPos()).multiply(1.0D, 0.0D, 1.0D).closerThan(pCameraPos.multiply(1.0D, 0.0D, 1.0D), this.getViewDistance());
//    }
//}
