package io.github.anttluca.red_reign.tags;

import io.github.anttluca.red_reign.RedReign;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class RRItemTags {
    public static final TagKey<Item> BLOODSTAINED_RELICS = ItemTags.create(
        Identifier.fromNamespaceAndPath(
            RedReign.MODID, "bloodstained_relics"));
}
