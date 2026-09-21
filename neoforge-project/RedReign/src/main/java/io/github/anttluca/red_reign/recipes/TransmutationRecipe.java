package io.github.anttluca.red_reign.recipes;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.anttluca.red_reign.init.InitRecipes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Optional;

public record TransmutationRecipe(Ingredient input, ItemStackTemplate output) implements Recipe<RecipeInput> {
    public static final MapCodec<TransmutationRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(inst ->
        inst.group(
            Ingredient.CODEC.fieldOf("input").forGetter(r -> r.input),
            ItemStackTemplate.CODEC.fieldOf("result").forGetter(r -> r.output)
        ).apply(inst, TransmutationRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, TransmutationRecipe> STREAM_CODEC = StreamCodec.composite(
        Ingredient.CONTENTS_STREAM_CODEC, r -> r.input,
        ItemStackTemplate.STREAM_CODEC, r -> r.output,
        TransmutationRecipe::new
    );

    @Override
    @Deprecated
    public boolean matches(RecipeInput input, Level level) {
        return false;
    }

    public boolean matches(ItemStack input) {
        return this.input.test(input);
    }

    @Override
    public ItemStack assemble(RecipeInput input) {
        return this.output.create();
    }

    @Override
    public boolean showNotification() {
        return false;
    }

    @Override
    public String group() {
        return "";
    }

    @Override
    public RecipeSerializer<? extends Recipe<RecipeInput>> getSerializer() {
        return InitRecipes.TRANSMUTATION_SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends Recipe<RecipeInput>> getType() {
        return InitRecipes.TRANSMUTATION_TYPE.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.CRAFTING_MISC;
    }

    public Optional<Ingredient> getIngredient() {
        return Optional.of(this.input);
    }

    public ItemStackTemplate getOutput() {
        return this.output;
    }

    public static Optional<TransmutationRecipe> getCurrentRecipe(Level level, ItemStack stack) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return Optional.empty();
        }

        Optional<RecipeHolder<TransmutationRecipe>> trRecipe = serverLevel.recipeAccess()
                .getRecipeFor(
                    InitRecipes.TRANSMUTATION_TYPE.get(),
                    CraftingInput.of(1, 1, List.of(stack)),
                    level
                );
        if (trRecipe.isEmpty()) return Optional.empty();

        return trRecipe.map(RecipeHolder::value);
    }
}
