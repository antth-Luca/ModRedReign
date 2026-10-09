package io.github.anttluca.red_reign.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public abstract class PatchouliProvider implements DataProvider {
    protected static final String BOOK = "book";

    private static final String PATCHOULI_BOOKS = "patchouli_books";
    private static final String CATEGORIES = "categories";
    private static final String ENTRIES = "entries";

    private final PackOutput output;
    private final String bookTexture;
    private final String creativeTab;

    protected final String modid;
    protected final String bookName;

    public PatchouliProvider(PackOutput output, String modid, String bookName, String bookTexture, String creativeTab) {
        this.output = output;
        this.modid = modid;
        this.bookName = bookName;
        this.bookTexture = bookTexture;
        this.creativeTab = creativeTab;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        List<CompletableFuture<?>> futures = new ArrayList<>();

        // book.json
        futures.add(save(cache, book(), output.getOutputFolder(PackOutput.Target.DATA_PACK)
                .resolve(this.modid)
                .resolve(PATCHOULI_BOOKS)
                .resolve(this.bookName)
                .resolve("book.json")
        ));

        Path bookFolder = output.getOutputFolder(PackOutput.Target.RESOURCE_PACK)
                .resolve(PATCHOULI_BOOKS)
                .resolve(this.bookName);

        // Categories
        Path catFolder = bookFolder.resolve(CATEGORIES);
        // FOR category : target = catFolder.resolve(); futures.add(save(cache, category, target))

        // Entries
        Path entFolder = bookFolder.resolve(ENTRIES);
        // FOR entry : target = entFolder.resolve(); futures.add(save(cache, entry, target))

        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    private static CompletableFuture<?> save(CachedOutput cache, JsonElement json, Path path) {
        return DataProvider.saveStable(cache, json, path);
    }

    protected JsonObject book() {
        final String nameKey = BOOK + this.modid + ".name";
        final String subtitleKey = BOOK + this.modid + ".subtitle";
        final String landingKey = BOOK + this.modid + ".landing";

        JsonObject b = new JsonObject();
        b.addProperty("name", nameKey);
        b.addProperty("subtitle", subtitleKey);
        b.addProperty("landing_text", landingKey);
        b.addProperty("book_texture", this.bookTexture);
        b.addProperty("model", this.modid + ":book");
        b.addProperty("creative_tab", this.creativeTab);
        b.addProperty("use_blocky_font", true);
        b.addProperty("use_resource_pack", true);

        JsonObject m = new JsonObject();
        m.addProperty("$(fan)", "$(#720000)$(italic)");

        b.add("macros", m);

        return b;
    }

    protected abstract Map<Path, List<JsonObject>> categories();

    protected abstract Map<Path, List<JsonObject>> entries();

    @Override
    public String getName() {
        return this.modid + " Patchouli";
    }
}
