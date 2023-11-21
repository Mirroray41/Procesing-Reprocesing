package net.zapp.prore.register.itemtabs;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.zapp.prore.ProcessingReprocessing;
import net.zapp.prore.register.items.ItemRegister;
public class ItemTabRegister {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB,
            ProcessingReprocessing.MOD_ID);

    public static RegistryObject<CreativeModeTab> PRORE_TAB = CREATIVE_MODE_TABS.register("prore_tab", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ItemRegister.TEMPITEM.get()))
                    .title(Component.translatable("itemtab.prore_tab")).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}