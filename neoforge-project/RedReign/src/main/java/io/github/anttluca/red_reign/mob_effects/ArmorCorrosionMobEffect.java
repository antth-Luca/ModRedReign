package io.github.anttluca.red_reign.mob_effects;

import io.github.anttluca.red_reign.init.InitMobEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.puffish.attributesmod.api.PuffishAttributes;

public class ArmorCorrosionMobEffect extends MobEffect {
    public ArmorCorrosionMobEffect() {
        super(MobEffectCategory.HARMFUL, 0x4D566F);
        this.addAttributeModifier(
                PuffishAttributes.ARMOR_SHRED,
                InitMobEffects.ARMOR_CORROSION.getId(),
                1.0F,  // 1pt per amplifier
                AttributeModifier.Operation.ADD_VALUE
        );
    }
}
