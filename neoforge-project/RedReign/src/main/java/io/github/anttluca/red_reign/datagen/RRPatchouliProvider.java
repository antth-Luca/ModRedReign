package io.github.anttluca.red_reign.datagen;

import com.google.gson.JsonObject;
import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.api.PatchouliProvider;
import io.github.anttluca.red_reign.init.InitCreativeTabs;
import net.minecraft.data.PackOutput;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class RRPatchouliProvider extends PatchouliProvider {
    public RRPatchouliProvider(PackOutput output) {
        super(output,
            RedReign.MODID,
            "crimson_grimoire",
            "patchouli:textures/gui/book_red.png",
            InitCreativeTabs.MAIN.getRegisteredName()
        );
    }

    @Override
    protected Map<Path, List<JsonObject>> categories() {
        return Map.of();
    }

    @Override
    protected Map<Path, List<JsonObject>> entries() {
        return Map.of();
    }
}
