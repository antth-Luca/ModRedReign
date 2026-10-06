package io.github.anttluca.red_reign.datagen;

import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.init.InitFluids;
import io.github.anttluca.red_reign.tags.RRFluidTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;

import java.util.concurrent.CompletableFuture;

public class RRFluidTagsProvider extends FluidTagsProvider {
    public RRFluidTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, RedReign.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        tag(RRFluidTags.MELTED_BEESWAX)
                .add(InitFluids.MELTED_BEESWAX.get())
                .add(InitFluids.FLOWING_MELTED_BEESWAX.get());
    }
}
