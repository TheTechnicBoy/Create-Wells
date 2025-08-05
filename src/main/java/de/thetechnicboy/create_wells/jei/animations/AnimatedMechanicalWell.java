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
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class AnimatedMechanicalWell extends AnimatedKinetics {

    private final ResourceLocation  block;
    private final FluidExtractionRecipe.Direction direction;
    private final boolean blockTag;
    private final List<Block> blocks;
    private final Map<String, String> state;

    public AnimatedMechanicalWell(FluidExtractionRecipe.Direction direction) {
        this.direction = direction;
        this.block = null;
        this.blockTag = false;
        this.blocks = null;
        this.state = new HashMap<>();
    }

    public AnimatedMechanicalWell(FluidExtractionRecipe.Direction direction, ResourceLocation  block, boolean blockTag, Map<String, String> state) {
        this.direction = direction;
        this.block = block;
        this.blockTag = blockTag;
        this.state = state;

        Optional<? extends HolderSet.Named<Block>> tagOptional =
                BuiltInRegistries.BLOCK.getTag(TagKey.create(Registries.BLOCK, block));

        if(tagOptional.isPresent()) {
            blocks = tagOptional.get().stream()
                    .map(holder -> holder.value())
                    .toList();
        } else {
            blocks = List.of();
        }
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

        BlockState _block = getBlock();

        if (_block != null) {
            blockElement(_block)
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

    private BlockState getBlock(){
        if(block == null) return null;
        if(!blockTag) {
            Block _block = BuiltInRegistries.BLOCK.get(block);
            if (_block == null) return null;

            BlockState blockState = _block.defaultBlockState();

            for (Map.Entry<String, String> requiredProperty : state.entrySet()) {
                String propertyName = requiredProperty.getKey();
                String requiredValue = requiredProperty.getValue();

                for (Property<?> property : blockState.getProperties()) {
                    if (!property.getName().equals(propertyName)) continue;

                    for (Comparable<?> possible : property.getPossibleValues()) {
                        if (possible.toString().equals(requiredValue)) {
                            blockState = setPropertyUnchecked(blockState, property, possible);
                            break;
                        }
                    }
                }
            }

            return blockState;
        }
        else {
            if(blocks.isEmpty()) return null;
            double cycle = (AnimationTickHolder.getRenderTime() - offset * 8) % (blocks.size() * 20);
            return blocks.get((int)(cycle / 20)).defaultBlockState();
        }

    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static <T extends Comparable<T>> BlockState setPropertyUnchecked(BlockState state, Property<?> property, Comparable<?> value) {
        Property<T> p = (Property<T>) property;
        T v = (T) value;
        return state.setValue(p, v);
    }
}
