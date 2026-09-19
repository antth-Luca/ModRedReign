package io.github.anttluca.red_reign.items.relics.custom;

import io.github.anttluca.red_reign.handlers.RRItemTooltipsHandler;
import io.github.anttluca.red_reign.handlers.RRRelicsPropsHandler;
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

public class RedSignetItem extends RRBaseRelic {
    public static final float LIFE_STEAL_MULTIPLY = 1.4F;

    public RedSignetItem(Properties props) {
        super(RRRelicsPropsHandler.addHPCostProps(props));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext ctx, TooltipDisplay display, Consumer<Component> builder, TooltipFlag flag) {
        super.appendHoverText(stack, ctx, display, builder, flag);
        RRItemTooltipsHandler.addLoreAndEffects(InitItems.RED_SIGNET.getId().getPath(), 2, builder);
        RRItemTooltipsHandler.indicateOwner(stack, ctx, builder);
    }

    @Override
    public CurioAttributeModifiers getDefaultCurioAttributeModifiers(ItemStack stack) {
        return CurioAttributeModifiers.builder()
                .addModifier(
                    PuffishAttributes.LIFE_STEAL,
                    new AttributeModifier(
                        InitItems.RED_SIGNET.getId(),
                        0.1F,  // 10%
                        AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                    ),
                    CuriosSlotTypes.Preset.RING.id()
                )
                .build();
    }

    // Effects in: RRDynamicModificationImplMixin.red_reign$redirectOrModify and RRRelicsWorksEvent.onPlayerDmgPre
}
