package io.github.anttluca.red_reign.items;

import io.github.anttluca.red_reign.components.TooltipImageDataComponent;
import io.github.anttluca.red_reign.handlers.RRItemTooltipsHandler;
import io.github.anttluca.red_reign.init.InitDataComponentTypes;
import io.github.anttluca.red_reign.init.InitItems;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Unit;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.enchantment.Repairable;

import java.util.function.Consumer;

public class BladeOfTheBloodstainedLamentItem extends Item {
    public BladeOfTheBloodstainedLamentItem(Properties props) {
        super(props
                .sword(ToolMaterial.NETHERITE, 5.0F, -2.4F)
                .fireResistant()
                .component(DataComponents.REPAIRABLE, new Repairable(HolderSet.empty()))
                .component(DataComponents.UNBREAKABLE, Unit.INSTANCE)
                .component(InitDataComponentTypes.TOOLTIP_IMAGE.get(), TooltipImageDataComponent.LIFE_INFUSED)
        );
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext ctx, TooltipDisplay display, Consumer<Component> builder, TooltipFlag flag) {
        super.appendHoverText(stack, ctx, display, builder, flag);
        RRItemTooltipsHandler.addLoreAndEffects(InitItems.BLADE_OF_THE_BLOODSTAINED_LAMENT.getId().getPath(), 1, builder);
    }
}
