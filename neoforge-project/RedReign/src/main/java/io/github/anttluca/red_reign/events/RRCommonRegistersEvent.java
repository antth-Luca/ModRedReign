package io.github.anttluca.red_reign.events;

import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.entities.red_queens_avatar.RedQueensAvatar;
import io.github.anttluca.red_reign.init.InitEntityTypes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = RedReign.MODID)
public class RRCommonRegistersEvent {
    @SubscribeEvent
    public static void onRegisterEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(InitEntityTypes.RED_QUEENS_AVATAR.get(), RedQueensAvatar.createAttributes().build());
    }
}
