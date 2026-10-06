package io.github.anttluca.red_reign.events;

import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.datagen.*;
import io.github.anttluca.red_reign.entities.red_queen_power.RedQueenPowerModel;
import io.github.anttluca.red_reign.entities.red_queen_power.RedQueenPowerRenderer;
import io.github.anttluca.red_reign.entities.red_queens_avatar.RedQueensAvatarModel;
import io.github.anttluca.red_reign.entities.red_queens_avatar.RedQueensAvatarRenderer;
import io.github.anttluca.red_reign.fluids.MeltedBeeswaxFluid;
import io.github.anttluca.red_reign.init.InitEntityTypes;
import io.github.anttluca.red_reign.init.InitFluids;
import io.github.anttluca.red_reign.init.InitItems;
import io.github.anttluca.red_reign.init.InitMenuTypes;
import io.github.anttluca.red_reign.screens.CraftingTableOfRedQueenScreen;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ComputeFovModifierEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterFluidModelsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = RedReign.MODID)
public class RRClientEvent {
    @SubscribeEvent
    public static void onFovModify(ComputeFovModifierEvent event) {
        Player player = event.getPlayer();
        ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);

        if (player.isUsingItem()
            && stack.is(InitItems.BLADE_OF_THE_BLOODSTAINED_LAMENT.get())) {
                int time = player.getTicksUsingItem();
                if (time > 0) event.setNewFovModifier(event.getNewFovModifier() - time * -0.015F);
        }
    }

    @SubscribeEvent
    public static void onGatherClientData(GatherDataEvent.Client event) {
        event.createDatapackRegistryObjects(RRDatapackRegistries.WORLD_BUILDER);

        event.createProvider(RRAdvancementsSubProvider::create);
        event.createProvider(RRItemTagsProvider::new);
        event.createProvider(RRBlockTagsProvider::new);
        event.createProvider(RRFluidTagsProvider::new);
        event.createProvider(RREntityTypeTagsProvider::new);
        event.createProvider(RRDamageTypeTagsProvider::new);
        event.createProvider(RRCuriosProvider::new);
        event.createProvider(RRLootTablesProvider::create);
        event.createProvider(RRGlobalLootModifierProvider::new);
        event.createProvider(RRModelsProvider::new);
        event.createProvider(RRRecipeProvider.Runner::new);
    }

    // Registers
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

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(InitEntityTypes.RED_QUEENS_AVATAR.get(), RedQueensAvatarRenderer::new);
        event.registerEntityRenderer(InitEntityTypes.RED_QUEEN_POWER.get(), RedQueenPowerRenderer::new);
    }

    @SubscribeEvent
    public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(RedQueensAvatarRenderer.LAYER_LOCATION, RedQueensAvatarModel::createBodyLayer);
        event.registerLayerDefinition(RedQueenPowerRenderer.LAYER_LOCATION, RedQueenPowerModel::createHeadLayer);
    }
}
