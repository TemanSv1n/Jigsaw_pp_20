package net.svisvi.jigsawpp.procedures;

import java.util.Random;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;

/**
 * MadMilkUt
 */
public class MadMilkUt {

    private static int bibus(Vec3 pos){
    int re = 0;
    Double x = pos.x();
    Double y = pos.y();
    Double z = pos.z();
    
    Double wholex = Math.floor(x);
    Double wholey = Math.floor(y);
    Double wholez = Math.floor(z);
    
    if(Double.compare(wholex, x) == 0){
      re = 1;
    }
    if(Double.compare(wholey, y) == 0){
      re = 2;
    }
    if(Double.compare(wholez, z) == 0){
      re = 3;
    }
    return re;
  }

  private static void abobay(LevelAccessor pLevel, double pX, double pY, double pZ, double xspeed, double ySpeed, double zSpeed){
    Random random = new Random();
    pLevel.addParticle(ParticleTypes.POOF, pX, pY, pZ, xspeed, 0.1f, zSpeed);
    pLevel.addParticle(ParticleTypes.POOF, pX, pY, pZ, xspeed * -1, 0.1f, zSpeed * -1);
    pLevel.addParticle(ParticleTypes.POOF, pX, pY, pZ, xspeed, 0.1f, zSpeed * -1);
    pLevel.addParticle(ParticleTypes.POOF, pX, pY, pZ, xspeed * -1, 0.1f, zSpeed);
  }

  public static void exec(Vec3 pos, Level pLevel){
    try{
      int da = bibus(pos);
      Double pX = pos.x();
      Double pY = pos.y();
      Double pZ = pos.z();
      Random random = new Random();
      switch (da) {
        case 1:
          for (int i = 0; i < 15; i++) {
            abobay(pLevel, pX, pY, pZ, 0.2d, random.nextDouble(0.1, 0.9), random.nextDouble(0.1, 0.3));
          }
          System.out.println(1);
          break;
        case 2:
          for (int i = 0; i < 15; i++) {
            abobay(pLevel, pX, pY, pZ, random.nextDouble(0.1, 0.9), 0.2d, random.nextDouble(0.1, 0.3));
          }
          System.out.println(2);
          break;
        case 3:
          for (int i = 0; i < 15; i++) {
            abobay(pLevel, pX, pY, pZ, random.nextDouble(0.1, 0.3), random.nextDouble(0.1, 0.9), 0.2d);
          }
          System.out.println(3);
          break;

        default:
          break;
        }
    } catch(Error e){
      System.out.println(e);
    }
  }
}
