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
    public static final Identifier GUI_TEXTURE = Identifier.fromNamespaceAndPath(RedReign.MODID,
            "textures/gui/container/crafting_table_of_red_queen.png");
    public static final String HP_COST_KEY = "block.red_reign.crafting_table_of_red_queen.hp_cost";
    public static final int LIFE_COLOR = 0xFF720000;

    private static final int LABELS_COLOR = 0xFFE4E4E4;

    protected int imageHeight = 186;
    protected int costLabelX = inventoryLabelX + 14;
    protected int costLabelY = imageHeight - 122;

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

        Component costText = Component.translatable(HP_COST_KEY)
                .append(String.valueOf(hpCost));
        guiGraphics.text(
            this.font,
            costText,
            this.costLabelX,
            this.costLabelY,
            LIFE_COLOR,
            false
        );
    }
}
