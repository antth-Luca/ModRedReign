package io.github.anttluca.red_reign.mixins;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.anttluca.red_reign.init.InitDataComponentTypes;
import io.github.anttluca.red_reign.init.InitItems;
import io.github.anttluca.red_reign.utils.components.StolenLifeDataComponentUtils;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.puffish.attributesmod.api.PuffishAttributes;
import net.puffish.attributesmod.util.DynamicModificationImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DynamicModificationImpl.class)
public class RRDynamicModificationImplMixin {
    @Unique
    private boolean red_reign$isLifeSteal;

    @Unique
    private LivingEntity red_reign$entity;

    @Inject(
            method = "withPositive",
            at = @At("HEAD")
    )
    private void red_reign$checkLifeSteal(Holder<Attribute> attribute, LivingEntity entity, CallbackInfoReturnable<?> cbInfo) {
        if (attribute.is(PuffishAttributes.LIFE_STEAL)) {
            System.out.println("Verificando o atributo");
            this.red_reign$isLifeSteal = true;
            this.red_reign$entity = entity;
        }
    }

    @ModifyReturnValue(
            method = "relativeTo(F)F",
            at = @At("RETURN")
    )
    private float red_reign$redirectLifeSteal(float amount) {
        if (!red_reign$isLifeSteal
            || amount <= 0.0F) return amount;

        if (!(red_reign$entity instanceof Player player)) return amount;

        ItemStack stack = player.getMainHandItem();
        if (stack.isEmpty()
            || !stack.has(InitDataComponentTypes.STOLEN_LIFE.get())) return amount;

        StolenLifeDataComponentUtils.addLife(stack, amount);
        return 0.0F;
    }
}
