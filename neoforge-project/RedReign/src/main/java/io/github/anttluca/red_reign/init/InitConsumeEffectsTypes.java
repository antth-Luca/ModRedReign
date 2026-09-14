package io.github.anttluca.red_reign.init;

import com.mojang.serialization.MapCodec;
import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.mob_effects.consumes.RedQueenDeathProtectionConsumeEffect;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class InitConsumeEffectsTypes {
    public static final DeferredRegister<ConsumeEffect.Type<?>> TYPES = DeferredRegister.create(
        BuiltInRegistries.CONSUME_EFFECT_TYPE, RedReign.MODID);

    // Consume Effects Types
    public static final DeferredHolder<ConsumeEffect.Type<?>, ConsumeEffect.Type<RedQueenDeathProtectionConsumeEffect>> RED_QUEEN_DEATH_PROTECTION = TYPES.register(
        "red_queen_death_protection", () -> new ConsumeEffect.Type<>(
            MapCodec.unit(new RedQueenDeathProtectionConsumeEffect()),
            StreamCodec.unit(new RedQueenDeathProtectionConsumeEffect())
        )
    );
}
