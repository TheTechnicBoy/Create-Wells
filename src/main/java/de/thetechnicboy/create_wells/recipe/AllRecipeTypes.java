package de.thetechnicboy.create_wells.recipe;


import com.mojang.serialization.Codec;
import de.thetechnicboy.create_wells.CreateWells;
import net.createmod.catnip.lang.Lang;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.crafting.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;


public class AllRecipeTypes   {

    private static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER  = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, CreateWells.MODID);
    private static final DeferredRegister<RecipeType<?>> RECIPE_TYPE  = DeferredRegister.create(Registries.RECIPE_TYPE, CreateWells.MODID);

    public static DeferredHolder<RecipeSerializer<?>, FluidExtractionRecipeSerializer> FLUID_EXTRACTION_SERIALIZER
            = RECIPE_SERIALIZER.register("fluid_extraction", FluidExtractionRecipeSerializer::new);
    public static DeferredHolder<RecipeType<?>, RecipeType<FluidExtractionRecipe>> FLUID_EXTRACTION_TYPE
            = RECIPE_TYPE.register("fluid_extraction", () -> RecipeType.simple(CreateWells.genRL("fluid_extraction")));



    public static void register(IEventBus bus) {
        RECIPE_SERIALIZER.register(bus);
        RECIPE_TYPE.register(bus);
    }


}
