package io.github.anttluca.red_reign.datagen;

import io.github.anttluca.red_reign.init.InitBlocks;
import io.github.anttluca.red_reign.init.InitItems;
import io.github.anttluca.red_reign.utils.RRResourceKeyUtils.LootTableKeys;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class RRLootTablesProvider {
    public static LootTableProvider create(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
        return new LootTableProvider(
            output,
            Set.of(),
            List.of(
                new LootTableProvider.SubProviderEntry(RRAdvancementLootSubProvider::new, LootContextParamSets.ADVANCEMENT_REWARD),
                new LootTableProvider.SubProviderEntry(RRBlockLootSubProvider::new, LootContextParamSets.BLOCK),
                new LootTableProvider.SubProviderEntry(RRChestLootSubProvider::new, LootContextParamSets.CHEST)
            ),
            lookup
        );
    }

    // Advancements
    public static class RRAdvancementLootSubProvider implements LootTableSubProvider {
        public RRAdvancementLootSubProvider(HolderLookup.Provider lookup) {}

        @Override
        public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
            output.accept(LootTableKeys.ACTIVATE_RL_ALTAR, LootTable.lootTable().withPool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1))
                    .add(
                        LootItem.lootTableItem(InitItems.VAMPIRE_ROSE.get())
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                    )
            ));
        }
    }

    // Blocks
    public static class RRBlockLootSubProvider extends BlockLootSubProvider {
        public RRBlockLootSubProvider(HolderLookup.Provider lookup) {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags(), lookup);
        }

        @Override
        public void generate() {
            // Block os Poppies
            add(InitBlocks.BOUQUET_OF_POPPIES.get(), createShearsOrSilkTouchOnlyDrop(InitItems.BOUQUET_OF_POPPIES.get()));

            // Altar of the Red Lady
            add(InitBlocks.ALTAR_OF_RED_LADY.get(), noDrop());

            // Crafting Table of Red Queen
            dropSelf(InitBlocks.CRAFTING_TABLE_OF_RED_QUEEN.get());

            // Rose Quartz Block
            dropSelf(InitBlocks.ROSE_QUARTZ_BLOCK.get());
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return InitBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
        }
    }

    // Chests
    public static class RRChestLootSubProvider implements LootTableSubProvider {
        public RRChestLootSubProvider(HolderLookup.Provider lookup) {}

        @Override
        public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
            // Ancienty City
            output.accept(
                LootTableKeys.ANCIENTY_CITY,
                LootTable.lootTable().withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(
                            LootItem.lootTableItem(InitItems.EARTHLY_ICHOR.get())
                                    .when(LootItemRandomChanceCondition.randomChance(0.2F))
                        )
                )
            );

            // Village/Metalworking
            output.accept(
                LootTableKeys.METALWORKING,
                LootTable.lootTable().withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1))
                        .add(
                            LootItem.lootTableItem(InitItems.INTRINSIC_MECHANISM.get())
                        )
                )
            );
        }
    }
}
