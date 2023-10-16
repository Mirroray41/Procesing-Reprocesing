package net.zapp.prore.data.GeneratorRegisters;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.zapp.prore.data.BlockLootTablesGenerator;

import java.util.List;
import java.util.Set;

public class BlockLootTableRegister {
    public static LootTableProvider create(PackOutput output) {
        return new LootTableProvider(output, Set.of(),
                List.of(new LootTableProvider.SubProviderEntry(BlockLootTablesGenerator::new, LootContextParamSets.BLOCK)));
    }
}