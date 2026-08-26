package io.github.anttluca.red_reign.init;

import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.mob_effects.SensitiveSkinMobEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class InitMobEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(
            Registries.MOB_EFFECT, RedReign.MODID);

    // Mob Effects
    public static final DeferredHolder<MobEffect, MobEffect> SENSITIVE_SKIN = MOB_EFFECTS.register(
        "sensitive_skin", SensitiveSkinMobEffect::new);
}
