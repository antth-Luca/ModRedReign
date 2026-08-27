package io.github.anttluca.red_reign.events;

import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.fluids.MeltedBeeswaxFluid;
import io.github.anttluca.red_reign.init.InitFluids;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterFluidModelsEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

@EventBusSubscriber(modid = RedReign.MODID)
public class RRRegisterFluidsEvent {
    @SubscribeEvent
    public static void registerOnClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerFluidType(
                MeltedBeeswaxFluid.getExtension(),
                InitFluids.MELTED_BEESWAX_TYPE
        );
    }

    @SubscribeEvent
    public static void registerFluidModelsEvent(RegisterFluidModelsEvent event) {
        event.register(
                MeltedBeeswaxFluid.getModelUnbaked(),
                InitFluids.MELTED_BEESWAX,
                InitFluids.FLOWING_MELTED_BEESWAX
        );
    }
}
