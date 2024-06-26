package net.zapp.prore.register.blocks.custom.machine;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.zapp.prore.register.blocks.custom.machine.electric_furnace.ElectricFurnaceMenu;
import net.zapp.prore.register.blocks.custom.machine.electric_furnace.ElectricFurnaceTile;

import java.util.function.Supplier;

public class MachineEnergySyncS2C {
    private final int energy;
    private final BlockPos pos;

    public MachineEnergySyncS2C(int energy, BlockPos pos) {
        this.energy = energy;
        this.pos = pos;
    }

    public MachineEnergySyncS2C(FriendlyByteBuf buf) {
        this.energy = buf.readInt();
        this.pos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(energy);
        buf.writeBlockPos(pos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            if (Minecraft.getInstance().level.getBlockEntity(pos) instanceof ElectricFurnaceTile tile) {
                tile.setEnergyLevel(energy);
                if (Minecraft.getInstance().player.containerMenu instanceof ElectricFurnaceMenu menu && menu.getBlockEntity().getBlockPos().equals(pos))
                    menu.getBlockEntity().setEnergyLevel(energy);
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