package net.zapp.prore.register.blocks.custom;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.zapp.prore.ProcessingReprocessing;
import net.zapp.prore.register.blocks.custom.machine.MachineEnergySyncS2C;
import net.zapp.prore.register.blocks.custom.machine.SidedConfSyncC2S;
import net.zapp.prore.register.blocks.custom.pipes.PipeSyncS2C;

public class Packets {
    private static SimpleChannel INSTANCE;
    private static int packedId = 0;
    private static int id() {
        return packedId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(ProcessingReprocessing.MOD_ID, "packets"))
                .networkProtocolVersion(() -> "1.0").clientAcceptedVersions(s -> true).serverAcceptedVersions(s -> true).simpleChannel();

        INSTANCE = net;

        net.messageBuilder(MachineEnergySyncS2C.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(MachineEnergySyncS2C::new).encoder(MachineEnergySyncS2C::toBytes).consumerMainThread(MachineEnergySyncS2C::handle).add();

        net.messageBuilder(PipeSyncS2C.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PipeSyncS2C::new).encoder(PipeSyncS2C::toBytes).consumerMainThread(PipeSyncS2C::handle).add();

        net.messageBuilder(SidedConfSyncC2S.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SidedConfSyncC2S::new).encoder(SidedConfSyncC2S::toBytes).consumerMainThread(SidedConfSyncC2S::handle).add();

    }



    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToClients(MSG message) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }


}