package net.svisvi.jigsawpp.entity.beaver_zombie.beaver_zombie_spawner;

import java.util.Random;
import java.util.logging.Level;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.TickTask;
import net.minecraft.server.commands.DifficultyCommand;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraftforge.common.Tags.EntityTypes;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.svisvi.jigsawpp.entity.beaverSpider.BeaverSpiderEntity;
import net.svisvi.jigsawpp.entity.beaver_zombie.BeaverZombieEntity;
import net.svisvi.jigsawpp.entity.init.ModEntities;
import net.minecraft.world.entity.SpawnGroupData;

/**
 * SpawnBeaverZombieGoal
 */
public class SpawnBeaverZombieGoal extends Goal {
  private final BeaverZombieSpawner bzSpawner;  
  private EntityType bz = ModEntities.ZOMBIE_BEAVER.get();

  public SpawnBeaverZombieGoal(BeaverZombieSpawner pMob) {
    this.bzSpawner = pMob;
  }

  @Override
  public void tick() {
    super.tick();
    ServerLevel serverLevel = (ServerLevel) this.bzSpawner.level();   
    serverLevel.getServer().tell(new TickTask(serverLevel.getServer().getTickCount(), () -> {
      if(this.bzSpawner.isAlive()){ 
        Random rand = new Random(); 
        int BeaverZombieCount = rand.nextInt(30, 40);
        double x = this.bzSpawner.getX();
        double y = this.bzSpawner.getY();
        double z = this.bzSpawner.getZ();
        spawnParticle(x, y, z );
        serverLevel.addParticle(ParticleTypes.EXPLOSION, x, y, z, 0, 0, 0);
        double xe = x - 1.5d;
        double ze = z - 1.5d;
        for(int i = 0; i <= BeaverZombieCount; i++){
          double xd = xe + rand.nextDouble(0d, 3.0d);
          double zd = ze + rand.nextDouble(0d, 3.0d);
          spawnBvz(xd, y, zd, serverLevel);
        }
      }
      this.bzSpawner.kill();
    }));
  }

  private void spawnParticle(double pX, double pY, double pZ){
    Random rand = new Random();
    for(int i = 0; i <= 10; i++){
      double x = pX + rand.nextDouble(-3.0d, 3.0d);
      double y = pY + rand.nextDouble(-3.0d, 3.0d);
      double z = pZ + rand.nextDouble(-3.0d, 3.0d);
      this.bzSpawner.level().addParticle(ParticleTypes.EXPLOSION, x, y, z, 1f, 1f, 1f); 

    }

  }

  private void spawnBvz(double xd, double y, double zd, ServerLevel serverLevel){
    Random rand = new Random();
    BeaverZombieEntity bvd = new BeaverZombieEntity(bz, serverLevel);
    if(bvd != null){
      int b = rand.nextInt(0, 30);
      int c = rand.nextInt(0, 20);
      if(b <= 1){
         bvd.setBaby(true);;              
      }
      if(c <= 1){
        BeaverSpiderEntity beaverSpiderEntity = new BeaverSpiderEntity(ModEntities.BEAVER_SPIDER.get(), serverLevel);
        if(beaverSpiderEntity != null) {
          beaverSpiderEntity.moveTo(xd, y, zd);
          bvd.startRiding(beaverSpiderEntity);
          serverLevel.addFreshEntityWithPassengers(beaverSpiderEntity);

        }
      //if(c <= 1){
      //  Spider spider = (Spider)EntityType.SPIDER.create(this.bzSpawner.level());
      //  if(spider != null) {
      //    spider.moveTo(xd, y, zd);
      //    bvd.startRiding(spider);
      //    serverLevel.addFreshEntityWithPassengers(spider);
      //  }
      }else{
        bvd.moveTo(xd, y, zd);
        serverLevel.addFreshEntity(bvd);
      }
    }
  }



  @Override
  public boolean canUse() {
     Player nearestPlayer = this.bzSpawner.level().getNearestPlayer(this.bzSpawner, 30);
    return nearestPlayer != null;   
  }
  
}
