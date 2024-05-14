package net.zapp.prore.register.blocks;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zapp.prore.ProcessingReprocessing;
import net.zapp.prore.register.blocks.custom.machine.battery_controller.BatteryControllerBlock;
import net.zapp.prore.register.blocks.custom.machine.electric_furnace.ElectricFurnaceBlock;
import net.zapp.prore.register.blocks.custom.pipes.BasePipeBlock;
import net.zapp.prore.register.blocks.custom.pipes.Energy.EnergyPipeBlock;
import net.zapp.prore.register.blocks.custom.pipes.PipeBlockWithoutEntity;
import net.zapp.prore.register.items.ItemRegister;

import java.util.function.Supplier;

public class BlockRegister {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, ProcessingReprocessing.MOD_ID);

    public static final RegistryObject<Block> TEMPBLOCK = registerBlock("tempblock",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.SLIME_BLOCK)
                    .strength(0.1f)));

    public static final RegistryObject<Block> STEEL_BLOCK = registerBlock("steel_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .strength(0.1f)));
    public static final RegistryObject<Block> LEAD_BLOCK = registerBlock("lead_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .strength(0.1f)));

    public static final RegistryObject<Block> ELECTRIC_FURNACE = registerBlock("electric_furnace",
            () -> new ElectricFurnaceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .strength(0.1f).lightLevel(state -> state.getValue(ElectricFurnaceBlock.WORKING) ? 15 : 0).noOcclusion()));

    public static final RegistryObject<Block> BATTERY_CASING = registerBlock("battery_casing",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .strength(0.1f)));
    public static final RegistryObject<Block> BATTERY_CASING_GLASS = registerBlock("battery_casing_glass",
            () ->  new GlassBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .strength(0.1F).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> BATTERY_CATHODE_CONNECTOR = registerBlock("battery_cathode_connector",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .strength(0.1f)));
    public static final RegistryObject<Block> BATTERY_ANODE_CONNECTOR = registerBlock("battery_anode_connector",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)
                    .strength(0.1f)));

    public static final RegistryObject<Block> BATTERY_CONTROLLER = registerBlock("battery_controller",
            () -> new BatteryControllerBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));

    public static final RegistryObject<Block> PIPE = registerBlock("pipe",
            () -> new BasePipeBlock((float) 3 / 16, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));

    public static final RegistryObject<Block> ENERGY_PIPE = registerBlock("energy_pipe",
            () -> new EnergyPipeBlock((float) 3 / 16, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));



    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<T> registerBlockWithoutBlockItem(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ItemRegister.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}