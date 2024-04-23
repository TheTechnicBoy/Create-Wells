package de.thetechnicboy.create_wells.jei;

import com.simibubi.create.compat.jei.EmptyBackground;
import com.simibubi.create.compat.jei.ItemIcon;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import de.thetechnicboy.create_wells.CreateWells;
import de.thetechnicboy.create_wells.item.ModItems;
import de.thetechnicboy.create_wells.recipe.FluidExtractionRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class FluixExtrationCategory extends CreateRecipeCategory<FluidExtractionRecipe> {

    public FluixExtrationCategory(Info<FluidExtractionRecipe> info) {
        super(info);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, FluidExtractionRecipe recipe, IFocusGroup focuses) {
        builder
                .addSlot(RecipeIngredientRole.INPUT, getBackground().getWidth()/2 - 56, 3)
                .setBackground(getRenderedSlot(), -1, -1)
                .addItemStack(new ItemStack(ModItems.MECHANICAL_WELL.get()));
    }
}
