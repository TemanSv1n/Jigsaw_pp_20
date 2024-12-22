package net.svisvi.jigsawpp.item.pspack;


import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.entity.stone_beaver.StoneBeaverEntity;
import net.svisvi.jigsawpp.item.init.ModItems;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BeaverStaffItem extends SwordItem {
    public BeaverStaffItem(Tier tier) {
        super(tier, 3, -3.4f, new Properties());
    }

    public static Entity victim(ItemStack itemStack, ServerLevel level){return level.getEntity(itemStack.getOrCreateTag().getUUID("victim"));}
    public static void setVictim(ItemStack itemStack, LivingEntity entity){itemStack.getOrCreateTag().putUUID("victim", entity.getUUID());}

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        BlockPos pos = pPlayer.getOnPos();
        ItemStack staff = pPlayer.getItemInHand(pUsedHand);
        pPlayer.getCooldowns().addCooldown(staff.getItem(), 60);
        {
            final Vec3 _center = new Vec3(pos.getX(),pos.getY(),pos.getZ());
            List<Entity> _entfound = pLevel.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(7 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                    .collect(Collectors.toList());
            for (Entity entityiterator : _entfound) {
                if (entityiterator instanceof StoneBeaverEntity beaver){
                    if (pLevel instanceof ServerLevel _level) {
                        beaver.setTarget((LivingEntity) victim(staff, _level));
                        _level.sendParticles(ParticleTypes.ANGRY_VILLAGER, beaver.getX(), beaver.getY(), beaver.getZ(), 10, 0.5, 0.5, 0.5, 0);
                    }
                    if (!pLevel.isClientSide()) {
                        pLevel.playSound(null, beaver.getOnPos(), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.ravager.roar")), SoundSource.BLOCKS, 1, 1);
                    } else {
                        pLevel.playLocalSound(beaver.getOnPos(), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.ravager.roar")), SoundSource.BLOCKS, 1, 1, false);

                    }
                }

            }
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        if (pAttacker.isShiftKeyDown()) {
            setVictim(pStack, pTarget);
            Level level = pTarget.level();
            if (pTarget.level() instanceof ServerLevel _level)
                _level.sendParticles(ParticleTypes.FLASH, pTarget.getX(), pTarget.getY(), pTarget.getZ(), 10, 0.5, 0.5, 0.5, 0);

            if (!level.isClientSide()) {
                level.playSound(null, pTarget.getOnPos(), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.respawn_anchor.charge")), SoundSource.BLOCKS, 1, 1);
            } else {
                level.playLocalSound(pTarget.getOnPos(), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.respawn_anchor.charge")), SoundSource.BLOCKS, 1, 1, false);

            }
        }
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    @Override
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        list.add(Component.translatable("item.jigsaw_pp.beaver_staff.desc"));
    }
}
