package de.thetechnicboy.create_wells.jei.animations;

import com.mojang.blaze3d.vertex.PoseStack;

import com.mojang.math.Axis;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import de.thetechnicboy.create_wells.block.mechanical_well.MechanicalWellBlock;
import de.thetechnicboy.create_wells.block.ModBlocks;
import de.thetechnicboy.create_wells.recipe.FluidExtractionRecipe;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraftforge.registries.ForgeRegistries;
import java.util.List;


public class AnimatedMechanicalWell extends AnimatedKinetics {

    private final ResourceLocation  block;
    private final FluidExtractionRecipe.Direction direction;
    private final boolean blockTag;
    private final List<Block> blocks;
    public AnimatedMechanicalWell(FluidExtractionRecipe.Direction direction) {
        this.direction = direction;
        this.block = null;
        this.blockTag = false;
        this.blocks = null;
    }

    public AnimatedMechanicalWell(FluidExtractionRecipe.Direction direction, ResourceLocation  block, boolean blockTag) {
        this.direction = direction;
        this.block = block;
        this.blockTag = blockTag;

        blocks = ForgeRegistries.BLOCKS.tags().getTag(TagKey.create(Registries.BLOCK, block)).stream().toList();
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

        Block _block = getBlock();

        if (_block != null) {
            blockElement(_block.defaultBlockState())
                    .atLocal(-1.5, _Direction == FluidExtractionRecipe.Direction.UPSIDE_DOWN ? 0.20 : 2.20, 0)
                    .scale(23)
                    .render(graphics);
            blockElement(ModBlocks.RED_MECHANICAL_WELL.get().defaultBlockState().setValue(MechanicalWellBlock.HALF, DoubleBlockHalf.LOWER).setValue(MechanicalWellBlock.UPSIDE_DOWN, _Direction == FluidExtractionRecipe.Direction.UPSIDE_DOWN))
                    .atLocal(-1.5, 1.20, 0)
                    .scale(23)
                    .render(graphics);
            blockElement(ModBlocks.RED_MECHANICAL_WELL.get().defaultBlockState().setValue(MechanicalWellBlock.HALF, DoubleBlockHalf.UPPER).setValue(MechanicalWellBlock.UPSIDE_DOWN, _Direction == FluidExtractionRecipe.Direction.UPSIDE_DOWN))
                    .atLocal(-1.5, _Direction != FluidExtractionRecipe.Direction.UPSIDE_DOWN ? 0.20 : 2.20, 0)
                    .scale(23)
                    .render(graphics);
            blockElement(shaft(Direction.Axis.Z))
                    .atLocal(-1.5, 1.20, 0)
                    .rotateBlock(0,0,getCurrentAngle())
                    .scale(23)
                    .render(graphics);
        } else {
            blockElement(ModBlocks.RED_MECHANICAL_WELL.get().defaultBlockState().setValue(MechanicalWellBlock.HALF, DoubleBlockHalf.LOWER).setValue(MechanicalWellBlock.UPSIDE_DOWN, _Direction == FluidExtractionRecipe.Direction.UPSIDE_DOWN))
                    .atLocal(-1.5, _Direction == FluidExtractionRecipe.Direction.UPSIDE_DOWN ? 0.70 : 1.7, 0)
                    .scale(23)
                    .render(graphics);
            blockElement(ModBlocks.RED_MECHANICAL_WELL.get().defaultBlockState().setValue(MechanicalWellBlock.HALF, DoubleBlockHalf.UPPER).setValue(MechanicalWellBlock.UPSIDE_DOWN, _Direction == FluidExtractionRecipe.Direction.UPSIDE_DOWN))
                    .atLocal(-1.5, _Direction != FluidExtractionRecipe.Direction.UPSIDE_DOWN ? 0.70 : 1.7, 0)
                    .scale(23)
                    .render(graphics);
            blockElement(shaft(Direction.Axis.Z))
                    .atLocal(-1.5, _Direction == FluidExtractionRecipe.Direction.UPSIDE_DOWN ? 0.70 : 1.7, 0)
                    .rotateBlock(0,0, getCurrentAngle())
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

    private Block getBlock(){
        if(block == null) return null;
        if(!blockTag)
            return ForgeRegistries.BLOCKS.getValue(block);
        else {
            if(blocks.isEmpty()) return null;
            double cycle = (AnimationTickHolder.getRenderTime() - offset * 8) % (blocks.size() * 20);
            return blocks.get((int)(cycle / 20));
        }
    }
}
