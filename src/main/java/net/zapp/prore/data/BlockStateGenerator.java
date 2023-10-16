package net.zapp.prore.data;

import net.minecraft.core.BlockPos;
import net.minecraft.data.PackOutput;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.zapp.prore.ProcesingReprocesing;
import net.zapp.prore.register.blocks.BlockRegister;

public class BlockStateGenerator extends BlockStateProvider {
    public BlockStateGenerator(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, ProcesingReprocesing.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(BlockRegister.TEMPBLOCK);
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}