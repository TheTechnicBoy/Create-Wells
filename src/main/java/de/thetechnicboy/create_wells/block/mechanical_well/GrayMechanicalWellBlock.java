package de.thetechnicboy.create_wells.block.mechanical_well;


import de.thetechnicboy.create_wells.block.ModBlocks;
import de.thetechnicboy.create_wells.block.mechanical_well.entity.GrayMechanicalWellEntity;
import de.thetechnicboy.create_wells.block.mechanical_well.entity.MechanicalWellEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

public class GrayMechanicalWellBlock extends MechanicalWellBlock {


    @Override
    public Class<MechanicalWellEntity> getBlockEntityClass() {
        return MechanicalWellEntity.class;
    }

    @Override
    public BlockEntityType<? extends MechanicalWellEntity> getBlockEntityType() {
        return ModBlocks.GRAY_MECHANICAL_WELL_BLOCKENTITY.get();

    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER ? new GrayMechanicalWellEntity(pos, state) : null;
    }

}


