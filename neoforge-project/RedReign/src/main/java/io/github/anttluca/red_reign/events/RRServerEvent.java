package io.github.anttluca.red_reign.events;

import io.github.anttluca.red_reign.RedReign;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = RedReign.MODID)
public class RRServerEvent {
    @SubscribeEvent
    public static void onGatherServerData(GatherDataEvent.Server event) {

    }
}
