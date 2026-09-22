package io.github.anttluca.red_reign.handlers;

import io.github.anttluca.red_reign.networking.packets.RRDisplayItemActivationPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class RRClientPayloadHandler {
    public static void handleDisplayItemActivation(RRDisplayItemActivationPayload payload, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            if (mc.level == null) return;
            Entity entity = mc.level.getEntity(payload.entityId());
            if (entity instanceof LivingEntity living) {
                mc.gameRenderer.displayItemActivation(payload.stack());
                mc.level.playLocalSound(
                    living.getX(),
                    living.getY(),
                    living.getZ(),
                    SoundEvents.ENDER_DRAGON_GROWL,
                    living.getSoundSource(),
                    1.0F, 1.0F,
                    false
                );
                mc.getSoundManager().playDelayed(
                        new SimpleSoundInstance(
                                SoundEvents.GHAST_AMBIENT,
                                living.getSoundSource(),
                                1.0F, 1.0F,
                                RandomSource.create(entity.getRandom().nextLong()),
                                living.getX(),
                                living.getY(),
                                living.getZ()
                        ),
                        20
                );
                mc.getSoundManager().playDelayed(
                    new SimpleSoundInstance(
                        SoundEvents.GHAST_AMBIENT,
                        living.getSoundSource(),
                        1.0F, 1.0F,
                        RandomSource.create(entity.getRandom().nextLong()),
                        living.getX(),
                        living.getY(),
                        living.getZ()
                    ),
                    30
                );
            }
        });
    }
}
