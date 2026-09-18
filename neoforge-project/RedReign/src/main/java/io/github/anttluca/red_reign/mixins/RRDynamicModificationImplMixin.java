package io.github.anttluca.red_reign.mixins;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.anttluca.red_reign.handlers.CurioItemsHandler;
import io.github.anttluca.red_reign.init.InitAttributes;
import io.github.anttluca.red_reign.init.InitDataComponentTypes;
import io.github.anttluca.red_reign.init.InitItems;
import io.github.anttluca.red_reign.items.relics.custom.VortexPearlItem;
import io.github.anttluca.red_reign.utils.components.StolenLifeDataComponentUtils;
import io.github.anttluca.red_reign.world.data.RedReignWorldData;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
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
    private static final float RR_HEALING_REDUCTION = 0.4F;

    @Unique
    private boolean red_reign$isLifeSteal;

    @Unique
    private boolean red_reign$isHealing;
    @Unique
    private float red_reign$initialHealingValue;

    @Unique
    private boolean red_reign$isMagicResistance;

    @Unique
    private LivingEntity red_reign$entity;

    @Inject(
            method = "withPositive",
            at = @At("HEAD")
    )
    private void red_reign$checkPositiveAttribute(Holder<Attribute> attribute, LivingEntity entity, CallbackInfoReturnable<?> cbInfo) {
        red_reign$checkAttribute(attribute, entity);
    }

    @Inject(
            method = "withNegative",
            at = @At("HEAD")
    )
    private void red_reign$checkNegativeAttribute(Holder<Attribute> attribute, LivingEntity entity, CallbackInfoReturnable<?> cbInfo) {
        red_reign$checkAttribute(attribute, entity);
    }

    @Unique
    private void red_reign$checkAttribute(Holder<Attribute> attribute, LivingEntity entity) {
        if (attribute.is(PuffishAttributes.LIFE_STEAL)) {
            this.red_reign$isLifeSteal = true;
            this.red_reign$entity = entity;
        } else if (attribute.is(PuffishAttributes.HEALING)) {
            this.red_reign$isHealing = true;
            this.red_reign$entity = entity;
        } else if (attribute.is(PuffishAttributes.MAGIC_RESISTANCE)) {
            this.red_reign$isMagicResistance = true;
            this.red_reign$entity = entity;
        }
    }

    @ModifyReturnValue(
            method = "relativeTo(F)F",
            at = @At("RETURN")
    )
    private float red_reign$redirectOrModify(float amount) {
        if (!(red_reign$entity instanceof Player player)) return amount;

        if (red_reign$isLifeSteal) {
            if (amount <= 0.0F) return amount;

            ItemStack stack = player.getMainHandItem();
            if (stack.isEmpty()
                    || !stack.has(InitDataComponentTypes.STOLEN_LIFE.get())) return amount;

            StolenLifeDataComponentUtils.addLife(stack, amount);
            return 0.0F;

        } else if (red_reign$isMagicResistance) {
            if (CurioItemsHandler.hasCurio(player, InitItems.VORTEX_PEARL.get())) {
                AttributeInstance armor = player.getAttribute(Attributes.ARMOR);
                if (armor == null) return amount;

                return amount + (float) armor.getValue() * VortexPearlItem.MAGIC_RESISTANCE_P_ARMOR;
            }
        }

        return amount;
    }

    @Inject(
            method = "applyTo(F)F",
            at = @At("HEAD")
    )
    private void red_reign$captureHealingValue(float initial, CallbackInfoReturnable<Float> cbInfoR) {
        this.red_reign$initialHealingValue = initial;
    }

    @ModifyReturnValue(
            method = "applyTo(F)F",
            at = @At("RETURN")
    )
    private float red_reign$modifyHealing(float result) {
        if (!red_reign$isHealing
            || result <= 0.0F) return result;

        LivingEntity entity = red_reign$entity;
        if (entity == null
            || !(entity.level() instanceof ServerLevel serverLevel)) {
                return result;
        }

        if (!RedReignWorldData.get(serverLevel, ServerLevel.OVERWORLD).isActive()) {
            return result;
        }

        return Mth.absMax(result - red_reign$initialHealingValue * RR_HEALING_REDUCTION, 0.0F);
    }
}
