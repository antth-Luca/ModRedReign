package io.github.anttluca.red_reign.init;

import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.triggers.custom.ActivateAltarOfRedLadyTrigger;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class InitTriggers {
    public static final DeferredRegister<CriterionTrigger<?>> TRIGGERS = DeferredRegister.create(
            Registries.TRIGGER_TYPE, RedReign.MODID);

    // Triggers
    public static final DeferredHolder<CriterionTrigger<?>, ActivateAltarOfRedLadyTrigger> ACTIVATE_ALTAR_OF_RED_LADY =
        TRIGGERS.register("activate_altar_of_red_lady", ActivateAltarOfRedLadyTrigger::new);
}
