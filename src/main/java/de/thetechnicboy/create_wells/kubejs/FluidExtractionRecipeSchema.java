package de.thetechnicboy.create_wells.kubejs;

import de.thetechnicboy.create_wells.CreateWells;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.StringComponent;
import dev.latvian.mods.kubejs.recipe.schema.KubeRecipeFactory;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import dev.latvian.mods.kubejs.util.IntBounds;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public interface FluidExtractionRecipeSchema {

    RecipeKey<String> FLUID = StringComponent.ID
            .outputKey("fluid");
    RecipeKey<Integer> AMOUNT = NumberComponent.INT
            .outputKey("amount");

    RecipeKey<String> DIRECTION = StringComponent.OPTIONAL_STRING
            .otherKey("direction").optional("BOTH");
    RecipeKey<Integer> Y_MIN = NumberComponent.INT
            .otherKey("yMin").optional(-255);
    RecipeKey<Integer> Y_MAX = NumberComponent.INT
            .otherKey("yMax").optional(-255);
    RecipeKey<String> BLOCK = StringComponent.OPTIONAL_STRING
            .otherKey("block").optional("");
    RecipeKey<String> STATE = StringComponent.OPTIONAL_STRING
            .otherKey("state").optional("[]");
    RecipeKey<Integer> RPM = NumberComponent.INT
            .otherKey("rpm").optional(0);
    RecipeKey<List<String>> BIOME = StringComponent.ID
            .instance().asList().withBounds(IntBounds.OPTIONAL).otherKey("biome").optional(List.of());
    RecipeKey<List<String>> DIMENSION = StringComponent.ID
            .instance().asList().withBounds(IntBounds.OPTIONAL).otherKey("dimension").optional(List.of());

    RecipeSchema SCHEMA = new RecipeSchema(FLUID, AMOUNT, DIRECTION, Y_MIN, Y_MAX, BLOCK, STATE, RPM, BIOME, DIMENSION)
            .factory(new KubeRecipeFactory(
                    CreateWells.genRL("add_fluid_extraction"),
                    FluidExtractionKubeRecipe.class,
                    FluidExtractionKubeRecipe::new
            ))
            .typeOverride(CreateWells.genRL("fluid_extraction"))
            .constructor(FLUID, AMOUNT);
}