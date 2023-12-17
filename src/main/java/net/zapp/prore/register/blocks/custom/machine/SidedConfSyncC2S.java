package net.zapp.prore.register.blocks.custom.machine;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.zapp.prore.register.blocks.custom.machine.electric_furnace.ElectricFurnaceTile;

import java.util.function.Supplier;

public class SidedConfSyncC2S {

    protected BlockPos pos;
    protected int up;
    protected int down;
    protected int left;
    protected int right;
    protected int push_pull;


    public SidedConfSyncC2S(FriendlyByteBuf buf) {
        this.up = buf.readInt();
        this.down = buf.readInt();
        this.left = buf.readInt();
        this.right = buf.readInt();
        this.push_pull = buf.readInt();

        this.pos = buf.readBlockPos();
    }

    public SidedConfSyncC2S(int up, int down, int left, int right, int push_pull, BlockPos pos) {
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        this.push_pull = push_pull;

        this.pos = pos;
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(up);
        buf.writeInt(down);
        buf.writeInt(left);
        buf.writeInt(right);
        buf.writeInt(push_pull);

        buf.writeBlockPos(pos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            if (context.getSender().level().getBlockEntity(pos) instanceof ElectricFurnaceTile tile) {
                tile.setUp(up);
                tile.setDown(down);
                tile.setLeft(left);
                tile.setRight(right);
                tile.setPushPull(push_pull);
                tile.setChanged();
            }
        });
        return true;
    }
}
