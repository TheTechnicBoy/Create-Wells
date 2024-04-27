package de.thetechnicboy.create_wells.jei;

import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import de.thetechnicboy.create_wells.jei.animations.AnimatedMechanicalWell;
import de.thetechnicboy.create_wells.recipe.FluidExtractionRecipe;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
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
                        .addSlot(RecipeIngredientRole.INPUT, 10, getBackground().getHeight() / 2)
                        .setBackground(getRenderedSlot(), -1, -1)
                        .addItemStacks(items);
            } else {
                builder
                        .addSlot(RecipeIngredientRole.INPUT, 10, getBackground().getHeight() / 2)
                        .setBackground(getRenderedSlot(), -1, -1)
                        .addItemStack(ForgeRegistries.ITEMS.getValue(recipe.getCondition().getBlock()).getDefaultInstance());
            }
        }

        builder
                .addSlot(RecipeIngredientRole.OUTPUT, getBackground().getWidth() - 40, getBackground().getHeight() - 20)
                .setBackground(getRenderedSlot(), -1, -1)
                .addIngredient(ForgeTypes.FLUID_STACK, withImprovedVisibility(new FluidStack(recipe.getOutput().getFluid(), recipe.getOutput().getAmount())))
                .addTooltipCallback(addFluidTooltip(recipe.getOutput().getAmount()));
    }

    @Override
    public void draw(FluidExtractionRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics graphics, double mouseX, double mouseY){
        super.draw(recipe, recipeSlotsView, graphics, mouseX, mouseY);

        AnimatedMechanicalWell well;
        if(recipe.getCondition().getBlock() != null) well = new AnimatedMechanicalWell(recipe.getCondition().getDirection(), recipe.getCondition().getBlock(), recipe.getCondition().isBlockTag());
        else well = new AnimatedMechanicalWell(recipe.getCondition().getDirection());
        well.draw(graphics, getBackground().getWidth() / 2 - 17, 22);
    }

}
