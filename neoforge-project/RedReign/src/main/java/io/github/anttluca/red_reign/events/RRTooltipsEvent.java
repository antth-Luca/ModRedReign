package io.github.anttluca.red_reign.events;

import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.contexts.RRTooltipContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;

@EventBusSubscriber(modid = RedReign.MODID)
public class RRTooltipsEvent {
    @SubscribeEvent
    public static void onRenderTooltipPre(RenderTooltipEvent.Pre event) {
        ItemStack hoveredStack = event.getItemStack();

        if (hoveredStack.isEmpty()) {
            RRTooltipContext.clear();
            return;
        }

        RRTooltipContext.set(hoveredStack);
    }
}
