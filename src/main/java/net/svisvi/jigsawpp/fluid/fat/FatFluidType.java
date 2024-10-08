package net.svisvi.jigsawpp.fluid.fat;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.function.Consumer;

public class FatFluidType extends FluidType {
//    private final ResourceLocation stillTexture; //текстуры и дрисня кароче нахой
//    private final ResourceLocation flowingTexure;
//    private final ResourceLocation overlayTexture;
//    private final int tintColor;
//    private final Vector3f fogColor; //цвет затемнения в воде нахой

    public FatFluidType(
//            final ResourceLocation stillTexture, final  ResourceLocation flowingTexure, final ResourceLocation overlayTexture,
//                          final int tintColor, final Vector3f fogColor, final Properties properties
    ) {
        super(Properties.create().fallDistanceModifier(0F).canExtinguish(true).supportsBoating(false).canHydrate(true).motionScale(0.007D).temperature(500).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                .sound(SoundActions.BUCKET_EMPTY, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.bucket.empty_lava"))).sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH));



//        super(FluidType.Properties.create().fallDistanceModifier(0F).canExtinguish(false).supportsBoating(true).canHydrate(true).motionScale(-0.014D).temperature(400).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
//                .sound(SoundActions.BUCKET_EMPTY, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.cow.milk"))).sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH));
//        this.stillTexture =stillTexture;
//        this.flowingTexure =flowingTexure;
//        this.overlayTexture = overlayTexture;
//        this.tintColor = tintColor;
//        this.fogColor = fogColor;
    }


    @Override
    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
        consumer.accept(new IClientFluidTypeExtensions() {
            private static final ResourceLocation STILL_TEXTURE = new ResourceLocation("jigsaw_pp:block/fat_still"),
                    FLOWING_TEXTURE = new ResourceLocation("jigsaw_pp:block/fat_flow");

            @Override
            public ResourceLocation getStillTexture() {
                return STILL_TEXTURE;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return FLOWING_TEXTURE;
            }

//            @Override
//            public int getTintColor() {
//                return tintColor;
//            }
//
//            @Override
//            public ResourceLocation getOverlayTexture() {
//                return overlayTexture;
//            }

            @Override
            public @NotNull Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
                return new Vector3f(224f / 255f, 187f / 255f, 128f / 255f);
            }

            @Override
            public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick, float nearDistance, float farDistance, FogShape shape) {
                RenderSystem.setShaderFogStart(1f); //Это короче когда затемнение воды начинается нахой
                RenderSystem.setShaderFogEnd(4f); //Это короче когда затемнение воды заканчивается нахой
            }
        });
    }
}
