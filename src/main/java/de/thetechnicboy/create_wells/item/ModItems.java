package de.thetechnicboy.create_wells.item;

import de.thetechnicboy.create_wells.CreateWells;
import de.thetechnicboy.create_wells.block.ModBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, CreateWells.MODID);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }


    public static final RegistryObject<Item> BLACK_MECHANICAL_WELL = ITEMS.register("black_mechanical_well",
            () -> new BlockItem(ModBlocks.BLACK_MECHANICAL_WELL.get(), new Item.Properties()));
    public static final RegistryObject<Item> BLUE_MECHANICAL_WELL = ITEMS.register("blue_mechanical_well",
            () -> new BlockItem(ModBlocks.BLUE_MECHANICAL_WELL.get(), new Item.Properties()));
    public static final RegistryObject<Item> BROWN_MECHANICAL_WELL = ITEMS.register("brown_mechanical_well",
            () -> new BlockItem(ModBlocks.BROWN_MECHANICAL_WELL.get(), new Item.Properties()));
    public static final RegistryObject<Item> CYAN_MECHANICAL_WELL = ITEMS.register("cyan_mechanical_well",
            () -> new BlockItem(ModBlocks.CYAN_MECHANICAL_WELL.get(), new Item.Properties()));
    public static final RegistryObject<Item> GRAY_MECHANICAL_WELL = ITEMS.register("gray_mechanical_well",
            () -> new BlockItem(ModBlocks.GRAY_MECHANICAL_WELL.get(), new Item.Properties()));
    public static final RegistryObject<Item> GREEN_MECHANICAL_WELL = ITEMS.register("green_mechanical_well",
            () -> new BlockItem(ModBlocks.GREEN_MECHANICAL_WELL.get(), new Item.Properties()));
    public static final RegistryObject<Item> LIGHT_BLUE_MECHANICAL_WELL = ITEMS.register("light_blue_mechanical_well",
            () -> new BlockItem(ModBlocks.LIGHT_BLUE_MECHANICAL_WELL.get(), new Item.Properties()));
    public static final RegistryObject<Item> LIGHT_GRAY_MECHANICAL_WELL = ITEMS.register("light_gray_mechanical_well",
            () -> new BlockItem(ModBlocks.LIGHT_GRAY_MECHANICAL_WELL.get(), new Item.Properties()));
    public static final RegistryObject<Item> LIME_MECHANICAL_WELL = ITEMS.register("lime_mechanical_well",
            () -> new BlockItem(ModBlocks.LIME_MECHANICAL_WELL.get(), new Item.Properties()));
    public static final RegistryObject<Item> MAGENTA_MECHANICAL_WELL = ITEMS.register("magenta_mechanical_well",
            () -> new BlockItem(ModBlocks.MAGENTA_MECHANICAL_WELL.get(), new Item.Properties()));
    public static final RegistryObject<Item> ORANGE_MECHANICAL_WELL = ITEMS.register("orange_mechanical_well",
            () -> new BlockItem(ModBlocks.ORANGE_MECHANICAL_WELL.get(), new Item.Properties()));
    public static final RegistryObject<Item> PINK_MECHANICAL_WELL = ITEMS.register("pink_mechanical_well",
            () -> new BlockItem(ModBlocks.PINK_MECHANICAL_WELL.get(), new Item.Properties()));
    public static final RegistryObject<Item> PURPLE_MECHANICAL_WELL = ITEMS.register("purple_mechanical_well",
            () -> new BlockItem(ModBlocks.PURPLE_MECHANICAL_WELL.get(), new Item.Properties()));
    public static final RegistryObject<Item> RED_MECHANICAL_WELL = ITEMS.register("red_mechanical_well",
            () -> new BlockItem(ModBlocks.RED_MECHANICAL_WELL.get(), new Item.Properties()));
    public static final RegistryObject<Item> WHITE_MECHANICAL_WELL = ITEMS.register("white_mechanical_well",
            () -> new BlockItem(ModBlocks.WHITE_MECHANICAL_WELL.get(), new Item.Properties()));
    public static final RegistryObject<Item> YELLOW_MECHANICAL_WELL = ITEMS.register("yellow_mechanical_well",
            () -> new BlockItem(ModBlocks.YELLOW_MECHANICAL_WELL.get(), new Item.Properties()));

    public static final RegistryObject<Item> COW_CATALYST = ITEMS.register("cow_catalyst",
            () -> new BlockItem(ModBlocks.COW_CATALYST.get(), new Item.Properties()));
}
