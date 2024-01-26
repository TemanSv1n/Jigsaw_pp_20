package net.svisvi.jigsawpp.fluid;

//gupis нахой
import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import java.util.function.Consumer;

public class BaseFluidType extends FluidType{
    private final ResourceLocation stillTexture; //текстуры и дрисня кароче нахой
    private final ResourceLocation flowingTexure;
    private final ResourceLocation overlayTexture;
    private final int tintColor;
    private final Vector3f fogColor; //цвет затемнения в воде нахой
    public BaseFluidType(final ResourceLocation stillTexture, final  ResourceLocation flowingTexure, final ResourceLocation overlayTexture,
                         final int tintColor, final Vector3f fogColor, final Properties properties){
        super(properties);
        this.stillTexture =stillTexture;
        this.flowingTexure =flowingTexure;
        this.overlayTexture = overlayTexture;
        this.tintColor = tintColor;
        this.fogColor = fogColor;
    }
    public ResourceLocation getStillTexture(){
        return stillTexture;
    }
    public ResourceLocation getFlowingTexure(){
        return flowingTexure;
    }
    public int getTintColor() {
        return tintColor;
    }

    public ResourceLocation getOverlayTexture() {
        return overlayTexture;
    }
    private Vector3f getFogColor() {
        return  fogColor;
    }
    @Override
    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
        consumer.accept(new IClientFluidTypeExtensions() {
            @Override
            public ResourceLocation getStillTexture() {
                return stillTexture;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return flowingTexure;
            }

            @Override
            public int getTintColor() {
                return tintColor;
            }

            @Override
            public ResourceLocation getOverlayTexture() {
                return overlayTexture;
            }

            @Override
            public @NotNull Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
                return fogColor;
            }

            @Override
            public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick, float nearDistance, float farDistance, FogShape shape) {
                RenderSystem.setShaderFogStart(1f); //Это короче когда затемнение воды начинается нахой
                RenderSystem.setShaderFogStart(3f); //Это короче когда затемнение воды заканчивается нахой
            }
        });

    }
}
