package io.github.anttluca.red_reign.items.relics.custom;

import io.github.anttluca.red_reign.handlers.RRItemTooltipsHandler;
import io.github.anttluca.red_reign.handlers.RRRelicsPropsHandler;
import io.github.anttluca.red_reign.init.InitAttributes;
import io.github.anttluca.red_reign.init.InitItems;
import io.github.anttluca.red_reign.items.custom.RRBaseRelic;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.puffish.attributesmod.api.PuffishAttributes;
import top.theillusivec4.curios.api.CurioAttributeModifiers;
import top.theillusivec4.curios.api.CuriosSlotTypes;

import java.util.function.Consumer;

public class AmethystResonatorItem extends RRBaseRelic {
    public AmethystResonatorItem(Properties props) {
        super(RRRelicsPropsHandler.addHPCostProps(props));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext ctx, TooltipDisplay display, Consumer<Component> builder, TooltipFlag flag) {
        super.appendHoverText(stack, ctx, display, builder, flag);
        RRItemTooltipsHandler.addLore(InitItems.AMETHYST_RESONATOR.getId().getPath(), builder);
        RRItemTooltipsHandler.indicateOwner(stack, ctx, builder);
    }

    @Override
    public CurioAttributeModifiers getDefaultCurioAttributeModifiers(ItemStack stack) {
        Identifier id = InitItems.AMETHYST_RESONATOR.getId();
        String slot = CuriosSlotTypes.Preset.CHARM.id();

        return CurioAttributeModifiers.builder()
                .addModifier(
                    PuffishAttributes.HEALING,
                    new AttributeModifier(
                        id,
                        0.2F,  // 20%
                        AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                    ),
                    slot
                )
                .addModifier(
                    PuffishAttributes.EXPERIENCE,
                    new AttributeModifier(
                        id,
                        2.0F,  // +200% = 3x
                        AttributeModifier.Operation.ADD_VALUE
                    ),
                    slot
                )
                .addModifier(
                    PuffishAttributes.FORTUNE,
                    new AttributeModifier(
                        id,
                        1.0F,  // +I
                        AttributeModifier.Operation.ADD_VALUE
                    ),
                    slot
                )
                .addModifier(
                    InitAttributes.LOOTING,
                    new AttributeModifier(
                        id,
                        1.0F,  // +I
                        AttributeModifier.Operation.ADD_VALUE
                    ),
                    slot
                )
                .build();
    }
}
