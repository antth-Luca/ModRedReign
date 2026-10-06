package io.github.anttluca.red_reign.datagen;

import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.init.InitBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;

import java.util.concurrent.CompletableFuture;

public class RRBlockTagsProvider extends BlockTagsProvider {
    public RRBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, RedReign.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(InitBlocks.CRAFTING_TABLE_OF_RED_QUEEN.get())
                .add(InitBlocks.ROSE_QUARTZ_BLOCK.get());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(InitBlocks.CRAFTING_TABLE_OF_RED_QUEEN.get());
    }
}
