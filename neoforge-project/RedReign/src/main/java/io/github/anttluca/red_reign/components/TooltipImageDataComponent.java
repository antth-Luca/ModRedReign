package io.github.anttluca.red_reign.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.anttluca.red_reign.RedReign;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

public record TooltipImageDataComponent(
        Identifier decor,
        int bgColorStart, int bgColorEnd,
        int borderColorStart, int borderColorEnd,
        int partOffset, int cornerOffset
) {
    // Default values
    public static final int DEFAULT_PART_OFFSET = -1;
    public static final int DEFAULT_CORNER_OFFSET = 2;
    // Red Reign tooltips
    public static final TooltipImageDataComponent BLOODSTAINED = new TooltipImageDataComponent(
        Identifier.fromNamespaceAndPath(RedReign.MODID, "textures/gui/tooltip/bloodstained.png"),
        0xF7101010, 0xF7101010,
        0xFF711E1E, 0xFF491717,
        DEFAULT_PART_OFFSET, DEFAULT_CORNER_OFFSET
    );
    public static final TooltipImageDataComponent LIFE_INFUSED = new TooltipImageDataComponent(
            Identifier.fromNamespaceAndPath(RedReign.MODID, "textures/gui/tooltip/life_infused.png"),
            0xF7101010, 0xF7101010,
            0xFF711E1E, 0xFF491717,
            DEFAULT_PART_OFFSET, DEFAULT_CORNER_OFFSET
    );
    // Codec
    public static final MapCodec<TooltipImageDataComponent> MAP_CODEC = RecordCodecBuilder.mapCodec(builder ->
        builder.group(
            Identifier.CODEC.fieldOf("decor").forGetter(TooltipImageDataComponent::decor),
            Codec.INT.fieldOf("bgColorStart").forGetter(TooltipImageDataComponent::bgColorStart),
            Codec.INT.fieldOf("bgColorEnd").forGetter(TooltipImageDataComponent::bgColorEnd),
            Codec.INT.fieldOf("borderColorStart").forGetter(TooltipImageDataComponent::borderColorStart),
            Codec.INT.fieldOf("borderColorEnd").forGetter(TooltipImageDataComponent::borderColorEnd),
            Codec.INT.fieldOf("partOffset").forGetter(TooltipImageDataComponent::partOffset),
            Codec.INT.fieldOf("cornerOffset").forGetter(TooltipImageDataComponent::cornerOffset)
        ).apply(builder, TooltipImageDataComponent::new)
    );
    public static final StreamCodec<FriendlyByteBuf, TooltipImageDataComponent> STREAM_CODEC = StreamCodec.composite(
        Identifier.STREAM_CODEC, TooltipImageDataComponent::decor,
        ByteBufCodecs.INT, TooltipImageDataComponent::bgColorStart,
        ByteBufCodecs.INT, TooltipImageDataComponent::bgColorEnd,
        ByteBufCodecs.INT, TooltipImageDataComponent::borderColorStart,
        ByteBufCodecs.INT, TooltipImageDataComponent::borderColorEnd,
        ByteBufCodecs.INT, TooltipImageDataComponent::partOffset,
        ByteBufCodecs.INT, TooltipImageDataComponent::cornerOffset,
        TooltipImageDataComponent::new
    );
}
