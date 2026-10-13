package io.github.anttluca.red_reign.items;

import com.klikli_dev.modonomicon.item.ModonomiconCustomItemBase;
import io.github.anttluca.red_reign.components.TooltipImageDataComponent;
import io.github.anttluca.red_reign.handlers.RRItemTooltipsHandler;
import io.github.anttluca.red_reign.init.InitDataComponentTypes;
import io.github.anttluca.red_reign.init.InitItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class CrimsonGrimoire extends ModonomiconCustomItemBase {
    public CrimsonGrimoire(Properties props) {
        super(InitItems.CRIMSON_GRIMOIRE.getId(), props
                .stacksTo(1)
                .fireResistant()
                .component(InitDataComponentTypes.TOOLTIP_IMAGE.get(), TooltipImageDataComponent.BLOODSTAINED)
        );
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext ctx, TooltipDisplay display, Consumer<Component> builder, TooltipFlag flag) {
        super.appendHoverText(stack, ctx, display, builder, flag);
        RRItemTooltipsHandler.addLore(InitItems.CRIMSON_GRIMOIRE.getId().getPath(), builder);
    }
}
