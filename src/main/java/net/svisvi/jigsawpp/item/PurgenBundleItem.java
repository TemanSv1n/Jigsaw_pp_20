package net.svisvi.jigsawpp.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.BundleTooltip;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class PurgenBundleItem extends BundleItem {
    private static final String TAG_ITEMS = "jigsaw_pp:pilules";
    public static final int MAX_WEIGHT = 32;
    private static final int BUNDLE_IN_BUNDLE_WEIGHT = 4;
    private static final int BAR_COLOR = Mth.color(0.4F, 0.4F, 1.0F);

    public PurgenBundleItem() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.COMMON)
                .food((new FoodProperties.Builder()).nutrition(0).saturationMod(0f).alwaysEat().meat()
                        .build()));
    }


    public static float getFullnessDisplay(ItemStack pStack) {
        return (float)getContentWeight(pStack) / MAX_WEIGHT;
    }

    public boolean overrideStackedOnOther(ItemStack pStack, Slot pSlot, ClickAction pAction, Player pPlayer) {
        if (pStack.getCount() != 1 || pAction != ClickAction.SECONDARY) {
            return false;
        } else {
            ItemStack itemstack = pSlot.getItem();
            if (itemstack.isEmpty()) {
                this.playRemoveOneSound(pPlayer);
                removeOne(pStack).ifPresent((p_150740_) -> {
                    add(pStack, pSlot.safeInsert(p_150740_));
                });
            } else if (itemstack.getItem().canFitInsideContainerItems()) {
                if (itemstack.is(ItemTags.create(new ResourceLocation("jigsaw_pp:pilules")))) {
                    int i = (MAX_WEIGHT - getContentWeight(pStack)) / getWeight(itemstack);
                    int j = add(pStack, pSlot.safeTake(itemstack.getCount(), i, pPlayer));
                    if (j > 0) {
                        this.playInsertSound(pPlayer);
                    }
                }
            }

            return true;
        }
    }

    public boolean overrideOtherStackedOnMe(ItemStack pStack, ItemStack pOther, Slot pSlot, ClickAction pAction, Player pPlayer, SlotAccess pAccess) {
        if (pStack.getCount() != 1) return false;
        if (pAction == ClickAction.SECONDARY && pSlot.allowModification(pPlayer)) {
            if (pOther.isEmpty()) {
                removeOne(pStack).ifPresent((p_186347_) -> {
                    this.playRemoveOneSound(pPlayer);
                    pAccess.set(p_186347_);
                });
            } else {
                if (pOther.is(ItemTags.create(new ResourceLocation("jigsaw_pp:pilules")))) {
                    int i = add(pStack, pOther);
                    if (i > 0) {
                        this.playInsertSound(pPlayer);
                        pOther.shrink(i);
                    }
                }
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemstack, Level world, LivingEntity entity) {
        this.stopUsing(entity);
        ItemStack stack = ItemStack.EMPTY;
        if (entity instanceof Player player) {
            stack = useFirst(itemstack, player);
        }
        stack.getItem().finishUsingItem(stack, world, entity);
//        System.out.println(stack.toString());
//        System.out.println(stack.getItem().toString());

        return itemstack;
    }

    /**
     * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
     * {@link}.
     */
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
//        InteractionResultHolder<ItemStack> ar = super.use(pLevel, pPlayer, pUsedHand);
//        ItemStack itemStack = ar.getObject();
//        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
//
//        if (pPlayer.isShiftKeyDown()) {
//            if (dropContents(itemstack, pPlayer)) {
//                this.playDropContentsSound(pPlayer);
//                pPlayer.awardStat(Stats.ITEM_USED.get(this));
//                return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
//            } else {
//                return InteractionResultHolder.fail(itemstack);
//            }
//        }
//        return ar;

        pPlayer.playSound(SoundEvents.PLAYER_BURP, 1.0F, 1.0F);
        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        return ItemUtils.startUsingInstantly(pLevel, pPlayer, pUsedHand);
    }

    public static ItemStack useFirst(ItemStack pStack, Player pPlayer) {
        CompoundTag compoundtag = pStack.getOrCreateTag();
        if (!compoundtag.contains(TAG_ITEMS)) {
            return ItemStack.EMPTY;
        } else {
            ItemStack itemStack = ItemStack.EMPTY;
            if (pPlayer instanceof ServerPlayer) {
                ListTag listtag = compoundtag.getList(TAG_ITEMS, 10);

//                this.playRemoveOneSound(pPlayer);
//                removeOne(pStack).ifPresent((pp) -> {
//                   itemStack = pp;
//                });
                itemStack = shrinkOne(pStack).get();
                if (itemStack == null) {
                    itemStack = ItemStack.EMPTY;
                }
            }

            //pStack.removeTagKey(TAG_ITEMS);
            return itemStack;
        }
    }

    public static Optional<ItemStack> shrinkOne(ItemStack pStack) {
        CompoundTag compoundtag = pStack.getOrCreateTag();
        if (!compoundtag.contains(TAG_ITEMS)) {
            return Optional.empty();
        } else {
            ListTag listtag = compoundtag.getList(TAG_ITEMS, 10);
            if (listtag.isEmpty()) {
                return Optional.empty();
            } else {
                int i = 0;
                CompoundTag compoundtag1 = listtag.getCompound(0);
                ItemStack itemstack = ItemStack.of(compoundtag1);
                if (itemstack.getCount() == 1) {
                    listtag.remove(0);
                } else {
                    itemstack.shrink(1);
                    listtag.remove(0);
                    add(pStack, itemstack);
                }
                if (listtag.isEmpty()) {
                    pStack.removeTagKey(TAG_ITEMS);
                }
                itemstack.setCount(1);
                return Optional.of(itemstack);
            }
        }
    }

    public boolean isBarVisible(ItemStack pStack) {
        return getContentWeight(pStack) > 0;
    }

    public int getBarWidth(ItemStack pStack) {
        return Math.min(1 + 12 * getContentWeight(pStack) / MAX_WEIGHT, 13);
    }

    public int getBarColor(ItemStack pStack) {
        return BAR_COLOR;
    }

    private static int add(ItemStack pBundleStack, ItemStack pInsertedStack) {
        if (!pInsertedStack.isEmpty() && pInsertedStack.is(ItemTags.create(new ResourceLocation("jigsaw_pp:pilules"))) && pInsertedStack.getItem().canFitInsideContainerItems()) {
            CompoundTag compoundtag = pBundleStack.getOrCreateTag();
            if (!compoundtag.contains(TAG_ITEMS)) {
                compoundtag.put(TAG_ITEMS, new ListTag());
            }

            int i = getContentWeight(pBundleStack);
            int j = getWeight(pInsertedStack);
            int k = Math.min(pInsertedStack.getCount(), (MAX_WEIGHT - i) / j);
            if (k == 0) {
                return 0;
            } else {
                ListTag listtag = compoundtag.getList(TAG_ITEMS, 10);
                Optional<CompoundTag> optional = getMatchingItem(pInsertedStack, listtag);
                if (optional.isPresent()) {
                    CompoundTag compoundtag1 = optional.get();
                    ItemStack itemstack = ItemStack.of(compoundtag1);
                    itemstack.grow(k);
                    itemstack.save(compoundtag1);
                    listtag.remove(compoundtag1);
                    listtag.add(0, (Tag)compoundtag1);
                } else {
                    ItemStack itemstack1 = pInsertedStack.copyWithCount(k);
                    CompoundTag compoundtag2 = new CompoundTag();
                    itemstack1.save(compoundtag2);
                    listtag.add(0, (Tag)compoundtag2);
                }

                return k;
            }
        } else {
            return 0;
        }
    }

    private static Optional<CompoundTag> getMatchingItem(ItemStack pStack, ListTag pList) {
        return pStack.is(Items.BUNDLE) ? Optional.empty() : pList.stream().filter(CompoundTag.class::isInstance).map(CompoundTag.class::cast).filter((p_186350_) -> {
            return ItemStack.isSameItemSameTags(ItemStack.of(p_186350_), pStack);
        }).findFirst();
    }

    private static int getWeight(ItemStack pStack) {
        if (pStack.is(Items.BUNDLE)) {
            return BUNDLE_IN_BUNDLE_WEIGHT + getContentWeight(pStack);
        } else {
            if ((pStack.is(Items.BEEHIVE) || pStack.is(Items.BEE_NEST)) && pStack.hasTag()) {
                CompoundTag compoundtag = BlockItem.getBlockEntityData(pStack);
                if (compoundtag != null && !compoundtag.getList("Bees", 10).isEmpty()) {
                    return MAX_WEIGHT;
                }
            }

//            float s = MAX_WEIGHT / pStack.getMaxStackSize();
//            if (s < 1){s = 1;}
//            return (int)s;
            return 1;
        }
    }

    public static int getContentWeight(ItemStack pStack) {
        return getContents(pStack).mapToInt((p_186356_) -> {
            return getWeight(p_186356_) * p_186356_.getCount();
        }).sum();
    }

    private static Optional<ItemStack> removeOne(ItemStack pStack) {
        CompoundTag compoundtag = pStack.getOrCreateTag();
        if (!compoundtag.contains(TAG_ITEMS)) {
            return Optional.empty();
        } else {
            ListTag listtag = compoundtag.getList(TAG_ITEMS, 10);
            if (listtag.isEmpty()) {
                return Optional.empty();
            } else {
                int i = 0;
                CompoundTag compoundtag1 = listtag.getCompound(0);
                ItemStack itemstack = ItemStack.of(compoundtag1);
                listtag.remove(0);
                if (listtag.isEmpty()) {
                    pStack.removeTagKey(TAG_ITEMS);
                }

                return Optional.of(itemstack);
            }
        }
    }

    private static boolean dropContents(ItemStack pStack, Player pPlayer) {
        CompoundTag compoundtag = pStack.getOrCreateTag();
        if (!compoundtag.contains(TAG_ITEMS)) {
            return false;
        } else {
            if (pPlayer instanceof ServerPlayer) {
                ListTag listtag = compoundtag.getList(TAG_ITEMS, 10);

                for(int i = 0; i < listtag.size(); ++i) {
                    CompoundTag compoundtag1 = listtag.getCompound(i);
                    ItemStack itemstack = ItemStack.of(compoundtag1);
                    pPlayer.drop(itemstack, true);
                }
            }

            pStack.removeTagKey(TAG_ITEMS);
            return true;
        }
    }

    public static Stream<ItemStack> getContents(ItemStack pStack) {
        CompoundTag compoundtag = pStack.getTag();
        if (compoundtag == null) {
            return Stream.empty();
        } else {
            ListTag listtag = compoundtag.getList(TAG_ITEMS, 10);
            return listtag.stream().map(CompoundTag.class::cast).map(ItemStack::of);
        }
    }

    public Optional<TooltipComponent> getTooltipImage(ItemStack pStack) {
        NonNullList<ItemStack> nonnulllist = NonNullList.create();
        getContents(pStack).forEach(nonnulllist::add);
        return Optional.of(new BundleTooltip(nonnulllist, getContentWeight(pStack)));
    }

    /**
     * Allows items to add custom lines of information to the mouseover description.
     */
    public void appendHoverText(ItemStack pStack, Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("item.minecraft.bundle.fullness", getContentWeight(pStack), MAX_WEIGHT).withStyle(ChatFormatting.GRAY));
    }

    public void onDestroyed(ItemEntity pItemEntity) {
        ItemUtils.onContainerDestroyed(pItemEntity, getContents(pItemEntity.getItem()));
    }

    private void playRemoveOneSound(Entity pEntity) {
        pEntity.playSound(SoundEvents.BUNDLE_REMOVE_ONE, 0.8F, 0.8F + pEntity.level().getRandom().nextFloat() * 0.4F);
    }

    private void playInsertSound(Entity pEntity) {
        pEntity.playSound(SoundEvents.BUNDLE_INSERT, 0.8F, 0.8F + pEntity.level().getRandom().nextFloat() * 0.4F);
    }

    private void playDropContentsSound(Entity pEntity) {
        pEntity.playSound(SoundEvents.BUNDLE_DROP_CONTENTS, 0.8F, 0.8F + pEntity.level().getRandom().nextFloat() * 0.4F);
    }




    //Usage anim
    @Override
    public int getUseDuration(ItemStack itemstack) {
        return 32;
    }
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.DRINK;
    }
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        this.stopUsing(pLivingEntity);
    }

    private void stopUsing(LivingEntity pUser) {
        pUser.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
    }

}
