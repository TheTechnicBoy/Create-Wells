package de.thetechnicboy.create_wells.jei;

import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import de.thetechnicboy.create_wells.CreateWells;
import de.thetechnicboy.create_wells.block.ModBlocks;
import de.thetechnicboy.create_wells.recipe.FluidExtractionRecipe;
import de.thetechnicboy.create_wells.recipe.AllRecipeTypes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IIngredientManager;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class CreateWellsJEI implements IModPlugin {

    private static final ResourceLocation ID = CreateWells.genRL("jei_plugin");


    private final List<CreateRecipeCategory<?>> allCategories = new ArrayList<>();
    public IIngredientManager ingredientManager;

    public static IJeiRuntime runtime;

    public void loadCategories(){
        allCategories.clear();

        CreateRecipeCategory<?> fluid_extraction = new CreateRecipeCategory.Builder(FluidExtractionRecipe.class)
                .addTypedRecipes(() -> AllRecipeTypes.FLUID_EXTRACTION_TYPE.get())
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
                .build(CreateWells.genRL("fluid_extraction"), FluidExtractionCategory::new);
        allCategories.add(fluid_extraction);
    }

    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        loadCategories();
        registration.addRecipeCategories(allCategories.toArray(IRecipeCategory[]::new));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        ingredientManager = registration.getIngredientManager();
        allCategories.forEach(c -> c.registerRecipes(registration));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        allCategories.forEach(c -> c.registerCatalysts(registration));
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime runtime) {
        CreateWellsJEI.runtime = runtime;
    }
}
