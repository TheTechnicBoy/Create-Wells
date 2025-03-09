package de.thetechnicboy.create_wells.block;

import de.thetechnicboy.create_wells.CreateWells;
import de.thetechnicboy.create_wells.block.mechanical_well.*;
import de.thetechnicboy.create_wells.block.mechanical_well.entity.*;

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

    public static final RegistryObject<Block> BLACK_MECHANICAL_WELL = BLOCKS.register("black_mechanical_well",
            () -> new BlackMechanicalWellBlock());
    public static final RegistryObject<Block> BLUE_MECHANICAL_WELL = BLOCKS.register("blue_mechanical_well",
            () -> new BlueMechanicalWellBlock());
    public static final RegistryObject<Block> BROWN_MECHANICAL_WELL = BLOCKS.register("brown_mechanical_well",
            () -> new BrownMechanicalWellBlock());
    public static final RegistryObject<Block> CYAN_MECHANICAL_WELL = BLOCKS.register("cyan_mechanical_well",
            () -> new CyanMechanicalWellBlock());
    public static final RegistryObject<Block> GRAY_MECHANICAL_WELL = BLOCKS.register("gray_mechanical_well",
            () -> new GrayMechanicalWellBlock());
    public static final RegistryObject<Block> GREEN_MECHANICAL_WELL = BLOCKS.register("green_mechanical_well",
            () -> new GreenMechanicalWellBlock());
    public static final RegistryObject<Block> LIGHT_BLUE_MECHANICAL_WELL = BLOCKS.register("light_blue_mechanical_well",
            () -> new LightBlueMechanicalWellBlock());
    public static final RegistryObject<Block> LIGHT_GRAY_MECHANICAL_WELL = BLOCKS.register("light_gray_mechanical_well",
            () -> new LightGrayMechanicalWellBlock());
    public static final RegistryObject<Block> LIME_MECHANICAL_WELL = BLOCKS.register("lime_mechanical_well",
            () -> new LimeMechanicalWellBlock());
    public static final RegistryObject<Block> MAGENTA_MECHANICAL_WELL = BLOCKS.register("magenta_mechanical_well",
            () -> new MagentaMechanicalWellBlock());
    public static final RegistryObject<Block> ORANGE_MECHANICAL_WELL = BLOCKS.register("orange_mechanical_well",
            () -> new OrangeMechanicalWellBlock());
    public static final RegistryObject<Block> PINK_MECHANICAL_WELL = BLOCKS.register("pink_mechanical_well",
            () -> new PinkMechanicalWellBlock());
    public static final RegistryObject<Block> PURPLE_MECHANICAL_WELL = BLOCKS.register("purple_mechanical_well",
            () -> new PurpleMechanicalWellBlock());
    public static final RegistryObject<Block> RED_MECHANICAL_WELL = BLOCKS.register("red_mechanical_well",
            () -> new RedMechanicalWellBlock());
    public static final RegistryObject<Block> WHITE_MECHANICAL_WELL = BLOCKS.register("white_mechanical_well",
            () -> new WhiteMechanicalWellBlock());
    public static final RegistryObject<Block> YELLOW_MECHANICAL_WELL = BLOCKS.register("yellow_mechanical_well",
            () -> new YellowMechanicalWellBlock());


    public static final RegistryObject<BlockEntityType<BlackMechanicalWellEntity>> BLACK_MECHANICAL_WELL_BLOCKENTITY = BLOCK_ENTITIES.register("black_mechanical_well",
            () -> BlockEntityType.Builder.of(BlackMechanicalWellEntity::new, ModBlocks.BLACK_MECHANICAL_WELL.get()).build(null));
    public static final RegistryObject<BlockEntityType<BlueMechanicalWellEntity>> BLUE_MECHANICAL_WELL_BLOCKENTITY = BLOCK_ENTITIES.register("blue_mechanical_well",
            () -> BlockEntityType.Builder.of(BlueMechanicalWellEntity::new, ModBlocks.BLUE_MECHANICAL_WELL.get()).build(null));
    public static final RegistryObject<BlockEntityType<BrownMechanicalWellEntity>> BROWN_MECHANICAL_WELL_BLOCKENTITY = BLOCK_ENTITIES.register("brown_mechanical_well",
            () -> BlockEntityType.Builder.of(BrownMechanicalWellEntity::new, ModBlocks.BROWN_MECHANICAL_WELL.get()).build(null));
    public static final RegistryObject<BlockEntityType<CyanMechanicalWellEntity>> CYAN_MECHANICAL_WELL_BLOCKENTITY = BLOCK_ENTITIES.register("cyan_mechanical_well",
            () -> BlockEntityType.Builder.of(CyanMechanicalWellEntity::new, ModBlocks.CYAN_MECHANICAL_WELL.get()).build(null));
    public static final RegistryObject<BlockEntityType<GrayMechanicalWellEntity>> GRAY_MECHANICAL_WELL_BLOCKENTITY = BLOCK_ENTITIES.register("gray_mechanical_well",
            () -> BlockEntityType.Builder.of(GrayMechanicalWellEntity::new, ModBlocks.GRAY_MECHANICAL_WELL.get()).build(null));
    public static final RegistryObject<BlockEntityType<GreenMechanicalWellEntity>> GREEN_MECHANICAL_WELL_BLOCKENTITY = BLOCK_ENTITIES.register("green_mechanical_well",
            () -> BlockEntityType.Builder.of(GreenMechanicalWellEntity::new, ModBlocks.GREEN_MECHANICAL_WELL.get()).build(null));
    public static final RegistryObject<BlockEntityType<LightBlueMechanicalWellEntity>> LIGHT_BLUE_MECHANICAL_WELL_BLOCKENTITY = BLOCK_ENTITIES.register("light_blue_mechanical_well",
            () -> BlockEntityType.Builder.of(LightBlueMechanicalWellEntity::new, ModBlocks.LIGHT_BLUE_MECHANICAL_WELL.get()).build(null));
    public static final RegistryObject<BlockEntityType<LightGrayMechanicalWellEntity>> LIGHT_GRAY_MECHANICAL_WELL_BLOCKENTITY = BLOCK_ENTITIES.register("light_gray_mechanical_well",
            () -> BlockEntityType.Builder.of(LightGrayMechanicalWellEntity::new, ModBlocks.LIGHT_GRAY_MECHANICAL_WELL.get()).build(null));
    public static final RegistryObject<BlockEntityType<LimeMechanicalWellEntity>> LIME_MECHANICAL_WELL_BLOCKENTITY = BLOCK_ENTITIES.register("lime_mechanical_well",
            () -> BlockEntityType.Builder.of(LimeMechanicalWellEntity::new, ModBlocks.LIME_MECHANICAL_WELL.get()).build(null));
    public static final RegistryObject<BlockEntityType<MagentaMechanicalWellEntity>> MAGENTA_MECHANICAL_WELL_BLOCKENTITY = BLOCK_ENTITIES.register("magenta_mechanical_well",
            () -> BlockEntityType.Builder.of(MagentaMechanicalWellEntity::new, ModBlocks.MAGENTA_MECHANICAL_WELL.get()).build(null));
    public static final RegistryObject<BlockEntityType<OrangeMechanicalWellEntity>> ORANGE_MECHANICAL_WELL_BLOCKENTITY = BLOCK_ENTITIES.register("orange_mechanical_well",
            () -> BlockEntityType.Builder.of(OrangeMechanicalWellEntity::new, ModBlocks.ORANGE_MECHANICAL_WELL.get()).build(null));
    public static final RegistryObject<BlockEntityType<PinkMechanicalWellEntity>> PINK_MECHANICAL_WELL_BLOCKENTITY = BLOCK_ENTITIES.register("pink_mechanical_well",
            () -> BlockEntityType.Builder.of(PinkMechanicalWellEntity::new, ModBlocks.PINK_MECHANICAL_WELL.get()).build(null));
    public static final RegistryObject<BlockEntityType<PurpleMechanicalWellEntity>> PURPLE_MECHANICAL_WELL_BLOCKENTITY = BLOCK_ENTITIES.register("purple_mechanical_well",
            () -> BlockEntityType.Builder.of(PurpleMechanicalWellEntity::new, ModBlocks.PURPLE_MECHANICAL_WELL.get()).build(null));
    public static final RegistryObject<BlockEntityType<RedMechanicalWellEntity>> RED_MECHANICAL_WELL_BLOCKENTITY = BLOCK_ENTITIES.register("red_mechanical_well",
            () -> BlockEntityType.Builder.of(RedMechanicalWellEntity::new, ModBlocks.RED_MECHANICAL_WELL.get()).build(null));
    public static final RegistryObject<BlockEntityType<WhiteMechanicalWellEntity>> WHITE_MECHANICAL_WELL_BLOCKENTITY = BLOCK_ENTITIES.register("white_mechanical_well",
            () -> BlockEntityType.Builder.of(WhiteMechanicalWellEntity::new, ModBlocks.WHITE_MECHANICAL_WELL.get()).build(null));
    public static final RegistryObject<BlockEntityType<YellowMechanicalWellEntity>> YELLOW_MECHANICAL_WELL_BLOCKENTITY = BLOCK_ENTITIES.register("yellow_mechanical_well",
            () -> BlockEntityType.Builder.of(YellowMechanicalWellEntity::new, ModBlocks.YELLOW_MECHANICAL_WELL.get()).build(null));



}

