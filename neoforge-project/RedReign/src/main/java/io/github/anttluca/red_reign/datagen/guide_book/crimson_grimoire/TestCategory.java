package io.github.anttluca.red_reign.datagen.guide_book.crimson_grimoire;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import io.github.anttluca.red_reign.datagen.guide_book.crimson_grimoire.test.TestIntroEntry;
import net.minecraft.world.item.Items;

public class TestCategory extends CategoryProvider {
    public static final String ID = "test_category";

    public TestCategory(ModonomiconProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generateEntries() {
        this.add(new TestIntroEntry(this).generate());
    }

    @Override
    protected String categoryName() { return ""; }

    @Override
    protected BookIconModel categoryIcon() {
        return BookIconModel.create(Items.BEDROCK);
    }

    @Override
    public String categoryId() { return ID; }
}
