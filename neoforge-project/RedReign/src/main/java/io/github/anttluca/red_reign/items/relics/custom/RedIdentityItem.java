package io.github.anttluca.red_reign.items.relics.custom;

import io.github.anttluca.red_reign.handlers.RRItemTooltipsHandler;
import io.github.anttluca.red_reign.handlers.RRRelicsPropsHandler;
import io.github.anttluca.red_reign.init.InitItems;
import io.github.anttluca.red_reign.items.custom.RRBaseRelic;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.puffish.attributesmod.api.PuffishAttributes;
import top.theillusivec4.curios.api.CurioAttributeModifiers;
import top.theillusivec4.curios.api.CuriosSlotTypes;
import top.theillusivec4.curios.api.SlotAttribute;

import java.util.function.Consumer;

public class RedIdentityItem extends RRBaseRelic {
    private static final double LINEAR = 0.01852D;
    private static final double QUADRATIC = 0.19444D;

    public RedIdentityItem(Properties props) {
        super(RRRelicsPropsHandler.addHPCostProps(props));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext ctx, TooltipDisplay display, Consumer<Component> builder, TooltipFlag flag) {
        super.appendHoverText(stack, ctx, display, builder, flag);
        RRItemTooltipsHandler.addLoreAndEffects(InitItems.RED_IDENTITY.getId().getPath(), 1, builder);
        RRItemTooltipsHandler.indicateOwner(stack, ctx, builder);
    }

    @Override
    public CurioAttributeModifiers getDefaultCurioAttributeModifiers(ItemStack stack) {
        Identifier id = InitItems.RED_IDENTITY.getId();
        String slot = CuriosSlotTypes.Preset.HEAD.id();

        return CurioAttributeModifiers.builder()
                .addModifier(
                    SlotAttribute.getOrCreate(CuriosSlotTypes.Preset.CHARM.id()),
                    new AttributeModifier(
                        id,
                        1.0F,
                        AttributeModifier.Operation.ADD_VALUE
                    ),
                    slot
                )
                .addModifier(
                    PuffishAttributes.STEALTH,
                    new AttributeModifier(
                        id,
                        2.0F,
                        AttributeModifier.Operation.ADD_VALUE
                    ),
                    slot
                )
                .addModifier(
                    Attributes.ARMOR,
                    new AttributeModifier(
                        id,
                        1.0F,
                        AttributeModifier.Operation.ADD_VALUE
                    ),
                    slot
                )
                .build();
    }

    // Effect in: RRRelicsWorksEvent.onPlayerDmgPre
    public static float getDamageBonus(Player player) {
        double missingHp = 1.0D - player.getHealth() / player.getMaxHealth();
        return (float) (LINEAR * missingHp + QUADRATIC * missingHp * missingHp);
    }
}
