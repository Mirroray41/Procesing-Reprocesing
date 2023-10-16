package net.zapp.prore.data;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.zapp.prore.ProcesingReprocesing;
import net.zapp.prore.register.blocks.BlockRegister;
import net.zapp.prore.register.items.ItemRegister;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class RecipeGenerator extends RecipeProvider implements IConditionBuilder {
    public RecipeGenerator(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {

        nineBlockStorageRecipes(consumer, RecipeCategory.BUILDING_BLOCKS, ItemRegister.TEMPITEM.get(), RecipeCategory.MISC,
                BlockRegister.TEMPBLOCK.get());

        /*ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.STRING, 4)
                .requires(ItemRegister.COTTON_BALL.get(), 3)
                .unlockedBy("has_cotton", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ItemRegister.COTTON_BALL.get()).build()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegister.FINE_GRAVEL_DUST.get(), 4)
                .requires(BlockRegister.FINE_GRAVEL_BLOCK.get())
                .unlockedBy("has_fine_gravel", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(BlockRegister.FINE_GRAVEL_BLOCK.get()).build()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, BlockRegister.FINE_GRAVEL_BLOCK.get())
                .requires(ItemRegister.FINE_GRAVEL_DUST.get(), 4)
                .unlockedBy("has_fine_gravel", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(BlockRegister.FINE_GRAVEL_BLOCK.get()).build()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegister.SMALL_COPPER_DUST.get(), 9).requires(ItemRegister.COPPER_DUST.get())
                .unlockedBy("has_copper_dust", inventoryTrigger(ItemPredicate.Builder.item().of(ItemRegister.COPPER_DUST.get()).build()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegister.SMALL_IRON_DUST.get(), 9).requires(ItemRegister.IRON_DUST.get())
                .unlockedBy("has_iron_dust", inventoryTrigger(ItemPredicate.Builder.item().of(ItemRegister.IRON_DUST.get()).build()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegister.SMALL_GOLD_DUST.get(), 9).requires(ItemRegister.GOLD_DUST.get())
                .unlockedBy("has_gold_dust", inventoryTrigger(ItemPredicate.Builder.item().of(ItemRegister.GOLD_DUST.get()).build()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegister.SMALL_GLOWSTONE_DUST.get(), 9).requires(Items.GLOWSTONE_DUST)
                .unlockedBy("has_glowstone_dust", inventoryTrigger(ItemPredicate.Builder.item().of(Items.GLOWSTONE_DUST).build()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegister.SMALL_REDSTONE_DUST.get(), 9).requires(Items.REDSTONE)
                .unlockedBy("has_redstone", inventoryTrigger(ItemPredicate.Builder.item().of(Items.REDSTONE).build()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegister.SMALL_NETHERITE_SCRAP_DUST.get(), 9).requires(ItemRegister.NETHERITE_SCRAP_DUST.get())
                .unlockedBy("has_netherite_scrap_dust", inventoryTrigger(ItemPredicate.Builder.item().of(ItemRegister.NETHERITE_SCRAP_DUST.get()).build()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegister.COPPER_DUST.get()).requires(ItemRegister.SMALL_COPPER_DUST.get(), 9)
                .unlockedBy("has_small_copper_dust", inventoryTrigger(ItemPredicate.Builder.item().of(ItemRegister.SMALL_COPPER_DUST.get()).build()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegister.IRON_DUST.get()).requires(ItemRegister.SMALL_IRON_DUST.get(), 9)
                .unlockedBy("has_small_iron_dust", inventoryTrigger(ItemPredicate.Builder.item().of(ItemRegister.SMALL_IRON_DUST.get()).build()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegister.GOLD_DUST.get()).requires(ItemRegister.SMALL_GOLD_DUST.get(), 9)
                .unlockedBy("has_small_gold_dust", inventoryTrigger(ItemPredicate.Builder.item().of(ItemRegister.SMALL_GOLD_DUST.get()).build()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.GLOWSTONE_DUST).requires(ItemRegister.SMALL_GLOWSTONE_DUST.get(), 9)
                .unlockedBy("has_small_glowstone_dust", inventoryTrigger(ItemPredicate.Builder.item().of(ItemRegister.SMALL_GLOWSTONE_DUST.get()).build()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.REDSTONE).requires(ItemRegister.SMALL_REDSTONE_DUST.get(), 9)
                .unlockedBy("has_small_redstone_dust", inventoryTrigger(ItemPredicate.Builder.item().of(ItemRegister.SMALL_REDSTONE_DUST.get()).build()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemRegister.NETHERITE_SCRAP_DUST.get()).requires(ItemRegister.SMALL_NETHERITE_SCRAP_DUST.get(), 9)
                .unlockedBy("has_small_netherite_scrap_dust", inventoryTrigger(ItemPredicate.Builder.item().of(ItemRegister.SMALL_NETHERITE_SCRAP_DUST.get()).build()))
                .save(consumer);
        smelt(consumer, ItemRegister.COPPER_DUST.get(), RecipeCategory.MISC, Items.COPPER_INGOT, 0.1F, 200, "small_copper_dust");
        smelt(consumer, ItemRegister.IRON_DUST.get(), RecipeCategory.MISC, Items.IRON_INGOT, 0.1F, 200, "small_iron_dust");
        smelt(consumer, ItemRegister.GOLD_DUST.get(), RecipeCategory.MISC, Items.GOLD_INGOT, 0.1F, 200, "small_gold_dust");
        smelt(consumer, ItemRegister.NETHERITE_BLEND.get(), RecipeCategory.MISC, Items.NETHERITE_INGOT, 0.1F, 200, "netherite_blend");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemRegister.STONE_SQUEEGEE.get())
                .define('S', Items.COBBLESTONE)
                .define('W', Items.OAK_PLANKS)
                .define('X', Items.STICK)
                .pattern("   ")
                .pattern("XWX")
                .pattern("SSS")
                .unlockedBy("has_cobblestone", inventoryTrigger(ItemPredicate.Builder.item().of(Items.COBBLESTONE).build()))
                .save(consumer);*/

    }





    protected static void nineBlockStorageRecipes(Consumer<FinishedRecipe> p_249580_, RecipeCategory p_251203_, ItemLike p_251689_, RecipeCategory p_251376_, ItemLike p_248771_) {
        nineBlockStorageRecipes(p_249580_, p_251203_, p_251689_, p_251376_, p_248771_, getSimpleRecipeName(p_248771_), (String)null, getSimpleRecipeName(p_251689_), (String)null);
    }

    protected static void nineBlockStorageRecipes(Consumer<FinishedRecipe> p_250423_, RecipeCategory p_250083_, ItemLike p_250042_,
                                                  RecipeCategory p_248977_, ItemLike p_251911_, String p_250475_, @Nullable String p_248641_,
                                                  String p_252237_, @Nullable String p_250414_) {
        ShapelessRecipeBuilder.shapeless(p_250083_, p_250042_, 9).requires(p_251911_).group(p_250414_).unlockedBy(getHasName(p_251911_), has(p_251911_))
                .save(p_250423_, new ResourceLocation(ProcesingReprocesing.MOD_ID, p_252237_));
        ShapedRecipeBuilder.shaped(p_248977_, p_251911_).define('#', p_250042_).pattern("###").pattern("###").pattern("###").group(p_248641_)
                .unlockedBy(getHasName(p_250042_), has(p_250042_)).save(p_250423_, new ResourceLocation(ProcesingReprocesing.MOD_ID, p_250475_));
    }

    public void smelt(Consumer<FinishedRecipe> finishedRecipe, ItemLike in, RecipeCategory recipeCategory, ItemLike out, float exp, int time, String name) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(in), recipeCategory, out, exp, time)
                .unlockedBy("has_item", has(in)).save(finishedRecipe, new ResourceLocation(ProcesingReprocesing.MOD_ID ,name + "_smelt"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(in), recipeCategory, out, exp, time/2)
                .unlockedBy("has_item", has(in)).save(finishedRecipe, new ResourceLocation(ProcesingReprocesing.MOD_ID ,name + "_blast"));
    }

    public void blast_only(Consumer<FinishedRecipe> finishedRecipe, ItemLike in, RecipeCategory recipeCategory, ItemLike out, float exp, int time, String name) {
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(in), recipeCategory, out, exp, time)
                .unlockedBy("has_item", has(in)).save(finishedRecipe, new ResourceLocation(ProcesingReprocesing.MOD_ID ,name + "_blast"));
    }
    public void cook(Consumer<FinishedRecipe> finishedRecipe, ItemLike in, RecipeCategory recipeCategory, ItemLike out, float exp, int time, String name) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(in), recipeCategory ,out, exp, time)
                .unlockedBy("has_item", has(in)).save(finishedRecipe, new ResourceLocation(ProcesingReprocesing.MOD_ID ,name + "_smelt"));
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(in), recipeCategory, out, exp, time/2)
                .unlockedBy("has_item", has(in)).save(finishedRecipe, new ResourceLocation(ProcesingReprocesing.MOD_ID ,name + "_smoke"));
    }
}