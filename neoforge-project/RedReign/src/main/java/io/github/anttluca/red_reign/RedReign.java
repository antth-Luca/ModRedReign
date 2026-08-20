package io.github.anttluca.red_reign;

import io.github.anttluca.red_reign.init.InitBlocks;
import io.github.anttluca.red_reign.init.InitCreativeTabs;
import io.github.anttluca.red_reign.init.InitItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;

@Mod(RedReign.MODID)
public class RedReign {
    public static final String MODID = "red_reign";

    public RedReign(IEventBus bus, ModContainer container) {
        // Inits
        InitBlocks.BLOCKS.register(bus);
        InitItems.ITEMS.register(bus);
        InitCreativeTabs.TABS.register(bus);
    }
}
