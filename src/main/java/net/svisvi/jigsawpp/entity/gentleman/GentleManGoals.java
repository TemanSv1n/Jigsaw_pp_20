package net.svisvi.jigsawpp.entity.gentleman;

import java.util.EnumSet;
import java.util.List;
import java.util.Random;

//import net.createmod.ponder.foundation.registration.GenericMultiTagBuilder.Component;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.svisvi.jigsawpp.entity.drist_tnt.PrimedDristTnt;
import net.svisvi.jigsawpp.entity.emitters.FartGasEmitterEntity;
import net.svisvi.jigsawpp.entity.emitters.PoopGasEmitterEntity;
import net.svisvi.jigsawpp.entity.projectile.PurgenPiluleProjectile;
import net.svisvi.jigsawpp.entity.projectile.ThrownSweetBreadProjectile;
import net.svisvi.jigsawpp.entity.projectile.granadeProjectiles.AbstractGrenadeProjectile;
import net.svisvi.jigsawpp.entity.projectile.granadeProjectiles.GassyGrenadeProjectile;
import net.svisvi.jigsawpp.entity.projectile.granadeProjectiles.PonosGrenadeProjectile;
import net.svisvi.jigsawpp.entity.projectile.granadeProjectiles.PurgenGrenadeProjectile;
import net.svisvi.jigsawpp.item.init.ModItems;
import net.svisvi.jigsawpp.item.sweet_bread.SweetBreadItem;

public class GentleManGoals {

    public abstract static class AbstractGentlemanPhase extends MeleeAttackGoal {
        protected final GentleManEntity gentleman;
        protected byte canUsePhase;  
        protected static final Random random = new Random();
        protected static int ticksTillNextMagicNumber;
        protected static Component phaseComponent;
        protected static boolean ifPhaseComponentWasUsed;


        public AbstractGentlemanPhase(PathfinderMob pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen, byte pCanUsePhase) {
            super(pMob, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
            this.gentleman = (GentleManEntity) pMob;
            this.setFlags(EnumSet.of(Goal.Flag.LOOK));
            this.canUsePhase = pCanUsePhase;
        }

        @Override
        public boolean canUse() {
            return this.getCanUsePhase() == this.gentleman.getPhase() && super.canUse();
        }

        public static void setPhaseComponent(Component phaseComponent) {
            AbstractGentlemanPhase.phaseComponent = phaseComponent;
        }

        public static Component getPhaseComponent() {
            return phaseComponent;
        }

        public void teleportToTarget() {
            LivingEntity target = this.gentleman.getTarget();

            if(!this.gentleman.level().isClientSide()) {
                this.gentleman.level().addFreshEntity(new PoopGasEmitterEntity(this.gentleman.level(), this.gentleman.getX(), this.gentleman.getY(), this.gentleman.getZ(), 2, 60));
            }

            if(target != null)
                this.gentleman.teleportTo(target.getX(), target.getY() + 0.2d, target.getZ());
        }

        public byte getCanUsePhase() {
            return this.canUsePhase;
        }

        public void setCanUsePhase(byte pCanUsePhase) {
            this.canUsePhase = pCanUsePhase;
        }

        public void spawnGrenades(int maxThrowItemCount) {
            if (!this.gentleman.level().isClientSide) {
                for(int i = 0; i <= maxThrowItemCount; i++) {
                    this.gentleman.playSound(SoundEvents.DISPENSER_LAUNCH, 1.0f, random.nextFloat(0.5f, 1.0f));
                    AbstractGrenadeProjectile grenade = getRandomGrenade(this.gentleman.level(), this.gentleman);
                    this.gentleman.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(this.grenadeItem(grenade), 1));
                    grenade.setPos(this.gentleman.getX(), this.gentleman.getEyeY() - 0.1, this.gentleman.getZ());
                    grenade.setOwner(this.gentleman);
                    grenade.setAffectOwner(false);
                    grenade.setInstaboom(true);
                    double xVel = random.nextDouble(-1.0d, 1.0d) * 0.5; 
                    double yVel = 0.5 + random.nextDouble() * 0.5;
                    double zVel = random.nextDouble(-1.0d, 1.0d) * 0.5;
                    grenade.setDeltaMovement(xVel, yVel, zVel);
                    this.gentleman.swing(InteractionHand.MAIN_HAND);
                    this.gentleman.level().addFreshEntity(grenade);
                }
            }
        }


