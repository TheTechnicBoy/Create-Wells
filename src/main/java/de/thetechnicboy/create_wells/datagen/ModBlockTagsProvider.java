package de.thetechnicboy.create_wells.datagen;

import de.thetechnicboy.create_wells.CreateWells;
import de.thetechnicboy.create_wells.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {

    public static final TagKey<Block> WELLS = BlockTags.create(CreateWells.genRL("wells"));

    public ModBlockTagsProvider(PackOutput output, CompletableFuture<Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, CreateWells.MODID, existingFileHelper);
    }
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(WELLS).add(ModBlocks.BLACK_MECHANICAL_WELL.get());
        tag(WELLS).add(ModBlocks.BLUE_MECHANICAL_WELL.get());
        tag(WELLS).add(ModBlocks.BROWN_MECHANICAL_WELL.get());
        tag(WELLS).add(ModBlocks.CYAN_MECHANICAL_WELL.get());
        tag(WELLS).add(ModBlocks.GRAY_MECHANICAL_WELL.get());
        tag(WELLS).add(ModBlocks.GREEN_MECHANICAL_WELL.get());
        tag(WELLS).add(ModBlocks.LIGHT_BLUE_MECHANICAL_WELL.get());
        tag(WELLS).add(ModBlocks.LIGHT_GRAY_MECHANICAL_WELL.get());
        tag(WELLS).add(ModBlocks.LIME_MECHANICAL_WELL.get());
        tag(WELLS).add(ModBlocks.MAGENTA_MECHANICAL_WELL.get());
        tag(WELLS).add(ModBlocks.ORANGE_MECHANICAL_WELL.get());
        tag(WELLS).add(ModBlocks.PINK_MECHANICAL_WELL.get());
        tag(WELLS).add(ModBlocks.PURPLE_MECHANICAL_WELL.get());
        tag(WELLS).add(ModBlocks.RED_MECHANICAL_WELL.get());
        tag(WELLS).add(ModBlocks.WHITE_MECHANICAL_WELL.get());
        tag(WELLS).add(ModBlocks.YELLOW_MECHANICAL_WELL.get());
        tag(BlockTags.MINEABLE_WITH_PICKAXE).addTag(WELLS);
    }
}
