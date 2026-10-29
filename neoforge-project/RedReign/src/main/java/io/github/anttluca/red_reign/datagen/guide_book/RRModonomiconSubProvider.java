package io.github.anttluca.red_reign.datagen.guide_book;

import com.klikli_dev.modonomicon.api.datagen.SingleBookSubProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookModel;
import com.klikli_dev.modonomicon.book.BookCategoryBackgroundParallaxLayer;
import com.klikli_dev.modonomicon.book.BookLockedCategoryDisplay;
import com.klikli_dev.modonomicon.client.gui.book.theme.defaults.DefaultBookTheme;
import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.datagen.guide_book.crimson_grimoire.ChapterICategory;
import io.github.anttluca.red_reign.datagen.guide_book.crimson_grimoire.ChapterIICategory;
import io.github.anttluca.red_reign.datagen.guide_book.crimson_grimoire.ChapterIIICategory;
import io.github.anttluca.red_reign.datagen.guide_book.crimson_grimoire.ChapterIVCategory;
import io.github.anttluca.red_reign.init.InitItems;
import net.minecraft.resources.Identifier;

public class RRModonomiconSubProvider extends SingleBookSubProvider {
    private static final BookCategoryBackgroundParallaxLayer RQ_BASE_LAYER = new BookCategoryBackgroundParallaxLayer(
        Identifier.fromNamespaceAndPath(RedReign.MODID, "textures/gui/parallax/rq_base.png"),
        0.7F, -1.0F
    );
    private static final BookCategoryBackgroundParallaxLayer RQ_1_LAYER = new BookCategoryBackgroundParallaxLayer(
        Identifier.fromNamespaceAndPath(RedReign.MODID, "textures/gui/parallax/rq_1.png"),
        1.0F, -1.0F
    );
    private static final BookCategoryBackgroundParallaxLayer RQ_2_LAYER = new BookCategoryBackgroundParallaxLayer(
        Identifier.fromNamespaceAndPath(RedReign.MODID, "textures/gui/parallax/rq_2.png"),
        1.4F, -1.0F
    );

    public RRModonomiconSubProvider() {
        super("book", RedReign.MODID);
    }

    @Override
    protected void registerDefaultMacros() { }

    @Override
    protected void generateCategories() {
        this.add(new ChapterICategory(this).generate()
                .withBackgroundParallaxLayer(RQ_BASE_LAYER)
                .withBackgroundParallaxLayer(RQ_1_LAYER)
                .withBackgroundParallaxLayer(RQ_2_LAYER));
        this.add(new ChapterIICategory(this).generate()
                .withBackgroundParallaxLayer(RQ_BASE_LAYER)
                .withBackgroundParallaxLayer(RQ_1_LAYER)
                .withBackgroundParallaxLayer(RQ_2_LAYER));
        this.add(new ChapterIIICategory(this).generate()
                .withBackgroundParallaxLayer(RQ_BASE_LAYER)
                .withBackgroundParallaxLayer(RQ_1_LAYER)
                .withBackgroundParallaxLayer(RQ_2_LAYER));
        this.add(new ChapterIVCategory(this).generate()
                .withBackgroundParallaxLayer(RQ_BASE_LAYER)
                .withBackgroundParallaxLayer(RQ_1_LAYER)
                .withBackgroundParallaxLayer(RQ_2_LAYER));
    }

    @Override
    protected BookModel additionalSetup(BookModel book) {
        return super.additionalSetup(book)
                .withGenerateBookItem(false)
                .withCustomBookItem(InitItems.CRIMSON_GRIMOIRE.getId())
                .withTheme(t -> t
                        .withId(this.modLoc("crimson_grimoire"))
                        .withType(DefaultBookTheme.ID))
                .withFont(Identifier.withDefaultNamespace("default"))
                .withDefaultAutoScale(false)
                .withDefaultAllowPageSplit(true);
    }

    @Override
    protected String bookName() { return ""; }

    @Override
    protected String bookTooltip() { return ""; }
}
