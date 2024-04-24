package de.thetechnicboy.create_wells.jei;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllFluids;
import com.simibubi.create.Create;
import com.simibubi.create.compat.jei.EmptyBackground;
import com.simibubi.create.compat.jei.ItemIcon;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import com.simibubi.create.foundation.utility.Color;
import com.simibubi.create.foundation.utility.Lang;
import de.thetechnicboy.create_wells.CreateWells;
import de.thetechnicboy.create_wells.item.ModItems;
import de.thetechnicboy.create_wells.jei.animations.AnimatedMechanicalWell;
import de.thetechnicboy.create_wells.recipe.FluidExtractionRecipe;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotTooltipCallback;
import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class FluidExtractionCategory extends CreateRecipeCategory<FluidExtractionRecipe> {

    public FluidExtractionCategory(Info<FluidExtractionRecipe> info) {
        super(info);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, FluidExtractionRecipe recipe, IFocusGroup focuses) {
        builder
                .addSlot(RecipeIngredientRole.OUTPUT, getBackground().getWidth() - 40, getBackground().getHeight() - 20)
                .setBackground(getRenderedSlot(), -1, -1)
                .addIngredient(ForgeTypes.FLUID_STACK, withImprovedVisibility(new FluidStack(recipe.getOutput().getFluid(), recipe.getOutput().getAmount())))
                .addTooltipCallback(addFluidTooltip(recipe.getOutput().getAmount()));

    }

    @Override
    public void draw(FluidExtractionRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY){
        super.draw(recipe, recipeSlotsView, graphics, mouseX, mouseY);

        AnimatedMechanicalWell well = new AnimatedMechanicalWell(recipe.getCondition().getDirection());
        well.draw(graphics, getBackground().getWidth() / 2 - 17, 22);
    }

}
