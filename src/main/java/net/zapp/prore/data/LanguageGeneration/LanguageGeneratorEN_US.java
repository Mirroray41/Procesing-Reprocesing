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
        add("tooltip.shift", "Hold shift for more information");
        add("itemtab.prore_tab","Processing Reprocessing");

        add(ItemRegister.TEMPITEM.get(), "Temp Item");
        add(BlockRegister.TEMPBLOCK.get(), "Temp Block");

        add(ItemRegister.STEEL_NUGGET.get(), "Steel Nugget");
        add(ItemRegister.STEEL_INGOT.get(), "Steel Ingot");

        add(BlockRegister.STEEL_BLOCK.get(), "Steel Block");
        add(BlockRegister.ELECTRIC_FURNACE.get(), "Electric Furnace");

        add(ItemRegister.BATTERY.get(), "Battery");

        add("prore.container.electric_furnace", "Electric Furnace");
        add("prore.info.upgrades", "§7In these slots you can add upgrades to make your machine better!");
        add("prore.info.arguments", "§7In these slots you can add arguments to make your machine do different things!");

        add("prore.port.0", "§8None");

        add("prore.port.1", "§bLight Blue");
        add("prore.port.2", "§3Cyan");
        add("prore.port.3", "§1Blue");
        add("prore.port.4", "§5Purple");

        add("prore.port.5", "§cRed");
        add("prore.port.6", "§6Orange");
        add("prore.port.7", "§dYellow");
        add("prore.port.8", "§dPink");

        add("prore.port.9", "White");

        add("prore.push_pull.0", "§8None");
        add("prore.push_pull.1", "§bPush");
        add("prore.push_pull.2", "§cPull");
        add("prore.push_pull.3", "Both");

        add("prore.configure.up", "§7Configure: §r§nUp:§r ");
        add("prore.configure.down", "§7Configure: §r§nDown:§r ");
        add("prore.configure.left", "§7Configure: §r§nLeft:§r ");
        add("prore.configure.right", "§7Configure: §r§nRight:§r ");
        add("prore.configure.push_pull", "§7Configure: §r§nPush/Pull:§r ");

        add(ItemRegister.BLANK_UPGRADE.get(), "Blank Upgrade");
        add("item.prore.blank_upgrade.desc", "A base for upgrades");
        add(ItemRegister.BLANK_ARGUMENT.get(), "Blank Argument");
        add("item.prore.blank_argument.desc", "A base for arguments");

        add(ItemRegister.SPEED_UPGRADE.get(), "Speed Upgrade");
        add("item.prore.speed_upgrade.desc", "Increases the speed of the machine");
        add("item.prore.speed_upgrade.tooltip.1", "Increases the speed of the machine by 100%");
        add("item.prore.speed_upgrade.tooltip.2", "Increases the power consumption of the machine by 200%");

        add(ItemRegister.EFFICIENCY_UPGRADE.get(), "Efficiency Upgrade");
        add("item.prore.efficiency_upgrade.desc", "Decreases the power consumption of the machine");
        add("item.prore.efficiency_upgrade.tooltip.1", "Decreases the power consumption of the machine by 50%");
        add("item.prore.efficiency_upgrade.tooltip.2", "Decreases the speed of the machine by 25%");

        add(ItemRegister.SMOKING_ARGUMENT.get(), "Smoking Argument");
        add("item.prore.smoking_argument.desc", "Limits the machine to only smoking recipes but improves the speed");
        add("item.prore.smoking_argument.tooltip.1", "Increases the speed of the machine by 100%");
        add("item.prore.smoking_argument.tooltip.2", "Limits the machine to only smoking recipes");
        add(ItemRegister.BLASTING_ARGUMENT.get(), "Blasting Argument");
        add("item.prore.blasting_argument.desc", "Limits the machine to only blasting recipes but improves the speed");
        add("item.prore.blasting_argument.tooltip.1", "Increases the speed of the machine by 100%");
        add("item.prore.blasting_argument.tooltip.2", "Limits the machine to only blasting recipes");
        add(ItemRegister.OVERCLOCK_ARGUMENT.get(), "Overclock Argument");
        add("item.prore.overclock_argument.desc", "Increases the speed of the machine but increases the power consumption");
        add("item.prore.overclock_argument.tooltip.1", "Increases the speed of the machine by a factor of 2");
        add("item.prore.overclock_argument.tooltip.2", "Increases the power consumption of the machine by a factor of 4");

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