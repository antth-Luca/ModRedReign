package io.github.anttluca.red_reign.utils.components;

import io.github.anttluca.red_reign.components.StolenLifeDataComponent;
import io.github.anttluca.red_reign.init.InitDataComponentTypes;
import net.minecraft.world.item.ItemStack;

public class StolenLifeDataComponentUtils {
    public static final float MAX_STOLEN_LIFE = 20.0F;
    public static final float MIN_STOLEN_LIFE = 0.0F;

    public static void setLife(ItemStack stack, float amount) {
        float newAmount = Math.clamp(amount, MIN_STOLEN_LIFE, MAX_STOLEN_LIFE);
        stack.set(
            InitDataComponentTypes.STOLEN_LIFE.get(),
            new StolenLifeDataComponent(newAmount)
        );
    }

    public static float getLife(ItemStack stack) {
        return stack.getOrDefault(
            InitDataComponentTypes.STOLEN_LIFE,
            StolenLifeDataComponent.EMPTY
        ).life();
    }
}
