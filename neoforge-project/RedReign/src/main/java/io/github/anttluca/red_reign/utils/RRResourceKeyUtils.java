package io.github.anttluca.red_reign.utils;

import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.datagen.RRAdvancementsSubProvider;
import io.github.anttluca.red_reign.init.InitRecipes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.storage.loot.LootTable;

public class RRResourceKeyUtils {
    public static class EnchantmentKeys {
        public static final ResourceKey<Enchantment> TRANSMUTATION = ResourceKey.create(
            Registries.ENCHANTMENT, Identifier.fromNamespaceAndPath(
                RedReign.MODID, InitRecipes.TRANSMUTATION_TYPE.getId().getPath()
            ));
    }

    public static class LootTableKeys {
        public static final ResourceKey<LootTable> ACTIVATE_RL_ALTAR = ResourceKey.create(
            Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(
                RedReign.MODID, "advancements/" + RRAdvancementsSubProvider.ACTIVATE_RL_ALTAR
            ));

        public static final ResourceKey<LootTable> ANCIENTY_CITY = ResourceKey.create(
            Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(
                RedReign.MODID, "chests/ancienty_city"
            ));

        public static final ResourceKey<LootTable> METALWORKING = ResourceKey.create(
            Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(
                RedReign.MODID, "chests/village/village_metalworking"
            ));
    }

    public static class DamageTypeKeys {
        public static final ResourceKey<DamageType> BLEEDING = ResourceKey.create(
            Registries.DAMAGE_TYPE, Identifier.fromNamespaceAndPath(
                RedReign.MODID, "bleeding"
            ));

        public static final ResourceKey<DamageType> RQ_POWER = ResourceKey.create(
            Registries.DAMAGE_TYPE, Identifier.fromNamespaceAndPath(
                RedReign.MODID, "red_queen_power"
            ));
    }

    public static class WorldGenKeys {
        public static final ResourceKey<StructureTemplatePool> RUINS_POOL = ResourceKey.create(
            Registries.TEMPLATE_POOL, Identifier.fromNamespaceAndPath(RedReign.MODID, "red_lady_ruins"
        ));

        public static final ResourceKey<Structure> RUINS = ResourceKey.create(
            Registries.STRUCTURE, Identifier.fromNamespaceAndPath(RedReign.MODID, "red_lady_ruins"
        ));

        public static final ResourceKey<StructureSet> RUINS_SET = ResourceKey.create(
            Registries.STRUCTURE_SET, Identifier.fromNamespaceAndPath(RedReign.MODID, "red_lady_ruins"
        ));
    }
}
