package io.github.anttluca.red_reign.events;

import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.handlers.CurioItemsHandler;
import io.github.anttluca.red_reign.init.InitItems;
import io.github.anttluca.red_reign.items.relics.custom.CoralGauntletItem;
import io.github.anttluca.red_reign.items.relics.custom.DaisySilverMeteorItem;
import io.github.anttluca.red_reign.items.relics.custom.RedIdentityItem;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AnvilUpdateEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.living.LivingKnockBackEvent;

@EventBusSubscriber(modid = RedReign.MODID)
public class RRRelicsWorksEvent {
    // Relic: DaisySilverMeteor
    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event) {
        if (CurioItemsHandler.hasCurio(event.getPlayer(), InitItems.DAISY_SILVER_METEOR.get())) {
            int origXpCost = event.getXpCost();
            event.setXpCost(Mth.ceil(origXpCost * DaisySilverMeteorItem.XP_COST_MODIFIER));
        }
    }

    @SubscribeEvent
    public static void onPlayerDmgPre(LivingDamageEvent.Pre event) {
        if (event.getSource().getEntity() instanceof Player player) {
            // Relic: CoralGauntlet
            if (CurioItemsHandler.hasCurio(player, InitItems.CORAL_GAUNTLET.get())) {
                LivingEntity target = event.getEntity();
                if (target.getHealth() <= target.getMaxHealth() * CoralGauntletItem.TARGET_THRESHOLD) {
                    float newDamage = event.getNewDamage() * CoralGauntletItem.DAMAGE_MULTIPLY;
                    event.setNewDamage(newDamage);
                    player.heal(newDamage * CoralGauntletItem.LIFE_STEAL_BONUS);
                }
            }

            // Relic: RedIdentity
            if (CurioItemsHandler.hasCurio(player, InitItems.RED_IDENTITY.get())) {
                float dmgBonus = RedIdentityItem.getDamageBonus(player);
                event.setNewDamage(event.getNewDamage() * (1.0F + dmgBonus));
            }
        }
    }

    // Relic: RoseAnchor
    @SubscribeEvent
    public static void onPlayerKnockback(LivingKnockBackEvent event) {
        if (event.getEntity() instanceof Player player
            && CurioItemsHandler.hasCurio(player, InitItems.ROSE_ANCHOR.get())) {
                event.setCanceled(true);
        }
    }

    // Relic: RoseAnchor
    @SubscribeEvent
    public static void onPlayerFall(LivingFallEvent event) {
        if (event.getEntity() instanceof Player player
            && CurioItemsHandler.hasCurio(player, InitItems.ROSE_ANCHOR.get())) {
                event.setCanceled(true);
        }
    }
}
