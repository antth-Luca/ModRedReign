package io.github.anttluca.red_reign.mixins;

import io.github.anttluca.red_reign.components.TooltipImageDataComponent;
import io.github.anttluca.red_reign.contexts.RRTooltipContext;
import io.github.anttluca.red_reign.init.InitDataComponentTypes;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.tooltip.TooltipRenderUtil;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(TooltipRenderUtil.class)
public class RRTooltipRenderUtilMixin {
    @Unique private static final int TEXTURE_WIDTH = 64;
    @Unique private static final int TEXTURE_HEIGHT = 16;

    @Unique private static final int CORNER_WIDTH = TEXTURE_WIDTH / 4;

    @Unique private static final int CENTER_WIDTH = TEXTURE_WIDTH - CORNER_WIDTH * 2;

    @Unique private static final int PARTS_HEIGHT = TEXTURE_HEIGHT / 2;


    @Inject(
            method = "extractTooltipBackground",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void red_reign$replaceTooltip(GuiGraphicsExtractor graphics, int x, int y, int width, int height, Identifier identifier, CallbackInfo cbInfo) {
        @Nullable TooltipImageDataComponent tooltipImage = RRTooltipContext.getStack().get(InitDataComponentTypes.TOOLTIP_IMAGE.get());
        if (tooltipImage == null) return;

        cbInfo.cancel();

        // Background
        graphics.nextStratum();
        graphics.fillGradient(x - 4, y - 3, x + width + 4, y + height + 3, tooltipImage.bgColorStart(), tooltipImage.bgColorEnd());
        graphics.fillGradient(x - 3, y - 4, x + width + 3, y - 3, tooltipImage.bgColorStart(), tooltipImage.bgColorStart());
        graphics.fillGradient(x - 3, y + height + 3, x + width + 3, y + height + 4, tooltipImage.bgColorEnd(), tooltipImage.bgColorEnd());

        // Border
        graphics.nextStratum();
        graphics.fillGradient(x - 2, y - 3, x + width + 2, y - 2, tooltipImage.borderColorStart(), tooltipImage.borderColorStart());
        graphics.fillGradient(x - 2, y + height + 2, x + width + 2, y + height + 3, tooltipImage.borderColorEnd(), tooltipImage.borderColorEnd());
        graphics.fillGradient(x - 3, y - 3, x - 2, y + height + 3, tooltipImage.borderColorStart(), tooltipImage.borderColorEnd());
        graphics.fillGradient(x + width + 2, y - 3, x + width + 3, y + height + 3, tooltipImage.borderColorStart(), tooltipImage.borderColorEnd());

        // Decorations
        @Nullable Identifier texture = tooltipImage.decor();
        if (texture == null) return;

        graphics.nextStratum();
        // Corners
        graphics.blit(RenderPipelines.GUI_TEXTURED, texture, x - 4, y - 3, 0, 0, CORNER_WIDTH, PARTS_HEIGHT, CORNER_WIDTH, PARTS_HEIGHT, TEXTURE_WIDTH, TEXTURE_HEIGHT);
        graphics.blit(RenderPipelines.GUI_TEXTURED, texture, x - 4 + CORNER_WIDTH, y - 3, 16, 0, CENTER_WIDTH, PARTS_HEIGHT, CENTER_WIDTH, PARTS_HEIGHT, TEXTURE_WIDTH, TEXTURE_HEIGHT);
        graphics.blit(RenderPipelines.GUI_TEXTURED, texture, x + width + 4 - CORNER_WIDTH, y - 3, 48, 0, CORNER_WIDTH, PARTS_HEIGHT, CORNER_WIDTH, PARTS_HEIGHT, TEXTURE_WIDTH, TEXTURE_HEIGHT);
        graphics.blit(RenderPipelines.GUI_TEXTURED, texture, x - 4, y + height + 3 - PARTS_HEIGHT, 0, 8, CORNER_WIDTH, PARTS_HEIGHT, CORNER_WIDTH, PARTS_HEIGHT, TEXTURE_WIDTH, TEXTURE_HEIGHT);
        // Center
        graphics.blit(RenderPipelines.GUI_TEXTURED, texture, x - 4 + CORNER_WIDTH, y + height + 3 - PARTS_HEIGHT, 16, 8, CENTER_WIDTH, PARTS_HEIGHT, CENTER_WIDTH, PARTS_HEIGHT, TEXTURE_WIDTH, TEXTURE_HEIGHT);
        graphics.blit(RenderPipelines.GUI_TEXTURED, texture, x + width + 4 - CORNER_WIDTH, y + height + 3 - PARTS_HEIGHT, 48, 8, CORNER_WIDTH, PARTS_HEIGHT, CORNER_WIDTH, PARTS_HEIGHT, TEXTURE_WIDTH, TEXTURE_HEIGHT);
    }
}
