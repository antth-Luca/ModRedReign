package io.github.anttluca.red_reign.items;

import io.github.anttluca.red_reign.components.StolenLifeDataComponent;
import io.github.anttluca.red_reign.init.InitDataComponentTypes;
import io.github.anttluca.red_reign.utils.components.StolenLifeDataComponentUtils;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Unit;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class ChaliceOfTheBloodbladeItem extends Item {
    public ChaliceOfTheBloodbladeItem(Properties props) {
        super(props
                .sword(ToolMaterial.DIAMOND, 3.0F, -2.4F)
                .component(DataComponents.UNBREAKABLE, Unit.INSTANCE)
                .component(InitDataComponentTypes.STOLEN_LIFE.get(), StolenLifeDataComponent.EMPTY)
        );
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
        builder.accept(Component.literal(StolenLifeDataComponentUtils.getLife(itemStack) + " / " + StolenLifeDataComponentUtils.MAX_STOLEN_LIFE));
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return StolenLifeDataComponentUtils.getLife(stack) > 0;
    }

    public float getWidthForBar(ItemStack stack) {
        float stolen = StolenLifeDataComponentUtils.getLife(stack);
        if (stolen == 0) return 1;

        return (float) (1 - stolen / StolenLifeDataComponentUtils.MAX_STOLEN_LIFE);
    }

    public int getScaledBarWidth(ItemStack stack) {
        return Math.round(13.0F - 13.0F * getWidthForBar(stack));
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return getScaledBarWidth(stack);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return 0x24292EFF;
    }
}
