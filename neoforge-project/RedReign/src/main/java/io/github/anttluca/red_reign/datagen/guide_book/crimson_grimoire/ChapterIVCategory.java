package io.github.anttluca.red_reign.datagen.guide_book.crimson_grimoire;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import io.github.anttluca.red_reign.datagen.guide_book.RRResearchSubProvider;
import io.github.anttluca.red_reign.datagen.guide_book.crimson_grimoire.chapter_iv.IntroIVEntry;
import io.github.anttluca.red_reign.datagen.guide_book.crimson_grimoire.chapter_iv.PurificationSpellEntry;
import io.github.anttluca.red_reign.init.InitItems;

public class ChapterIVCategory extends CategoryProvider {
    public static final String ID = "chapter_iv";

    public ChapterIVCategory(ModonomiconProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generateEntries() {
        BookEntryModel introIV = this.add(new IntroIVEntry(this).generate());
        BookEntryModel purificationSpell = this.add(new PurificationSpellEntry(this).generate()
                .withCondition(RRResearchSubProvider.RED_SIGNET)
                .hideWhileLocked(true)
                .withParent(introIV));
        this.layout().entry(purificationSpell).below(introIV, 2);
    }

    @Override
    protected String categoryName() { return ""; }

    @Override
    protected BookIconModel categoryIcon() { return BookIconModel.create(InitItems.PURIFICATION_SPELL); }

    @Override
    public String categoryId() { return ID; }
}
