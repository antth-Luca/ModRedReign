package io.github.anttluca.red_reign.items;

import io.github.anttluca.red_reign.handlers.RRItemTooltipsHandler;
import io.github.anttluca.red_reign.init.InitItems;
import io.github.anttluca.red_reign.items.custom.RRBaseItem;
import io.github.anttluca.red_reign.mob_effects.consumes.RedQueenDeathProtectionConsumeEffect;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.DeathProtection;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.item.consume_effects.ClearAllStatusEffectsConsumeEffect;

import java.util.List;
import java.util.function.Consumer;

public class TotemOfTheRedQueenItem extends RRBaseItem {
    public static final DeathProtection RED_QUEEN_DEATH_PROTECTION = new DeathProtection(
        List.of(
            new ClearAllStatusEffectsConsumeEffect(),
            new RedQueenDeathProtectionConsumeEffect(),
            new ApplyStatusEffectsConsumeEffect(List.of(
                new MobEffectInstance(MobEffects.REGENERATION, 900, 1),
                new MobEffectInstance(MobEffects.ABSORPTION, 100, 1),
                new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0)
            ))
        )
    );

    public TotemOfTheRedQueenItem(Properties props) {
        super(props
                .stacksTo(1)
                .rarity(Rarity.UNCOMMON)
                .component(DataComponents.DEATH_PROTECTION, RED_QUEEN_DEATH_PROTECTION)
        );
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext ctx, TooltipDisplay display, Consumer<Component> builder, TooltipFlag flag) {
        super.appendHoverText(stack, ctx, display, builder, flag);
        RRItemTooltipsHandler.addLore(InitItems.TOTEM_OF_THE_RED_QUEEN.getId().getPath(), builder);
    }
}
