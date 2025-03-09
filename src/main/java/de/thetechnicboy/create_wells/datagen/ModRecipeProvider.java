package de.thetechnicboy.create_wells.datagen;

import de.thetechnicboy.create_wells.CreateWells;
import de.thetechnicboy.create_wells.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;

import java.util.HashMap;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(PackOutput output){
        super(output);
    }
    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        HashMap<Item, Block> map = new HashMap<>();
        map.put(Items.BLACK_DYE, ModBlocks.BLACK_MECHANICAL_WELL.get());
        map.put(Items.BLUE_DYE, ModBlocks.BLUE_MECHANICAL_WELL.get());
        map.put(Items.BROWN_DYE, ModBlocks.BROWN_MECHANICAL_WELL.get());
        map.put(Items.CYAN_DYE, ModBlocks.CYAN_MECHANICAL_WELL.get());
        map.put(Items.GRAY_DYE, ModBlocks.GRAY_MECHANICAL_WELL.get());
        map.put(Items.GREEN_DYE, ModBlocks.GREEN_MECHANICAL_WELL.get());
        map.put(Items.LIGHT_BLUE_DYE, ModBlocks.LIGHT_BLUE_MECHANICAL_WELL.get());
        map.put(Items.LIGHT_GRAY_DYE, ModBlocks.LIGHT_GRAY_MECHANICAL_WELL.get());
        map.put(Items.LIME_DYE, ModBlocks.LIME_MECHANICAL_WELL.get());
        map.put(Items.MAGENTA_DYE, ModBlocks.MAGENTA_MECHANICAL_WELL.get());
        map.put(Items.ORANGE_DYE, ModBlocks.ORANGE_MECHANICAL_WELL.get());
        map.put(Items.PINK_DYE, ModBlocks.PINK_MECHANICAL_WELL.get());
        map.put(Items.PURPLE_DYE, ModBlocks.PURPLE_MECHANICAL_WELL.get());
        map.put(Items.RED_DYE, ModBlocks.RED_MECHANICAL_WELL.get());
        map.put(Items.WHITE_DYE, ModBlocks.WHITE_MECHANICAL_WELL.get());
        map.put(Items.YELLOW_DYE, ModBlocks.YELLOW_MECHANICAL_WELL.get());

        map.forEach((dye, well) -> {
            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, well)
                    .pattern("RDR")
                    .pattern("/L/")
                    .pattern("SBS")
                    .define('R', Tags.Items.INGOTS_BRICK)
                    .define('/', com.simibubi.create.AllBlocks.SHAFT.get())
                    .define('L', Items.LEAD)
                    .define('B', Items.BUCKET)
                    .define('S', Blocks.STONE_BRICKS)
                    .define('D', dye)
                    .unlockedBy("has_bucket", has(Items.BUCKET))
                    .showNotification(false)
                    .save(consumer);
        });

        map.forEach((dye, well) -> {
            ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, well)
                    .requires(ModItemTagsProvider.WELLS)
                    .requires(dye)
                    .unlockedBy("has_bucket", has(Items.BUCKET))
                    .save(consumer, CreateWells.genRL("dye_"+ dye.toString().replace("_dye", "") + "_mechanical_well").toString());
        });



    }
}
