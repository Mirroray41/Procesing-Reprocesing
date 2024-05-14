package net.zapp.prore.register.blocks.custom;


import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zapp.prore.ProcessingReprocessing;
import net.zapp.prore.register.blocks.custom.machine.electric_furnace.ElectricFurnaceMenu;

public class MenuRegister {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, ProcessingReprocessing.MOD_ID);

    public static final RegistryObject<MenuType<ElectricFurnaceMenu>> ELECTRIC_FURNACE = registerMenuType(ElectricFurnaceMenu::new, "electric_furnace_menu");


    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory, String name) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }
    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}