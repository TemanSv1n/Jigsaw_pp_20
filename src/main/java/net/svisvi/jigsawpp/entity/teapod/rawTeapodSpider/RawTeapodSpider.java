package net.svisvi.jigsawpp.entity.teapod.rawTeapodSpider;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.svisvi.jigsawpp.entity.projectile.SlonProjectile;
import net.svisvi.jigsawpp.entity.teapod.teapodSpider.TeapodAtackGoal;
import net.svisvi.jigsawpp.entity.teapod.teapodSpider.TeapodSpider;
import net.svisvi.jigsawpp.entity.teapod.teapodSpider.TeapodSpiderGoals;

public class RawTeapodSpider extends TeapodSpider {
    public RawTeapodSpider(EntityType<? extends TeapodSpider> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        //this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.4F));

        this.goalSelector.addGoal(1, new WaterAvoidingRandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 40.0F));
        //this.goalSelector.addGoal(2, new RangedAttackGoal(this, 1.0, 60, 10.0F));
        this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
        //this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
        //this.targetSelector.addGoal(2, new TeapodSpiderGoals<>(this, Player.class));
        //this.targetSelector.addGoal(2, new TeapodAtackGoal(this));
    }

    @Override
    public void performRangedAttack(LivingEntity pTarget, float pVelocity) {
    }


}
