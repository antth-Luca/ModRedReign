package io.github.anttluca.red_reign.init;

import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.components.StolenLifeDataComponent;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class InitDataComponentTypes {
    public static final DeferredRegister<DataComponentType<?>> TYPES = DeferredRegister.create(
            Registries.DATA_COMPONENT_TYPE, RedReign.MODID);

    // Data Component Types
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<StolenLifeDataComponent>> STOLEN_LIFE = TYPES.register(
        "stolen_life", () -> DataComponentType.<StolenLifeDataComponent>builder()
                .persistent(StolenLifeDataComponent.MAP_CODEC.codec())
                .networkSynchronized(StolenLifeDataComponent.STREAM_CODEC).build());
}
