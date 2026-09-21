package io.github.anttluca.red_reign.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.anttluca.red_reign.init.InitRecipes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Optional;

public record TransmutationRecipe(Ingredient input, int levelRequired, ItemStackTemplate output) implements Recipe<RecipeInput> {
    public static final MapCodec<TransmutationRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(inst ->
        inst.group(
            Ingredient.CODEC.fieldOf("input").forGetter(r -> r.input),
            Codec.INT.fieldOf("level_required").forGetter(r -> r.levelRequired),
            ItemStackTemplate.CODEC.fieldOf("result").forGetter(r -> r.output)
        ).apply(inst, TransmutationRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, TransmutationRecipe> STREAM_CODEC = StreamCodec.composite(
        Ingredient.CONTENTS_STREAM_CODEC, r -> r.input,
            ByteBufCodecs.INT, r -> r.levelRequired,
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
    public boolean isSpecial() {
        return true;
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

    @Override
    public List<RecipeDisplay> display() {
        return List.of();
    }

    public Optional<Ingredient> getIngredient() {
        return Optional.of(this.input);
    }

    public int getLevelRequired() {return levelRequired;}

    public ItemStackTemplate getOutput() {return this.output;}

    public static Optional<TransmutationRecipe> getCurrentRecipe(Level level, ItemStack stack) {
        if (stack.isEmpty()
            || !(level instanceof ServerLevel serverLevel)) {
                return Optional.empty();
        }

        return serverLevel.recipeAccess()
                .recipeMap()
                .byType(InitRecipes.TRANSMUTATION_TYPE.get())
                .stream()
                .filter((r) -> r.value().matches(stack))
                .findFirst()
                .map(RecipeHolder::value);
    }
}
