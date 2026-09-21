package io.github.anttluca.red_reign.utils;

import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.init.InitRecipes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;

public class RREnchantmentsUtils {
    public static final ResourceKey<Enchantment> TRANSMUTATION_KEY = ResourceKey.create(
        Registries.ENCHANTMENT, Identifier.fromNamespaceAndPath(
            RedReign.MODID, InitRecipes.TRANSMUTATION_TYPE.getId().getPath()
    ));
}
