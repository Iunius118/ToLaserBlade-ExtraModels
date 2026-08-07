package com.github.iunius118.tolaserblade.exmodels.data;

import com.github.iunius118.tolaserblade.CommonClass;
import com.github.iunius118.tolaserblade.exmodels.data.recipe.BlueprintRecipeBuilder;
import com.github.iunius118.tolaserblade.item.LaserBladeColor;
import com.github.iunius118.tolaserblade.item.crafting.ColoringRecipe;
import com.github.iunius118.tolaserblade.tag.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.packs.VanillaRecipeProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends VanillaRecipeProvider {

    public ModRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
        super(provider, output);
    }

    @Override
    protected void buildRecipes() {
        // Remodel
        buildRemodelRecipe(Ingredient.of(Items.IRON_SWORD), 101);
        buildRemodelRecipe(Ingredient.of(Items.YELLOW_CANDLE), 217);
        buildRemodelRecipe(Ingredient.of(Items.SALMON), 222);
        buildRemodelRecipe(Ingredient.of(Items.DRAGON_BREATH), 305);
        buildRemodelRecipe(Ingredient.of(Items.SHIELD), 316);
        buildRemodelRecipe(Ingredient.of(Items.SLIME_BALL), 407);
        buildRemodelRecipe(Ingredient.of(Items.DIAMOND_ORE), 504);
        buildRemodelRecipe(Ingredient.of(Items.DIAMOND_ORE), 519);
        buildRemodelRecipe(Ingredient.of(Items.HONEYCOMB), 606);
        buildRemodelRecipe(Ingredient.of(Items.COD), 726);
        buildRemodelRecipe(Ingredient.of(Items.CROSSBOW), 801);
        buildRemodelRecipe(Ingredient.of(Items.TROPICAL_FISH), 808);
        buildRemodelRecipe(Ingredient.of(Items.BARREL), 825);
        buildRemodelRecipe(Ingredient.of(Items.LIGHTNING_ROD), 903);
        buildRemodelRecipe(Ingredient.of(Items.PISTON), 913);
        buildRemodelRecipe(Ingredient.of(Items.SPYGLASS), 924);
        buildRemodelRecipe(Ingredient.of(Items.IRON_AXE), 1009);
        buildRemodelRecipe(Ingredient.of(Items.WOODEN_SWORD), 1108);
        buildRemodelRecipe(Ingredient.of(Items.STONE_SWORD), 1216);
        buildRemodelRecipe(Ingredient.of(Items.END_CRYSTAL), 1221);
    }

    private void buildRemodelRecipe(Ingredient ingredient, int modelType) {
        blueprintRemodel(RecipeCategory.MISC, modelType)
                .requires(ModTags.Items.LASER_BLADES)
                .requires(ingredient)
                .unlockedBy("has_laser_blade", this.has(ModTags.Items.LASER_BLADES))
                .save(output, CommonClass.modLocation("blueprint/remodel/model_" + modelType));
    }

    private String getItemId(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).toString();
    }

    private BlueprintRecipeBuilder blueprintBlending(RecipeCategory category) {
        return BlueprintRecipeBuilder.blending(this.items, category);
    }

    private BlueprintRecipeBuilder blueprintColoring(RecipeCategory category, LaserBladeColor color) {
        var colors = List.of(
                new ColoringRecipe.PartColor(color.gripColor(), ColoringRecipe.PartColor.BlendMode.UNCHANGED),
                new ColoringRecipe.PartColor(color.outerBladeColor(),
                        color.isOuterBladeSubColor() ? ColoringRecipe.PartColor.BlendMode.SUBTRACTIVE
                                : ColoringRecipe.PartColor.BlendMode.UNCHANGED),
                new ColoringRecipe.PartColor(color.innerBladeColor(),
                        color.isInnerBladeSubColor() ? ColoringRecipe.PartColor.BlendMode.SUBTRACTIVE
                                : ColoringRecipe.PartColor.BlendMode.UNCHANGED)
        );
        return BlueprintRecipeBuilder.coloring(this.items, category, colors);
    }

    private BlueprintRecipeBuilder blueprintCrafting(RecipeCategory category, ItemStackTemplate result) {
        return BlueprintRecipeBuilder.crafting(this.items, category, result);
    }

    private BlueprintRecipeBuilder blueprintEnchantment(RecipeCategory category, ResourceKey<Enchantment> enchantment) {
        var enchantments = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return BlueprintRecipeBuilder.enchantment(this.items, enchantments, category, enchantment);
    }

    private BlueprintRecipeBuilder blueprintRemodel(RecipeCategory category, int modelType) {
        return BlueprintRecipeBuilder.remodel(this.items, category, modelType);
    }

    private BlueprintRecipeBuilder blueprintRepair(RecipeCategory category, float rate) {
        return BlueprintRecipeBuilder.repair(this.items, category, rate);
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
            super(packOutput, registries);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput output) {
            return new ModRecipeProvider(registryLookup, output);
        }

        @Override
        public String getName() {
            return "Recipes";
        }
    }
}
