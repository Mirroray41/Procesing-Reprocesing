package net.zapp.prore.register.blocks.custom.machine.battery_controller;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.zapp.prore.register.blocks.BlockRegister;

import javax.annotation.Nullable;
import java.util.List;

public class BatteryControllerBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public BatteryControllerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(FACING, Direction.NORTH)
        );
    }
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        if (pContext.getPlayer().isCrouching()) {
            return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection());
        }
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, LevelAccessor level, BlockPos pos, Rotation direction) {
        return state.setValue(FACING, direction.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_48725_) {
        p_48725_.add(FACING);
    }

    private int getLimit(Level level, BlockPos pos, Direction direction) {
        int returnInt = 0;
        BlockPos currentPos = pos;
        while (true) {
            if (returnInt >= 16) {
                break;
            }
            if (isCasingValid(level.getBlockState(currentPos.relative(direction)).getBlock())) {
                currentPos = currentPos.relative(direction);
                returnInt++;
            } else {
                break;
            }
        }
        return returnInt;
    }

    private String humanFormatPos(BlockPos pos) {
        return pos.getX() + ", " + pos.getY() + ", "  + pos.getZ();
    }

    private List casingBlocks = List.of(BlockRegister.BATTERY_CASING.get(), BlockRegister.BATTERY_CASING_GLASS.get(), BlockRegister.BATTERY_ANODE_CONNECTOR.get(), BlockRegister.BATTERY_CATHODE_CONNECTOR.get(), this.asBlock());

    public boolean isCasingValid(Block block) {
        return casingBlocks.contains(block);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (level.isClientSide) return InteractionResult.SUCCESS;
        Direction facing = level.getBlockState(pos).getValue(FACING);
        int right = getLimit(level, pos, facing.getClockWise());
        int left = getLimit(level, pos, facing.getCounterClockWise());
        int up = getLimit(level, pos, Direction.UP);
        int down = getLimit(level, pos, Direction.DOWN);

        int depth = getLimit(level, pos.offset(0, -down, 0), facing.getOpposite()) + 1;
        int height = up + down + 1;
        int width = left + right + 1;
        boolean canBuild = true;


        for (int h = 0; h < 2; h++) {
            for (int d = 0; d < depth; d++) {
                for (int w = 0; w < width; w++) {
                    BlockPos relativePos = pos.relative(facing.getOpposite(), depth - 1 - d).offset(0, (-down) + (h * (height - 1)), 0).relative(facing.getClockWise(), right - w);
                    if (!isCasingValid(level.getBlockState(relativePos).getBlock())) {
                        player.sendSystemMessage(Component.literal("Missing casing block at: " + humanFormatPos(relativePos)));
                        canBuild = false;
                    }
                }
            }
        }

        for (int h = 0; h < height - 2; h++) {
            for (int d = 0; d < 2; d++) {
                for (int w = 0; w < width; w++) {
                    BlockPos relativePos = pos.relative(facing.getOpposite(), (depth - 1) * d).offset(0, (-down) + h + 1, 0).relative(facing.getClockWise(), right - w);
                    if (!isCasingValid(level.getBlockState(relativePos).getBlock())) {
                        player.sendSystemMessage(Component.literal("Missing casing block at: " + humanFormatPos(relativePos)));
                        canBuild = false;
                    }
                }
            }
        }
        for (int h = 0; h < height - 2; h++) {
            for (int d = 0; d < depth - 2; d++) {
                for (int w = 0; w < 2; w++) {
                    BlockPos relativePos = pos.relative(facing.getOpposite(), depth - 2 - d).offset(0, (-down) + h + 1, 0).relative(facing.getClockWise(), right - ((width - 1)* w));
                    if (!isCasingValid(level.getBlockState(relativePos).getBlock())) {
                        player.sendSystemMessage(Component.literal("Missing casing block at: " + humanFormatPos(relativePos)));
                        canBuild = false;
                    }
                }
            }
        }

        for (int h = 0; h < height - 2; h++) {
            for (int d = 0; d < depth - 2; d++) {
                for (int w = 0; w < width - 2; w++) {
                    BlockPos relativePos = pos.relative(facing.getOpposite(), depth - 2 - d).offset(0, (-down) + h + 1, 0).relative(facing.getClockWise(), right - ((width - 2) - w));
                    if (!level.getBlockState(relativePos).is(Blocks.AIR)) {
                        player.sendSystemMessage(Component.literal(level.getBlockState(relativePos).getBlock().toString()));
                    }
                }
            }
        }
        player.sendSystemMessage(Component.literal(String.valueOf(canBuild)));

        return InteractionResult.SUCCESS;
    }
}
