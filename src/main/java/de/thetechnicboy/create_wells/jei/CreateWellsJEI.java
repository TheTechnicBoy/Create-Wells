package de.thetechnicboy.create_wells.jei;

import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import de.thetechnicboy.create_wells.CreateWells;
import de.thetechnicboy.create_wells.block.ModBlocks;
import de.thetechnicboy.create_wells.recipe.FluidExtractionRecipe;
import de.thetechnicboy.create_wells.recipe.ModRecipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IIngredientManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class CreateWellsJEI implements IModPlugin {

    private static final ResourceLocation ID = CreateWells.genRL("jei_plugin");
    public IIngredientManager ingredientManager;

    final List<CreateRecipeCategory<?>> ALL = new ArrayList<>();
    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        ALL.clear();

        ALL.add(builder(FluidExtractionRecipe.class)
                .addTypedRecipes(() -> ModRecipes.FLUID_EXTRACTION_TYPE)
                .catalyst(ModBlocks.BLACK_MECHANICAL_WELL::get)
                .catalyst(ModBlocks.BLUE_MECHANICAL_WELL::get)
                .catalyst(ModBlocks.BROWN_MECHANICAL_WELL::get)
                .catalyst(ModBlocks.CYAN_MECHANICAL_WELL::get)
                .catalyst(ModBlocks.GRAY_MECHANICAL_WELL::get)
                .catalyst(ModBlocks.GREEN_MECHANICAL_WELL::get)
                .catalyst(ModBlocks.LIGHT_BLUE_MECHANICAL_WELL::get)
                .catalyst(ModBlocks.LIGHT_GRAY_MECHANICAL_WELL::get)
                .catalyst(ModBlocks.LIME_MECHANICAL_WELL::get)
                .catalyst(ModBlocks.MAGENTA_MECHANICAL_WELL::get)
                .catalyst(ModBlocks.ORANGE_MECHANICAL_WELL::get)
                .catalyst(ModBlocks.PINK_MECHANICAL_WELL::get)
                .catalyst(ModBlocks.PURPLE_MECHANICAL_WELL::get)
                .catalyst(ModBlocks.RED_MECHANICAL_WELL::get)
                .catalyst(ModBlocks.WHITE_MECHANICAL_WELL::get)
                .catalyst(ModBlocks.YELLOW_MECHANICAL_WELL::get)
                .itemIcon(ModBlocks.RED_MECHANICAL_WELL.get())
                .emptyBackground(180, 80)
                .build("fluid_extraction", FluidExtractionCategory::new)
        );

        ALL.forEach(registration::addRecipeCategories);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        ingredientManager = registration.getIngredientManager();
        ALL.forEach(c -> c.registerRecipes(registration));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        ALL.forEach(c -> c.registerCatalysts(registration));
    }


    private <T extends Recipe<?>> CategoryBuilder<T> builder(Class<? extends T> recipeClass) {
        return new CategoryBuilder<>(recipeClass);
    }

}
