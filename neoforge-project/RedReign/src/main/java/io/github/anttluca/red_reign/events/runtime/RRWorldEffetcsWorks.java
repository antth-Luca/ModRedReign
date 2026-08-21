package io.github.anttluca.red_reign.events.runtime;

import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.world.data.RedReignWorldData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.gamerules.GameRules;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.level.LevelEvent;

// Register only RedReignWorldData changed
public class RRWorldEffetcsWorks {
    public static void disablePlayerNaturalRegen(ServerLevel serverLevel) {
        // Player passive HP regen. disabled
        serverLevel.getGameRules().set(
            GameRules.NATURAL_HEALTH_REGENERATION,
            false,
            serverLevel.getServer()
        );
    }

    @SubscribeEvent
    public static void onLivingHeal(LivingHealEvent event) {
        // All entities heal reducerd in 60%
        event.setAmount(event.getAmount() * 0.4F);
    }

    @EventBusSubscriber(modid = RedReign.MODID)
    public static class Bootstrap {
        @SubscribeEvent
        public static void onLoadWorld(LevelEvent.Load event) {
            if (event.getLevel() instanceof ServerLevel serverLevel) {
                if (RedReignWorldData.get(serverLevel, ServerLevel.OVERWORLD).isActive()) {
                    NeoForge.EVENT_BUS.register(RRWorldEffetcsWorks.class);
                }
            }
        }
    }
}
