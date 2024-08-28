package net.svisvi.jigsawpp.item.pspack;


import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.svisvi.jigsawpp.item.init.ModItems;

public class EmptyBottleItem extends SwordItem {
    public EmptyBottleItem() {
        super(new Tier() {
            public int getUses() {
                return 100;
            }

            public float getSpeed() {
                return 4f;
            }

            public float getAttackDamageBonus() {
                return 0f;
            }

            public int getLevel() {
                return 2;
            }

            public int getEnchantmentValue() {
                return 2;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(Blocks.GREEN_STAINED_GLASS));
            }
        }, 3, -2.4f, new Properties());
    }
    @Override
    public boolean hurtEnemy(ItemStack itemstack, LivingEntity entity, LivingEntity sourceentity) {
        boolean retval = super.hurtEnemy(itemstack, entity, sourceentity);
        BotleLivingEntityIsHitWithToolProcedure.execute(entity.level(), entity.getX(), entity.getY(), entity.getZ(), entity, itemstack);
        return retval;
    }
    public class BotleLivingEntityIsHitWithToolProcedure {
        public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
            if (entity == null)
                return;
            itemstack.shrink(1);
            if (world instanceof Level _level) {
                if (!_level.isClientSide()) {
                    _level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.glass.break")), SoundSource.PLAYERS, 1, 1);
                } else {
                    _level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.glass.break")), SoundSource.PLAYERS, 1, 1, false);
                }
            }
            if (world.isClientSide())
                Minecraft.getInstance().gameRenderer.displayItemActivation(itemstack);
            if (world instanceof ServerLevel _level) {
                ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack(ModItems.ROSE_BOTTLE.get()));
                entityToSpawn.setPickUpDelay(10);
                _level.addFreshEntity(entityToSpawn);

            }
        }

    }} // | || || |-
