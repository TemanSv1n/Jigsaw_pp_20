package net.svisvi.jigsawpp.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.FluidState;
import net.svisvi.jigsawpp.effect.init.ModEffects;
import net.svisvi.jigsawpp.fluid.init.ModFluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityCanStandOnFluidMixin {

    /**
     * Universal fluid standing behavior for all living entities
     */
    @Inject(
            method = "canStandOnFluid",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onCanStandOnFluid(FluidState fluidState, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;

        // Unified logic for all entities
        boolean canStand = false;

        if (entity.hasEffect(ModEffects.POOP_WALKING.get()) && (fluidState.is(ModFluids.PONOS.get()))) {
            canStand = true;
        }

        // Set the result if we have a custom decision
        if (canStand) {
            cir.setReturnValue(true);
        }
        // For false conditions, we don't cancel - let original logic run
    }
}