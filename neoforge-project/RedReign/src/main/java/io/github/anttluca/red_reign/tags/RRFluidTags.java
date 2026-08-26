package io.github.anttluca.red_reign.tags;

import io.github.anttluca.red_reign.RedReign;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

public class RRFluidTags {
    public static final TagKey<Fluid> MELTED_BEESWAX = FluidTags.create(
            Identifier.fromNamespaceAndPath(
                    RedReign.MODID,
                    "melted_beeswax"));
}
