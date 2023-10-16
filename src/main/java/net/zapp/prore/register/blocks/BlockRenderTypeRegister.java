package net.zapp.prore.register.blocks;


import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.zapp.prore.ProcesingReprocesing;

@Mod.EventBusSubscriber(modid = ProcesingReprocesing.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BlockRenderTypeRegister{
    @SubscribeEvent
    public static void register(FMLClientSetupEvent event) {
    }
}
