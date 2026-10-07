package io.github.anttluca.red_reign.datagen;

import io.github.anttluca.red_reign.RedReign;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class RRPatchouliProvider implements DataProvider {
    private static final String CRIMSON_GRIMOIRE = "crimson_grimoire";
    private static final String 

    private final PackOutput output;

    public RRPatchouliProvider(PackOutput output) {
        this.output = output;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        List<CompletableFuture<?>> jobs = new ArrayList<>();

        Path dataFolder = output.getOutputFolder(PackOutput.Target.DATA_PACK).resolve(RedReign.MODID);
        Path enFolder = output.getOutputFolder(PackOutput.Target.RESOURCE_PACK).resolve(RedReign.MODID)
                .resolve("patchouli_books")
                .resolve(CRIMSON_GRIMOIRE)
                .resolve("en_us");
        Path ptFolder = output.getOutputFolder(PackOutput.Target.RESOURCE_PACK).resolve(RedReign.MODID)
                .resolve("patchouli_books")
                .resolve(CRIMSON_GRIMOIRE)
                .resolve("pt_br");

        // book.json
        jobs.add()
    }

    @Override
    public String getName() {
        return "Red Reign Patchouli";
    }
}
