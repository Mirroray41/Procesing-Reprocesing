package net.zapp.prore.data.LanguageGeneration;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import net.zapp.prore.ProcesingReprocesing;
import net.zapp.prore.register.blocks.BlockRegister;
import net.zapp.prore.register.items.ItemRegister;

public class LanguageGeneratorEN_US extends LanguageProvider {
    public LanguageGeneratorEN_US(PackOutput output) {
        //Change the locale parameter to add different languages!
        super(output, ProcesingReprocesing.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(ItemRegister.TEMPITEM.get(), "Temp Item");
        add(BlockRegister.TEMPBLOCK.get(), "Temp Block");

        add("itemtab.prore_tab","Processing Reprocessing");
    }
}