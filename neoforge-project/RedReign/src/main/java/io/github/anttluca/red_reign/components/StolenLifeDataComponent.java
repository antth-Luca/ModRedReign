package io.github.anttluca.red_reign.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record StolenLifeDataComponent(float life) {
    public static final StolenLifeDataComponent EMPTY = new StolenLifeDataComponent(0);
    public static final MapCodec<StolenLifeDataComponent> MAP_CODEC = RecordCodecBuilder.mapCodec(builder ->
        builder.group(
            Codec.FLOAT.fieldOf("hp").forGetter(StolenLifeDataComponent::life)
        ).apply(builder, StolenLifeDataComponent::new)
    );
    public static final StreamCodec<FriendlyByteBuf, StolenLifeDataComponent> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.FLOAT, StolenLifeDataComponent::life,
        StolenLifeDataComponent::new
    );
}
