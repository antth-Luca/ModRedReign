package io.github.anttluca.red_reign.datagen.guide_book;

import com.klikli_dev.modonomicon.api.datagen.SingleBookSubProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookModel;
import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.datagen.guide_book.crimson_grimoire.ChapterICategory;
import io.github.anttluca.red_reign.datagen.guide_book.crimson_grimoire.ChapterIICategory;
import io.github.anttluca.red_reign.datagen.guide_book.crimson_grimoire.ChapterIIICategory;
import io.github.anttluca.red_reign.init.InitItems;
import net.minecraft.resources.Identifier;

public class RRModonomiconSubProvider extends SingleBookSubProvider {
    public RRModonomiconSubProvider() {
        super("book", RedReign.MODID);
    }

    @Override
    protected void registerDefaultMacros() { }

    @Override
    protected void generateCategories() {
        this.add(new ChapterICategory(this).generate());
        this.add(new ChapterIICategory(this).generate());
        this.add(new ChapterIIICategory(this).generate());
    }

    @Override
    protected BookModel additionalSetup(BookModel book) {
        return super.additionalSetup(book)
                .withGenerateBookItem(false)
                .withCustomBookItem(InitItems.CRIMSON_GRIMOIRE.getId())
                .withFont(Identifier.withDefaultNamespace("default"))
                .withDefaultAutoScale(false)
                .withDefaultAllowPageSplit(true);
    }

    @Override
    protected String bookName() { return ""; }

    @Override
    protected String bookTooltip() { return ""; }
}
