package io.github.anttluca.red_reign.screens;

import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.screens.menu.CraftingTableOfRedQueenMenu;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class CraftingTableOfRedQueenScreen extends AbstractContainerScreen<CraftingTableOfRedQueenMenu> {
    private static final Identifier GUI_TEXTURE = Identifier.fromNamespaceAndPath(RedReign.MODID,
            "textures/gui/container/crafting_table_of_red_queen.png");

    protected int imageHeight = 186;

    public CraftingTableOfRedQueenScreen(CraftingTableOfRedQueenMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);

        this.titleLabelY = 2;
        this.inventoryLabelY = this.imageHeight - 102;
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float a) {
        super.extractBackground(guiGraphics, mouseX, mouseY, a);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, GUI_TEXTURE, x, y, 0, 0, imageWidth, imageHeight, 256, 256);

        renderHPCostText(guiGraphics, x, y);
    }

    private void renderHPCostText(GuiGraphicsExtractor guiGrapgics, int x, int y) {
        int hpCost = menu.getHPCost();
        if (hpCost <= 0) return;

        guiGrapgics.text(
            this.font,
            Component.translatable(
                "block.red_reign.crafting_table_of_red_queen.hp_cost"
            ).append(String.valueOf(hpCost)),
            x + 12,
            y + 18,
            -12566464,
            false
        );
    }
}
