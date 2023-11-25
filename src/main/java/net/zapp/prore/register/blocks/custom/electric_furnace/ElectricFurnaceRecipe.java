package net.zapp.prore.register.blocks.custom.electric_furnace;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.zapp.prore.ProcessingReprocessing;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class ElectricFurnaceRecipe implements Recipe<SimpleContainer> {
    private NonNullList<Ingredient> ingredients;
    private NonNullList<Integer> ingredientCounts;
    private final ItemStack output;
    private int outputAmount;
    private final ResourceLocation id;
    private int processingTime;
    private int powerUsage;

    public ElectricFurnaceRecipe(NonNullList<Ingredient> ingredients, NonNullList<Integer> ingredientCounts,  ItemStack output, int outputAmount, ResourceLocation id, int processingTime, int powerUsage) {
        this.ingredients = ingredients;
        this.ingredientCounts = ingredientCounts;
        this.output = output;
        this.outputAmount = outputAmount;
        this.id = id;
        this.processingTime = processingTime;
        this.powerUsage = powerUsage;
    }

    public ElectricFurnaceRecipe(NonNullList<Ingredient> ingredients, ItemStack output, ResourceLocation id) {
        this.ingredients = ingredients;
        this.ingredientCounts = NonNullList.withSize(1, 1);
        this.output = output;
        this.outputAmount = 1;
        this.id = id;
        this.processingTime = 100;
        this.powerUsage = 32;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if (pLevel.isClientSide()) return false;
        int numIngredients = ingredients.size();

        for (int i = 0; i < numIngredients; i++) {
            if (test(pContainer, i, i, ingredientCounts.get(i))) continue;
            else return false;
        }
        return true;
    }

    private boolean test(SimpleContainer container, int ingNum, int slot, int amount) {
        return ingredients.get(ingNum).test(container.getItem(slot)) && container.getItem(slot).getCount() >= amount;
    }
    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<ElectricFurnaceRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "electric_furnace";
    }

    public static class Serializer implements RecipeSerializer<ElectricFurnaceRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(ProcessingReprocessing.MOD_ID, "electric_furnace");

        @Override
        public ElectricFurnaceRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {

            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);
            NonNullList<Integer> amounts = NonNullList.withSize(1, 1);
            for(int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
                amounts.set(i, GsonHelper.getAsInt(ingredients.get(i).getAsJsonObject(), "count", 1));
            }

            JsonArray details = pSerializedRecipe.getAsJsonArray("details");
            int processingTime = GsonHelper.getAsInt(details.get(0).getAsJsonObject(), "time", 100);
            int powerUsage = GsonHelper.getAsInt(details.get(1).getAsJsonObject(), "power", 0);

            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));
            int amount = GsonHelper.getAsInt(pSerializedRecipe.get("output").getAsJsonObject(), "count", 1);

            return new ElectricFurnaceRecipe(inputs, amounts, output, amount, pRecipeId, processingTime, powerUsage);
        }

        @Override
        public @Nullable ElectricFurnaceRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);

            for(int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(pBuffer));
            }

            ItemStack output = pBuffer.readItem();
            return new ElectricFurnaceRecipe(inputs, output, pRecipeId);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, ElectricFurnaceRecipe pRecipe) {
            pBuffer.writeInt(pRecipe.ingredients.size());

            for (Ingredient ingredient : pRecipe.getIngredients()) {
                ingredient.toNetwork(pBuffer);
            }

            pBuffer.writeItemStack(pRecipe.getResultItem(null), false);
        }
    }
    public int getPowerUsageTick() {
        return powerUsage;
    }
    public int getProcessingTime() {
        return processingTime;
    }
}