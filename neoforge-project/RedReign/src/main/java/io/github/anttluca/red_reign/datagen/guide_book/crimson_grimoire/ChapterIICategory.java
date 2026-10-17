package io.github.anttluca.red_reign.datagen.guide_book.crimson_grimoire;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import io.github.anttluca.red_reign.datagen.guide_book.crimson_grimoire.chapter_ii.IntroIIEntry;
import io.github.anttluca.red_reign.datagen.guide_book.crimson_grimoire.chapter_ii.WorldEffectsEntry;
import net.minecraft.world.item.Items;

public class ChapterIICategory extends CategoryProvider {
    public static final String ID = "chapter_ii";

    public ChapterIICategory(ModonomiconProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generateEntries() {
        BookEntryModel introII = this.add(new IntroIIEntry(this).generate());
        BookEntryModel worldEffects = this.add(new WorldEffectsEntry(this).generate().withParent(introII));
        this.layout().entry(worldEffects).below(introII, 2);
    }

    @Override
    protected String categoryName() { return ""; }

    @Override
    protected BookIconModel categoryIcon() { return BookIconModel.create(Items.NETHER_WART_BLOCK); }

    @Override
    public String categoryId() { return ID; }
}
