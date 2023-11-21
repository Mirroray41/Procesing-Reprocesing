package net.zapp.prore.data;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.zapp.prore.ProcessingReprocessing;
import net.zapp.prore.register.items.ItemRegister;

public class ItemModelGenerator extends ItemModelProvider {
    public ItemModelGenerator(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ProcessingReprocessing.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        miscItem(ItemRegister.TEMPITEM);
        miscItem(ItemRegister.STEEL_INGOT);
        miscItem(ItemRegister.STEEL_NUGGET);
        miscItem(ItemRegister.CONFIGURATION1);
        miscItem(ItemRegister.CONFIGURATION2);
        miscItem(ItemRegister.CONFIGURATION3);
        miscItem(ItemRegister.CONFIGURATION4);
        miscItem(ItemRegister.CONFIGURATION5);
        miscItem(ItemRegister.CONFIGURATION6);
        miscItem(ItemRegister.CONFIGURATION7);
        miscItem(ItemRegister.CONFIGURATION8);
        miscItem(ItemRegister.CONFIGURATION9);
        toolItem(ItemRegister.WRENCH);
    }

    private ItemModelBuilder miscItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(ProcessingReprocessing.MOD_ID,"item/" + item.getId().getPath()));
    }

    private ItemModelBuilder toolItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(ProcessingReprocessing.MOD_ID,"item/" + item.getId().getPath()));
    }
}