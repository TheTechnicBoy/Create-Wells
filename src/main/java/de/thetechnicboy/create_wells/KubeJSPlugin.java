package de.thetechnicboy.create_wells;

import de.thetechnicboy.create_wells.kubejs.FluidExtractionRecipeSchema;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchemaRegistry;

public class KubeJSPlugin implements dev.latvian.mods.kubejs.plugin.KubeJSPlugin {
    @Override
    public void registerRecipeSchemas(RecipeSchemaRegistry registry){
        registry.register(CreateWells.genRL("add_fluid_extraction"), FluidExtractionRecipeSchema.SCHEMA);
    }
}
