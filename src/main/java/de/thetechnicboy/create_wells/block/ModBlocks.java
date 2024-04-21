package de.thetechnicboy.create_wells.block;

import de.thetechnicboy.create_wells.CreateWells;
import de.thetechnicboy.create_wells.block.entity.MechanicalWellBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, CreateWells.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, CreateWells.MODID);

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        BLOCK_ENTITIES.register(eventBus);
    }

    public static final RegistryObject<Block> MECHANICAL_WELL = BLOCKS.register("mechanical_well",
            () -> new MechanicalWellBlock());

    public static final RegistryObject<BlockEntityType<MechanicalWellBlockEntity>> MECHANICAL_WELL_BLOCKENTITY = BLOCK_ENTITIES.register("mechanical_well",
            () -> BlockEntityType.Builder.of(MechanicalWellBlockEntity::new, ModBlocks.MECHANICAL_WELL.get()).build(null));
}

