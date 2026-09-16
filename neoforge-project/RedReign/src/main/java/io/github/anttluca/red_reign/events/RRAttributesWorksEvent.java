package io.github.anttluca.red_reign.events;

import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.init.InitAttributes;
import io.github.anttluca.red_reign.utils.AttributesUtils;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = RedReign.MODID)
public class RRAttributesWorksEvent {
    // Create/Append attributes to entities
    @SubscribeEvent
    public static void onSetAttributes(EntityAttributeModificationEvent event) {
        InitAttributes.PLAYER_ATTRIBUTES.getEntries().forEach(attribute ->
            event.add(EntityType.PLAYER, attribute));
    }

    @SubscribeEvent
    public static void onFireLivingHurt(LivingIncomingDamageEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide()) return;

        float damage = event.getAmount();

        // Attribute: Fire damage modifier
        if (event.getSource().is(DamageTypeTags.IS_FIRE)) {
            AttributeInstance fireDamage = entity.getAttribute(InitAttributes.FIRE_DAMAGE);
            if (fireDamage != null) {
                damage *= (float) fireDamage.getValue();
            }
        }
        // Attribute: Venom damage modifier
        else if (event.getSource().is(NeoForgeMod.POISON_DAMAGE)) {
            AttributeInstance venomDamage = entity.getAttribute(InitAttributes.POISON_DAMAGE);
            if (venomDamage != null) {
                damage *= (float) venomDamage.getValue();
            }
        }

        event.setAmount(damage);
    }

    // Attribute: Heal modifier
    @SubscribeEvent
    public static void onLivingHeal(LivingHealEvent event) {
        double newAmount = event.getAmount() * AttributesUtils.getHealModifier(event.getEntity());
        event.setAmount((float) newAmount);
    }
}
