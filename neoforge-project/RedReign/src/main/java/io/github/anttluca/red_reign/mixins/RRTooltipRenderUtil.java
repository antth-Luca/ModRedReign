package io.github.anttluca.red_reign.mixins;

import io.github.anttluca.red_reign.components.TooltipImageDataComponent;
import io.github.anttluca.red_reign.contexts.RRTooltipContext;
import io.github.anttluca.red_reign.init.InitDataComponentTypes;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.tooltip.TooltipRenderUtil;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(TooltipRenderUtil.class)
public class RRTooltipRenderUtil {
    @Inject(
            method = "extractTooltipBackground",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void red_reign$replaceTooltip(GuiGraphicsExtractor graphics, int x, int y, int width, int height, Identifier identifier, CallbackInfo cbInfo) {
        @Nullable TooltipImageDataComponent tooltipImage = RRTooltipContext.getStack().get(InitDataComponentTypes.TOOLTIP_IMAGE.get());
        if (tooltipImage == null) return;

        // Background
        graphics.nextStratum();
        graphics.fillGradient(x - 4, y - 3, x + width + 4, y + height + 3, tooltipImage.bgStart(), tooltipImage.bgEnd());
        graphics.fillGradient(x - 3, y - 4, x + width + 3, y - 3, tooltipImage.bgStart(), tooltipImage.bgStart());
        graphics.fillGradient(x - 3, y + height + 3, x + width + 3, y + height + 4, tooltipImage.bgEnd(), tooltipImage.bgEnd());

        // Border
        graphics.nextStratum();
        graphics.fillGradient(x - 2, y - 3, x + width + 2, y - 2, tooltipImage.borderStart(), tooltipImage.borderStart());
        graphics.fillGradient(x - 2, y + height + 2, x + width + 2, y + height + 3, tooltipImage.borderEnd(), tooltipImage.borderEnd());
        graphics.fillGradient(x - 3, y - 3, x - 2, y + height + 3, tooltipImage.borderStart(), tooltipImage.borderEnd());
        graphics.fillGradient(x + width + 2, y - 3, x + width + 3, y + height + 3, tooltipImage.borderStart(), tooltipImage.borderEnd());

        cbInfo.cancel();
    }


}
