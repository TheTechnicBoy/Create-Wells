package de.thetechnicboy.create_wells.datagen;

import de.thetechnicboy.create_wells.CreateWells;
import de.thetechnicboy.create_wells.block.mechanical_well.MechanicalWellBlock;
import de.thetechnicboy.create_wells.block.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.HashMap;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, CreateWells.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        ModelFile.UncheckedModelFile WellBase = new ModelFile.UncheckedModelFile(modLoc("block/mechanical_well_base"));

        ModelFile.UncheckedModelFile blackWellRoof = new ModelFile.UncheckedModelFile(modLoc("block/black_mechanical_well_roof"));
        ModelFile.UncheckedModelFile blueWellRoof = new ModelFile.UncheckedModelFile(modLoc("block/blue_mechanical_well_roof"));
        ModelFile.UncheckedModelFile brownWellRoof = new ModelFile.UncheckedModelFile(modLoc("block/brown_mechanical_well_roof"));
        ModelFile.UncheckedModelFile cyanWellRoof = new ModelFile.UncheckedModelFile(modLoc("block/cyan_mechanical_well_roof"));
        ModelFile.UncheckedModelFile grayWellRoof = new ModelFile.UncheckedModelFile(modLoc("block/gray_mechanical_well_roof"));
        ModelFile.UncheckedModelFile greenWellRoof = new ModelFile.UncheckedModelFile(modLoc("block/green_mechanical_well_roof"));
        ModelFile.UncheckedModelFile lightBlueWellRoof = new ModelFile.UncheckedModelFile(modLoc("block/light_blue_mechanical_well_roof"));
        ModelFile.UncheckedModelFile lightGrayWellRoof = new ModelFile.UncheckedModelFile(modLoc("block/light_gray_mechanical_well_roof"));
        ModelFile.UncheckedModelFile limeWellRoof = new ModelFile.UncheckedModelFile(modLoc("block/lime_mechanical_well_roof"));
        ModelFile.UncheckedModelFile magentaWellRoof = new ModelFile.UncheckedModelFile(modLoc("block/magenta_mechanical_well_roof"));
        ModelFile.UncheckedModelFile orangeWellRoof = new ModelFile.UncheckedModelFile(modLoc("block/orange_mechanical_well_roof"));
        ModelFile.UncheckedModelFile pinkWellRoof = new ModelFile.UncheckedModelFile(modLoc("block/pink_mechanical_well_roof"));
        ModelFile.UncheckedModelFile purpleWellRoof = new ModelFile.UncheckedModelFile(modLoc("block/purple_mechanical_well_roof"));
        ModelFile.UncheckedModelFile redWellRoof = new ModelFile.UncheckedModelFile(modLoc("block/red_mechanical_well_roof"));
        ModelFile.UncheckedModelFile whiteWellRoof = new ModelFile.UncheckedModelFile(modLoc("block/white_mechanical_well_roof"));
        ModelFile.UncheckedModelFile yellowWellRoof = new ModelFile.UncheckedModelFile(modLoc("block/yellow_mechanical_well_roof"));

        ModelFile.UncheckedModelFile cowCatalyst = new ModelFile.UncheckedModelFile(modLoc("block/cow_catalyst"));
        this.simpleBlockWithItem(ModBlocks.COW_CATALYST.get(), cowCatalyst);

        this.getVariantBuilder(ModBlocks.BLACK_MECHANICAL_WELL.get()).forAllStates(state -> ConfiguredModel.builder()
                .modelFile(state.getValue(MechanicalWellBlock.HALF).equals(DoubleBlockHalf.LOWER) ? WellBase: blackWellRoof)
                .rotationX(state.getValue(MechanicalWellBlock.UPSIDE_DOWN) ? 180 : 0)
                .rotationY(state.getValue(MechanicalWellBlock.AXIS).equals(Direction.Axis.X) ? 0: 90)
                .build());
        this.getVariantBuilder(ModBlocks.BLUE_MECHANICAL_WELL.get()).forAllStates(state -> ConfiguredModel.builder()
                .modelFile(state.getValue(MechanicalWellBlock.HALF).equals(DoubleBlockHalf.LOWER) ? WellBase: blueWellRoof)
                .rotationX(state.getValue(MechanicalWellBlock.UPSIDE_DOWN) ? 180 : 0)
                .rotationY(state.getValue(MechanicalWellBlock.AXIS).equals(Direction.Axis.X) ? 0: 90)
                .build());
        this.getVariantBuilder(ModBlocks.BROWN_MECHANICAL_WELL.get()).forAllStates(state -> ConfiguredModel.builder()
                .modelFile(state.getValue(MechanicalWellBlock.HALF).equals(DoubleBlockHalf.LOWER) ? WellBase: brownWellRoof)
                .rotationX(state.getValue(MechanicalWellBlock.UPSIDE_DOWN) ? 180 : 0)
                .rotationY(state.getValue(MechanicalWellBlock.AXIS).equals(Direction.Axis.X) ? 0: 90)
                .build());
        this.getVariantBuilder(ModBlocks.CYAN_MECHANICAL_WELL.get()).forAllStates(state -> ConfiguredModel.builder()
                .modelFile(state.getValue(MechanicalWellBlock.HALF).equals(DoubleBlockHalf.LOWER) ? WellBase: cyanWellRoof)
                .rotationX(state.getValue(MechanicalWellBlock.UPSIDE_DOWN) ? 180 : 0)
                .rotationY(state.getValue(MechanicalWellBlock.AXIS).equals(Direction.Axis.X) ? 0: 90)
                .build());
        this.getVariantBuilder(ModBlocks.GRAY_MECHANICAL_WELL.get()).forAllStates(state -> ConfiguredModel.builder()
                .modelFile(state.getValue(MechanicalWellBlock.HALF).equals(DoubleBlockHalf.LOWER) ? WellBase: grayWellRoof)
                .rotationX(state.getValue(MechanicalWellBlock.UPSIDE_DOWN) ? 180 : 0)
                .rotationY(state.getValue(MechanicalWellBlock.AXIS).equals(Direction.Axis.X) ? 0: 90)
                .build());
        this.getVariantBuilder(ModBlocks.GREEN_MECHANICAL_WELL.get()).forAllStates(state -> ConfiguredModel.builder()
                .modelFile(state.getValue(MechanicalWellBlock.HALF).equals(DoubleBlockHalf.LOWER) ? WellBase: greenWellRoof)
                .rotationX(state.getValue(MechanicalWellBlock.UPSIDE_DOWN) ? 180 : 0)
                .rotationY(state.getValue(MechanicalWellBlock.AXIS).equals(Direction.Axis.X) ? 0: 90)
                .build());
        this.getVariantBuilder(ModBlocks.LIGHT_BLUE_MECHANICAL_WELL.get()).forAllStates(state -> ConfiguredModel.builder()
                .modelFile(state.getValue(MechanicalWellBlock.HALF).equals(DoubleBlockHalf.LOWER) ? WellBase: lightBlueWellRoof)
                .rotationX(state.getValue(MechanicalWellBlock.UPSIDE_DOWN) ? 180 : 0)
                .rotationY(state.getValue(MechanicalWellBlock.AXIS).equals(Direction.Axis.X) ? 0: 90)
                .build());
        this.getVariantBuilder(ModBlocks.LIGHT_GRAY_MECHANICAL_WELL.get()).forAllStates(state -> ConfiguredModel.builder()
                .modelFile(state.getValue(MechanicalWellBlock.HALF).equals(DoubleBlockHalf.LOWER) ? WellBase: lightGrayWellRoof)
                .rotationX(state.getValue(MechanicalWellBlock.UPSIDE_DOWN) ? 180 : 0)
                .rotationY(state.getValue(MechanicalWellBlock.AXIS).equals(Direction.Axis.X) ? 0: 90)
                .build());
        this.getVariantBuilder(ModBlocks.LIME_MECHANICAL_WELL.get()).forAllStates(state -> ConfiguredModel.builder()
                .modelFile(state.getValue(MechanicalWellBlock.HALF).equals(DoubleBlockHalf.LOWER) ? WellBase: limeWellRoof)
                .rotationX(state.getValue(MechanicalWellBlock.UPSIDE_DOWN) ? 180 : 0)
                .rotationY(state.getValue(MechanicalWellBlock.AXIS).equals(Direction.Axis.X) ? 0: 90)
                .build());
        this.getVariantBuilder(ModBlocks.MAGENTA_MECHANICAL_WELL.get()).forAllStates(state -> ConfiguredModel.builder()
                .modelFile(state.getValue(MechanicalWellBlock.HALF).equals(DoubleBlockHalf.LOWER) ? WellBase: magentaWellRoof)
                .rotationX(state.getValue(MechanicalWellBlock.UPSIDE_DOWN) ? 180 : 0)
                .rotationY(state.getValue(MechanicalWellBlock.AXIS).equals(Direction.Axis.X) ? 0: 90)
                .build());
        this.getVariantBuilder(ModBlocks.ORANGE_MECHANICAL_WELL.get()).forAllStates(state -> ConfiguredModel.builder()
                .modelFile(state.getValue(MechanicalWellBlock.HALF).equals(DoubleBlockHalf.LOWER) ? WellBase: orangeWellRoof)
                .rotationX(state.getValue(MechanicalWellBlock.UPSIDE_DOWN) ? 180 : 0)
                .rotationY(state.getValue(MechanicalWellBlock.AXIS).equals(Direction.Axis.X) ? 0: 90)
                .build());
        this.getVariantBuilder(ModBlocks.PINK_MECHANICAL_WELL.get()).forAllStates(state -> ConfiguredModel.builder()
                .modelFile(state.getValue(MechanicalWellBlock.HALF).equals(DoubleBlockHalf.LOWER) ? WellBase: pinkWellRoof)
                .rotationX(state.getValue(MechanicalWellBlock.UPSIDE_DOWN) ? 180 : 0)
                .rotationY(state.getValue(MechanicalWellBlock.AXIS).equals(Direction.Axis.X) ? 0: 90)
                .build());
        this.getVariantBuilder(ModBlocks.PURPLE_MECHANICAL_WELL.get()).forAllStates(state -> ConfiguredModel.builder()
                .modelFile(state.getValue(MechanicalWellBlock.HALF).equals(DoubleBlockHalf.LOWER) ? WellBase: purpleWellRoof)
                .rotationX(state.getValue(MechanicalWellBlock.UPSIDE_DOWN) ? 180 : 0)
                .rotationY(state.getValue(MechanicalWellBlock.AXIS).equals(Direction.Axis.X) ? 0: 90)
                .build());
        this.getVariantBuilder(ModBlocks.RED_MECHANICAL_WELL.get()).forAllStates(state -> ConfiguredModel.builder()
                .modelFile(state.getValue(MechanicalWellBlock.HALF).equals(DoubleBlockHalf.LOWER) ? WellBase: redWellRoof)
                .rotationX(state.getValue(MechanicalWellBlock.UPSIDE_DOWN) ? 180 : 0)
                .rotationY(state.getValue(MechanicalWellBlock.AXIS).equals(Direction.Axis.X) ? 0: 90)
                .build());
        this.getVariantBuilder(ModBlocks.WHITE_MECHANICAL_WELL.get()).forAllStates(state -> ConfiguredModel.builder()
                .modelFile(state.getValue(MechanicalWellBlock.HALF).equals(DoubleBlockHalf.LOWER) ? WellBase: whiteWellRoof)
                .rotationX(state.getValue(MechanicalWellBlock.UPSIDE_DOWN) ? 180 : 0)
                .rotationY(state.getValue(MechanicalWellBlock.AXIS).equals(Direction.Axis.X) ? 0: 90)
                .build());
        this.getVariantBuilder(ModBlocks.YELLOW_MECHANICAL_WELL.get()).forAllStates(state -> ConfiguredModel.builder()
                .modelFile(state.getValue(MechanicalWellBlock.HALF).equals(DoubleBlockHalf.LOWER) ? WellBase: yellowWellRoof)
                .rotationX(state.getValue(MechanicalWellBlock.UPSIDE_DOWN) ? 180 : 0)
                .rotationY(state.getValue(MechanicalWellBlock.AXIS).equals(Direction.Axis.X) ? 0: 90)
                .build());
    }

}
