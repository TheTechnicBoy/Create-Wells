package de.thetechnicboy.create_wells.recipe;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record FluidExtractionContainer(BlockPos pos, FluidExtractionRecipe.Direction direction, int rpm) implements RecipeInput {

    @Override
    public ItemStack getItem(int slot) {
        throw new IllegalArgumentException("Keine Item-Slots");
    }

    @Override
    public int size() {
        return 0;
    }
}
