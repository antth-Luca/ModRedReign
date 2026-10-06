package io.github.anttluca.red_reign.datagen;

import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.init.InitItems;
import io.github.anttluca.red_reign.recipes.HPCostRecipe;
import io.github.anttluca.red_reign.recipes.PurificationRecipe;
import io.github.anttluca.red_reign.recipes.TransmutationRecipe;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class RRRecipeProvider extends RecipeProvider {
    protected RRRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        // HP Cost Recipe
        hpCost(InitItems.ALLAY_CAGE.get(),
            Map.of(
                'I', Items.IRON_INGOT,
                'A', Items.AMETHYST_SHARD
            ),
            List.of(
                " I ",
                "IAI",
                " I "
            )
        );

        hpCost(InitItems.BLADE_OF_THE_BLOODSTAINED_LAMENT.get(),
            Map.of(
                'N', Items.NETHERITE_INGOT,
                'T', InitItems.CRYSTALLIZED_TEAR.get(),
                'C', InitItems.CHALICE_OF_THE_BLOODBLADE.get()
            ),
            List.of(
                " N ",
                " T ",
                " C "
            )
        );

        hpCost(InitItems.CATALYST_OF_EVERYTHING.get(), 0, ShapedRecipePattern.of(
            Map.of(
                'W', Ingredient.of(Items.ECHO_SHARD),
                'P', Ingredient.of(Items.BLAZE_POWDER),
                'E', Ingredient.of(Items.ENDER_PEARL),
                'C', Ingredient.of(Items.CHORUS_FRUIT),
                'S', tag(ItemTags.SOUL_FIRE_BASE_BLOCKS),
                'B', Ingredient.of(Items.BONE_MEAL),
                'O', Ingredient.of(Items.OBSIDIAN),
                'R', Ingredient.of(Items.REDSTONE),
                'G', Ingredient.of(Items.GUNPOWDER)
            ),
            List.of(
                "WPE",
                "CSB",
                "ORG"
            )
        ));

        hpCost(InitItems.HEALING_BULB.get(),
            Map.of(
                'G', Items.GOLD_NUGGET,
                'L', Items.GLASS,
                'M', InitItems.INTRINSIC_MECHANISM.get(),
                'I', Items.IRON_NUGGET
            ),
            List.of(
                "GLG",
                "LML",
                "I I"
            )
        );

        hpCost(InitItems.TOTEM_OF_THE_RED_QUEEN.get(),
            Map.of(
                'R', InitItems.REDSTONE_CRYSTAL.get(),
                'T', Items.TOTEM_OF_UNDYING,
                'N', Items.NETHERITE_INGOT
            ),
            List.of(
                "RTR",
                " N "
            )
        );

        hpCost(InitItems.CHALICE_OF_THE_BLOODBLADE.get(), 4,
            Map.of(
                'S', Items.SWEET_BERRIES,
                'G', Items.GLASS,
                'T', Items.GHAST_TEAR,
                'I', Items.IRON_NUGGET
            ),
            List.of(
                "SGS",
                " T ",
                " I "
            )
        );

        hpCost(InitItems.LAZULI_PROVIDENCE.get(), 10,
            Map.of(
                'I', Items.IRON_NUGGET,
                'L', Items.LAPIS_BLOCK
            ),
            List.of(
                "II",
                "LL"
            )
        );

        hpCost(InitItems.DAISY_SILVER_METEOR.get(), 20,
            Map.of(
                'I', Items.IRON_NUGGET,
                'L', Items.LAPIS_BLOCK,
                'C', Items.IRON_CHAIN
            ),
            List.of(
                " I ",
                "ILI",
                "CIC"
            )
        );

        hpCost(InitItems.CORAL_GAUNTLET.get(), 30,
            Map.of(
                'R', Items.BRAIN_CORAL_BLOCK,
                'H', Items.HEART_OF_THE_SEA,
                'B', Items.BUBBLE_CORAL_BLOCK
            ),
            List.of(
                " RR",
                "RHR",
                " BB"
            )
        );

        hpCost(InitItems.ROSE_ANCHOR.get(), 40,
            Map.of(
                'L', Items.LEATHER,
                'R', InitItems.ROSE_QUARTZ_BLOCK.get()
            ),
            List.of(
                " L ",
                "L L",
                "RL "
            )
        );

        hpCost(InitItems.VORTEX_PEARL.get(), 50,
            Map.of(
                'C', Items.IRON_CHAIN,
                'F', Items.CHORUS_FRUIT,
                'E', Items.ENDER_PEARL

            ),
            List.of(
                "CFC",
                " E "
            )
        );

        hpCost(InitItems.AMETHYST_RESONATOR.get(), 60,
            Map.of(
                'I', Items.IRON_INGOT,
                'A', Items.AMETHYST_SHARD,
                'S', Items.SCULK
            ),
            List.of(
                "IAI",
                "SIS"
            )
        );

        hpCost(InitItems.RED_IDENTITY.get(), 70,
            Map.of(
                'C', Items.CRIMSON_PLANKS,
                'R', InitItems.REDSTONE_CRYSTAL.get()
            ),
            List.of(
                "CCC",
                "RCR",
                " C "
            )
        );

        hpCost(InitItems.RED_SIGNET.get(), 80,
            Map.of(
                'R', InitItems.REDSTONE_CRYSTAL.get(),
                'G', Items.GOLD_INGOT,
                'N', Items.NETHERITE_SCRAP
            ),
            List.of(
                "RGN",
                "G N",
                "NN "
            )
        );

        // Purification Recipe
        purification(InitItems.CRYSTALLIZED_TEAR.get(), InitItems.PALE_POPPY.get(), Items.POPPY);

        // Transmutation Recipe
        transmutation(InitItems.PURIFICATION_SPELL.get(), 30, InitItems.CATALYST_OF_EVERYTHING.get());

        // Vanilla Recipes
        shaped(RecipeCategory.MISC, InitItems.BOUQUET_OF_POPPIES.get())
                .pattern("PPP")
                .pattern("PPP")
                .pattern("PPP")
                .define('P', Items.POPPY)
                .unlockedBy("has_poppy", has(Items.POPPY))
                .showNotification(false)
                .save(this.output);

        shaped(RecipeCategory.MISC, InitItems.CRAFTING_TABLE_OF_RED_QUEEN.get())
                .pattern("GCG")
                .pattern("RBR")
                .pattern("BBB")
                .define('G', Items.GOLD_NUGGET)
                .define('C', Items.WHITE_CARPET)
                .define('R', InitItems.REDSTONE_CRYSTAL.get())
                .define('B', Items.BLACKSTONE)
                .unlockedBy("has_gold_hugget", has(Items.GOLD_NUGGET))
                .unlockedBy("has_white_carpet", has(Items.WHITE_CARPET))
                .unlockedBy("has_redstone_crystal", has(InitItems.REDSTONE_CRYSTAL.get()))
                .unlockedBy("has_blackstone", has(Items.BLACKSTONE))
                .save(this.output);

        shapeless(RecipeCategory.MISC, InitItems.HONEYCOMB_BUCKET.get())
                .requires(Items.BUCKET)
                .requires(Items.HONEYCOMB)
                .requires(Items.HONEYCOMB)
                .requires(Items.HONEYCOMB)
                .requires(Items.HONEYCOMB)
                .unlockedBy("has_bucket", has(Items.BUCKET))
                .unlockedBy("has_honeycomb", has(Items.HONEYCOMB))
                .save(this.output);

        smeltingResultFromBase(InitItems.MELTED_BEESWAX_BUCKET.get(), InitItems.HONEYCOMB_BUCKET.get());

        smeltingResultFromBase(InitItems.REDSTONE_CRYSTAL.get(), Items.REDSTONE);

        shapeless(RecipeCategory.MISC, InitItems.ROSE_QUARTZ_BLOCK.get())
                .requires(Items.QUARTZ)
                .requires(Items.QUARTZ)
                .requires(InitItems.REDSTONE_CRYSTAL.get())
                .requires(InitItems.REDSTONE_CRYSTAL.get())
                .unlockedBy("has_quartz", has(Items.QUARTZ))
                .unlockedBy("has_redstone_crystal", has(InitItems.REDSTONE_CRYSTAL.get()))
                .save(this.output);
    }

    private void hpCost(Item result, Map<Character, Item> items, List<String> pattern) {
        hpCost(result, 0, items, pattern);
    }

    private void hpCost(Item result, float cost, Map<Character, Item> items, List<String> pattern) {
        Map<Character, Ingredient> key = items.entrySet().stream()
                .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    e -> Ingredient.of(e.getValue())
                ));
        hpCost(result, cost, ShapedRecipePattern.of(key, pattern));
    }

    private void hpCost(Item result, float cost, ShapedRecipePattern pattern) {
        custom(getName(result), new HPCostRecipe(
            pattern,
            cost,
            new ItemStackTemplate(result)
        ));
    }

    private void purification(Item result, Item ingredient) {
        custom(getName(result), new PurificationRecipe(
            Ingredient.of(ingredient),
            Optional.empty(),
            new ItemStackTemplate(result)
        ));
    }

    private void purification(Item result, Item display, Item ingredient) {
        custom(getName(result), new PurificationRecipe(
            Ingredient.of(ingredient),
            Optional.of(new ItemStackTemplate(display)),
            new ItemStackTemplate(result)
        ));
    }

    private void transmutation(Item result, int levelRq, Item ingredient) {
        custom(getName(result), new TransmutationRecipe(
            Ingredient.of(ingredient),
            levelRq,
            new ItemStackTemplate(result)
        ));
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

    private String getName(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).getPath();
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
