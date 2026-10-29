package io.github.anttluca.red_reign.datagen.guide_book.crimson_grimoire;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookCategoryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.book.BookLockedCategoryDisplay;
import io.github.anttluca.red_reign.datagen.guide_book.RRResearchSubProvider;
import io.github.anttluca.red_reign.datagen.guide_book.crimson_grimoire.chapter_i.IntroEntry;
import io.github.anttluca.red_reign.init.InitItems;

public class ChapterICategory extends CategoryProvider {
    public static final String ID = "chapter_i";

    public ChapterICategory(ModonomiconProviderBase parent) {
        super(parent);
    }

    @Override
    protected BookCategoryModel additionalSetup(BookCategoryModel category) {
        return super.additionalSetup(category)
                .withCondition(RRResearchSubProvider.CRYSTALLIZED_TEAR)
                .withLockedCategoryDisplay(BookLockedCategoryDisplay.HIDDEN);
    }

    @Override
    protected void generateEntries() {
        this.add(new IntroEntry(this).generate());
    }

    @Override
    protected String categoryName() { return ""; }

    @Override
    protected BookIconModel categoryIcon() {
        return BookIconModel.create(InitItems.ALTAR_OF_RED_LADY);
    }

    @Override
    public String categoryId() { return ID; }
}
