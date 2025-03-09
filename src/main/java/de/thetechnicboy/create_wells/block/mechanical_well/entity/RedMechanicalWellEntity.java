package de.thetechnicboy.create_wells.block.mechanical_well.entity;

import de.thetechnicboy.create_wells.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class RedMechanicalWellEntity extends MechanicalWellEntity {
    public RedMechanicalWellEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.RED_MECHANICAL_WELL_BLOCKENTITY.get(), pos, state);
    }
}
