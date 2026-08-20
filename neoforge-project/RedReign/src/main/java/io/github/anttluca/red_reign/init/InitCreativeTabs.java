package io.github.anttluca.red_reign.init;

import io.github.anttluca.red_reign.RedReign;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class InitCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(
            Registries.CREATIVE_MODE_TAB, RedReign.MODID);

    // Creative Tabs
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN = TABS.register(
            "main", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.red_reign"))
                    .icon(() -> new ItemStack(InitItems.BOUQUET_OF_ROSES.get()))
                    .displayItems((dParams, out) -> {
                        InitItems.ITEMS.getEntries().forEach(item -> out.accept(item.get()));
                    }).build()
    );
}
