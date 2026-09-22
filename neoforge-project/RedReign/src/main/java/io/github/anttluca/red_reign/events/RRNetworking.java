package io.github.anttluca.red_reign.events;

import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.handlers.RRClientPayloadHandler;
import io.github.anttluca.red_reign.networking.packets.RRDisplayItemActivationPayload;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber
public class RRNetworking {
    @SubscribeEvent
    public static void onRegisterNetworking(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(RedReign.MODID).versioned("1.0");

        registrar.playToClient(
            RRDisplayItemActivationPayload.TYPE,
            RRDisplayItemActivationPayload.STREAM_CODEC,
            RRClientPayloadHandler::handleDisplayItemActivation
        );
    }
}
