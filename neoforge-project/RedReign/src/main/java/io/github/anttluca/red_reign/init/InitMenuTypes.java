package io.github.anttluca.red_reign.init;

import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.screens.menu.CraftingTableOfRedQueenMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class InitMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(
            Registries.MENU, RedReign.MODID);

    // Types
    public static final DeferredHolder<MenuType<?>, MenuType<CraftingTableOfRedQueenMenu>> CRAFTING_TABLE_OF_RED_QUEEN_MENU = MENU_TYPES.register(
        "crafting_table_of_red_queen_menu", () -> IMenuTypeExtension.create(CraftingTableOfRedQueenMenu::new));
}
