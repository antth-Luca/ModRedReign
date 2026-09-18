package io.github.anttluca.red_reign.init;

import io.github.anttluca.red_reign.RedReign;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class InitAttributes {
    public static final DeferredRegister<Attribute> PLAYER_ATTRIBUTES = DeferredRegister.create(
        BuiltInRegistries.ATTRIBUTE, RedReign.MODID);

    public static final DeferredRegister<Attribute> LIVING_ATTRIBUTES = DeferredRegister.create(
        BuiltInRegistries.ATTRIBUTE, RedReign.MODID);

    // Player Attributes
    public static final DeferredHolder<Attribute, Attribute> LOOTING = PLAYER_ATTRIBUTES.register(
        "looting", () -> new RangedAttribute(
            "attribute." + RedReign.MODID + ".looting",
            0, 0, 10
        ).setSyncable(true));

    // Living Entity Attributes
    public static final DeferredHolder<Attribute, Attribute> FIRE_DAMAGE = LIVING_ATTRIBUTES.register(
        "fire_damage", () -> new RangedAttribute(
            "attribute." + RedReign.MODID + ".fire_damage",
            1, 0, 10
        ).setSyncable(true).setSentiment(Attribute.Sentiment.NEGATIVE));

    public static final DeferredHolder<Attribute, Attribute> POISON_DAMAGE = LIVING_ATTRIBUTES.register(
        "poison_damage", () -> new RangedAttribute(
            "attribute." + RedReign.MODID + ".poison_damage",
            1, 0, 10
        ).setSyncable(true).setSentiment(Attribute.Sentiment.NEGATIVE));

    public static final DeferredHolder<Attribute, Attribute> PHYSICAL_DAMAGE = LIVING_ATTRIBUTES.register(
        "physical_damage", () -> new RangedAttribute(
            "attribute." + RedReign.MODID + ".physical_damage",
            1, 0, 10
        ).setSyncable(true).setSentiment(Attribute.Sentiment.NEGATIVE));
}
