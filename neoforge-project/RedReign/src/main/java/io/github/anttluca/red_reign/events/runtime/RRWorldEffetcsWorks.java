package io.github.anttluca.red_reign.events.runtime;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;

// Register only RedReignWorldData changed
public class RRWorldEffetcsWorks {
    @SubscribeEvent
    public static void onLivingHeal(LivingHealEvent event) {
        event.setAmount(event.getAmount() * 0.4F);
    }
}
