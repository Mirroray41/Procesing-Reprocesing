package net.zapp.prore;

import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.zapp.prore.register.blocks.BlockRegister;
import net.zapp.prore.register.blocks.custom.MenuRegister;
import net.zapp.prore.register.blocks.custom.Packets;
import net.zapp.prore.register.blocks.custom.RecipeRegister;
import net.zapp.prore.register.blocks.custom.TileEntityRegister;
import net.zapp.prore.register.blocks.custom.electric_furnace.ElectricFurnaceScreen;
import net.zapp.prore.register.items.ItemRegister;
import net.zapp.prore.register.itemtabs.ItemTabRegister;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ProcessingReprocessing.MOD_ID)
public class ProcessingReprocessing {
    public static final String MOD_ID = "prore";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ProcessingReprocessing() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ItemRegister.register(modEventBus);
        BlockRegister.register(modEventBus);
        ItemTabRegister.register(modEventBus);

        MenuRegister.register(modEventBus);
        TileEntityRegister.register(modEventBus);
        RecipeRegister.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        Packets.register();
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTab() == ItemTabRegister.PRORE_TAB.get()) {
            event.accept(BlockRegister.TEMPBLOCK.get());
            event.accept(ItemRegister.TEMPITEM.get());

            event.accept(BlockRegister.STEEL_BLOCK.get());
            event.accept(ItemRegister.STEEL_INGOT.get());
            event.accept(ItemRegister.STEEL_NUGGET.get());

            event.accept(BlockRegister.ELECTRIC_FURNACE.get());

            event.accept(ItemRegister.WRENCH);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(MenuRegister.ELECTRIC_FURNACE.get(), ElectricFurnaceScreen::new);

        }
    }
}