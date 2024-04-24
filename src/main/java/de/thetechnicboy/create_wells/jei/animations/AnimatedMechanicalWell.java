package de.thetechnicboy.create_wells.jei.animations;

import com.mojang.blaze3d.vertex.PoseStack;

import com.mojang.math.Axis;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import de.thetechnicboy.create_wells.block.MechanicalWellBlock;
import de.thetechnicboy.create_wells.block.ModBlocks;
import de.thetechnicboy.create_wells.recipe.FluidExtractionRecipe;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraftforge.registries.ForgeRegistries;


public class AnimatedMechanicalWell extends AnimatedKinetics {

    private final Block block;
    private final FluidExtractionRecipe.Direction direction;
    public AnimatedMechanicalWell(FluidExtractionRecipe.Direction direction) {
        this.direction = direction;
        this.block = null;
    }

    public AnimatedMechanicalWell(FluidExtractionRecipe.Direction direction, Block block) {
        this.direction = direction;
        this.block = block;
    }
    @Override
    public void  draw(GuiGraphics graphics, int xOffset, int yOffset) {
        PoseStack matrixStack = graphics.pose();
        matrixStack.pushPose();
        matrixStack.translate(xOffset, yOffset, 200);
        matrixStack.mulPose(Axis.XP.rotationDegrees(-15.5f));
        matrixStack.mulPose(Axis.YP.rotationDegrees(22.5f));

        FluidExtractionRecipe.Direction _Direction;
        if(direction != FluidExtractionRecipe.Direction.BOTH) _Direction = direction;
        else _Direction = getDirectionSwapper();


        if (block != null) {
            blockElement(block.defaultBlockState())
                    .atLocal(-1.5, _Direction == FluidExtractionRecipe.Direction.UPSIDE_DOWN ? 0.20 : 2.20, 0)
                    .scale(23)
                    .render(graphics);
            blockElement(ModBlocks.MECHANICAL_WELL.get().defaultBlockState().setValue(MechanicalWellBlock.HALF, DoubleBlockHalf.LOWER).setValue(MechanicalWellBlock.UPSIDE_DOWN, _Direction == FluidExtractionRecipe.Direction.UPSIDE_DOWN))
                    .atLocal(-1.5, 1.20, 0)
                    .scale(23)
                    .render(graphics);
            blockElement(ModBlocks.MECHANICAL_WELL.get().defaultBlockState().setValue(MechanicalWellBlock.HALF, DoubleBlockHalf.UPPER).setValue(MechanicalWellBlock.UPSIDE_DOWN, _Direction == FluidExtractionRecipe.Direction.UPSIDE_DOWN))
                    .atLocal(-1.5, _Direction != FluidExtractionRecipe.Direction.UPSIDE_DOWN ? 0.20 : 2.20, 0)
                    .scale(23)
                    .render(graphics);
        } else {
            blockElement(ModBlocks.MECHANICAL_WELL.get().defaultBlockState().setValue(MechanicalWellBlock.HALF, DoubleBlockHalf.LOWER).setValue(MechanicalWellBlock.UPSIDE_DOWN, _Direction == FluidExtractionRecipe.Direction.UPSIDE_DOWN))
                    .atLocal(-1.5, _Direction == FluidExtractionRecipe.Direction.UPSIDE_DOWN ? 0.70 : 1.7, 0)
                    .scale(23)
                    .render(graphics);
            blockElement(ModBlocks.MECHANICAL_WELL.get().defaultBlockState().setValue(MechanicalWellBlock.HALF, DoubleBlockHalf.UPPER).setValue(MechanicalWellBlock.UPSIDE_DOWN, _Direction == FluidExtractionRecipe.Direction.UPSIDE_DOWN))
                    .atLocal(-1.5, _Direction != FluidExtractionRecipe.Direction.UPSIDE_DOWN ? 0.70 : 1.7, 0)
                    .scale(23)
                    .render(graphics);
        }

        matrixStack.popPose();
    }

    private FluidExtractionRecipe.Direction getDirectionSwapper() {
        float cycle = (AnimationTickHolder.getRenderTime() - offset * 8) % 80;
        if(cycle < 40) return FluidExtractionRecipe.Direction.NORMAL;
        else return FluidExtractionRecipe.Direction.UPSIDE_DOWN;
    }
}