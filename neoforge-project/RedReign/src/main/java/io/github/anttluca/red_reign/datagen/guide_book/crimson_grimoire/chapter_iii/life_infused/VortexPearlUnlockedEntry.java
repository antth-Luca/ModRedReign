package io.github.anttluca.red_reign.datagen.guide_book.crimson_grimoire.chapter_iii.life_infused;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import io.github.anttluca.red_reign.init.InitItems;

public class VortexPearlUnlockedEntry extends EntryProvider {
    public static final String ID = "vortex_pearl_unlocked";
    public static final String PG_MAIN = "main";

    public VortexPearlUnlockedEntry(CategoryProviderBase parent) {
        super(parent);
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
    protected GuiSprite entryBackground() { return EntryBackground.DEFAULT; }

    @Override
    protected BookIconModel entryIcon() { return BookIconModel.create(InitItems.VORTEX_PEARL); }

    @Override
    protected String entryId() { return ID; }
}
