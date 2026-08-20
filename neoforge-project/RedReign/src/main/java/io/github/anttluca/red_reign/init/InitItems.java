package io.github.anttluca.red_reign.init;

import io.github.anttluca.red_reign.RedReign;
import net.minecraft.world.item.BlockItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class InitItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(RedReign.MODID);

    // Items

    // Block Items
    public static final DeferredItem<BlockItem> BOUQUET_OF_POPPIES = ITEMS.registerSimpleBlockItem(
        InitBlocks.BOUQUET_OF_POPPIES);
}
