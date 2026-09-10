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
    @Unique
    private static final int DECOR_TEXTURE_WIDTH = 64;
    @Unique
    private static final int DECOR_TEXTURE_HEIGHT = 16;

    @Unique
    private static final int DECOR_CORNER_WIDTH = 8;
    @Unique
    private static final int DECOR_CENTER_WIDTH = 48;

    @Unique
    private static final int DECOR_PART_HEIGHT = 8;

    @Unique
    private static final int DECOR_LEFT_U = 0;
    @Unique
    private static final int DECOR_CENTER_U = 8;
    @Unique
    private static final int DECOR_RIGHT_U = 56;

    @Unique
    private static final int DECOR_TOP_V = 0;
    @Unique
    private static final int DECOR_BOTTOM_V = 8;

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

        final int cornerOffset = tooltipImage.cornerOffset();

        final int leftX = x - DECOR_CORNER_WIDTH + cornerOffset;
        final int rightX = x + width - cornerOffset;

        final int topY = y - DECOR_PART_HEIGHT + cornerOffset;
        final int bottomY = y + height - cornerOffset;

        graphics.nextStratum();
        // Corners
        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                texture,
                leftX,
                topY,
                DECOR_LEFT_U,
                DECOR_TOP_V,
                DECOR_CORNER_WIDTH,
                DECOR_PART_HEIGHT,
                DECOR_TEXTURE_WIDTH,
                DECOR_TEXTURE_HEIGHT
        );
        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                texture,
                rightX,
                topY,
                DECOR_RIGHT_U,
                DECOR_TOP_V,
                DECOR_CORNER_WIDTH,
                DECOR_PART_HEIGHT,
                DECOR_TEXTURE_WIDTH,
                DECOR_TEXTURE_HEIGHT
        );
        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                texture,
                leftX,
                bottomY,
                DECOR_LEFT_U,
                DECOR_BOTTOM_V,
                DECOR_CORNER_WIDTH,
                DECOR_PART_HEIGHT,
                DECOR_TEXTURE_WIDTH,
                DECOR_TEXTURE_HEIGHT
        );
        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                texture,
                rightX,
                bottomY,
                DECOR_RIGHT_U,
                DECOR_BOTTOM_V,
                DECOR_CORNER_WIDTH,
                DECOR_PART_HEIGHT,
                DECOR_TEXTURE_WIDTH,
                DECOR_TEXTURE_HEIGHT
        );
        // Center
        if (width >= DECOR_CENTER_WIDTH) {
            final int partOffset = tooltipImage.partOffset();

            final int centerX = x + (width - DECOR_CENTER_WIDTH) / 2;

            final int topCenterY = y - DECOR_PART_HEIGHT + partOffset;
            final int bottomCenterY = y + height - partOffset;

            graphics.blit(
                    RenderPipelines.GUI_TEXTURED,
                    texture,
                    centerX,
                    topCenterY,
                    DECOR_CENTER_U,
                    DECOR_TOP_V,
                    DECOR_CENTER_WIDTH,
                    DECOR_PART_HEIGHT,
                    DECOR_TEXTURE_WIDTH,
                    DECOR_TEXTURE_HEIGHT
            );
            graphics.blit(
                    RenderPipelines.GUI_TEXTURED,
                    texture,
                    centerX,
                    bottomCenterY,
                    DECOR_CENTER_U,
                    DECOR_BOTTOM_V,
                    DECOR_CENTER_WIDTH,
                    DECOR_PART_HEIGHT,
                    DECOR_TEXTURE_WIDTH,
                    DECOR_TEXTURE_HEIGHT
            );
        }
    }
}
