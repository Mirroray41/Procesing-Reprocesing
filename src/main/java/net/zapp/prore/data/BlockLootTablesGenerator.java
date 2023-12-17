package net.zapp.prore.data;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import net.zapp.prore.register.blocks.BlockRegister;

import java.util.Set;

public class BlockLootTablesGenerator extends BlockLootSubProvider {
    public BlockLootTablesGenerator() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        dropSelf(BlockRegister.TEMPBLOCK.get());
        dropSelf(BlockRegister.STEEL_BLOCK.get());
        dropSelf(BlockRegister.ELECTRIC_FURNACE.get());
        dropSelf(BlockRegister.BATTERY_CASING.get());
        dropSelf(BlockRegister.BATTERY_CONTROLLER.get());
        dropSelf(BlockRegister.BATTERY_CASING_GLASS.get());
        dropSelf(BlockRegister.LEAD_BLOCK.get());
        dropSelf(BlockRegister.BATTERY_ANODE_CONNECTOR.get());
        dropSelf(BlockRegister.BATTERY_CATHODE_CONNECTOR.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return BlockRegister.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}