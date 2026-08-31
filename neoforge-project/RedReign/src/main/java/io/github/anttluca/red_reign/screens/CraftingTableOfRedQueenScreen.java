package io.github.anttluca.red_reign.screens;

import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.screens.menu.CraftingTableOfRedQueenMenu;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class CraftingTableOfRedQueenScreen extends AbstractContainerScreen<CraftingTableOfRedQueenMenu> {
    private static final Identifier GUI_TEXTURE = Identifier.fromNamespaceAndPath(RedReign.MODID,
            "textures/gui/container/crafting_table_of_red_queen.png");

    protected int imageHeight = 185;

    public CraftingTableOfRedQueenScreen(CraftingTableOfRedQueenMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);

        this.titleLabelY = 2;
        this.inventoryLabelY = this.imageHeight - 98;
    }
}
