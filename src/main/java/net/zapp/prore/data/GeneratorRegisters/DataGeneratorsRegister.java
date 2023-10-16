package net.zapp.prore.data.GeneratorRegisters;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.zapp.prore.ProcesingReprocesing;
import net.zapp.prore.data.BlockStateGenerator;
import net.zapp.prore.data.ItemModelGenerator;
import net.zapp.prore.data.LanguageGeneration.LanguageGeneratorEN_US;
import net.zapp.prore.data.RecipeGenerator;

@Mod.EventBusSubscriber(modid = ProcesingReprocesing.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGeneratorsRegister {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(true, new RecipeGenerator(packOutput));
        generator.addProvider(true, BlockLootTableRegister.create(packOutput));
        generator.addProvider(true, new BlockStateGenerator(packOutput, existingFileHelper));
        generator.addProvider(true, new ItemModelGenerator(packOutput, existingFileHelper));
        generator.addProvider(true, new LanguageGeneratorEN_US(packOutput));
    }
}