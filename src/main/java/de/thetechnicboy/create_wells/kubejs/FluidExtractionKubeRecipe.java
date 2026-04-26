package de.thetechnicboy.create_wells.kubejs;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.recipe.KubeRecipe;

import java.util.List;

public class FluidExtractionKubeRecipe extends KubeRecipe {

    public FluidExtractionKubeRecipe direction(String direction) {
        setValue(FluidExtractionRecipeSchema.DIRECTION, direction);
        return this;
    }

    public FluidExtractionKubeRecipe yMin(int yMin) {
        setValue(FluidExtractionRecipeSchema.Y_MIN, yMin);
        return this;
    }

    public FluidExtractionKubeRecipe yMax(int yMax) {
        setValue(FluidExtractionRecipeSchema.Y_MAX, yMax);
        return this;
    }

    public FluidExtractionKubeRecipe y(int yMin, int yMax) {
        setValue(FluidExtractionRecipeSchema.Y_MIN, yMin);
        setValue(FluidExtractionRecipeSchema.Y_MAX, yMax);
        return this;
    }

    public FluidExtractionKubeRecipe block(String block) {
        setValue(FluidExtractionRecipeSchema.BLOCK, block);
        return this;
    }

    public FluidExtractionKubeRecipe block(String block, String state) {
        setValue(FluidExtractionRecipeSchema.BLOCK, block);
        setValue(FluidExtractionRecipeSchema.STATE, state);
        return this;
    }

    public FluidExtractionKubeRecipe state(String state) {
        setValue(FluidExtractionRecipeSchema.STATE, state);
        return this;
    }

    public FluidExtractionKubeRecipe rpm(int rpm) {
        setValue(FluidExtractionRecipeSchema.RPM, rpm);
        return this;
    }

    public FluidExtractionKubeRecipe biome(String... biomes) {
        setValue(FluidExtractionRecipeSchema.BIOME, List.of(biomes));
        return this;
    }

    public FluidExtractionKubeRecipe dimension(String... dimensions) {
        setValue(FluidExtractionRecipeSchema.DIMENSION, List.of(dimensions));
        return this;
    }

    @Override
    public void serialize() {
        JsonObject condition = new JsonObject();
        condition.addProperty("direction", getValue(FluidExtractionRecipeSchema.DIRECTION));
        condition.addProperty("yMin", getValue(FluidExtractionRecipeSchema.Y_MIN));
        condition.addProperty("yMax", getValue(FluidExtractionRecipeSchema.Y_MAX));
        condition.addProperty("block", getValue(FluidExtractionRecipeSchema.BLOCK));
        condition.addProperty("state", getValue(FluidExtractionRecipeSchema.STATE));
        condition.addProperty("rpm", getValue(FluidExtractionRecipeSchema.RPM));

        JsonArray biomes = new JsonArray();
        for (String b : getValue(FluidExtractionRecipeSchema.BIOME)) biomes.add(b);
        condition.add("biome", biomes);

        JsonArray dimensions = new JsonArray();
        for (String d : getValue(FluidExtractionRecipeSchema.DIMENSION)) dimensions.add(d);
        condition.add("dimension", dimensions);

        json.add("condition", condition);

        JsonObject output = new JsonObject();
        output.addProperty("fluid", getValue(FluidExtractionRecipeSchema.FLUID));
        output.addProperty("amount", getValue(FluidExtractionRecipeSchema.AMOUNT));

        json.add("output", output);
    }

    @Override
    public KubeRecipe serializeChanges() {
        super.serializeChanges();
        json.addProperty("type", "create_wells:fluid_extraction");
        return this;
    }
}
