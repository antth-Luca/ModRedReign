package io.github.anttluca.red_reign.datagen.guide_book.crimson_grimoire;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookCategoryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import io.github.anttluca.red_reign.datagen.guide_book.crimson_grimoire.chapter_ii.ChapterIIIntroEntry;
import net.minecraft.world.item.Items;

public class ChapterIICategory extends CategoryProvider {
    public static final String ID = "chapter_ii";

    public ChapterIICategory(ModonomiconProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generateEntries() {
        this.add(new ChapterIIIntroEntry(this).generate());
    }

    @Override
    protected String categoryName() { return ""; }

    @Override
    protected BookIconModel categoryIcon() { return BookIconModel.create(Items.NETHER_WART_BLOCK); }

    @Override
    public String categoryId() { return ID; }
}
