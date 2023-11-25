package net.zapp.prore.register.blocks.custom.electric_furnace;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.zapp.prore.ProcessingReprocessing;
import net.zapp.prore.register.blocks.custom.*;
import net.zapp.prore.register.items.ItemRegister;
import net.zapp.prore.register.items.custom.ItemHoldingEnergy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import javax.annotation.Nonnull;
import javax.swing.text.html.parser.TagElement;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class ElectricFurnaceTile extends BlockEntity implements MenuProvider {
    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 100;
    private int currentEnergyUsage = 32;

    private int maxEnergyInput = 64;

    private int energyStorageCapacity = 8000;

    private int up;
    private int down;
    private int left;
    private int right;
    private int push_pull;

    private float speedModifier = 1f;
    float lastSpeedModifier = 1f;
    private float efficiencyModifier = 1f;

    private final static byte number_of_inputs = 1;

    private final static byte number_of_outputs = 1;


    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();

    public ElectricFurnaceTile(BlockPos pos, BlockState state) {
        super(TileEntityRegister.ELECTRIC_FURNACE.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> ElectricFurnaceTile.this.progress;
                    case 1 -> ElectricFurnaceTile.this.maxProgress;
                    case 2 -> ElectricFurnaceTile.this.getEnergyStorage().getEnergyStored();
                    case 3 -> ElectricFurnaceTile.this.energyStorageCapacity;
                    case 4 -> ElectricFurnaceTile.this.up;
                    case 5 -> ElectricFurnaceTile.this.down;
                    case 6 -> ElectricFurnaceTile.this.left;
                    case 7 -> ElectricFurnaceTile.this.right;
                    case 8 -> ElectricFurnaceTile.this.push_pull;
                    case 9 -> ElectricFurnaceTile.this.currentEnergyUsage;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> ElectricFurnaceTile.this.progress = pValue;
                    case 1 -> ElectricFurnaceTile.this.maxProgress = pValue;
                    case 2 -> ElectricFurnaceTile.this.energyStorage.setEnergy(((ElectricFurnaceTile.this.getEnergyStorage().getEnergyStored() >= 0) ? 1 : 0) * pValue);
                    case 3 -> ElectricFurnaceTile.this.energyStorageCapacity = pValue;
                    case 4 -> ElectricFurnaceTile.this.up = pValue;
                    case 5 -> ElectricFurnaceTile.this.down = pValue;
                    case 6 -> ElectricFurnaceTile.this.left = pValue;
                    case 7 -> ElectricFurnaceTile.this.right = pValue;
                    case 8 -> ElectricFurnaceTile.this.push_pull = pValue;
                    case 9 -> ElectricFurnaceTile.this.currentEnergyUsage = pValue;
                }
            }

            @Override
            public int getCount() {
                return 10;
            }
        };
    }

    private boolean isCompatibleUpgrade(ItemStack stack) {
        if (stack.getItem() == ItemRegister.SPEED_UPGRADE.get()) return true;
        else return stack.getItem() == ItemRegister.EFFICIENCY_UPGRADE.get();
    }
    private boolean isCompatibleArgument(ItemStack stack) {
        if (stack.getItem() == ItemRegister.SMOKING_ARGUMENT.get()) return true;
        else if (stack.getItem() == ItemRegister.BLASTING_ARGUMENT.get()) return true;
        else return stack.getItem() == ItemRegister.OVERCLOCK_ARGUMENT.get();
    }

    private boolean hasItemEnergy(ItemStack stack) {
        if (stack.hasTag() && stack.getTagElement("data") != null) {
            return true;
        }
        return false;
    }

    private final ItemStackHandler itemHandler = new ItemStackHandler(8) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0 -> true;
                case 1 -> false;
                case 2, 3, 4 -> isCompatibleUpgrade(stack);
                case 5, 6 -> isCompatibleArgument(stack);
                case 7 -> (hasItemEnergy(stack));
                default -> super.isItemValid(slot, stack);
            };
        }
    };

    private final CustomEnergyStorage energyStorage = new CustomEnergyStorage(energyStorageCapacity, maxEnergyInput) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            Packets.sendToClients(new EnergySyncS2C(this.energy, getBlockPos()));
        }
    };


    public IEnergyStorage getEnergyStorage() {
        return energyStorage;
    }
    public void setEnergyLevel(int energy) {
        this.energyStorage.setEnergy(energy);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyEnergyHandler = LazyOptional.of(() -> energyStorage);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        lazyItemHandler.cast();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("electric_furnace.inv", itemHandler.serializeNBT());
        nbt.putInt("electric_furnace.progress", this.progress);
        nbt.putInt("electric_furnace.energy", energyStorage.getEnergyStored());
        nbt.putInt("electric_furnace.up", data.get(4));
        nbt.putInt("electric_furnace.down", data.get(5));
        nbt.putInt("electric_furnace.left", data.get(6));
        nbt.putInt("electric_furnace.right", data.get(7));
        nbt.putInt("electric_furnace.push_pull", data.get(8));
        nbt.putInt("electric_furnace.current_energy_usage", data.get(9));
        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("electric_furnace.inv"));
        progress = nbt.getInt("electric_furnace.progress");
        energyStorage.setEnergy(nbt.getInt("electric_furnace.energy"));
        data.set(4, nbt.getInt("electric_furnace.up"));
        data.set(5, nbt.getInt("electric_furnace.down"));
        data.set(6, nbt.getInt("electric_furnace.left"));
        data.set(7, nbt.getInt("electric_furnace.right"));
        data.set(8, nbt.getInt("electric_furnace.push_pull"));
        data.set(9, nbt.getInt("electric_furnace.current_energy_usage"));
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable(ProcessingReprocessing.MOD_ID + ".container.electric_furnace");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        Packets.sendToClients(new EnergySyncS2C(this.energyStorage.getEnergyStored(), worldPosition));
        return new ElectricFurnaceMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    private final Map<Integer, LazyOptional<WrappedHandler>> sidedWrappedHandlerMap =
            Map.of(1, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 0, (i, s) -> true)),
                    2, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 0, (i, s) -> true)),
                    3, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 0, (i, s) -> true)),
                    4, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 0, (i, s) -> true)),
                    5, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 1, (i, s) -> false)),
                    6, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 1, (i, s) -> false)),
                    7, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 1, (i, s) -> false)),
                    8, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 1, (i, s) -> false)),
                    9, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 0 || i == 1, (i, s) -> true)));

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if (side == null) {
                return lazyItemHandler.cast();
            }

            if (side == Direction.DOWN && down > 0) {
                return sidedWrappedHandlerMap.get(down).cast();
            } else if (side == Direction.UP && up > 0) {
                return sidedWrappedHandlerMap.get(up).cast();
            } else if (side == getFacingDirection().getCounterClockWise() && right > 0) {
                return sidedWrappedHandlerMap.get(right).cast();
            } else if (side == getFacingDirection().getClockWise() && left > 0) {
                return sidedWrappedHandlerMap.get(left).cast();
            }
        }

        if (cap == ForgeCapabilities.ENERGY && side == getFacingDirection().getOpposite()) {
            return lazyEnergyHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    public Direction getFacingDirection() {
        return this.getBlockState().getValue(ElectricFurnaceBlock.FACING);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++)
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    private void setModifiers(SimpleContainer inv) {
        efficiencyModifier = 1f;
        speedModifier = 1f;
        for (int i = 2; i < 5; i++) {
            if (inv.getItem(i).is(ItemRegister.SPEED_UPGRADE.get() )) {
                efficiencyModifier += efficiencyModifier;
                speedModifier -= speedModifier / 2;
            } else if (inv.getItem(i).is(ItemRegister.EFFICIENCY_UPGRADE.get())) {
                efficiencyModifier -= efficiencyModifier / 2;
                speedModifier += speedModifier / 2;
            }
        }
        for (int i = 5; i < 7; i++) {
            if (inv.getItem(i).is(ItemRegister.SMOKING_ARGUMENT.get())) {
                speedModifier -= speedModifier / 2;
            } else if (inv.getItem(i).is(ItemRegister.BLASTING_ARGUMENT.get())) {
                speedModifier -= speedModifier / 2;
            } else if (inv.getItem(i).is(ItemRegister.OVERCLOCK_ARGUMENT.get())) {
                speedModifier = speedModifier / 2;
                efficiencyModifier = efficiencyModifier * 4;
            }
        }
    }


    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if (pLevel.isClientSide()) {
            return;
        }
        ElectricFurnaceTile tile = (ElectricFurnaceTile) pLevel.getBlockEntity(pPos);

        SimpleContainer inventory = new SimpleContainer(tile.itemHandler.getSlots());
        for (int i = 0; i < tile.itemHandler.getSlots(); i++)
            inventory.setItem(i, tile.itemHandler.getStackInSlot(i));
        setModifiers(inventory);

        if(hasRecipe(tile)) {
            if(hasEnergy(energyStorage, data.get(9))) {
                increaseCraftingProgress();
                energyStorage.extractEnergy(data.get(9), false);
                setChanged(pLevel, pPos, pState);
                pState = pState.setValue(ElectricFurnaceBlock.WORKING, true);

            } else {
                pState = pState.setValue(ElectricFurnaceBlock.WORKING, false);
            }

            if(hasProgressFinished()) {
                craftItem();
                resetProgress();
            }

        } else {
            resetProgress();
            pState = pState.setValue(ElectricFurnaceBlock.WORKING, false);

        }
        level.setBlock(pPos, pState, 3);

        if (data.get(2) < data.get(3)) {
            ItemStack batteryStack = this.itemHandler.getStackInSlot(7);
            CompoundTag data = batteryStack.getTagElement("data");
            if (hasItemEnergy(batteryStack)) {
                if (data.getInt("energy") > 0) {
                    data.putInt("energy", data.getInt("energy") - data.getInt("outputRate"));
                    energyStorage.receiveEnergy(data.getInt("outputRate"), false);
                    if (data.getInt("energy") < 0) {
                        data.putInt("energy", 0);
                    }
                }
            }
        }
    }

    private void craftItem() {
        Optional<ElectricFurnaceRecipe> recipe = getCurrentRecipe();
        ItemStack result = recipe.get().getResultItem(null);

        this.itemHandler.extractItem(INPUT_SLOT, 1, false);

        this.itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(result.getItem(),
                this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + result.getCount()));
    }

    public boolean hasRecipe(ElectricFurnaceTile tile) {

        SimpleContainer inventory = new SimpleContainer(tile.itemHandler.getSlots());
        for (int i = 0; i < tile.itemHandler.getSlots(); i++)
            inventory.setItem(i, tile.itemHandler.getStackInSlot(i));

        Optional<ElectricFurnaceRecipe> recipe = getCurrentRecipe();
        if(recipe.isEmpty()) {
            return false;
        }

        boolean hasRecipe = recipe.isPresent() && hasEnoughIngredients(recipe.get(), inventory, level)
                && canOutput(inventory, recipe.get().getResultItem(level.registryAccess()), 1) && canOutputAmount(inventory, 1);

        if (hasRecipe) {
            if (speedModifier != lastSpeedModifier) {
                tile.data.set(0, Math.round(tile.data.get(0) * (speedModifier / lastSpeedModifier)));
                lastSpeedModifier = speedModifier;
            }
            tile.data.set(1, Math.round(recipe.get().getProcessingTime() * speedModifier));

        }
        tile.data.set(9, Math.round(recipe.get().getPowerUsageTick() * efficiencyModifier));
        return hasRecipe;
    }

    private static boolean hasEnergy(IEnergyStorage energyStorage, int CurrentUsage) {
        return energyStorage.getEnergyStored() >= CurrentUsage;
    }
    private static <T extends Recipe<SimpleContainer>> boolean hasEnoughIngredients(T recipe, SimpleContainer inventory, Level level) {
        return recipe.matches(inventory, level);
    }
    private static boolean canOutput(SimpleContainer inventory, ItemStack stack, int outputSlotIndex) {
        return inventory.getItem(outputSlotIndex).getItem() == stack.getItem() || inventory.getItem(outputSlotIndex).isEmpty();
    }
    private static boolean canOutputAmount(SimpleContainer inventory, int outputSlotIndex) {
        return inventory.getItem(outputSlotIndex).getMaxStackSize() > inventory.getItem(outputSlotIndex).getCount();
    }

    private Optional<ElectricFurnaceRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));
        }

        return this.level.getRecipeManager().getRecipeFor(ElectricFurnaceRecipe.Type.INSTANCE, inventory, level);
    }

    private void increaseCraftingProgress() {
        progress++;
    }

    public boolean hasProgressFinished() {
        return progress >= maxProgress;
    }

    private void resetProgress() {
        progress = 0;
    }

    public byte getNumberOfInputs() {
        return number_of_inputs;
    }

    public byte getNumberOfOutputs() {
        return number_of_outputs;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    public void setUp(int up) {
        if (up > 9) up = 9;
        if (up < 0) up = 0;
        data.set(4, up);
    }
    public void setDown(int down) {
        if (down > 9) down = 9;
        if (down < 0) down = 0;
        data.set(5, down);
    }
    public void setLeft(int left) {
        if (left > 9) left = 9;
        if (left < 0) left = 0;
        data.set(6, left);
    }
    public void setRight(int right) {
        if (right > 9) right = 9;
        if (right < 0) right = 0;
        data.set(7, right);
    }
    public void setPushPull(int push_pull) {
        if (push_pull > 3) push_pull = 3;
        if (push_pull < 0) push_pull = 0;
        data.set(8, push_pull);
    }

    public void syncClientToServer(BlockPos pos) {
        Packets.sendToServer(new SidedConfSyncC2S(data.get(4), data.get(5), data.get(6), data.get(7), data.get(8), pos));
    }
}