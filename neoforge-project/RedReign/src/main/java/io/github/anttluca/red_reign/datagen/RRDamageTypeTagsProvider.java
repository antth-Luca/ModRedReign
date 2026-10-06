package io.github.anttluca.red_reign.datagen;

import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.tags.RRDamageTypeTags;
import io.github.anttluca.red_reign.utils.RRResourceKeyUtils.DamageTypeKeys;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class RRDamageTypeTagsProvider extends DamageTypeTagsProvider {
    public RRDamageTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, RedReign.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(DamageTypeKeys.BLEEDING, RRDamageTypeTags.IS_RED_QUEEN);

        tag(DamageTypeKeys.RED_QUEEN_POWER, RRDamageTypeTags.IS_RED_QUEEN);

        tag(Tags.DamageTypes.IS_MAGIC).addTag(RRDamageTypeTags.IS_RED_QUEEN);
    }

    @SafeVarargs
    private void tag(ResourceKey<DamageType> type, TagKey<DamageType>... tags) {
        for (TagKey<DamageType> key : tags) {
            tag(key).add(type);
        }
    }
}
