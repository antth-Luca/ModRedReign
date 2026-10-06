package io.github.anttluca.red_reign.tags;

import io.github.anttluca.red_reign.RedReign;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;

public class RRDamageTypeTags {
    public static final TagKey<DamageType> IS_RED_QUEEN = TagKey.create(
        Registries.DAMAGE_TYPE, Identifier.fromNamespaceAndPath(
            RedReign.MODID, "is_red_queen"));
}
