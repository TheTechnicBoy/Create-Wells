package de.thetechnicboy.create_wells.jei;

import com.simibubi.create.compat.jei.EmptyBackground;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import de.thetechnicboy.create_wells.CreateWells;
import de.thetechnicboy.create_wells.jei.animations.AnimatedMechanicalWell;
import de.thetechnicboy.create_wells.recipe.FluidExtractionRecipe;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import com.simibubi.create.compat.jei.category.BasinCategory;

import java.util.ArrayList;
import java.util.List;

public class FluidExtractionCategory extends CreateRecipeCategory<FluidExtractionRecipe> {

    public FluidExtractionCategory(Info<FluidExtractionRecipe> info) {
        super(info);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, FluidExtractionRecipe recipe, IFocusGroup focuses) {
        if(recipe.getCondition().getBlock() != null) {
            if (recipe.getCondition().isBlockTag()) {
                List<Block> blocks = ForgeRegistries.BLOCKS.tags().getTag(TagKey.create(Registries.BLOCK, recipe.getCondition().getBlock())).stream().toList();
                List<ItemStack> items = new ArrayList<>();
                if(!blocks.isEmpty()) {
                    blocks.forEach(b -> items.add(b.asItem().getDefaultInstance()));
                }
                else {
                    items.add(Items.STRUCTURE_VOID.getDefaultInstance().setHoverName(Component.literal("Tag '#" + recipe.getCondition().getBlock() +"' is empty!")));
                }


                builder
                        .addSlot(RecipeIngredientRole.INPUT, 3, getBackground().getHeight() / 2 - 7)
                        .setBackground(getRenderedSlot(), -1, -1)
                        .addItemStacks(items);
            } else {
                builder
                        .addSlot(RecipeIngredientRole.INPUT, 3, getBackground().getHeight() / 2 - 7)
                        .setBackground(getRenderedSlot(), -1, -1)
                        .addItemStack(ForgeRegistries.ITEMS.getValue(recipe.getCondition().getBlock()).getDefaultInstance());
            }
        }

        int outputAmount = recipe.getOutput().getAmount() / (recipe.getOutput().getSpeed() / 20); // mb per Second
        builder
                .addSlot(RecipeIngredientRole.OUTPUT, getWidth() / 2 - 20, getBackground().getHeight() - 20)
                .setBackground(getRenderedSlot(), -1, -1)
                .addIngredient(ForgeTypes.FLUID_STACK, withImprovedVisibility(new FluidStack(recipe.getOutput().getFluid(), recipe.getOutput().getAmount())))
                .addTooltipCallback(addFluidTooltip(outputAmount <= 0 ? 1 : outputAmount));
    }

    @Override
    public void draw(FluidExtractionRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY){
        super.draw(recipe, recipeSlotsView, graphics, mouseX, mouseY);

        FluidExtractionRecipe.Condition condition = recipe.getCondition();

        String text = (condition.getYMin() == -255? "-∞" : condition.getYMin()) + " -> " + (condition.getYMax() == -255? "+∞" : condition.getYMax());
        int Color = 0x73c7c3;
        graphics.drawString(Minecraft.getInstance().font , "Height:" ,getBackground().getWidth() / 2 + 2, getBackground().getHeight() / 2 - 37, Color);
        graphics.drawString(Minecraft.getInstance().font, "  " + text, getBackground().getWidth() / 2 + 2, getBackground().getHeight() / 2 - 27, Color);

        graphics.drawString(Minecraft.getInstance().font , "Dimension:" ,getBackground().getWidth() / 2 + 2, getBackground().getHeight() / 2 - 17, Color);
        if(!condition.getDimension().isEmpty()) graphics.drawString(Minecraft.getInstance().font , "  " + condition.getDimension().get((int) (AnimationTickHolder.getRenderTime() % (condition.getDimension().size() * 30) ) / 30 ).getPath() ,getBackground().getWidth() / 2 + 2, getBackground().getHeight() / 2 - 7, Color);
        else graphics.drawString(Minecraft.getInstance().font , "  Not Important"  ,getBackground().getWidth() / 2 + 2, getBackground().getHeight() / 2 - 7, Color);

        graphics.drawString(Minecraft.getInstance().font , "Biome:" ,getBackground().getWidth() / 2 + 2, getBackground().getHeight() / 2 + 3, Color);
        if(!condition.getBiome().isEmpty()) graphics.drawString(Minecraft.getInstance().font , "  " + condition.getBiome().get((int) ((AnimationTickHolder.getRenderTime() + 15) % (condition.getBiome().size() * 30) ) / 30 ).getPath() ,getBackground().getWidth() / 2 + 2, getBackground().getHeight() / 2 + 13, Color);
        else graphics.drawString(Minecraft.getInstance().font , "  Not Important"  ,getBackground().getWidth() / 2 + 2, getBackground().getHeight() / 2 + 13, Color);

        graphics.drawString(Minecraft.getInstance().font , "RPM:" ,getBackground().getWidth() / 2 + 2, getBackground().getHeight() / 2 + 23, Color);
        if(condition.getRPM() != 0) graphics.drawString(Minecraft.getInstance().font , "  >= " + condition.getRPM() ,getBackground().getWidth() / 2 + 2, getBackground().getHeight() / 2 + 33, Color);
        else graphics.drawString(Minecraft.getInstance().font , "  Not Important"  ,getBackground().getWidth() / 2 + 2, getBackground().getHeight() / 2 + 33, Color);

        AnimatedMechanicalWell well;
        if(recipe.getCondition().getBlock() != null) well = new AnimatedMechanicalWell(recipe.getCondition().getDirection(), recipe.getCondition().getBlock(), recipe.getCondition().isBlockTag());
        else well = new AnimatedMechanicalWell(recipe.getCondition().getDirection());
        well.draw(graphics, getBackground().getWidth() / 2 - 29, 22);

        AllGuiTextures.JEI_DOWN_ARROW.render(graphics, getWidth() / 2 - 26, getHeight() / 2);
    }

}
