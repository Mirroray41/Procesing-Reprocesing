package net.zapp.prore.register.blocks.custom.pipes;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;


public class PipeBlockWithoutEntity extends PipeBlock {
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;

    public PipeBlockWithoutEntity(float p_55159_, Properties p_55160_) {
        super(p_55159_, p_55160_);
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(NORTH, Boolean.valueOf(false))
                        .setValue(EAST, Boolean.valueOf(false))
                        .setValue(SOUTH, Boolean.valueOf(false))
                        .setValue(WEST, Boolean.valueOf(false))
                        .setValue(UP, Boolean.valueOf(false))
                        .setValue(DOWN, Boolean.valueOf(false)));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_51735_) {
        p_51735_.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        return this.defaultBlockState()
                .setValue(DOWN, checkForShape(world, pos, Direction.DOWN))
                .setValue(UP, checkForShape(world, pos, Direction.UP))
                .setValue(NORTH, checkForShape(world, pos, Direction.NORTH))
                .setValue(EAST, checkForShape(world, pos, Direction.EAST))
                .setValue(SOUTH, checkForShape(world, pos, Direction.SOUTH))
                .setValue(WEST, checkForShape(world, pos, Direction.WEST));
    }

    public BlockState updateShape(BlockState p_51728_, Direction p_51729_, BlockState p_51730_, LevelAccessor p_51731_, BlockPos p_51732_, BlockPos p_51733_) {
        boolean flag = p_51730_.is(this);
        return p_51728_.setValue(PROPERTY_BY_DIRECTION.get(p_51729_), Boolean.valueOf(flag));
    }

    public boolean isPathfindable(BlockState p_51719_, BlockGetter p_51720_, BlockPos p_51721_, PathComputationType p_51722_) {
        return false;
    }

    public boolean checkForShape(Level world, BlockPos pos, Direction direction) {
        BlockState blockstate = world.getBlockState(pos.relative(direction));
        return Boolean.valueOf(blockstate.is(this));
    }
}
