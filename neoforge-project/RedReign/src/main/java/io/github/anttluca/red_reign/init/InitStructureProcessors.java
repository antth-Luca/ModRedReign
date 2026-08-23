package io.github.anttluca.red_reign.init;

import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.world.processors.RedLadyRuinsProcessor;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class InitStructureProcessors {
    public static final DeferredRegister<StructureProcessorType<?>> PROCESSORS = DeferredRegister.create(
            Registries.STRUCTURE_PROCESSOR, RedReign.MODID);

    // Structure Processors
    public static final DeferredHolder<StructureProcessorType<?>, StructureProcessorType<RedLadyRuinsProcessor>> RED_LADY_RUINS = PROCESSORS.register(
        "red_lady_ruins", () -> () -> RedLadyRuinsProcessor.CODEC);
}
