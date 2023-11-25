package net.zapp.prore.register.items.custom;

import mekanism.common.config.value.CachedIntValue;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemHoldingEnergy extends Item {
    int energy;
    int energyCapacity;
    int inputRate;
    int outputRate;
    public ItemHoldingEnergy(Properties properties, int energyCapacity, int inputRate, int outputRate) {
        super(properties);
        this.energy = energyCapacity;
        this.energyCapacity = energyCapacity;
        this.inputRate = inputRate;
        this.outputRate = outputRate;
    }

    public ItemHoldingEnergy(Properties properties, int energyCapacity, int inputRate, int outputRate, int baseEnergyStored) {
        super(properties);
        this.energy = baseEnergyStored;
        this.energyCapacity = energyCapacity;
        this.inputRate = inputRate;
        this.outputRate = outputRate;
    }

    public int getData(ItemStack stack, String locator) {
        if(stack.hasTag()) {
            return stack.getTagElement("data").getInt(locator);
        } else return 0;
    }

    public void setData(ItemStack stack, String locator, int value) {
        if(stack.hasTag()) {
            stack.getTagElement("data").putInt(locator, value);
        }
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getBarColor(ItemStack p_150901_) {
        return 0x00FF00;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return Math.round(((float) getData(stack, "energy") / getData(stack, "energyCapacity")) * 13);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        if (!stack.hasTag()) {
            stack.addTagElement("data", new CompoundTag());
            stack.getTagElement("data").putInt("energy", energy);
            stack.getTagElement("data").putInt("energyCapacity", energyCapacity);
            stack.getTagElement("data").putInt("inputRate", inputRate);
            stack.getTagElement("data").putInt("outputRate", outputRate);
        }

        super.appendHoverText(stack, level, components, flag);
        components.add(Component.literal("Energy: " + getData(stack, "energy") + "/" + getData(stack, "energyCapacity")));
    }
}