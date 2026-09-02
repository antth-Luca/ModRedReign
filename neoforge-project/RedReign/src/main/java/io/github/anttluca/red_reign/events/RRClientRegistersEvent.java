package io.github.anttluca.red_reign.events;

import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.fluids.MeltedBeeswaxFluid;
import io.github.anttluca.red_reign.init.InitFluids;
import io.github.anttluca.red_reign.init.InitMenuTypes;
import io.github.anttluca.red_reign.screens.CraftingTableOfRedQueenScreen;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterFluidModelsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

@EventBusSubscriber(modid = RedReign.MODID)
public class RRClientRegistersEvent {
    @SubscribeEvent
    public static void registerOnClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerFluidType(
                MeltedBeeswaxFluid.getTypeExtension(),
                InitFluids.MELTED_BEESWAX_TYPE
        );
    }

    @SubscribeEvent
    public static void onRegisterFluidModels(RegisterFluidModelsEvent event) {
        event.register(
                MeltedBeeswaxFluid.getModelUnbaked(),
                InitFluids.MELTED_BEESWAX.get(),
                InitFluids.FLOWING_MELTED_BEESWAX.get()
        );
    }

    @SubscribeEvent
    public static void onRegisterScreens(RegisterMenuScreensEvent event) {
        event.register(InitMenuTypes.CRAFTING_TABLE_OF_RED_QUEEN_MENU.get(), CraftingTableOfRedQueenScreen::new);
    }
}
