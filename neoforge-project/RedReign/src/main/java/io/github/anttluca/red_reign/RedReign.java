package io.github.anttluca.red_reign;

import io.github.anttluca.red_reign.init.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;

@Mod(RedReign.MODID)
public class RedReign {
    public static final String MODID = "red_reign";

    public RedReign(IEventBus bus, ModContainer container) {
        // Inits
        InitStructureProcessors.PROCESSORS.register(bus);
        InitAttributes.PLAYER_ATTRIBUTES.register(bus);
        InitAttributes.LIVING_ATTRIBUTES.register(bus);
        InitMobEffects.MOB_EFFECTS.register(bus);
        InitFluids.TYPES.register(bus);
        InitFluids.FLUIDS.register(bus);
        InitBlocks.BLOCKS.register(bus);
        InitBlockEntityType.BE_TYPES.register(bus);
        InitMenuTypes.MENU_TYPES.register(bus);
        InitItems.ITEMS.register(bus);
        InitCreativeTabs.TABS.register(bus);
        InitTriggers.TRIGGERS.register(bus);
    }
}
