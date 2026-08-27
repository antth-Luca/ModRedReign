package io.github.anttluca.red_reign.init;

import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.fluids.MeltedBeeswaxFluid;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class InitFluids {
    // Attention! Fluids are register in BLOCKS too.
    public static final DeferredRegister<FluidType> TYPES = DeferredRegister.create(
        NeoForgeRegistries.FLUID_TYPES, RedReign.MODID);

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(
        Registries.FLUID, RedReign.MODID);

    // Fluid Types
    public static final DeferredHolder<FluidType, FluidType> MELTED_BEESWAX_TYPE = TYPES.register(
        "melted_beeswax_type", MeltedBeeswaxFluid::getType);

    // Fluids
    public static final DeferredHolder<Fluid, MeltedBeeswaxFluid.Source> MELTED_BEESWAX = FLUIDS.register(
        "melted_beeswax", () ->
            new MeltedBeeswaxFluid.Source(MeltedBeeswaxFluid.getDefaultProperties()));

    public static final DeferredHolder<Fluid, MeltedBeeswaxFluid.Flowing> FLOWING_MELTED_BEESWAX = FLUIDS.register(
        "flowing_melted_beeswax", () ->
            new MeltedBeeswaxFluid.Flowing(MeltedBeeswaxFluid.getDefaultProperties()));
}
