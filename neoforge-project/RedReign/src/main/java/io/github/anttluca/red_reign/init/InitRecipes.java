package io.github.anttluca.red_reign.init;

import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.recipes.HPCostRecipe;
import io.github.anttluca.red_reign.recipes.TransmutationRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class InitRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(
        Registries.RECIPE_SERIALIZER, RedReign.MODID);

    public static final DeferredRegister<RecipeType<?>> TYPES = DeferredRegister.create(
        Registries.RECIPE_TYPE, RedReign.MODID);

    // Serializers
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<HPCostRecipe>> HP_COST_SERIALIZER = SERIALIZERS.register(
        "hp_cost", () -> new RecipeSerializer<>(HPCostRecipe.MAP_CODEC, HPCostRecipe.STREAM_CODEC));

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<TransmutationRecipe>> TRANSMUTATION_SERIALIZER = SERIALIZERS.register(
        "transmutation", () -> new RecipeSerializer<>(TransmutationRecipe.MAP_CODEC, TransmutationRecipe.STREAM_CODEC));

    // Types
    public static final DeferredHolder<RecipeType<?>, RecipeType<HPCostRecipe>> HP_COST_TYPE = TYPES.register(
        "hp_cost", () -> new RecipeType<HPCostRecipe>() {
            @Override
            public String toString() {
                return HP_COST_TYPE.getRegisteredName();
            }
        });

    public static final DeferredHolder<RecipeType<?>, RecipeType<TransmutationRecipe>> TRANSMUTATION_TYPE = TYPES.register(
        "transmutation", () -> new RecipeType<TransmutationRecipe>() {
            @Override
            public String toString() {
                return TRANSMUTATION_TYPE.getRegisteredName();
            }
        });
}
