package io.github.anttluca.red_reign.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

public record TooltipImageDataComponent(
        Identifier decor,
        int bgStart, int bgEnd,
        int borderStart, int borderEnd
) {
    public static final TooltipImageDataComponent BLOODSTAINED = new TooltipImageDataComponent(
        null,
        0xFF120312, 0xFF120312,
        0xFF711E1E, 0xFF491717
    );
    public static final MapCodec<TooltipImageDataComponent> MAP_CODEC = RecordCodecBuilder.mapCodec(builder ->
        builder.group(
            Identifier.CODEC.fieldOf("hp").forGetter(TooltipImageDataComponent::decor),
            Codec.INT.fieldOf("bgStart").forGetter(TooltipImageDataComponent::bgStart),
            Codec.INT.fieldOf("bgEnd").forGetter(TooltipImageDataComponent::bgEnd),
            Codec.INT.fieldOf("borderStart").forGetter(TooltipImageDataComponent::borderStart),
            Codec.INT.fieldOf("borderEnd").forGetter(TooltipImageDataComponent::borderEnd)
        ).apply(builder, TooltipImageDataComponent::new)
    );
    public static final StreamCodec<FriendlyByteBuf, TooltipImageDataComponent> STREAM_CODEC = StreamCodec.composite(
        Identifier.STREAM_CODEC, TooltipImageDataComponent::decor,
        ByteBufCodecs.INT, TooltipImageDataComponent::bgStart,
        ByteBufCodecs.INT, TooltipImageDataComponent::bgEnd,
        ByteBufCodecs.INT, TooltipImageDataComponent::borderStart,
        ByteBufCodecs.INT, TooltipImageDataComponent::borderEnd,
        TooltipImageDataComponent::new
    );
}
