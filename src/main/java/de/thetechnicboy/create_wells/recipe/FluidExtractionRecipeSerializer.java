package de.thetechnicboy.create_wells.recipe;


import com.google.gson.JsonObject;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.thetechnicboy.create_wells.CreateWells;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.ArrayList;

public class FluidExtractionRecipeSerializer implements RecipeSerializer<FluidExtractionRecipe> {
    @Override
    public MapCodec<FluidExtractionRecipe> codec() {
        System.out.println("codec() wurde aufgerufen!");
        return RecordCodecBuilder.mapCodec(instance -> instance.group(
                FluidExtractionRecipe.FluidOutput.CODEC.fieldOf("output").forGetter(FluidExtractionRecipe::getOutput),
                FluidExtractionRecipe.Condition.CODEC.fieldOf("condition").forGetter(FluidExtractionRecipe::getCondition)
        ).apply(instance, (output, condition) -> FluidExtractionRecipe.registerRecipe(output, condition)));
    }


    @Override
    public StreamCodec<RegistryFriendlyByteBuf, FluidExtractionRecipe> streamCodec() {
        System.out.println("CODEC WIRD GESTREAMT");
        return new StreamCodec<>() {
            @Override
            public void encode(RegistryFriendlyByteBuf buf, FluidExtractionRecipe recipe) {
                recipe.getOutput().writeToPacket(buf);
                recipe.getCondition().writeToPacket(buf);
            }

            @Override
            public FluidExtractionRecipe decode(RegistryFriendlyByteBuf buf) {
                FluidExtractionRecipe.FluidOutput output = FluidExtractionRecipe.FluidOutput.fromPacket(buf);
                FluidExtractionRecipe.Condition condition = FluidExtractionRecipe.Condition.fromPacket(buf);
                return FluidExtractionRecipe.registerRecipe(output, condition);
            }
        };
    }
}
