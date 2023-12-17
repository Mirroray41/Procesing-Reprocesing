package net.zapp.prore.register.blocks;


import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.zapp.prore.ProcessingReprocessing;
import net.zapp.prore.register.blocks.custom.machine.TileEntityRegister;
import net.zapp.prore.register.blocks.custom.machine.electric_furnace.ElectricFurnaceTileRenderer;

@Mod.EventBusSubscriber(modid = ProcessingReprocessing.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BlockRenderTypeRegister{
    @SubscribeEvent
    public static void register(FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(BlockRegister.BATTERY_CASING_GLASS.get(), RenderType.translucent());
    }

    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(TileEntityRegister.ELECTRIC_FURNACE.get(), ElectricFurnaceTileRenderer::new);
    }
}
