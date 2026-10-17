package io.github.anttluca.red_reign.datagen.guide_book.crimson_grimoire.chapter_ii;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.EntryBackground;
import com.klikli_dev.modonomicon.api.datagen.EntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.klikli_dev.modonomicon.client.gui.book.theme.GuiSprite;
import net.minecraft.world.item.Items;

public class WorldEffectsEntry extends EntryProvider {
    public static final String ID = "effects";
    public static final String PG_EFFECTS = "effects";
    public static final String PG_CRAFT = "craft";

    public WorldEffectsEntry(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        this.page(PG_EFFECTS, () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.page(PG_CRAFT, () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
    }

    @Override
    protected String entryName() { return ""; }

    @Override
    protected GuiSprite entryBackground() { return EntryBackground.DEFAULT; }

    @Override
    protected BookIconModel entryIcon() { return BookIconModel.create(Items.REDSTONE_BLOCK); }

    @Override
    protected String entryId() { return ID; }
}
