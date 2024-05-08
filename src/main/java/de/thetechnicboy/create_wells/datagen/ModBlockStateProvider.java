package de.thetechnicboy.create_wells.datagen;

import de.thetechnicboy.create_wells.CreateWells;
import de.thetechnicboy.create_wells.block.MechanicalWellBlock;
import de.thetechnicboy.create_wells.block.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, CreateWells.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        ModelFile.UncheckedModelFile wellBase = new ModelFile.UncheckedModelFile(modLoc("block/mechanical_well_base"));
        ModelFile.UncheckedModelFile wellRoof = new ModelFile.UncheckedModelFile(modLoc("block/mechanical_well_roof"));
        ModelFile.UncheckedModelFile well = new ModelFile.UncheckedModelFile(modLoc("block/mechanical_well"));

        this.getVariantBuilder(ModBlocks.MECHANICAL_WELL.get()).forAllStates(state -> ConfiguredModel.builder()
                .modelFile(state.getValue(MechanicalWellBlock.HALF).equals(DoubleBlockHalf.LOWER) ? wellBase: wellRoof)
                .rotationX(state.getValue(MechanicalWellBlock.UPSIDE_DOWN) ? 180 : 0)
                .rotationY(state.getValue(MechanicalWellBlock.AXIS).equals(Direction.Axis.X) ? 0: 90)
                .build());
        this.simpleBlockItem(ModBlocks.MECHANICAL_WELL.get(), well);
    }

}