        @Override
        public void tick() {
            super.tick();
        }

        protected Item grenadeItem(AbstractGrenadeProjectile pGrenade) {
            Item gItem = null;
            if(pGrenade instanceof  GassyGrenadeProjectile)
                gItem = ModItems.GASSY_GRENADE.get();
            if(pGrenade instanceof PurgenGrenadeProjectile)
                gItem = ModItems.PURGEN_GRENADE.get();
            if(pGrenade instanceof PonosGrenadeProjectile)
                gItem = ModItems.PONOS_GRENADE.get();
            return gItem;
        } 

        protected AbstractGrenadeProjectile getRandomGrenade (Level pLevel, LivingEntity pLivingEntity) {
            AbstractGrenadeProjectile grenade;
            switch (random.nextInt(0, 3)) {
                case 0:
                    grenade = new GassyGrenadeProjectile(pLevel, pLivingEntity);
                    break;
                case 1:
                    grenade = new PurgenGrenadeProjectile(pLevel, pLivingEntity);
                    break;
                case 2:
                    grenade = new PonosGrenadeProjectile(pLevel, pLivingEntity);
                    break;
                default:
                    grenade = new PonosGrenadeProjectile(pLevel, pLivingEntity);
                    break;
            }
            return grenade;
        }

        protected void spawnTntAtPlayerPosition(LivingEntity pEnemy) {
            if (!this.gentleman.level().isClientSide()) {
                int radius = 30;
                AABB box = new AABB(this.gentleman.getX() - radius, this.gentleman.getY() - radius, this.gentleman.getZ() - radius,
                    this.gentleman.getX() + radius, this.gentleman.getY() + radius, this.gentleman.getZ() + radius);
                List<Player> players = this.gentleman.level().getEntitiesOfClass(Player.class, box);
                MinecraftServer server = this.gentleman.level().getServer();
                if (server != null && this.gentleman.level().random.nextFloat() >= 0.7) { server.getPlayerList().broadcastSystemMessage(this.gentleman.spawnTntAtPlayerComponent, false); }
                for (Player player : players) {
                    PrimedDristTnt tnt = new PrimedDristTnt(this.gentleman.level(), player.getX() + 0.5, player.getY() + 0.5, player.getZ() + 0.5, this.gentleman);
                    tnt.setFuse(40);
                    this.gentleman.level().addFreshEntity(tnt);
                    this.gentleman.level().playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
                    this.gentleman.level().gameEvent(this.gentleman, GameEvent.PRIME_FUSE, player.blockPosition());
                }
            }
        }

        protected void gentlemanGassy(Level pLevel, LivingEntity pEntity) {
            pEntity.playSound(SoundEvents.COW_MILK, 1.0f, 1.0f);
            if(!pLevel.isClientSide()) {
                FartGasEmitterEntity gasEmitterEntity = new FartGasEmitterEntity(pLevel, pEntity.getX(), pEntity.getY(), pEntity.getZ(), 2, 100);
                gasEmitterEntity.setOwner(pEntity);
                gasEmitterEntity.setAffectOwner(false);;
                pLevel.addFreshEntity(gasEmitterEntity);
            }
        }
    }

    public static class FirstGentlemanPhase extends AbstractGentlemanPhase {
        private int gassyAttackCouldown = 0;

        public FirstGentlemanPhase(PathfinderMob pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen,
                byte pCanUsePhase) {
            super(pMob, pSpeedModifier, pFollowingTargetEvenIfNotSeen, pCanUsePhase);
        }

