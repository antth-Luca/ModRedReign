package io.github.anttluca.red_reign.events;

import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.components.AdoptableDataComponent;
import io.github.anttluca.red_reign.init.InitDataComponentTypes;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ItemStackedOnOtherEvent;

@EventBusSubscriber(modid = RedReign.MODID)
public class RRComponentsWorksEvent {
    @SubscribeEvent
    public static void onItemStackedOther(ItemStackedOnOtherEvent event) {
        ItemStack carried = event.getCarriedItem();
        if (carried.has(InitDataComponentTypes.ADOPTABLE.get())) {
            AdoptableDataComponent adoptable = carried.getOrDefault(
                InitDataComponentTypes.ADOPTABLE.get(),
                AdoptableDataComponent.EMPTY
            );

            if (adoptable.isEmpty()) {
                carried.set(
                    InitDataComponentTypes.ADOPTABLE.get(),
                    new AdoptableDataComponent(event.getPlayer())
                );
            }
        }
    }
}
