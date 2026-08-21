package io.github.anttluca.red_reign.events.runtime;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

import java.util.*;
import java.util.function.Consumer;

// Register only RedReignWorldData changed
public class WhiteQueenDeathStory {
    private record ScheduledTask(int delayTicks, Consumer<MinecraftServer> action) { }

    private static final Queue<ScheduledTask> STEPS = new LinkedList<>();

    public static void startStorySequence() {
        STEPS.add(new ScheduledTask(0,
                WhiteQueenDeathStory::stepWhiteQueenGrowls));
    }

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        if (STEPS.isEmpty())
            NeoForge.EVENT_BUS.unregister(WhiteQueenDeathStory.class);

        MinecraftServer server = event.getServer();
        Iterator<ScheduledTask> iterator = STEPS.iterator();
        List<ScheduledTask> nextCycle = new ArrayList<>();

        while (iterator.hasNext()) {
            ScheduledTask task = iterator.next();
            if (task.delayTicks <= 1) {
                task.action().accept(server);
            } else {
                nextCycle.add(new ScheduledTask(task.delayTicks - 1, task.action()));
            }
            iterator.remove();
        }

        STEPS.addAll(nextCycle);
    }

    public static void stepWhiteQueenGrowls(MinecraftServer server) {
        Holder<SoundEvent> hSoundEvent = BuiltInRegistries.SOUND_EVENT.getOrThrow(
            ResourceKey.create(Registries.SOUND_EVENT, SoundEvents.ENDER_DRAGON_GROWL.location())
        );

        for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            player.connection.send(new ClientboundSoundPacket(
                    hSoundEvent,
                    SoundSource.MASTER,
                    player.getX(), player.getY(), player.getZ(),
                    1.0F, 1.0F,
                    player.level().getRandom().nextLong()
            ));
        }
    }
}
