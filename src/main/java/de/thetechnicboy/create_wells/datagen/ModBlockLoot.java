package de.thetechnicboy.create_wells.datagen;

import de.thetechnicboy.create_wells.block.mechanical_well.MechanicalWellBlock;
import de.thetechnicboy.create_wells.block.ModBlocks;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraftforge.registries.RegistryObject;
import java.util.Set;

public class ModBlockLoot extends BlockLootSubProvider {

    public ModBlockLoot() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.add(ModBlocks.BLACK_MECHANICAL_WELL.get(), (b) -> {
            return createSinglePropConditionTable(ModBlocks.BLACK_MECHANICAL_WELL.get(), MechanicalWellBlock.HALF, DoubleBlockHalf.LOWER);
        });
        this.add(ModBlocks.BLUE_MECHANICAL_WELL.get(), (b) -> {
            return createSinglePropConditionTable(ModBlocks.BLUE_MECHANICAL_WELL.get(), MechanicalWellBlock.HALF, DoubleBlockHalf.LOWER);
        });
        this.add(ModBlocks.BROWN_MECHANICAL_WELL.get(), (b) -> {
            return createSinglePropConditionTable(ModBlocks.BROWN_MECHANICAL_WELL.get(), MechanicalWellBlock.HALF, DoubleBlockHalf.LOWER);
        });
        this.add(ModBlocks.CYAN_MECHANICAL_WELL.get(), (b) -> {
            return createSinglePropConditionTable(ModBlocks.CYAN_MECHANICAL_WELL.get(), MechanicalWellBlock.HALF, DoubleBlockHalf.LOWER);
        });
        this.add(ModBlocks.GRAY_MECHANICAL_WELL.get(), (b) -> {
            return createSinglePropConditionTable(ModBlocks.GRAY_MECHANICAL_WELL.get(), MechanicalWellBlock.HALF, DoubleBlockHalf.LOWER);
        });
        this.add(ModBlocks.GREEN_MECHANICAL_WELL.get(), (b) -> {
            return createSinglePropConditionTable(ModBlocks.GREEN_MECHANICAL_WELL.get(), MechanicalWellBlock.HALF, DoubleBlockHalf.LOWER);
        });
        this.add(ModBlocks.LIGHT_BLUE_MECHANICAL_WELL.get(), (b) -> {
            return createSinglePropConditionTable(ModBlocks.LIGHT_BLUE_MECHANICAL_WELL.get(), MechanicalWellBlock.HALF, DoubleBlockHalf.LOWER);
        });
        this.add(ModBlocks.LIGHT_GRAY_MECHANICAL_WELL.get(), (b) -> {
            return createSinglePropConditionTable(ModBlocks.LIGHT_GRAY_MECHANICAL_WELL.get(), MechanicalWellBlock.HALF, DoubleBlockHalf.LOWER);
        });
        this.add(ModBlocks.LIME_MECHANICAL_WELL.get(), (b) -> {
            return createSinglePropConditionTable(ModBlocks.LIME_MECHANICAL_WELL.get(), MechanicalWellBlock.HALF, DoubleBlockHalf.LOWER);
        });
        this.add(ModBlocks.MAGENTA_MECHANICAL_WELL.get(), (b) -> {
            return createSinglePropConditionTable(ModBlocks.MAGENTA_MECHANICAL_WELL.get(), MechanicalWellBlock.HALF, DoubleBlockHalf.LOWER);
        });
        this.add(ModBlocks.ORANGE_MECHANICAL_WELL.get(), (b) -> {
            return createSinglePropConditionTable(ModBlocks.ORANGE_MECHANICAL_WELL.get(), MechanicalWellBlock.HALF, DoubleBlockHalf.LOWER);
        });
        this.add(ModBlocks.PINK_MECHANICAL_WELL.get(), (b) -> {
            return createSinglePropConditionTable(ModBlocks.PINK_MECHANICAL_WELL.get(), MechanicalWellBlock.HALF, DoubleBlockHalf.LOWER);
        });
        this.add(ModBlocks.PURPLE_MECHANICAL_WELL.get(), (b) -> {
            return createSinglePropConditionTable(ModBlocks.PURPLE_MECHANICAL_WELL.get(), MechanicalWellBlock.HALF, DoubleBlockHalf.LOWER);
        });
        this.add(ModBlocks.RED_MECHANICAL_WELL.get(), (b) -> {
            return createSinglePropConditionTable(ModBlocks.RED_MECHANICAL_WELL.get(), MechanicalWellBlock.HALF, DoubleBlockHalf.LOWER);
        });
        this.add(ModBlocks.WHITE_MECHANICAL_WELL.get(), (b) -> {
            return createSinglePropConditionTable(ModBlocks.WHITE_MECHANICAL_WELL.get(), MechanicalWellBlock.HALF, DoubleBlockHalf.LOWER);
        });
        this.add(ModBlocks.YELLOW_MECHANICAL_WELL.get(), (b) -> {
            return createSinglePropConditionTable(ModBlocks.YELLOW_MECHANICAL_WELL.get(), MechanicalWellBlock.HALF, DoubleBlockHalf.LOWER);
        });
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).toList();
    }
}
