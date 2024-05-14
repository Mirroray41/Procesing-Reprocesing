package net.zapp.prore.register.blocks.custom.pipes.Energy;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.zapp.prore.register.blocks.BlockRegister;
import net.zapp.prore.register.blocks.custom.Packets;
import net.zapp.prore.register.blocks.custom.TileEntityRegister;
import net.zapp.prore.register.blocks.custom.pipes.BasePipeBlock;
import net.zapp.prore.register.blocks.custom.pipes.PipeSyncS2C;
import net.zapp.prore.register.items.ItemRegister;

import javax.annotation.Nullable;
import java.awt.*;

public class EnergyPipeBlock extends BasePipeBlock {
    public EnergyPipeBlock(float p_55159_, Properties p_55160_) {
        super(p_55159_, p_55160_);
    }

    @Override
    public InteractionResult use(BlockState p_60503_, Level p_60504_, BlockPos p_60505_, Player p_60506_, InteractionHand p_60507_, BlockHitResult p_60508_) {
        if (p_60504_.isClientSide) return InteractionResult.SUCCESS;

        if (p_60506_.getItemInHand(p_60507_).getItem() == ItemRegister.WRENCH.get()) {
            EnergyPipeTile tile = (EnergyPipeTile) p_60504_.getBlockEntity(p_60505_);
            if (tile != null) {
                if (p_60508_.getLocation().y - p_60505_.getY() > 0.75) {
                    p_60506_.sendSystemMessage(Component.literal("You clicked the top half!"));
                    if (tile.data.get(3) == 1) {
                        tile.data.set(3, 0);
                    } else {
                        tile.data.set(3, 1);
                    }
                } else if (p_60508_.getLocation().y - p_60505_.getY() < 0.25) {
                    p_60506_.sendSystemMessage(Component.literal("You clicked the bottom half!"));
                    if (tile.data.get(4) == 1) {
                        tile.data.set(4, 0);
                    } else {
                        tile.data.set(4, 1);
                    }
                }

                if (p_60508_.getLocation().x - p_60505_.getX() > 0.75) {
                    p_60506_.sendSystemMessage(Component.literal("You clicked east!"));
                    if (tile.data.get(7) == 1) {
                        tile.data.set(7, 0);
                    } else {
                        tile.data.set(7, 1);
                    }
                } else if (p_60508_.getLocation().x - p_60505_.getX() < 0.25) {
                    p_60506_.sendSystemMessage(Component.literal("You clicked west!"));
                    if (tile.data.get(8) == 1) {
                        tile.data.set(8, 0);
                    } else {
                        tile.data.set(8, 1);
                    }
                }

                if (p_60508_.getLocation().z - p_60505_.getZ() > 0.75) {
                    p_60506_.sendSystemMessage(Component.literal("You clicked south!"));
                    if (tile.data.get(6) == 1) {
                        tile.data.set(6, 0);
                    } else {
                        tile.data.set(6, 1);
                    }
                } else if (p_60508_.getLocation().z - p_60505_.getZ() < 0.25) {
                    p_60506_.sendSystemMessage(Component.literal("You clicked north!"));
                    if (tile.data.get(5) == 1) {
                        tile.data.set(5, 0);
                    } else {
                        tile.data.set(5, 1);
                    }
                }

                if (!p_60503_.getValue(BasePipeBlock.UP)) tile.data.set(3, 0);
                if (!p_60503_.getValue(BasePipeBlock.DOWN)) tile.data.set(4, 0);
                if (!p_60503_.getValue(BasePipeBlock.NORTH)) tile.data.set(5, 0);
                if (!p_60503_.getValue(BasePipeBlock.SOUTH)) tile.data.set(6, 0);
                if (!p_60503_.getValue(BasePipeBlock.EAST)) tile.data.set(7, 0);
                if (!p_60503_.getValue(BasePipeBlock.WEST)) tile.data.set(8, 0);

                Packets.sendToClients(new PipeSyncS2C(tile.getEnergyStorage().getEnergyStored(), p_60505_, tile.data.get(3), tile.data.get(4), tile.data.get(5), tile.data.get(6), tile.data.get(7), tile.data.get(8)));
                p_60504_.sendBlockUpdated(p_60505_, p_60503_, p_60503_, 3);
                System.out.println("pull");
            }
        }




        return super.use(p_60503_, p_60504_, p_60505_, p_60506_, p_60507_, p_60508_);
    }



    public boolean hasEnergyCapability(BlockEntity blockEntity, Direction direction) {
        if (blockEntity == null) return false;
        return blockEntity.getCapability(ForgeCapabilities.ENERGY, null).isPresent() || blockEntity.getCapability(ForgeCapabilities.ENERGY, direction.getOpposite()).isPresent();
    }

    public boolean hasEnergyCapableBlockEntity(Level world, BlockState state, BlockPos pos, Direction direction) {
        if (!state.hasBlockEntity()) return false;
        BlockEntity blockEntity = world.getBlockEntity(pos.relative(direction));
        return hasEnergyCapability(blockEntity, direction);
    }

    public boolean hasEnergyCapableBlockEntity(LevelAccessor world, BlockState state, BlockPos pos, Direction direction) {
        if (!state.hasBlockEntity()) return false;
        BlockEntity blockEntity = world.getBlockEntity(pos.relative(direction));
        return hasEnergyCapability(blockEntity, direction);
    }

    @Override
    public BlockState updateShape(BlockState p_51728_, Direction p_51729_, BlockState p_51730_, LevelAccessor p_51731_, BlockPos p_51732_, BlockPos p_51733_) {
        boolean flag = p_51730_.is(this) || hasEnergyCapableBlockEntity(p_51731_, p_51730_, p_51732_, p_51729_);
        EnergyPipeTile tile = (EnergyPipeTile) p_51731_.getBlockEntity(p_51732_);
        if (tile != null) {
            if (!p_51728_.getValue(BasePipeBlock.UP)) tile.data.set(3, 0);
            if (!p_51728_.getValue(BasePipeBlock.DOWN)) tile.data.set(4, 0);
            if (!p_51728_.getValue(BasePipeBlock.NORTH)) tile.data.set(5, 0);
            if (!p_51728_.getValue(BasePipeBlock.SOUTH)) tile.data.set(6, 0);
            if (!p_51728_.getValue(BasePipeBlock.EAST)) tile.data.set(7, 0);
            if (!p_51728_.getValue(BasePipeBlock.WEST)) tile.data.set(8, 0);
        }
        return p_51728_.setValue(PROPERTY_BY_DIRECTION.get(p_51729_), Boolean.valueOf(flag));
    }

    @Override
    public boolean checkForShape(Level world, BlockPos pos, Direction direction) {
        BlockState blockstate = world.getBlockState(pos.relative(direction));
        return Boolean.valueOf(blockstate.is(this) || hasEnergyCapableBlockEntity(world, blockstate, pos, direction));
    }


    public boolean checkForShape(LevelAccessor world, BlockPos pos, Direction direction) {
        BlockState blockstate = world.getBlockState(pos.relative(direction));
        return Boolean.valueOf(blockstate.is(this) || hasEnergyCapableBlockEntity(world, blockstate, pos, direction));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new EnergyPipeTile(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, TileEntityRegister.ENERGY_PIPE.get(),
                (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1));
    }
}
