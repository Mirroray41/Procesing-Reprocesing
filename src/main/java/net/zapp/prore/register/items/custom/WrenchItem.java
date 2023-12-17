package net.zapp.prore.register.items.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class WrenchItem extends Item {
    public WrenchItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getLevel().isClientSide || context.getPlayer() == null) return super.useOn(context);
        if (context.getPlayer().isCrouching()) {


            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
        if (level.isClientSide) return false;
        if (player.isCrouching()) {
            player.sendSystemMessage(Component.literal(String.valueOf(level.getBlockState(pos))));
            //level.setBlockAndUpdate(pos, level.getBlockState(pos).setValue(BlockStateProperties.HORIZONTAL_FACING, level.getBlockState(pos).getValue(BlockStateProperties.HORIZONTAL_FACING).getCounterClockWise()));
            level.setBlockAndUpdate(pos, level.getBlockState(pos).setValue(BlockStateProperties.FACING, level.getBlockState(pos).getValue(BlockStateProperties.FACING).getCounterClockWise()));
        }
        return false;
    }
}
