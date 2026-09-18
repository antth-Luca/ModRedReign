package io.github.anttluca.red_reign.mixins;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.anttluca.red_reign.handlers.CurioItemsHandler;
import io.github.anttluca.red_reign.init.InitAttributes;
import io.github.anttluca.red_reign.init.InitItems;
import io.github.anttluca.red_reign.items.relics.custom.VortexPearlItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = LivingEntity.class, priority = 1100)
public abstract class RRLivingEntityMixin {
    @ModifyExpressionValue(
            method = "createLivingAttributes",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/ai/attributes/AttributeSupplier;builder()Lnet/minecraft/world/entity/ai/attributes/AttributeSupplier$Builder;"
            )
    )
    private static AttributeSupplier.Builder red_reign$modifyExpressionValueAtBuilder(AttributeSupplier.Builder builder) {
        InitAttributes.LIVING_ATTRIBUTES.getEntries().forEach(builder::add);

        return builder;
    }

    @ModifyExpressionValue(
            method = "decreaseAirSupply",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/ai/attributes/AttributeInstance;getValue()D"
            )
    )
    private double red_reign$modifyOxygenBonus(double value) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (!(entity instanceof Player player)) return value;

        if (CurioItemsHandler.hasCurio(player, InitItems.VORTEX_PEARL.get())) {
            AttributeInstance armor = player.getAttribute(Attributes.ARMOR);
            if (armor == null) return value;

            return value + armor.getValue() * VortexPearlItem.OXYGEN_BONUS_P_ARMOR;
        }

        return value;
    }
}
