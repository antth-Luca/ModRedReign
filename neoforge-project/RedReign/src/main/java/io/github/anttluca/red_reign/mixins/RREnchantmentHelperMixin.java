package io.github.anttluca.red_reign.mixins;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.anttluca.red_reign.init.InitAttributes;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnchantmentHelper.class)
public class RREnchantmentHelperMixin {
    @ModifyReturnValue(
            method = "getEnchantmentLevel",
            at = @At("RETURN")
    )
    private static int red_reign$modifyLootingLevel(int original, @Local(argsOnly = true) Holder<Enchantment> enchantment, @Local(argsOnly = true) LivingEntity entity) {
        if (entity instanceof Player player
            && enchantment.is(Enchantments.LOOTING)) {
                AttributeInstance looting = player.getAttribute(InitAttributes.LOOTING);
                if (looting == null) return original;

                return original + (int) looting.getValue();
        }

        return original;
    }
}
