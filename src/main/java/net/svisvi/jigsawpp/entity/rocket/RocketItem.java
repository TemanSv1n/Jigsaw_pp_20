package net.svisvi.jigsawpp.entity.rocket;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.svisvi.jigsawpp.entity.jetstream_chair.JetstreamChairEntity;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class RocketItem extends Item {
    private static final Predicate<Entity> ENTITY_PREDICATE;
//    private final Boat.Type type;
//    private final boolean hasChest;

    public RocketItem(Properties pProperties) {
        super(pProperties);
//        this.hasChest = pHasChest;
//        this.type = pType;
    }
    @Override
    public void appendHoverText(ItemStack itemstack, Level world, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        //list.add(Component.translatable("item.jigsaw_pp.jetstream_chair.desc"));
    }
    public InteractionResult useOn(UseOnContext pContext) {
        Direction $$1 = pContext.getClickedFace();
        if ($$1 == Direction.DOWN) {
            return InteractionResult.FAIL;
        } else {
            Level $$2 = pContext.getLevel();
            BlockPlaceContext $$3 = new BlockPlaceContext(pContext);
            BlockPos $$4 = $$3.getClickedPos();
            ItemStack $$5 = pContext.getItemInHand();
            Vec3 $$6 = Vec3.atBottomCenterOf($$4);
            AABB $$7 = ModEntities.ROCKET.get().getDimensions().makeBoundingBox($$6.x(), $$6.y(), $$6.z());
            if ($$2.noCollision((Entity)null, $$7) && $$2.getEntities((Entity)null, $$7).isEmpty()) {
                if ($$2 instanceof ServerLevel) {
                    ServerLevel $$8 = (ServerLevel)$$2;
                    Consumer<RocketEntity> $$9 = EntityType.createDefaultStackConfig($$8, $$5, pContext.getPlayer());
                    RocketEntity $$10 = (RocketEntity) ModEntities.ROCKET.get().create($$8, $$5.getTag(), $$9, $$4, MobSpawnType.SPAWN_EGG, true, true);
                    if ($$10 == null) {
                        return InteractionResult.FAIL;
                    }

                    float $$11 = (float) Mth.floor((Mth.wrapDegrees(pContext.getRotation() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
                    $$10.moveTo($$10.getX(), $$10.getY(), $$10.getZ(), $$11, 0.0F);
                    $$8.addFreshEntityWithPassengers($$10);
                    $$2.playSound((Player)null, $$10.getX(), $$10.getY(), $$10.getZ(), SoundEvents.ARMOR_STAND_PLACE, SoundSource.BLOCKS, 0.75F, 0.8F);
                    $$10.gameEvent(GameEvent.ENTITY_PLACE, pContext.getPlayer());
                }

                $$5.shrink(1);
                return InteractionResult.sidedSuccess($$2.isClientSide);
            } else {
                return InteractionResult.FAIL;
            }
        }
    }

//    private Boat getBoat(Level pLevel, HitResult pHitResult) {
//        return (Boat)(this.hasChest ? new ChestBoat(pLevel, pHitResult.getLocation().x, pHitResult.getLocation().y, pHitResult.getLocation().z) : new Boat(pLevel, pHitResult.getLocation().x, pHitResult.getLocation().y, pHitResult.getLocation().z));
//    }

    static {
        ENTITY_PREDICATE = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);
    }
}