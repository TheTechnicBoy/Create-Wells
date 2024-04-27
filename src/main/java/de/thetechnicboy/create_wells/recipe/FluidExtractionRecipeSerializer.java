package de.thetechnicboy.create_wells.recipe;

import com.google.gson.JsonObject;
import de.thetechnicboy.create_wells.CreateWells;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Nullable;

public class FluidExtractionRecipeSerializer implements RecipeSerializer<FluidExtractionRecipe> {
    @Override
    public FluidExtractionRecipe fromJson(ResourceLocation resourceLocation, JsonObject jsonObject) {
        FluidExtractionRecipe.FluidOutput output = FluidExtractionRecipe.FluidOutput.fromJSON(jsonObject.getAsJsonObject("output"));
        FluidExtractionRecipe.Condition condition = FluidExtractionRecipe.Condition.fromJSON(jsonObject.getAsJsonObject("condition"));
        CreateWells.LOGGER.info("FluidExtractionRecipe -  FromJson");
        return FluidExtractionRecipe.registerRecipe(resourceLocation, output, condition);
    }

    @Override
    public @Nullable FluidExtractionRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf) {
        FluidExtractionRecipe.FluidOutput output = FluidExtractionRecipe.FluidOutput.fromPacket(friendlyByteBuf);
        FluidExtractionRecipe.Condition condition = FluidExtractionRecipe.Condition.fromPacket(friendlyByteBuf);
        CreateWells.LOGGER.info("FluidExtractionRecipe - FromNetwork");
        return FluidExtractionRecipe.registerRecipe(resourceLocation, output, condition);
    }

    @Override
    public void toNetwork(FriendlyByteBuf friendlyByteBuf, FluidExtractionRecipe fluidExtractionRecipe) {
        fluidExtractionRecipe.getOutput().writeToPacket(friendlyByteBuf);
        fluidExtractionRecipe.getCondition().writeToPacket(friendlyByteBuf);
        CreateWells.LOGGER.info("FluidExtractionRecipe - ToNetwork");
    }
}
