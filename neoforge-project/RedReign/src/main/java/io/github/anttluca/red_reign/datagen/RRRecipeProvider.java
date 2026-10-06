package io.github.anttluca.red_reign.datagen;

import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.init.InitItems;
import io.github.anttluca.red_reign.recipes.PurificationRecipe;
import io.github.anttluca.red_reign.recipes.TransmutationRecipe;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class RRRecipeProvider extends RecipeProvider {
    protected RRRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        // HP Cost Recipe

        // Purification Recipe
        custom("crystallized_tear", new PurificationRecipe(
            Ingredient.of(Items.POPPY),
            Optional.of(new ItemStackTemplate(InitItems.PALE_POPPY.get())),
            new ItemStackTemplate(InitItems.CRYSTALLIZED_TEAR.get())
        ));

        // Transmutation Recipe
        custom("purification_spell", new TransmutationRecipe(
            Ingredient.of(InitItems.CATALYST_OF_EVERYTHING.get()),
            30,
            new ItemStackTemplate(InitItems.PURIFICATION_SPELL.get())
        ));

        // Vanilla Recipes
        shaped(RecipeCategory.MISC, InitItems.BOUQUET_OF_POPPIES.get())
                .pattern("PPP")
                .pattern("PPP")
                .pattern("PPP")
                .define('P', Items.POPPY)
                .unlockedBy("has_poppy", has(Items.POPPY))
                .showNotification(false)
                .save(this.output);
    }

    private void custom(String name, Recipe<?> recipe) {
        this.output.accept(
            ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(
                RedReign.MODID, recipe.group() + "/" + name
            )),
            recipe,
            null
        );
    }

    public static class Runner extends RecipeProvider.Runner {
        public Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
            super(output, lookup);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
            return new RRRecipeProvider(registries, output);
        }

        @Override
        public String getName() {
            return "Red Reign Recipes";
        }
    }
}
