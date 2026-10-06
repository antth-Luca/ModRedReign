package io.github.anttluca.red_reign.datagen;

import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.utils.RRResourceKeyUtils.LootTableKeys;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.AddTableLootModifier;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.concurrent.CompletableFuture;

public class RRGlobalLootModifierProvider extends GlobalLootModifierProvider {
    public RRGlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
        super(output, lookup, RedReign.MODID);
    }

    @Override
    protected void start() {
        addTable("ancient_city", "chests/ancient_city", LootTableKeys.ANCIENTY_CITY);
        addTable("village_armorer", "chests/village/village_armorer", LootTableKeys.METALWORKING);
        addTable("village_toolsmith", "chests/village/village_toolsmith", LootTableKeys.METALWORKING);
        addTable("village_weaponsmith", "chests/village/village_weaponsmith", LootTableKeys.METALWORKING);
    }

    private void addTable(String name, String vanillaTable, ResourceKey<LootTable> modTable) {
        add(name, new AddTableLootModifier(
            new LootItemCondition[] {
                LootTableIdCondition.builder(Identifier.withDefaultNamespace(vanillaTable)).build()
            },
            1000,
            modTable
        ));
    }
}
