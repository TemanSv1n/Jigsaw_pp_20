package net.svisvi.jigsawpp.block.crops;

import net.minecraft.world.level.block.CropBlock;
public class Beaweed extends CropBlock {
    public Beaweed(Properties Prop) {
        super(Prop);
    }

    @Override
    public int getMaxAge(){
        return 5;
    }
}
