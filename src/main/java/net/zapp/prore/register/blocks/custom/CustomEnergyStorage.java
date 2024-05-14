package net.zapp.prore.register.blocks.custom;

import net.minecraftforge.energy.EnergyStorage;

public abstract class CustomEnergyStorage extends EnergyStorage {
    public CustomEnergyStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        if (!canExtract())
            return 0;

        int extractedEnergy = (int) Math.min(energy, Math.min(Math.pow(2, 32), maxExtract));
        if (!simulate)
            energy -= extractedEnergy;

        if (extractedEnergy != 0) onEnergyChanged();

        return extractedEnergy;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        if (!canReceive())
            return 0;

        int receivedEnergy = (int) Math.min(capacity - energy, Math.min(Math.pow(2, 32), maxReceive));
        if (!simulate)
            energy += receivedEnergy;
        if (receivedEnergy != 0) onEnergyChanged();

        return receivedEnergy;
    }

    public int setEnergy(int energy) {
        this.energy = energy;
        return energy;
    }

    public abstract void onEnergyChanged();

    @Override
    public boolean canReceive() {
        return super.canReceive();
    }

    @Override
    public boolean canExtract() {
        return super.canExtract();
    }
}