package net.zapp.prore.register.blocks.custom.pipes;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.zapp.prore.register.blocks.custom.pipes.Energy.EnergyPipeTile;

import java.util.function.Supplier;

public class PipeSyncS2C {
    private final int energy;

    private final int up;

    private final int down;

    private final int north;

    private final int south;

    private final int west;

    private final int east;

    private final BlockPos pos;

    public PipeSyncS2C(int energy, BlockPos pos, int up, int down, int north, int south, int west, int east) {
        this.energy = energy;
        this.pos = pos;
        this.up = up;
        this.down = down;
        this.north = north;
        this.south = south;
        this.west = west;
        this.east = east;
    }

    public PipeSyncS2C(FriendlyByteBuf buf) {
        this.energy = buf.readInt();
        this.pos = buf.readBlockPos();
        this.up = buf.readInt();
        this.down = buf.readInt();
        this.north = buf.readInt();
        this.south = buf.readInt();
        this.west = buf.readInt();
        this.east = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(energy);
        buf.writeBlockPos(pos);
        buf.writeInt(up);
        buf.writeInt(down);
        buf.writeInt(north);
        buf.writeInt(south);
        buf.writeInt(west);
        buf.writeInt(east);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            if (Minecraft.getInstance().level.getBlockEntity(pos) instanceof EnergyPipeTile tile) {
                tile.setEnergyLevel(energy);
                tile.setUp(up);
                tile.setDown(down);
                tile.setNorth(north);
                tile.setSouth(south);
                tile.setWest(west);
                tile.setEast(east);
            }
        });
        return true;
    }

    /*
     * if (Minecraft.getInstance.level.getBlockEntity(pos) isntanceof BlockEntity entity) {
     *      entity.setEnergyLevel(energy);
     *
     *      if (Minecraft.getInstance().player.containerMenu instanceof BlockEntityMenu menu && menu.getBlockEntity().getBlockPos().equals(pos)) {
     *          menu.getBlockEntity().setEnergyLevel(energy);
     *      }
     * }
     */
}