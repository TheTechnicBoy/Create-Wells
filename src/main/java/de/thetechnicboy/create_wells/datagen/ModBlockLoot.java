package de.thetechnicboy.create_wells.datagen;

import de.thetechnicboy.create_wells.block.MechanicalWellBlock;
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
        this.add(ModBlocks.MECHANICAL_WELL.get(), (b) -> {
            return createSinglePropConditionTable(ModBlocks.MECHANICAL_WELL.get(), MechanicalWellBlock.HALF, DoubleBlockHalf.LOWER);
        });
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).toList();
    }
}
