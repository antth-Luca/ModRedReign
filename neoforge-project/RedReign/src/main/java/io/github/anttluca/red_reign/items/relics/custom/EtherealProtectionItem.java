package io.github.anttluca.red_reign.items.relics.custom;

import io.github.anttluca.red_reign.handlers.RRRelicsAddHPHandler;
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

public class EtherealProtectionItem extends RRBaseRelic {
    public EtherealProtectionItem(Properties props) {
        super(RRRelicsAddHPHandler.addProps(props));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext ctx, TooltipDisplay display, Consumer<Component> builder, TooltipFlag flag) {
        super.appendHoverText(stack, ctx, display, builder, flag);
        RRRelicsAddHPHandler.addTooltips(InitItems.ETHEREAL_PROTECTION.getId(), builder);
    }

    @Override
    public CurioAttributeModifiers getDefaultCurioAttributeModifiers(ItemStack stack) {
        CurioAttributeModifiers.Builder builder = CurioAttributeModifiers.builder();
        Identifier id = InitItems.ETHEREAL_PROTECTION.getId();
        CuriosSlotTypes.Preset slot = CuriosSlotTypes.Preset.HANDS;

        builder.addModifier(
            PuffishAttributes.RESISTANCE,
            new AttributeModifier(
                id,
                -0.1F,  // 10%
                AttributeModifier.Operation.ADD_VALUE
            ),
            slot.id()
        );
        RRRelicsAddHPHandler.addHealthModifier(builder, id, slot);

        return builder.build();
    }
}
