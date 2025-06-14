package de.thetechnicboy.create_wells.block.mechanical_well;

import com.simibubi.create.content.kinetics.base.DirectionalAxisKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import de.thetechnicboy.create_wells.block.mechanical_well.entity.MechanicalWellEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class MechanicalWellBlock extends DirectionalAxisKineticBlock implements IBE<MechanicalWellEntity> {

    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    public static final BooleanProperty UPSIDE_DOWN = BooleanProperty.create("upside_down");

    public static final VoxelShape SHAPE_BASE = Shapes.join(Shapes.block(), Block.box(3.0D, 2.0D, 3.0D, 13.0D, 16.0D, 13.0D), BooleanOp.ONLY_FIRST);
    public static final VoxelShape SHAPE_INNER_SUPPORT = Shapes.or(
            Block.box(7.5D, 0.0D, 1.0D, 8.5D, 15.0D, 2.0D),
            Block.box(7.5D, 0.0D, 14.0D, 8.5D, 15.0D, 15.0D),
            Block.box(7.5D, 7.0D, 2.0D, 8.5D, 8.0D, 14.0D),
            Block.box(5.0D, 4.5D, 4.5D, 11.0D, 10.5D, 11.5D)
    );
    public static final VoxelShape SHAPE_ROOF = Shapes.or(
            Block.box(5.5D, 12.5D, 0.0D, 10.5D, 15.707D, 16.0D),
            Block.box(2.75D, 10.5D, 0.0D, 5.5D, 13.75D, 16.0D),
            Block.box(10.5D, 10.5D, 0.0D, 13.25D, 13.75D, 16.0D),
            Block.box(0.0D, 8.0D, 0.0D, 2.75D, 11.25D, 16.0D),
            Block.box(13.25D, 8.0D, 0.0D, 16.0D, 11.25D, 16.0D),
            SHAPE_INNER_SUPPORT
    );

    public MechanicalWellBlock() {
        super(Properties.of().instrument(NoteBlockInstrument.BASEDRUM).strength(1.5F, 6.0F).requiresCorrectToolForDrops());
        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(AXIS, Direction.Axis.X)
                .setValue(HALF, DoubleBlockHalf.LOWER)
                .setValue(UPSIDE_DOWN, false));
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS, HALF, UPSIDE_DOWN);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Direction.Axis axis = context.getPlayer().isCrouching() ? context.getHorizontalDirection().getClockWise().getAxis()
                : context.getHorizontalDirection().getAxis();

        // regular placement
        if (pos.getY() < level.getMaxBuildHeight() - 1) {
            if (level.getBlockState(pos.above()).canBeReplaced(context)) {
                return this.defaultBlockState().setValue(AXIS, axis).setValue(UPSIDE_DOWN, false);
            }
        }

        // upside down placement
        if (pos.getY() > level.getMinBuildHeight() + 1) {
            if (level.getBlockState(pos.below()).canBeReplaced(context)) {
                return this.defaultBlockState().setValue(AXIS, axis).setValue(UPSIDE_DOWN, true);
            }
        }

        return null;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        DoubleBlockHalf half = state.getValue(HALF);
        Direction flippedDir1 = state.getValue(UPSIDE_DOWN) ? Direction.DOWN : Direction.UP;
        Direction flippedDir2 = state.getValue(UPSIDE_DOWN) ? Direction.UP : Direction.DOWN;
        if (direction.getAxis() != Direction.Axis.Y || ((half == DoubleBlockHalf.LOWER) != (direction == flippedDir1)) || (neighborState.is(this) && (neighborState.getValue(HALF) != half))) {
            if ((half != DoubleBlockHalf.LOWER) || (direction != flippedDir2) || state.canSurvive(level, currentPos)) {
                return state;
            }
        }
        return Blocks.AIR.defaultBlockState();
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        level.setBlockAndUpdate(pos.above(state.getValue(UPSIDE_DOWN) ? -1 : 1), state.setValue(HALF, DoubleBlockHalf.UPPER));
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide) {
            if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
                BlockPos otherPos = pos.below(state.getValue(UPSIDE_DOWN) ? -1 : 1);
                BlockState otherState = level.getBlockState(otherPos);
                if (otherState.is(this) && otherState.getValue(HALF) == DoubleBlockHalf.LOWER) {
                    if (player.isCreative()) {
                        level.destroyBlock(otherPos, false, player);
                    } else {
                        level.destroyBlock(otherPos, canHarvestBlock(state, level, pos, player), player);
                    }
                }
            }
        }

        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.IGNORE;
    }

    /*@Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
        return false;
    }*/

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
            return state.getValue(UPSIDE_DOWN) ? flipShapeUpsideDown(SHAPE_BASE) : SHAPE_BASE;
        } else if (state.getValue(AXIS) == Direction.Axis.X) {
            return state.getValue(UPSIDE_DOWN) ? flipShapeUpsideDown(SHAPE_ROOF) : SHAPE_ROOF;
        } else return state.getValue(UPSIDE_DOWN) ? flipShapeUpsideDown(flipShapeXZ(SHAPE_ROOF)) : flipShapeXZ(SHAPE_ROOF);
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos) {
        if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
            return state.getValue(UPSIDE_DOWN) ? flipShapeUpsideDown(SHAPE_BASE) : SHAPE_BASE;
        } else if (state.getValue(AXIS) == Direction.Axis.X) {
            return state.getValue(UPSIDE_DOWN) ? flipShapeUpsideDown(SHAPE_INNER_SUPPORT) : SHAPE_INNER_SUPPORT;
        } else return state.getValue(UPSIDE_DOWN) ? flipShapeUpsideDown(flipShapeXZ(SHAPE_INNER_SUPPORT)) : flipShapeXZ(SHAPE_INNER_SUPPORT);
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    @Override
    public VoxelShape getInteractionShape(BlockState state, BlockGetter level, BlockPos pos) {
        if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
            return Shapes.block();
        } else if (state.getValue(AXIS) == Direction.Axis.X) {
            return state.getValue(UPSIDE_DOWN) ? flipShapeUpsideDown(SHAPE_ROOF) : SHAPE_ROOF;
        } else return state.getValue(UPSIDE_DOWN) ? flipShapeUpsideDown(flipShapeXZ(SHAPE_ROOF)) : flipShapeXZ(SHAPE_ROOF);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        switch (rotation) {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:
                switch ((Direction.Axis) state.getValue(AXIS)) {
                    case Z:
                        return state.setValue(AXIS, Direction.Axis.X);
                    case X:
                        return state.setValue(AXIS, Direction.Axis.Z);
                    default:
                        return state;
                }
            default:
                return state;
        }
    }

    public static VoxelShape flipShapeXZ(VoxelShape shape) {
        VoxelShape[] buffer = new VoxelShape[] { shape, Shapes.empty() };
        buffer[0].forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
            buffer[1] = Shapes.or(buffer[1], Shapes.create(minZ, minY, minX, maxZ, maxY, maxX));
        });

        return buffer[1];
    }
    public static VoxelShape flipShapeUpsideDown(VoxelShape shape) {
        VoxelShape[] buffer = new VoxelShape[] { shape, Shapes.empty() };
        buffer[0].forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
            buffer[1] = Shapes.or(buffer[1], Shapes.create(minX, 1 - maxY, minZ, maxX, 1 - minY, maxZ));
        });

        return buffer[1];
    }

    public static float getFluidRenderHeight(int amount, int capacity, boolean upsideDown) {
        float height = amount * 14F / (16 * capacity) + (2F / 16);
        return upsideDown ? 1 - height : height;
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return state.getValue(AXIS) == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return !(face.getAxis() == state.getValue(AXIS));
    }

}


