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
    private static final int LABELS_COLOR = 0xE4E4E4FF;

    protected int imageHeight = 186;
    protected int costLabelY = imageHeight - 110;

    public CraftingTableOfRedQueenScreen(CraftingTableOfRedQueenMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);

        this.titleLabelY = -4;
        this.inventoryLabelY = this.imageHeight - 102;
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float a) {
        super.extractBackground(guiGraphics, mouseX, mouseY, a);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, GUI_TEXTURE, x, y, 0, 0, imageWidth, imageHeight, 256, 256);
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor guiGraphics, int xm, int ym) {
        guiGraphics.text(this.font, this.title, this.titleLabelX, this.titleLabelY, LABELS_COLOR, false);
        guiGraphics.text(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, LABELS_COLOR, false);

        float hpCost = this.menu.getHPCost();
        if (hpCost <= 0.0F) return;

        guiGraphics.text(
            this.font,
            Component.translatable(
                "block.red_reign.crafting_table_of_red_queen.hp_cost"
            ).append(String.valueOf(hpCost)),
            this.inventoryLabelX,
            this.costLabelY,
            LABELS_COLOR,
            false
        );
    }
}
