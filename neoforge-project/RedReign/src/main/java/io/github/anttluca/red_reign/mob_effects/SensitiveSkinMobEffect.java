package io.github.anttluca.red_reign.mob_effects;

import io.github.anttluca.red_reign.init.InitAttributes;
import io.github.anttluca.red_reign.init.InitMobEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class SensitiveSkinMobEffect extends MobEffect {
    public SensitiveSkinMobEffect() {
        super(MobEffectCategory.NEUTRAL, 0xFBDC75);
        this.addAttributeModifier(
                InitAttributes.FIRE_DAMAGE,
                InitMobEffects.SENSITIVE_SKIN.getId(),
                1.0F,
                AttributeModifier.Operation.ADD_VALUE
        );
    }
}
