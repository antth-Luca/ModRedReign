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

public class VortexPearlItem extends RRBaseRelic {
    public static final float OXYGEN_BONUS_P_ARMOR = 0.025F;
    public static final float MAGIC_RESISTANCE_P_ARMOR = 0.01F;

    public VortexPearlItem(Properties props) {
        super(RRRelicsPropsHandler.addHPCostProps(props));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext ctx, TooltipDisplay display, Consumer<Component> builder, TooltipFlag flag) {
        super.appendHoverText(stack, ctx, display, builder, flag);
        RRItemTooltipsHandler.addLoreAndEffects(InitItems.VORTEX_PEARL.getId().getPath(), 2, builder);
        RRItemTooltipsHandler.indicateOwner(stack, ctx, builder);
    }

    // Effects in: RRDynamicModificationImplMixin.red_reign$redirectOrModify and RRLivingEntityMixin.red_reign$modifyOxygenBonus
}
