package io.github.anttluca.red_reign.datagen;

import io.github.anttluca.red_reign.utils.RRResourceKeyUtils;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.enchantment.Enchantment;

public class RRDatapackRegistries {
    public static final RegistrySetBuilder WORLD_BUILDER = new RegistrySetBuilder()
            .add(Registries.DAMAGE_TYPE, RRDatapackRegistries::damageTypes)
            .add(Registries.ENCHANTMENT, RRDatapackRegistries::enchantments)

            .add(Registries.TEMPLATE_POOL, RRWorldGenProvider::pools)
            .add(Registries.STRUCTURE, RRWorldGenProvider::structures)
            .add(Registries.STRUCTURE_SET, RRWorldGenProvider::structureSets);

    public static void damageTypes(BootstrapContext<DamageType> ctx) {
        ctx.register(
            RRResourceKeyUtils.DamageTypeKeys.BLEEDING,
            new DamageType(
                RRResourceKeyUtils.DamageTypeKeys.BLEEDING.identifier().getPath(),
                DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER,
                0.1f
            )
        );
        ctx.register(
            RRResourceKeyUtils.DamageTypeKeys.RED_QUEEN_POWER,
            new DamageType(
                RRResourceKeyUtils.DamageTypeKeys.RED_QUEEN_POWER.identifier().getPath(),
                DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER,
                0.1f
            )
        );
    }

    public static void enchantments(BootstrapContext<Enchantment> ctx) {
        ctx.register(
            RRResourceKeyUtils.EnchantmentKeys.TRANSMUTATION,
            Enchantment.enchantment(Enchantment.definition(
                HolderSet.empty(),
                1,
                1,
                Enchantment.constantCost(0),
                Enchantment.constantCost(0),
                0,
                EquipmentSlotGroup.ANY
            ))
            .build(RRResourceKeyUtils.EnchantmentKeys.TRANSMUTATION.identifier())
        );
    }
}
