package io.github.anttluca.red_reign.items.relics.custom;

import io.github.anttluca.red_reign.handlers.RRItemTooltipsHandler;
import io.github.anttluca.red_reign.handlers.RRRelicsPropsHandler;
import io.github.anttluca.red_reign.init.InitItems;
import io.github.anttluca.red_reign.items.custom.RRBaseRelic;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class DaisySilverMeteorItem extends RRBaseRelic {
    public static final float XP_COST_MODIFIER = 0.5F;
    public static final float ANVIL_DMG_CHANCE_MODIFIER = 0.5F;

    public DaisySilverMeteorItem(Properties props) {
        super(RRRelicsPropsHandler.addHPCostProps(props));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext ctx, TooltipDisplay display, Consumer<Component> builder, TooltipFlag flag) {
        super.appendHoverText(stack, ctx, display, builder, flag);
        RRItemTooltipsHandler.addLoreAndEffects(InitItems.DAISY_SILVER_METEOR.getId().getPath(), 2, builder);
        RRItemTooltipsHandler.indicateOwner(stack, ctx, builder);
    }

    // Effects in: RRRelicsWorksEvent.onAnvilUpdate and RRAnvilMenuMixin
}
