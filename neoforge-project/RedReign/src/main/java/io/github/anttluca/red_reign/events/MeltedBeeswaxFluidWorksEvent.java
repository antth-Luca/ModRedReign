package io.github.anttluca.red_reign.events;

import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.fluids.MeltedBeeswaxFluid;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

import java.util.UUID;

@EventBusSubscriber(modid = RedReign.MODID)
public class MeltedBeeswaxFluidWorksEvent {
    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        ServerLevel serverLevel = event.getServer().overworld();
        long gameTick = serverLevel.getGameTime();

        MeltedBeeswaxFluid.LAST_EXPOSURE_TICKS.entrySet().removeIf(entry -> {
            UUID uuid = entry.getKey();
            Entity entity = serverLevel.getEntity(uuid);

            if (!(entity instanceof LivingEntity living)) {
                MeltedBeeswaxFluid.EXPOSURE_TICKS.remove(uuid);
                return true;
            }

            if (living.isInFluidType(MeltedBeeswaxFluid.getType())) {
                return false;
            }

            if (gameTick - entry.getValue() <= 1) {
                return false;
            }

            MeltedBeeswaxFluid.EXPOSURE_TICKS.remove(uuid);
            return true;
        });
    }
}
