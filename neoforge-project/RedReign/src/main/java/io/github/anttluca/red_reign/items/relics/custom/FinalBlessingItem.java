package io.github.anttluca.red_reign.items.relics.custom;

import io.github.anttluca.red_reign.handlers.RRItemTooltipsHandler;
import io.github.anttluca.red_reign.handlers.RRRelicsPropsHandler;
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

public class FinalBlessingItem extends RRBaseRelic {
    public FinalBlessingItem(Properties props) {
        super(RRRelicsPropsHandler.addHPSupplierProps(props));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext ctx, TooltipDisplay display, Consumer<Component> builder, TooltipFlag flag) {
        super.appendHoverText(stack, ctx, display, builder, flag);
        RRItemTooltipsHandler.addLore(InitItems.FINAL_BLESSING.getId().getPath(), builder);
    }

    @Override
    public CurioAttributeModifiers getDefaultCurioAttributeModifiers(ItemStack stack) {
        CurioAttributeModifiers.Builder builder = CurioAttributeModifiers.builder();
        Identifier id = InitItems.FINAL_BLESSING.getId();
        CuriosSlotTypes.Preset slot = CuriosSlotTypes.Preset.HANDS;

        builder.addModifier(
            PuffishAttributes.HEALING,
            new AttributeModifier(
                id,
                -0.1F,  // 10%
                AttributeModifier.Operation.ADD_MULTIPLIED_BASE
            ),
            slot.id()
        );
        RRRelicsPropsHandler.addDefaultHealthModifier(builder, id, slot);

        return builder.build();
    }
}
