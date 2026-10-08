package io.github.anttluca.red_reign.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.extensions.ILevelExtension;
import net.neoforged.neoforge.fluids.FluidType;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public abstract class LanguagesProvider implements DataProvider {
    private static final Codec<Map<String, Component>> CODEC = Codec.unboundedMap(Codec.STRING, ComponentSerialization.CODEC);

    private final List<Map<String, Component>> datas = new ArrayList<>();
    private final PackOutput output;
    private final String modid;
    private final List<String> locales;

    public LanguagesProvider(PackOutput output, String modid, List<String> locales) {
        this.output = output;
        this.modid = modid;
        this.locales = List.copyOf(locales);

        for (int l = 0; l < this.locales.size(); l++) {
            this.datas.add(new TreeMap<>());
        }
    }

    protected abstract void addTranslations();

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        addTranslations();

        List<CompletableFuture<?>> futures = new ArrayList<>();

        for (int l = 0; l < this.locales.size(); l++) {
            Map<String, Component> data = this.datas.get(l);
            if (data.isEmpty()) continue;

            Path target = this.output.getOutputFolder(PackOutput.Target.RESOURCE_PACK)
                    .resolve(this.modid)
                    .resolve("lang")
                    .resolve(this.locales.get(l) + ".json");

            futures.add(save(cache, data, target));
        }

        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Languages for mod" + this.modid + ": " + this.locales;
    }

    private CompletableFuture<?> save(CachedOutput cache, Map<String, Component> data, Path target) {
        final JsonElement json = CODEC.encode(data, JsonOps.INSTANCE, new JsonObject()).getOrThrow();
        return DataProvider.saveStable(cache, json, target);
    }

    protected void addStory(String key, String... values) {
        add("story.%s.%s".formatted(this.modid, key), values);
    }

    protected void addAdvancement(String key, String[] titles, String[] descriptions) {
        String base = "advancement.%s.%s.".formatted(this.modid, key);
        add(base + "title", titles);
        add(base + "description", descriptions);
    }

    protected void addDataComponent(String key, String... values) {
        add("data_component.%s.%s".formatted(this.modid, key), values);
    }

    protected void addEnchantment(String key, String[] names, String[] descriptions) {
        addEnchantment(key, names);
        addEnchantment(key + ".desc", descriptions);
    }

    protected void addEnchantment(String key, String... values) {
        add("enchantment.%s.%s".formatted(this.modid, key), values);
    }

    protected void addItem(Supplier<? extends Item> key, String... names) {
        addItem(key.get(), names);
    }

    protected void addItem(Item key, String... names) {
        add(key.getDescriptionId(), names);
    }

    protected void addBlock(Supplier<? extends Block> key, String... names) {
        addBlock(key.get(), names);
    }

    protected void addBlock(Block key, String... names) {
        add(key.getDescriptionId(), names);
    }

    protected void addAttribute(Supplier<? extends Attribute> key, String... names) {
        addAttribute(key.get(), names);
    }

    protected void addAttribute(Attribute key, String... names) {
        add(key.getDescriptionId(), names);
    }

    protected void addMobEffect(Supplier<? extends MobEffect> key, String... names) {
        addMobEffect(key.get(), names);
    }

    protected void addMobEffect(MobEffect key, String... names) {
        add(key.getDescriptionId(), names);
    }

    protected void addFluid(Supplier<? extends FluidType> key, String... names) {
        addFluid(key.get(), names);
    }

    protected void addFluid(FluidType key, String... names) {
        add(key.getDescriptionId(), names);
    }

    protected void addEntity(Supplier<? extends EntityType<?>> key, String... names) {
        addEntity(key.get(), names);
    }

    protected void addEntity(EntityType<?> key, String... names) {
        add(key.getDescriptionId(), names);
    }

    protected void addDimension(ResourceKey<Level> dimension, String... values) {
        add(dimension.identifier().toLanguageKey(ILevelExtension.TRANSLATION_PREFIX), values);
    }

    protected void addBiome(ResourceKey<Biome> biome, String... values) {
        add(biome.identifier().toLanguageKey("biome"), values);
    }

    protected void add(String key, String... values) {
        if (values.length < this.locales.size())
            throw new IllegalStateException("Key '%s' has %d values but there are %d locales"
                    .formatted(key, values.length, this.locales.size()));

        for (int v = 0; v < values.length; v++) {
            if (this.datas.get(v).put(key, Component.literal(values[v])) != null) {
                throw new IllegalStateException("Duplicate translation key %s for locale %s"
                        .formatted(key, this.locales.get(v)));
            }
        }
    }
}
