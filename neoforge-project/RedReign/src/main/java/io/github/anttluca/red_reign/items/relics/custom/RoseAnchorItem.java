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

import java.util.function.Consumer;

public class RoseAnchorItem extends RRBaseRelic {
    public RoseAnchorItem(Properties props) {
        super(RRRelicsPropsHandler.addHPCostProps(props));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext ctx, TooltipDisplay display, Consumer<Component> builder, TooltipFlag flag) {
        super.appendHoverText(stack, ctx, display, builder, flag);
        RRItemTooltipsHandler.addLoreAndEffects(InitItems.ROSE_ANCHOR.getId().getPath(), 2, builder);
        RRItemTooltipsHandler.indicateOwner(stack, ctx, builder);
    }

    @Override
    public CurioAttributeModifiers getDefaultCurioAttributeModifiers(ItemStack stack) {
        Identifier id = InitItems.ROSE_ANCHOR.getId();
        String slot = CuriosSlotTypes.Preset.BELT.id();

        return CurioAttributeModifiers.builder()
                .addModifier(
                    Attributes.ARMOR,
                    new AttributeModifier(
                        id,
                        4.0F,
                        AttributeModifier.Operation.ADD_VALUE
                    ),
                    slot
                )
                .addModifier(
                    Attributes.ARMOR_TOUGHNESS,
                    new AttributeModifier(
                        id,
                        1.0F,
                        AttributeModifier.Operation.ADD_VALUE
                    ),
                    slot
                )
                .build();
    }

    // Effects in: RRRelicsWorksEvent.onPlayerKnockback and RRRelicsWorksEvent.onPlayerFall
}
