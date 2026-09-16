package io.github.anttluca.red_reign.items.relics.custom;

import io.github.anttluca.red_reign.handlers.RRItemTooltipsHandler;
import io.github.anttluca.red_reign.handlers.RRRelicsPropsHandler;
import io.github.anttluca.red_reign.init.InitItems;
import io.github.anttluca.red_reign.items.custom.RRBaseRelic;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import top.theillusivec4.curios.api.CurioAttributeModifiers;
import top.theillusivec4.curios.api.CuriosSlotTypes;
import top.theillusivec4.curios.api.SlotAttribute;

import java.util.function.Consumer;

public class LazuliProvidenceItem extends RRBaseRelic {
    public LazuliProvidenceItem(Properties props) {
        super(RRRelicsPropsHandler.addHPCostProps(props));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext ctx, TooltipDisplay display, Consumer<Component> builder, TooltipFlag flag) {
        super.appendHoverText(stack, ctx, display, builder, flag);
        RRItemTooltipsHandler.addLore(InitItems.LAZULI_PROVIDENCE.getId().getPath(), builder);
        RRItemTooltipsHandler.indicateOwner(stack, ctx, builder);
    }

    @Override
    public CurioAttributeModifiers getDefaultCurioAttributeModifiers(ItemStack stack) {
        Identifier id = InitItems.LAZULI_PROVIDENCE.getId();
        String slot = CuriosSlotTypes.Preset.HEAD.id();

        return CurioAttributeModifiers.builder()
                .addModifier(
                    Attributes.LUCK,
                    new AttributeModifier(
                        id,
                        1.5F,
                        AttributeModifier.Operation.ADD_VALUE
                    ),
                    slot
                )
                .addModifier(
                    SlotAttribute.getOrCreate(CuriosSlotTypes.Preset.CHARM.id()),
                    new AttributeModifier(
                        id,
                        1.0F,
                        AttributeModifier.Operation.ADD_VALUE
                    ),
                    slot
                )
                .build();
    }
}
