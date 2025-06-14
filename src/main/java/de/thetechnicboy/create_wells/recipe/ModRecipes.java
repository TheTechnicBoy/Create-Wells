package de.thetechnicboy.create_wells.recipe;


import de.thetechnicboy.create_wells.CreateWells;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;


public class ModRecipes {

    private static final DeferredRegister<RecipeSerializer<?>> SERIALIZER_REGISTER = DeferredRegister.create(Registries.RECIPE_SERIALIZER, CreateWells.MODID);
    private static final DeferredRegister<RecipeType<?>> TYPE_REGISTER = DeferredRegister.create(Registries.RECIPE_TYPE, CreateWells.MODID);

    public static void register(IEventBus eventBus) {
        SERIALIZER_REGISTER.register(eventBus);
        TYPE_REGISTER.register(eventBus);
    }


    public static final RecipeType<FluidExtractionRecipe> FLUID_EXTRACTION_TYPE = new RecipeType<FluidExtractionRecipe>() {
        @Override
        public String toString(){
            return "fluid_extraction";
        }
    };

    public static final DeferredHolder<RecipeSerializer<?>, FluidExtractionRecipeSerializer> FLUID_EXTRACTION_RECIPE_SERIALIZER
            = SERIALIZER_REGISTER.register("fluid_extraction", () -> new FluidExtractionRecipeSerializer());
}
