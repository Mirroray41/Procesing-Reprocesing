package net.zapp.prore.register.blocks.custom.pipes.Energy;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.zapp.prore.register.blocks.custom.TileEntityRegister;
import net.zapp.prore.register.blocks.custom.CustomEnergyStorage;
import net.zapp.prore.register.blocks.custom.Packets;
import net.zapp.prore.register.blocks.custom.pipes.PipeData;
import net.zapp.prore.register.blocks.custom.pipes.PipeSyncS2C;

import javax.annotation.Nonnull;

public class EnergyPipeTile extends BlockEntity {

    protected final PipeData data;
    private int energyStorageCapacity = 200;
    private int rate = 32;
    private int up = 0;
    private int down = 0;
    private int north = 0;
    private int south = 0;
    private int west = 0;
    private int east = 0;

    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();


    public EnergyPipeTile(BlockPos p_155229_, BlockState p_155230_) {
        super(TileEntityRegister.ENERGY_PIPE.get(), p_155229_, p_155230_);
        this.data = new PipeData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> EnergyPipeTile.this.getEnergyStorage().getEnergyStored();
                    case 1 -> EnergyPipeTile.this.energyStorageCapacity;
                    case 2 -> EnergyPipeTile.this.rate;
                    case 3 -> EnergyPipeTile.this.up;
                    case 4 -> EnergyPipeTile.this.down;
                    case 5 -> EnergyPipeTile.this.north;
                    case 6 -> EnergyPipeTile.this.south;
                    case 7 -> EnergyPipeTile.this.west;
                    case 8 -> EnergyPipeTile.this.east;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> EnergyPipeTile.this.getEnergyStorage().setEnergy(((EnergyPipeTile.this.getEnergyStorage().getEnergyStored() >= 0) ? 1 : 0) * pValue);
                    case 1 -> EnergyPipeTile.this.energyStorageCapacity = pValue;
                    case 2 -> EnergyPipeTile.this.rate = pValue;
                    case 3 -> EnergyPipeTile.this.up = pValue;
                    case 4 -> EnergyPipeTile.this.down = pValue;
                    case 5 -> EnergyPipeTile.this.north = pValue;
                    case 6 -> EnergyPipeTile.this.south = pValue;
                    case 7 -> EnergyPipeTile.this.west = pValue;
                    case 8 -> EnergyPipeTile.this.east = pValue;
                }
            }

            @Override
            public int getCount() {
                return 9;
            }
        };
    }
    private final CustomEnergyStorage energyStorage = new CustomEnergyStorage(energyStorageCapacity, rate) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            Packets.sendToClients(new PipeSyncS2C(this.energy, getBlockPos(), data.get(3), data.get(4), data.get(5), data.get(6), data.get(7), data.get(8)));
        }
        
        @Override
        public boolean canReceive() {
            return true;
        }

        @Override
        public boolean canExtract() {
            return true;
        }
    };

    public CustomEnergyStorage getEnergyStorage() {
        return energyStorage;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyEnergyHandler = LazyOptional.of(() -> energyStorage);
    }


    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.putInt("energy_pipe.energy", energyStorage.getEnergyStored());
        nbt.putInt("energy_pipe.capacity", data.get(1));
        nbt.putInt("energy_pipe.rate", data.get(2));
        nbt.putInt("energy_pipe.up", data.get(3));
        nbt.putInt("energy_pipe.down", data.get(4));
        nbt.putInt("energy_pipe.north", data.get(5));
        nbt.putInt("energy_pipe.south", data.get(6));
        nbt.putInt("energy_pipe.west", data.get(7));
        nbt.putInt("energy_pipe.east", data.get(8));
        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        energyStorage.setEnergy(nbt.getInt("energy_pipe.energy"));
        data.set(1, nbt.getInt("energy_pipe.capacity"));
        data.set(2, nbt.getInt("energy_pipe.rate"));
        data.set(3, nbt.getInt("energy_pipe.up"));
        data.set(4, nbt.getInt("energy_pipe.down"));
        data.set(5, nbt.getInt("energy_pipe.north"));
        data.set(6, nbt.getInt("energy_pipe.south"));
        data.set(7, nbt.getInt("energy_pipe.west"));
        data.set(8, nbt.getInt("energy_pipe.east"));
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == ForgeCapabilities.ENERGY && side == null) {
            return lazyEnergyHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if (pLevel.isClientSide()) {
            return;
        }


        if (pLevel.getBlockEntity(pPos.above()) != null) {
            pLevel.getBlockEntity(pPos.above()).getCapability(ForgeCapabilities.ENERGY, null).ifPresent(energy -> {
                if (energy.canReceive() && energy.getEnergyStored() < energy.getMaxEnergyStored()) {
                    if (energyStorage.getEnergyStored() > 0) {
                        this.energyStorage.extractEnergy(1, false);
                        energy.receiveEnergy(1, false);
                    }
                }
            });
            pLevel.getBlockEntity(pPos.above()).getCapability(ForgeCapabilities.ENERGY, Direction.DOWN).ifPresent(energy -> {
                if (energy.canReceive() && energy.getEnergyStored() < energy.getMaxEnergyStored()) {
                    if (energyStorage.getEnergyStored() > 0) {
                        this.energyStorage.extractEnergy(1, false);
                        energy.receiveEnergy(1, false);
                    }
                }
            });
        }

        if (pLevel.getBlockEntity(pPos.below()) != null && data.get(4) == 1) {
            pLevel.getBlockEntity(pPos.below()).getCapability(ForgeCapabilities.ENERGY, null).ifPresent(energy -> {
                if (energy.canExtract() && this.energyStorage.getEnergyStored() < this.energyStorage.getMaxEnergyStored() && energy.getEnergyStored() > 0) {
                    energy.extractEnergy(1, false);
                    this.energyStorage.receiveEnergy(1, false);
                }
            });
            pLevel.getBlockEntity(pPos.below()).getCapability(ForgeCapabilities.ENERGY, Direction.UP).ifPresent(energy -> {
                if (energy.canExtract() && this.energyStorage.getEnergyStored() < this.energyStorage.getMaxEnergyStored() && energy.getEnergyStored() > 0) {
                    energy.extractEnergy(1, false);
                    this.energyStorage.receiveEnergy(1, false);
                }
            });
        }
    }

    private void giveEnergy(Level pLevel, BlockPos pPos, Direction pDirection) {
        if (pLevel.getBlockEntity(pPos.relative(pDirection)) != null) {
            pLevel.getBlockEntity(pPos.relative(pDirection)).getCapability(ForgeCapabilities.ENERGY, pDirection.getOpposite()).ifPresent(energy -> {
                if (energy.canReceive()) {
                    if (this.energyStorage.getEnergyStored() > energy.getMaxEnergyStored()) {
                        int energyToTransfer = this.energyStorage.getEnergyStored() - energy.getMaxEnergyStored() / 2;
                        energy.receiveEnergy(energyStorage.extractEnergy(energyToTransfer, true), true);
                    }
                }
            });
        }
    }


    public void setEnergyLevel(int energy) {
        this.energyStorage.setEnergy(energy);
    }

    public PipeData getNewData() {
        return data;
    }

    public void setUp(int up) {
        if (up > 2) up = 2;
        if (up < 0) up = 0;
        data.set(3, up);
    }
    public void setDown(int down) {
        if (down > 9) down = 9;
        if (down < 0) down = 0;
        data.set(4, down);
    }
    public void setNorth(int north) {
        if (north > 9) north = 9;
        if (north < 0) north = 0;
        data.set(5, north);
    }
    public void setSouth(int south) {
        if (south > 2) south = 0;
        if (south < 0) south = 0;
        data.set(6, south);
    }

    public void setEast(int east) {
        if (east > 9) east = 9;
        if (east < 0) east = 0;
        data.set(7, east);
    }
    public void setWest(int west) {
        if (west > 9) west = 9;
        if (west < 0) west = 0;
        data.set(8, west);
    }
}
