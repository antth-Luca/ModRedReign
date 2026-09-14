package io.github.anttluca.red_reign.mob_effects.consumes;

import io.github.anttluca.red_reign.init.InitConsumeEffectsTypes;
import io.github.anttluca.red_reign.init.InitItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.level.Level;
import net.puffish.attributesmod.api.DynamicModification;
import net.puffish.attributesmod.api.PuffishAttributes;

public record RedQueenDeathProtectionConsumeEffect() implements ConsumeEffect {
    @Override
    public ConsumeEffect.Type<RedQueenDeathProtectionConsumeEffect> getType() {
        return InitConsumeEffectsTypes.RED_QUEEN_DEATH_PROTECTION.get();
    }

    @Override
    public boolean apply(Level level, ItemStack stack, LivingEntity entity) {
        if (entity instanceof Player player
            && stack.is(InitItems.TOTEM_OF_THE_RED_QUEEN.get())) {
                float restore = DynamicModification.create()
                        .withPositive(PuffishAttributes.LIFE_STEAL, player)
                        .relativeTo(player.getMaxHealth());
                player.setHealth(restore);
        }

        return true;
    }
}
