package net.svisvi.jigsawpp.item.extinguisher;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.svisvi.jigsawpp.procedures.ExtinguisherUse;

public class ExtinguisherItem extends Item {
    public ExtinguisherItem(){
        super(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON).durability(250));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        pPlayer.getCooldowns().addCooldown(this, 10);
        ExtinguisherUse.Useclick(pPlayer);
        ItemStack _ist = pPlayer.getItemInHand(pUsedHand);
        if (_ist.hurt(1, RandomSource.create(), null)) {
            _ist.shrink(1);
            _ist.setDamageValue(0);
        }
        pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.LAVA_EXTINGUISH, SoundSource.PLAYERS, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));

        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
