package io.github.anttluca.red_reign.init;

import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.fluids.MeltedBeeswaxFluid;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class InitFluids {
    // Attention! Fluids are register in BLOCKS too.
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(
            Registries.FLUID, RedReign.MODID);

    // Fluids
    public static final DeferredHolder<Fluid, MeltedBeeswaxFluid.Source> MELTED_BEESWAX = FLUIDS.register(
        "melted_beeswax", MeltedBeeswaxFluid.Source::new);

    public static final DeferredHolder<Fluid, MeltedBeeswaxFluid.Flowing> FLOWING_MELTED_BEESWAX = FLUIDS.register(
        "flowing_melted_beeswax", MeltedBeeswaxFluid.Flowing::new);
}
