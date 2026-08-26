package io.github.anttluca.red_reign.mixins;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.anttluca.red_reign.init.InitAttributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
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

        System.out.println("Olá");

        return builder;
    }
}
