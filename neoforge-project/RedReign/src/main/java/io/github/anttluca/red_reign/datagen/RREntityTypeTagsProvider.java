package io.github.anttluca.red_reign.datagen;

import io.github.anttluca.red_reign.init.InitEntityTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;

import java.util.concurrent.CompletableFuture;

public class RREntityTypeTagsProvider extends EntityTypeTagsProvider {
    public RREntityTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        tag(EntityTypeTags.UNDEAD).add(InitEntityTypes.RED_QUEENS_AVATAR.get());

        tag(EntityTypeTags.IMPACT_PROJECTILES).add(InitEntityTypes.RED_QUEEN_POWER.get());

        tag(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES).add(InitEntityTypes.RED_QUEENS_AVATAR.get());

        tag(EntityTypeTags.FALL_DAMAGE_IMMUNE).add(InitEntityTypes.RED_QUEENS_AVATAR.get());
    }
}
