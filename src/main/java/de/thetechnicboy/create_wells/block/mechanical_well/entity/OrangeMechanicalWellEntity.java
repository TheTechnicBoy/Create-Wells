package de.thetechnicboy.create_wells.block.mechanical_well.entity;

import de.thetechnicboy.create_wells.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class OrangeMechanicalWellEntity extends MechanicalWellEntity {
    public OrangeMechanicalWellEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.ORANGE_MECHANICAL_WELL_BLOCKENTITY.get(), pos, state);
    }
}
