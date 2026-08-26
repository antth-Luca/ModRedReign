package io.github.anttluca.red_reign.events;

import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.init.InitAttributes;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber(modid = RedReign.MODID)
public class RRAttributesWorksEvent {
    // Create/Append attributes to entities
    @SubscribeEvent
    public static void onSetAttributes(EntityAttributeModificationEvent event) {
        InitAttributes.PLAYER_ATTRIBUTES.getEntries().forEach(attribute ->
            event.add(EntityType.PLAYER, attribute));
    }

    // Attribute: Fire damage modifier
    @SubscribeEvent
    public static void onFireLivingHurt(LivingIncomingDamageEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide()) return;

        if (event.getSource().is(DamageTypeTags.IS_FIRE)) {
            AttributeInstance fireDamage = entity.getAttribute(InitAttributes.FIRE_DAMAGE);
            if (fireDamage == null) return;

            float damage = event.getAmount();
            event.setAmount((float) (damage * fireDamage.getValue()));
        }
    }
}
