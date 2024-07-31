package net.svisvi.jigsawpp.item;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import java.util.Map;

import java.util.Random;

public class BeaverAxeItem extends AxeItem {
    public BeaverAxeItem() {
        super(new Tier() {
            public int getUses() {
                return 333;
            }

            public float getSpeed() {
                return 9f;
            }

            public float getAttackDamageBonus() {
                return 5f;
            }

            public int getLevel() {
                return 3;
            }

            public int getEnchantmentValue() {
                return 8;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.EMPTY;
            }
        }, 1, -2.7999999999999998f, new Item.Properties());
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        super.useOn(context);
        //BeaveraxeBlockDestroyedWithToolProcedure.execute(context.getLevel(), context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ(), context.getItemInHand());
        execute(context.getLevel(), context.getClickedPos(), context.getItemInHand());
        return InteractionResult.SUCCESS;
    }

    public static void execute(Level world, BlockPos pPos, ItemStack itemstack) {
        boolean found = false;
        int sx = 0;
        int sy = 0;
        int sz = 0;
        if ((new ItemStack((world.getBlockState(pPos)).getBlock())).is(ItemTags.create(new ResourceLocation("minecraft:logs")))) {
            int x = pPos.getX();
            int y = pPos.getY();
            int z = pPos.getZ();
            sx = -3;
            found = false;
            for (int index0 = 0; index0 < (int) (6); index0++) {
                sy = -3;
                for (int index1 = 0; index1 < (int) (6); index1++) {
                    sz = -3;
                    for (int index2 = 0; index2 < (int) (6); index2++) {
                        if ((new ItemStack((world.getBlockState(new BlockPos(x + sx, y + sy, z + sz))).getBlock())).is(ItemTags.create(new ResourceLocation("minecraft:logs")))) {
                            if (!(ForgeRegistries.BLOCKS.getKey((world.getBlockState(new BlockPos(x + sx, y + sy, z + sz))).getBlock()).toString()).contains("stripped")) {
                                world.levelEvent(2001, new BlockPos(x + sx, y + sy, z + sz), Block.getId((world.getBlockState(new BlockPos(x + sx, y + sy, z + sz)))));
                                {
                                    BlockPos _bp = new BlockPos(x + sx, y + sy, z + sz);
                                    BlockState _bs = ForgeRegistries.BLOCKS.getValue(new ResourceLocation((("stripped_" + (ForgeRegistries.BLOCKS.getKey((world.getBlockState(new BlockPos(x + sx, y + sy, z + sz))).getBlock()).toString())
                                                    .substring((int) 10, (int) (ForgeRegistries.BLOCKS.getKey((world.getBlockState(new BlockPos(x + sx, y + sy, z + sz))).getBlock()).toString()).length()))).toLowerCase(java.util.Locale.ENGLISH)))
                                            .defaultBlockState();
                                    BlockState _bso = world.getBlockState(_bp);
                                    for (Map.Entry<Property<?>, Comparable<?>> entry : _bso.getValues().entrySet()) {
                                        Property _property = _bs.getBlock().getStateDefinition().getProperty(entry.getKey().getName());
                                        if (_property != null && _bs.getValue(_property) != null)
                                            try {
                                                _bs = _bs.setValue(_property, (Comparable) entry.getValue());
                                            } catch (Exception e) {
                                            }
                                    }
                                    world.setBlock(_bp, _bs, 3);
                                }
                                {
                                    ItemStack _ist = itemstack;
                                    if (_ist.hurt(1, world.random, null)) {
                                        _ist.shrink(1);
                                        _ist.setDamageValue(0);
                                    }
                                }
                            }
                        }
                        sz = sz + 1;
                    }
                    sy = sy + 1;
                }
                sx = sx + 1;
            }
        }
    }


}