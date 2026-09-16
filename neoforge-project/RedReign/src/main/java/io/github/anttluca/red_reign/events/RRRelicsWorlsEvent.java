package io.github.anttluca.red_reign.events;

import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.handlers.CurioItemsHandler;
import io.github.anttluca.red_reign.init.InitItems;
import net.minecraft.util.Mth;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AnvilUpdateEvent;

@EventBusSubscriber(modid = RedReign.MODID)
public class RRRelicsWorlsEvent {
    // Relic: DaisySilverMeteor
    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event) {
        if (CurioItemsHandler.hasCurio(event.getPlayer(), InitItems.DAISY_SILVER_METEOR.get())) {
            int origXpCost = event.getXpCost();
            event.setXpCost(Mth.absMax(origXpCost / 2, 1));
        }
    }
}
