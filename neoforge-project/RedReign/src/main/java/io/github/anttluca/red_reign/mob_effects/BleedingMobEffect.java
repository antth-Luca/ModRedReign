package io.github.anttluca.red_reign.mob_effects;

import io.github.anttluca.red_reign.init.InitMobEffects;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.puffish.attributesmod.api.PuffishAttributes;

public class BleedingMobEffect extends MobEffect {
    private static final float LIFE_BASED_DAMAGE = 0.04F;  // 4% per amplifier
    private static final int DAMAGE_INTERVAL = 25;

    public BleedingMobEffect() {
        super(MobEffectCategory.HARMFUL, 0x6C100C);
        this.addAttributeModifier(
                PuffishAttributes.HEALING,
                InitMobEffects.BLEEDING.getId(),
                -0.025F,  // 2.5% per amplifier
                AttributeModifier.Operation.ADD_MULTIPLIED_BASE
        );
    }

    @Override
    public boolean applyEffectTick(ServerLevel level, LivingEntity mob, int amplification) {
        float currentMobHp = mob.getHealth();
        if (currentMobHp > 1.0F) {
            Registry<DamageType> dTypeReg = mob.damageSources().damageTypes;
            Holder<DamageType> dType = dTypeReg.get(NeoForgeMod.POISON_DAMAGE).orElse(dTypeReg.getOrThrow(DamageTypes.MAGIC));
            mob.hurtServer(level, new DamageSource(dType), currentMobHp * (1 + amplification) * LIFE_BASED_DAMAGE);
        }

        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int tickCount, int amplification) {
        int interval = DAMAGE_INTERVAL >> amplification;
        return interval > 0 ? tickCount % interval == 0 : true;
    }
}
