package net.zapp.prore.data;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.zapp.prore.ProcesingReprocesing;
import net.zapp.prore.register.items.ItemRegister;

public class ItemModelGenerator extends ItemModelProvider {
    public ItemModelGenerator(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ProcesingReprocesing.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        miscItem(ItemRegister.TEMPITEM);
    }

    private ItemModelBuilder miscItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(ProcesingReprocesing.MOD_ID,"item/" + item.getId().getPath()));
    }

    private ItemModelBuilder toolItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(ProcesingReprocesing.MOD_ID,"item/" + item.getId().getPath()));
    }
}