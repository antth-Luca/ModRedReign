package io.github.anttluca.red_reign.networking.packets;

import io.github.anttluca.red_reign.RedReign;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

public record RRDisplayItemActivationPayload(int entityId, ItemStack stack) implements CustomPacketPayload {
    public static final Type<RRDisplayItemActivationPayload> TYPE = new Type<>(Identifier.fromNamespaceAndPath(
        RedReign.MODID, "custom_display_item_activation"));

    public static final StreamCodec<RegistryFriendlyByteBuf, RRDisplayItemActivationPayload> STREAM_CODEC =
        StreamCodec.composite(
                ByteBufCodecs.VAR_INT, RRDisplayItemActivationPayload::entityId,
                ItemStack.STREAM_CODEC, RRDisplayItemActivationPayload::stack,
                RRDisplayItemActivationPayload::new
        );

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }
}
