package io.github.anttluca.red_reign.events.runtime;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingUseTotemEvent;

// Register only RedReignWorldData changed
public class RRItemsWorksEvent {
    @SubscribeEvent
    public static void onLivingUseTotem(LivingUseTotemEvent event) { }
}
