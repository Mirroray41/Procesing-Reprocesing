package net.zapp.prore.data.LanguageGeneration;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import net.zapp.prore.ProcessingReprocessing;
import net.zapp.prore.register.blocks.BlockRegister;
import net.zapp.prore.register.items.ItemRegister;

public class LanguageGeneratorEN_US extends LanguageProvider {
    public LanguageGeneratorEN_US(PackOutput output) {
        //Change the locale parameter to add different languages!
        super(output, ProcessingReprocessing.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(ItemRegister.TEMPITEM.get(), "Temp Item");
        add(BlockRegister.TEMPBLOCK.get(), "Temp Block");

        add(ItemRegister.STEEL_NUGGET.get(), "Steel Nugget");
        add(ItemRegister.STEEL_INGOT.get(), "Steel Ingot");

        add(BlockRegister.STEEL_BLOCK.get(), "Steel Block");
        add(BlockRegister.ELECTRIC_FURNACE.get(), "Electric Furnace");

        add("prore.container.electric_furnace", "Electric Furnace");

        add("itemtab.prore_tab","Processing Reprocessing");

        add(ItemRegister.CONFIGURATION1.get(), "Configuration 1");
        add(ItemRegister.CONFIGURATION2.get(), "Configuration 2");
        add(ItemRegister.CONFIGURATION3.get(), "Configuration 3");
        add(ItemRegister.CONFIGURATION4.get(), "Configuration 4");
        add(ItemRegister.CONFIGURATION5.get(), "Configuration 5");
        add(ItemRegister.CONFIGURATION6.get(), "Configuration 6");
        add(ItemRegister.CONFIGURATION7.get(), "Configuration 7");
        add(ItemRegister.CONFIGURATION8.get(), "Configuration 8");
        add(ItemRegister.CONFIGURATION9.get(), "Configuration 9");

        add(ItemRegister.WRENCH.get(), "Wrench");

    }
}