package io.github.anttluca.red_reign.recipes.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.anttluca.red_reign.init.InitRecipes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Optional;

public record HPCostRecipe(ShapedRecipePattern pattern, float hpCost, ItemStackTemplate output) implements Recipe<CraftingInput> {
    public static final MapCodec<HPCostRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(inst ->
        inst.group(
            ShapedRecipePattern.MAP_CODEC.forGetter(r -> r.pattern),
            Codec.FLOAT.fieldOf("hp_cost").forGetter(r -> r.hpCost),
            ItemStackTemplate.CODEC.fieldOf("result").forGetter(r -> r.output)
        ).apply(inst, HPCostRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, HPCostRecipe> STREAM_CODEC = StreamCodec.composite(
        ShapedRecipePattern.STREAM_CODEC, r -> r.pattern,
        ByteBufCodecs.FLOAT, r -> r.hpCost,
        ItemStackTemplate.STREAM_CODEC, r -> r.output,
        HPCostRecipe::new
    );

    @Override
    public boolean matches(CraftingInput input, Level level) {
        if (level.isClientSide()) return false;

        return this.pattern.matches(input);
    }

    @Override
    public ItemStack assemble(CraftingInput pInput) {
        return this.output.create();
    }

    @Override
    public boolean showNotification() {
        return true;
    }

    @Override
    public String group() {
        return "HP Cost";
    }

    @Override
    public RecipeSerializer<? extends Recipe<CraftingInput>> getSerializer() {
        return InitRecipes.HP_COST_SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends Recipe<CraftingInput>> getType() {
        return InitRecipes.HP_COST_TYPE.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.createFromOptionals(this.pattern.ingredients());
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.CRAFTING_MISC;
    }

    public List<Optional<Ingredient>> getIngredients() {
        return this.pattern.ingredients();
    }

    public float getHpCost() {
        return this.hpCost;
    }

    public ItemStackTemplate getOutput() {
        return this.output;
    }
}
