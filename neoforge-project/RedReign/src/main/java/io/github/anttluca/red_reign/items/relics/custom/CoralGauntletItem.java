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

public class CoralGauntletItem extends RRBaseRelic {
    public static final float TARGET_THRESHOLD = 0.25F;
    public static final float DAMAGE_MULTIPLY = 1.35F;
    public static final float LIFE_STEAL_BONUS = 0.1F;

    public CoralGauntletItem(Properties props) {
        super(RRRelicsPropsHandler.addHPCostProps(props));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext ctx, TooltipDisplay display, Consumer<Component> builder, TooltipFlag flag) {
        super.appendHoverText(stack, ctx, display, builder, flag);
        RRItemTooltipsHandler.addLoreAndEffects(InitItems.CORAL_GAUNTLET.getId().getPath(), 1, builder);
        RRItemTooltipsHandler.indicateOwner(stack, ctx, builder);
    }

    // Effects in: RRRelicsWorksEvent.onPlayerDmgPre
}
