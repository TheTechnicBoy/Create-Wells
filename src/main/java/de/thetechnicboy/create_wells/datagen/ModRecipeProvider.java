package de.thetechnicboy.create_wells.datagen;

import de.thetechnicboy.create_wells.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(PackOutput output){
        super(output);
    }
    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.MECHANICAL_WELL.get())
                .pattern("RRR")
                .pattern("/L/")
                .pattern("SBS")
                .define('R', Tags.Items.INGOTS_BRICK)
                .define('/', Tags.Items.RODS_WOODEN)
                .define('L', Items.LEAD)
                .define('B', Items.BUCKET)
                .define('S', Blocks.STONE_BRICKS)
                .unlockedBy("has_bucket", has(Items.BUCKET))
                .showNotification(false)
                .save(consumer);
    }
}
