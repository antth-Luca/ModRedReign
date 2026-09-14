package io.github.anttluca.red_reign.components;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public record AdoptableDataComponent(UUID owner) {
    public static final Component UNKNOWN_NAME = Component.translatable("component.red_reign.adoptable.unknown").withStyle(ChatFormatting.LIGHT_PURPLE);
    public static final AdoptableDataComponent EMPTY = new AdoptableDataComponent(new UUID(0L, 0L));
    public static final MapCodec<AdoptableDataComponent> MAP_CODEC = RecordCodecBuilder.mapCodec(builder ->
            builder.group(
                    UUIDUtil.CODEC.fieldOf("owner").forGetter(AdoptableDataComponent::owner)
            ).apply(builder, AdoptableDataComponent::new)
    );
    public static final StreamCodec<FriendlyByteBuf, AdoptableDataComponent> STREAM_CODEC = StreamCodec.composite(
            UUIDUtil.STREAM_CODEC, AdoptableDataComponent::owner,
            AdoptableDataComponent::new
    );

    public AdoptableDataComponent(Player player) {
        this(player.getUUID());
    }

    public boolean is(Player player) {
        return this.is(player.getUUID());
    }

    public boolean is(UUID uuid) {
        return this.owner.equals(uuid);
    }
}
