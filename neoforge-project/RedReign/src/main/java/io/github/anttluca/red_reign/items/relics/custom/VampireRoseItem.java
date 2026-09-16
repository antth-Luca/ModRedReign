package io.github.anttluca.red_reign.items.relics.custom;

import io.github.anttluca.red_reign.components.TooltipImageDataComponent;
import io.github.anttluca.red_reign.handlers.RRItemTooltipsHandler;
import io.github.anttluca.red_reign.init.InitDataComponentTypes;
import io.github.anttluca.red_reign.init.InitItems;
import io.github.anttluca.red_reign.items.custom.RRBaseRelic;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.puffish.attributesmod.api.PuffishAttributes;
import top.theillusivec4.curios.api.CurioAttributeModifiers;
import top.theillusivec4.curios.api.CuriosSlotTypes;

import java.util.function.Consumer;

public class VampireRoseItem extends RRBaseRelic {
    public VampireRoseItem(Properties props) {
        super(props
                .component(InitDataComponentTypes.TOOLTIP_IMAGE.get(), TooltipImageDataComponent.BLOODSTAINED)
        );
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext ctx, TooltipDisplay display, Consumer<Component> builder, TooltipFlag flag) {
        super.appendHoverText(stack, ctx, display, builder, flag);
        RRItemTooltipsHandler.addLore(InitItems.VAMPIRE_ROSE.getId().getPath(), builder);
    }

    @Override
    public CurioAttributeModifiers getDefaultCurioAttributeModifiers(ItemStack stack) {
        return CurioAttributeModifiers.builder()
                .addModifier(
                        PuffishAttributes.LIFE_STEAL,
                        new AttributeModifier(
                                InitItems.VAMPIRE_ROSE.getId(),
                                0.05F,  // 5%
                                AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                        ),
                        CuriosSlotTypes.Preset.CHARM.id()
                ).build();
    }
}
