package net.svisvi.jigsawpp.procedures.radio;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RadioUtils {

    public static void activateAround(String code, Level level, BlockPos pPos, @Nullable LivingEntity activator){
        final Vec3 _center = new Vec3(pPos.getX(), pPos.getY(), pPos.getZ());
        List<Entity> _entfound = level.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(128 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center)))
                .collect(Collectors.toList());
        for (Entity entityiterator : _entfound) {
            //HANDLING ENTITIES
            if (entityiterator instanceof IRadioActivatable radioentity){
                if (getEntityName(entityiterator).equals(code)) {
                    radioentity.activate(level, pPos, entityiterator, null, activator, null);
                }
            }
            //HANDLING ENTITIES WITH ARMOR: PLAYERS
            if (entityiterator instanceof Player player){
                activatePlayer(player, level, activator);
            } else if (entityiterator instanceof LivingEntity living) {
                //NON PLAYER
                activateArmoredEntity(living, level, activator);
            }

        }
    }

    public static void activateWithItem(ItemStack stack, Level level, BlockPos pPos, @Nullable LivingEntity owner){
        activateAround(getItemStackName(stack), level, pPos, owner);

    }

    public static String getItemStackName(ItemStack stack) {
        String name = stack.getDisplayName().getString();
        // Remove square brackets if present
        if (name.startsWith("[") && name.endsWith("]")) {
            name = name.substring(1, name.length() - 1);
        }
        return name;
    }

    public static String getEntityName(Entity entity){
        String name = entity.getDisplayName().getString();
        // Remove square brackets if present
        if (name.startsWith("[") && name.endsWith("]")) {
            name = name.substring(1, name.length() - 1);
        }
        return name;
    }

    public static void activatePlayer(Player player, Level level, Entity activator){
        for (ItemStack stack :player.getInventory().armor){
            if (stack.getItem() instanceof IRadioActivatable radiostack){
                radiostack.activate(level, player.getOnPos(), null, player, activator, stack);
            }
        }
    }

    public static void activateArmoredEntity(LivingEntity living, Level level, Entity activator){
        for (ItemStack stack : living.getArmorSlots()){
            if (stack.getItem() instanceof IRadioActivatable radiostack){
                radiostack.activate(level, living.getOnPos(), null, living, activator, stack);
            }
        }
    }
}
