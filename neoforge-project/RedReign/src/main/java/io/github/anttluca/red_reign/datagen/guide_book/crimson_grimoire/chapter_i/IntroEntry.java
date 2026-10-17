package io.github.anttluca.red_reign.datagen.guide_book.crimson_grimoire.chapter_i;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import io.github.anttluca.red_reign.datagen.guide_book.RRResearchSubProvider;
import net.minecraft.world.item.Items;

public class IntroEntry extends EntryProvider {
    public static final String ID = "intro";
    public static final String PG_MAIN = "main";

    public IntroEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected BookEntryModel additionalSetup(BookEntryModel entry) {
        return super.additionalSetup(entry)
                .withCondition(RRResearchSubProvider.CRYSTALLIZED_TEAR)
                .hideWhileLocked(true);
    }

    @Override
    protected void generatePages() {
        this.page(PG_MAIN, () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
    }

    @Override
    protected String entryName() { return ""; }

    @Override
    protected String entryDescription() { return ""; }

    @Override
    protected GuiSprite entryBackground() {
        return EntryBackground.DEFAULT;
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(Items.CHISELED_QUARTZ_BLOCK);
    }

    @Override
    protected String entryId() { return ID; }
}
