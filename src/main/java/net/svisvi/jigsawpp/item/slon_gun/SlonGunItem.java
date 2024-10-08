package net.svisvi.jigsawpp.item.slon_gun;

import net.minecraft.advancements.critereon.ItemDurabilityTrigger;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.entity.projectile.ExtinguisherProjectile;
import net.svisvi.jigsawpp.entity.projectile.SlonProjectile;
import net.svisvi.jigsawpp.item.ut.CustomArmPoseItem;
import org.jetbrains.annotations.Nullable;

public class SlonGunItem extends Item implements CustomArmPoseItem {

    private Fluid content;



    public SlonGunItem() {super(
            new Item.Properties().stacksTo(1).rarity(Rarity.COMMON).durability(1001)
    );
    };

    private static void chleen(BlockPos pos, LevelAccessor level){
        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
    }
    @Override
    @Nullable
    public HumanoidModel.@Nullable ArmPose getArmPose(ItemStack stack, AbstractClientPlayer player, InteractionHand hand) {
        if (!player.swinging && !player.isUsingItem()) {
            return HumanoidModel.ArmPose.CROSSBOW_CHARGE;
        }
        return null;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        InteractionResultHolder<ItemStack> interact = super.use(pLevel, pPlayer, pUsedHand);
        ItemStack itemuse = pPlayer.getItemInHand(pUsedHand);

        BlockHitResult hitResult = getPlayerPOVHitResult(pLevel, pPlayer, this.content == Fluids.EMPTY ? ClipContext.Fluid.SOURCE_ONLY : ClipContext.Fluid.NONE);
        if(pPlayer.isShiftKeyDown()) {
            if(hitResult.getType() == HitResult.Type.BLOCK){
                int damage = itemuse.getDamageValue();
                BlockPos pos = hitResult.getBlockPos();
                Direction direction = hitResult.getDirection();
                BlockPos pos1 = pos.relative(direction);
                BlockState state = pLevel.getBlockState(pos1);
                Block block = state.getBlock();
                if(block == Blocks.WATER){
                    if(damage > 0) {
                        chleen(pos1, pLevel);
                        pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.DROWNED_HURT_WATER, SoundSource.PLAYERS, 0.5f, 2f);
                        if (itemuse.hurt(-500, RandomSource.create(), null)) {
                            itemuse.shrink(0);
                            itemuse.setDamageValue(-10);
                        }
                        pPlayer.getCooldowns().addCooldown(this, 15);
                    }
                }
            }
        } else {
            int damage = itemuse.getDamageValue();
            if (damage < 999) {
                if (itemuse.hurt(100, RandomSource.create(), null)) {
                    itemuse.shrink(0);
                    itemuse.setDamageValue(-10);
                }
               SlonProjectile.shoot(pLevel, pPlayer, 2, 0);
                //buse(pPlayer);
                pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 0.5f, 2f);
                pPlayer.getCooldowns().addCooldown(this, 10);
            }
        }
        return interact;
    }
}
