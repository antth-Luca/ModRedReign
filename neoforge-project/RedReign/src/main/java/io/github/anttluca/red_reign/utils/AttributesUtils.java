package io.github.anttluca.red_reign.utils;

import io.github.anttluca.red_reign.init.InitAttributes;
import io.github.anttluca.red_reign.world.data.RedReignWorldData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

public class AttributesUtils {
    public static final float RR_HEAL_REDUCE = 0.4F;

    public static double getHealModifier(LivingEntity entity) {
        double healMod = entity.getAttribute(InitAttributes.HEAL).getValue();

        if (entity.level() instanceof ServerLevel serverLevel) {
            if (RedReignWorldData.get(serverLevel, ServerLevel.OVERWORLD).isActive()) {
                healMod -= RR_HEAL_REDUCE;
            }
        }

        return healMod;
    }
}
