package net.svisvi.jigsawpp.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.Advancement;
import net.minecraft.world.item.UseAnim;
import net.svisvi.jigsawpp.item.init.ModItems;
import net.minecraftforge.registries.ForgeRegistries;





public class Tube19Item extends Item {
    public Tube19Item() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON).food((new FoodProperties.Builder()).nutrition(0).saturationMod(0f).alwaysEat().meat()
                .build()));
    }

    @Override
    public UseAnim getUseAnimation(ItemStack itemstack) {
        return UseAnim.EAT;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemstack, Level world, LivingEntity entity) {
        curePotionEffect(entity, itemstack);
        if (world.isClientSide())
            Minecraft.getInstance().gameRenderer.displayItemActivation(new ItemStack(ModItems.TUBE_19.get()));

        ItemStack retvalsus = new ItemStack(ModItems.TUBE_19.get());

        double x = entity.getX();
        double y = entity.getY();
        double z = entity.getZ();

        this.stopUsing(entity);

        if ((entity instanceof Player _plr ? _plr.experienceLevel : 0) >= 1) {
            if (entity instanceof Player _player)
                _player.giveExperienceLevels(-(1));
            if (entity instanceof Player _player)
                _player.getCooldowns().addCooldown(itemstack.getItem(), 1200);
            if (world.isClientSide())
                Minecraft.getInstance().gameRenderer.displayItemActivation(itemstack);
                if (!world.isClientSide()) {
                    world.playSound(null, new BlockPos((int)x, (int)y, (int)z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("jigsaw_pp:clown_horn")), SoundSource.PLAYERS, 1, -1);
                } else {
                    world.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("jigsaw_pp:clown_horn")), SoundSource.PLAYERS, 1, -1, false);
                }
            if (world instanceof ServerLevel _level)
                _level.sendParticles(ParticleTypes.FLASH, x, y, z, 5, 0.3, 0.3, 0.3, 0);

        }

        return itemstack;

    }
    public boolean curePotionEffects(LivingEntity entityLiving, ItemStack curativeItem) {
        boolean ret = false;
        Iterator<MobEffectInstance> itr = entityLiving.getActiveEffectsMap().values().iterator();
        while (itr.hasNext()) {
            MobEffectInstance effect = itr.next();
//            if (MinecraftForge.EVENT_BUS.post(new PotionEvent.PotionRemoveEvent(entityLiving, effect))) {
//                continue;
//            }
            if (effect.getEffect().isBeneficial() == false) {
                //dont remove beneficial potions though such as speed, fire prot, night vision
                effect.getEffect().removeAttributeModifiers(entityLiving, entityLiving.getAttributes(), effect.getAmplifier());
                itr.remove();
                ret = true;
                //entityLiving.effectsDirty = true;
                if (entityLiving instanceof Player) {
                    ((Player) entityLiving).getCooldowns().addCooldown(this, 30);
                }
            }
        }
        return ret;
    }
    public boolean curePotionEffect(LivingEntity entityLiving, ItemStack curativeItem){
        Collection<MobEffectInstance> effects = entityLiving.getActiveEffectsMap().values();
        ArrayList<MobEffectInstance> badEffects = new ArrayList<>();
        for (MobEffectInstance effect : effects){
            if (!effect.getEffect().isBeneficial()){
                badEffects.add(effect);
            }
        }
        System.out.println(badEffects.toString());
        if (!badEffects.isEmpty()){
            Random random = new Random();
            MobEffectInstance randomEffect = badEffects.get(random.nextInt(badEffects.size()));
//            System.out.println(randomEffect.toString());
//            randomEffect.getEffect().removeAttributeModifiers(entityLiving, entityLiving.getAttributes(), randomEffect.getAmplifier());
            entityLiving.removeEffect(randomEffect.getEffect());

        }
        return false;
    }

    @Override
    public int getUseDuration(ItemStack itemstack) {
        return 32;
    }
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        this.stopUsing(pLivingEntity);
    }

    private void stopUsing(LivingEntity pUser) {
        pUser.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
    }
}
