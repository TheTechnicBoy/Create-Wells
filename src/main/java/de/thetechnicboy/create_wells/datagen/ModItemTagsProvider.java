package de.thetechnicboy.create_wells.datagen;

import de.thetechnicboy.create_wells.CreateWells;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    public static final TagKey<Item> WELLS = ItemTags.create(CreateWells.genRL("wells"));

    public ModItemTagsProvider(PackOutput output, CompletableFuture<Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, CreateWells.MODID, existingFileHelper);
    }
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        copy(ModBlockTagsProvider.WELLS, WELLS);
    }
}
