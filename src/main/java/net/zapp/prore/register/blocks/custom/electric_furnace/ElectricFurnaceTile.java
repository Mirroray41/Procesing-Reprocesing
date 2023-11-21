package net.zapp.prore.register.blocks.custom.electric_furnace;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import javax.annotation.Nonnull;
import java.util.Optional;

public class ElectricFurnaceTile extends BlockEntity implements MenuProvider, WorldlyContainer {
    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 100;
    private int energyUsage = 32;

    private int maxEnergyInput = 64;

    private int energyStorageCapacity = 8000;

    private int up;
    private int down;
    private int left;
    private int right;
    private int push_pull;

    private final static byte number_of_inputs = 1;

    private final static byte number_of_outputs = 1;


    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();

    protected NonNullList<ItemStack> items = NonNullList.withSize(8, ItemStack.EMPTY);

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
                }
            }

            @Override
            public int getCount() {
                return 9;
            }
        };
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
        for (LazyOptional<? extends IItemHandler> handler : handlers) handler.invalidate();
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        this.handlers = net.minecraftforge.items.wrapper.SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.WEST, Direction.EAST, Direction.NORTH);
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

    net.minecraftforge.common.util.LazyOptional<? extends net.minecraftforge.items.IItemHandler>[] handlers =
            net.minecraftforge.items.wrapper.SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.EAST, Direction.WEST, Direction.NORTH);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if (side == getFacingDirection().getClockWise() && right > 0) {
                return handlers[3].cast();
            }
            if (side == getFacingDirection().getCounterClockWise() && left > 0) {
                return handlers[2].cast();
            }
            if (side == Direction.UP && up > 0) {
                return handlers[0].cast();
            }
            if (side == Direction.DOWN && down > 0) {
                return handlers[1].cast();
            }
            return handlers[4].cast();
        }
        if (cap == ForgeCapabilities.ENERGY && side == getFacingDirection().getOpposite()) {
            return lazyEnergyHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    public Direction getFacingDirection() {
        return this.getBlockState().getValue(ElectricFurnaceBlock.FACING);
    }

    public int[] getSlotsForFace(Direction p_58363_) {
        if (p_58363_ == Direction.DOWN) {
            return new int[]{1};
        } else if (p_58363_ == Direction.UP) {
            return new int[]{0};
        } else {
            return new int[]{0};
        }
    }

    public boolean canPlaceItemThroughFace(int p_58336_, ItemStack p_58337_, @javax.annotation.Nullable Direction p_58338_) {
        return this.canPlaceItem(p_58336_, p_58337_);
    }

    public boolean canTakeItemThroughFace(int p_58392_, ItemStack p_58393_, Direction p_58394_) {
        if (p_58394_ == Direction.DOWN && p_58392_ == 1) {
            return p_58393_.is(Items.WATER_BUCKET) || p_58393_.is(Items.BUCKET);
        } else {
            return true;
        }
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++)
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if (pLevel.isClientSide()) {
            return;
        }

        System.out.println("tick");

        if(hasRecipe()) {
            System.out.println("has recipe");
            if(hasEnergy(energyStorage)) {
                System.out.println("has energy");
                increaseCraftingProgress();
                energyStorage.extractEnergy(energyUsage, false);
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
    }

    private void craftItem() {
        Optional<ElectricFurnaceRecipe> recipe = getCurrentRecipe();
        ItemStack result = recipe.get().getResultItem(null);

        this.itemHandler.extractItem(INPUT_SLOT, 1, false);

        this.itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(result.getItem(),
                this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + result.getCount()));
    }

    private boolean hasRecipe() {

        Optional<ElectricFurnaceRecipe> recipe = getCurrentRecipe();
        System.out.println(recipe);
        if(recipe.isEmpty()) {
            return false;
        }
        ItemStack result = recipe.get().getResultItem(getLevel().registryAccess());

        return canInsertAmountIntoOutputSlot(result.getCount()) && canInsertItemIntoOutputSlot(result.getItem());
    }

    private static boolean hasEnergy(IEnergyStorage energyStorage) {
        return energyStorage.getEnergyStored() > 0;
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

        System.out.println(this.itemHandler.getStackInSlot(0));

        return this.level.getRecipeManager().getRecipeFor(ElectricFurnaceRecipe.Type.INSTANCE, inventory, level);
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() || this.itemHandler.getStackInSlot(OUTPUT_SLOT).is(item);
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + count <= this.itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
    }

    private void increaseCraftingProgress() {
        progress++;
    }

    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }

    private void resetProgress() {
        progress = 0;
    }
    public int getEnergyUsage() {
        return energyUsage;
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

    @Override
    public int getContainerSize() {
        return this.items.size();
    }

    @Override
    public boolean isEmpty() {
        for(ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getItem(int p_18941_) {
        return this.items.get(p_18941_);
    }

    @Override
    public ItemStack removeItem(int p_18942_, int p_18943_) {
        return ContainerHelper.removeItem(this.items, p_18942_, p_18943_);
    }

    @Override
    public ItemStack removeItemNoUpdate(int p_18951_) {
        return ContainerHelper.takeItem(this.items, p_18951_);
    }

    @Override
    public void setItem(int p_18944_, ItemStack p_18945_) {
        ItemStack itemstack = this.items.get(p_18944_);
        this.items.set(p_18944_, p_18945_);
        if (p_18945_.getCount() > this.getMaxStackSize()) {
            p_18945_.setCount(this.getMaxStackSize());
        }
    }

    @Override
    public boolean stillValid(Player p_18946_) {
        return Container.stillValidBlockEntity(this, p_18946_);
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }
}