package io.github.anttluca.red_reign.events;

import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.handlers.CurioItemsHandler;
import io.github.anttluca.red_reign.init.InitItems;
import io.github.anttluca.red_reign.items.relics.custom.CoralGauntletItem;
import io.github.anttluca.red_reign.items.relics.custom.DaisySilverMeteorItem;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AnvilUpdateEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

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

    // Relic: CoralGauntlet
    @SubscribeEvent
    public static void onPlayerDmgPre(LivingDamageEvent.Pre event) {
        if (event.getSource().getEntity() instanceof Player player
            && CurioItemsHandler.hasCurio(player, InitItems.CORAL_GAUNTLET.get())) {
                LivingEntity target = event.getEntity();
                if (target.getHealth() <= target.getMaxHealth() * CoralGauntletItem.TARGET_THRESHOLD) {
                    float newDamage = event.getNewDamage() * CoralGauntletItem.DAMAGE_MULTIPLY;
                    event.setNewDamage(newDamage);
                    player.heal(newDamage * CoralGauntletItem.LIFE_STEAL_BONUS);
                }
        }
    }
}
