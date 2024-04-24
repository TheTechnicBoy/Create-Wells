package de.thetechnicboy.create_wells.jei;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllFluids;
import com.simibubi.create.Create;
import com.simibubi.create.compat.jei.EmptyBackground;
import com.simibubi.create.compat.jei.ItemIcon;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import de.thetechnicboy.create_wells.CreateWells;
import de.thetechnicboy.create_wells.item.ModItems;
import de.thetechnicboy.create_wells.recipe.FluidExtractionRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotTooltipCallback;
import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public class FluidExtractionCategory extends CreateRecipeCategory<FluidExtractionRecipe> {

    public FluidExtractionCategory(Info<FluidExtractionRecipe> info) {
        super(info);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, FluidExtractionRecipe recipe, IFocusGroup focuses) {
        builder
                .addSlot(RecipeIngredientRole.INPUT, getBackground().getWidth()/2 - 56, 3)
                .setBackground(getRenderedSlot(), -1, -1)
                .addItemStack(new ItemStack(AllBlocks.COGWHEEL.get()));
        builder
                .addSlot(RecipeIngredientRole.OUTPUT, getBackground().getWidth()/2 - 56, 33)
                .setBackground(getRenderedSlot(), -1, -1)
                .addFluidStack(recipe.getOutput().getFluid(), recipe.getOutput().getAmount())
                .addTooltipCallback(new IRecipeSlotTooltipCallback() {
                    @Override
                    public void onTooltip(IRecipeSlotView iRecipeSlotView, List<Component> list) {
                        addFluidTooltip(recipe.getOutput().getAmount());
                    }
                });

    }
}
