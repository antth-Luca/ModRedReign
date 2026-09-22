package io.github.anttluca.red_reign.items;

import io.github.anttluca.red_reign.handlers.RRItemTooltipsHandler;
import io.github.anttluca.red_reign.init.InitItems;
import io.github.anttluca.red_reign.items.custom.RRBaseItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class PurificationSpellItem extends RRBaseItem {
    public PurificationSpellItem(Properties props) {
        super(props
                .stacksTo(1)
        );
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext ctx, TooltipDisplay display, Consumer<Component> builder, TooltipFlag flag) {
        super.appendHoverText(stack, ctx, display, builder, flag);
        RRItemTooltipsHandler.addLoreAndEffects(InitItems.PURIFICATION_SPELL.getId().getPath(), 1, builder);
    }

    @Override
    public boolean isFoil(ItemStack itemStack) {
        return true;
    }
}
