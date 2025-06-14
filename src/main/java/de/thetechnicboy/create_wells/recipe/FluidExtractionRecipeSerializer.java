package de.thetechnicboy.create_wells.recipe;


import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.thetechnicboy.create_wells.CreateWells;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class FluidExtractionRecipeSerializer implements RecipeSerializer<FluidExtractionRecipe> {
    @Override
    public MapCodec<FluidExtractionRecipe> codec() {
        return RecordCodecBuilder.mapCodec(instance -> instance.group(
                ResourceLocation.CODEC.fieldOf("type").forGetter(FluidExtractionRecipe::getId),
                FluidExtractionRecipe.FluidOutput.CODEC.fieldOf("output").forGetter(FluidExtractionRecipe::getOutput),
                FluidExtractionRecipe.Condition.CODEC.fieldOf("condition").forGetter(FluidExtractionRecipe::getCondition)
        ).apply(instance, (id, output, condition) -> FluidExtractionRecipe.registerRecipe(id, output, condition)));
    }

    public StreamCodec<RegistryFriendlyByteBuf, FluidExtractionRecipe> streamCodec() {
        return new StreamCodec<>() {
            @Override
            public void encode(RegistryFriendlyByteBuf buf, FluidExtractionRecipe recipe) {
                buf.writeResourceLocation(recipe.getId());
                recipe.getOutput().writeToPacket(buf);
                recipe.getCondition().writeToPacket(buf);
            }

            @Override
            public FluidExtractionRecipe decode(RegistryFriendlyByteBuf buf) {
                ResourceLocation id = buf.readResourceLocation();
                FluidExtractionRecipe.FluidOutput output = FluidExtractionRecipe.FluidOutput.fromPacket(buf);
                FluidExtractionRecipe.Condition condition = FluidExtractionRecipe.Condition.fromPacket(buf);
                return FluidExtractionRecipe.registerRecipe(id, output, condition);
            }
        };
    }
}
