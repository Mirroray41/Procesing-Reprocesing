package net.zapp.prore.register.blocks.custom;


import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zapp.prore.ProcessingReprocessing;
import net.zapp.prore.register.blocks.BlockRegister;
import net.zapp.prore.register.blocks.custom.machine.electric_furnace.ElectricFurnaceTile;
import net.zapp.prore.register.blocks.custom.pipes.Energy.EnergyPipeTile;

public class TileEntityRegister {
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ProcessingReprocessing.MOD_ID);

    public static final RegistryObject<BlockEntityType<ElectricFurnaceTile>> ELECTRIC_FURNACE = TILE_ENTITIES.register("electric_furnace_entity",
            () -> BlockEntityType.Builder.of(ElectricFurnaceTile::new, BlockRegister.ELECTRIC_FURNACE.get()).build(null));

    public static final RegistryObject<BlockEntityType<EnergyPipeTile>> ENERGY_PIPE = TILE_ENTITIES.register("energy_pipe_entity",
            () -> BlockEntityType.Builder.of(EnergyPipeTile::new, BlockRegister.ENERGY_PIPE.get()).build(null));

    public static void register(IEventBus eventBus) {
        TILE_ENTITIES.register(eventBus);
    }
}