        @Override
        protected void checkAndPerformAttack(LivingEntity pEnemy, double pDistToEnemySqr) {
            super.checkAndPerformAttack(pEnemy, pDistToEnemySqr);
            if(gassyAttackCouldown % 60 == 0 && this.gentleman.level().getNearestPlayer(this.gentleman, 3) != null) {
                gassyAttackCouldown = 0;
                this.gentlemanGassy(this.gentleman.level(), this.gentleman);
            }else
                gassyAttackCouldown++;

            if(random.nextDouble() <= 0.05d)
                this.teleportToTarget();
        }
    }

    public static class SecondGentlemanPhase extends AbstractGentlemanPhase {
        private int gassyAttackCouldown = 0;

        public SecondGentlemanPhase(PathfinderMob pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen,
                byte pCanUsePhase) {
            super(pMob, pSpeedModifier, pFollowingTargetEvenIfNotSeen, pCanUsePhase);
        }

        @Override
        protected void checkAndPerformAttack(LivingEntity pEnemy, double pDistToEnemySqr) {
            if(this.gentleman.getItemInHand(InteractionHand.MAIN_HAND) != new ItemStack(ModItems.BEAVER_KNIFE.get()))
                this.gentleman.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(ModItems.BEAVER_KNIFE.get()));
            super.checkAndPerformAttack(pEnemy, pDistToEnemySqr);
            if(gassyAttackCouldown % 60 == 0 && this.gentleman.level().getNearestPlayer(this.gentleman, 3) != null) {
                gassyAttackCouldown = 0;
                this.gentlemanGassy(this.gentleman.level(), this.gentleman);
            }else
                gassyAttackCouldown++;

            if(random.nextDouble() <= 0.005d && !this.gentleman.level().isClientSide()) {
                if(!this.gentleman.level().isClientSide()) {
                    MinecraftServer server = this.gentleman.level().getServer();
                    if (server != null) { server.getPlayerList().broadcastSystemMessage(this.gentleman.shootPurgenPiluleComponent, false); }
                }
                PurgenPiluleProjectile bombr = new PurgenPiluleProjectile(this.gentleman.level());
                bombr.setArmor_piercing(true);
                bombr.shoot(this.gentleman.level(), this.gentleman, (float) (3 * 1.5), 0.0f, new ItemStack(ModItems.CRYSTAL_PURGEN_PILULE.get()));
            }

            if(random.nextDouble() <= 0.01d)
                this.spawnGrenades(5);
            
            if(random.nextDouble() <= 0.007d)
                this.spawnTntAtPlayerPosition(pEnemy);

            if(random.nextDouble() <= 0.05d)
                this.teleportToTarget();
        }
    }


    public static class ThirdGentlemanPhase extends AbstractGentlemanPhase {
        private int gassyAttackCouldown = 0;

        public ThirdGentlemanPhase(PathfinderMob pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen,
                byte pCanUsePhase) {
            super(pMob, pSpeedModifier, pFollowingTargetEvenIfNotSeen, pCanUsePhase);
        }

        @Override
        protected void checkAndPerformAttack(LivingEntity pEnemy, double pDistToEnemySqr) {
            if(gassyAttackCouldown % 60 == 0 && this.gentleman.level().getNearestPlayer(this.gentleman, 3) != null) {
                gassyAttackCouldown = 0;
                this.gentlemanGassy(this.gentleman.level(), this.gentleman);
            }else
                gassyAttackCouldown++;

            if(random.nextDouble() <= 0.2d)
                this.spawnGrenades(5);
            
            if(random.nextDouble() <= 0.1d)
                for(int i = 0; i <= 5; i++)
                this.spawnTntAtPlayerPosition(pEnemy);

            if(random.nextDouble() <= 0.05d)
                    this.teleportToTarget();

        }
    }
}
