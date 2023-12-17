package net.zapp.prore.register.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zapp.prore.ProcessingReprocessing;
import net.zapp.prore.register.items.custom.ItemHoldingEnergy;
import net.zapp.prore.register.items.custom.ItemWithTranslatableMultilineTooltip;
import net.zapp.prore.register.items.custom.ItemWithTranslatableTooltip;
import net.zapp.prore.register.items.custom.WrenchItem;

public class ItemRegister {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ProcessingReprocessing.MOD_ID);

    public static final RegistryObject<Item> TEMPITEM = ITEMS.register("tempitem",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> STEEL_INGOT = ITEMS.register("steel_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STEEL_NUGGET = ITEMS.register("steel_nugget",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> CONFIGURATION1 = ITEMS.register("configuration_1",
            () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> CONFIGURATION2 = ITEMS.register("configuration_2",
            () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> CONFIGURATION3 = ITEMS.register("configuration_3",
            () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> CONFIGURATION4 = ITEMS.register("configuration_4",
            () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> CONFIGURATION5 = ITEMS.register("configuration_5",
            () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> CONFIGURATION6 = ITEMS.register("configuration_6",
            () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> CONFIGURATION7 = ITEMS.register("configuration_7",
            () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> CONFIGURATION8 = ITEMS.register("configuration_8",
            () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> CONFIGURATION9 = ITEMS.register("configuration_9",
            () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> WRENCH = ITEMS.register("wrench",
            () -> new WrenchItem(new Item.Properties().durability(120)));

    public static final RegistryObject<Item> BLANK_UPGRADE = ITEMS.register("blank_upgrade",
            () -> new ItemWithTranslatableTooltip(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON), "item.prore.blank_upgrade"));
    public static final RegistryObject<Item> BLANK_ARGUMENT = ITEMS.register("blank_argument",
            () -> new ItemWithTranslatableTooltip(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON), "item.prore.blank_argument"));

    public static final RegistryObject<Item> SPEED_UPGRADE = ITEMS.register("speed_upgrade",
            () -> new ItemWithTranslatableMultilineTooltip(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON), "item.prore.speed_upgrade"));
    public static final RegistryObject<Item> EFFICIENCY_UPGRADE = ITEMS.register("efficiency_upgrade",
            () -> new ItemWithTranslatableMultilineTooltip(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON), "item.prore.efficiency_upgrade"));

    public static final RegistryObject<Item> SMOKING_ARGUMENT = ITEMS.register("smoking_argument",
            () -> new ItemWithTranslatableMultilineTooltip(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON), "item.prore.smoking_argument"));
    public static final RegistryObject<Item> BLASTING_ARGUMENT = ITEMS.register("blasting_argument",
            () -> new ItemWithTranslatableMultilineTooltip(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON), "item.prore.blasting_argument"));
    public static final RegistryObject<Item> OVERCLOCK_ARGUMENT = ITEMS.register("overclock_argument",
            () -> new ItemWithTranslatableMultilineTooltip(new Item.Properties().stacksTo(8).rarity(Rarity.UNCOMMON), "item.prore.overclock_argument"));

    public static final RegistryObject<Item> BATTERY = ITEMS.register("battery",
            () -> new ItemHoldingEnergy(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON), 2000, 64, 64));
    public static final RegistryObject<Item> BATTERY_2 = ITEMS.register("battery2",
            () -> new ItemHoldingEnergy(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC), 8000000, 8000, 8000));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}