package io.github.anttluca.red_reign.datagen.guide_book.crimson_grimoire;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import io.github.anttluca.red_reign.datagen.guide_book.RRResearchSubProvider;
import io.github.anttluca.red_reign.datagen.guide_book.crimson_grimoire.chapter_iii.BloodstainedEntry;
import io.github.anttluca.red_reign.datagen.guide_book.crimson_grimoire.chapter_iii.LifeInfusedEntry;
import io.github.anttluca.red_reign.datagen.guide_book.crimson_grimoire.chapter_iii.bloodstained.*;
import io.github.anttluca.red_reign.init.InitItems;

public class ChapterIIICategory extends CategoryProvider {
    public static final String ID = "chapter_iii";

    public ChapterIIICategory(ModonomiconProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generateEntries() {
        BookEntryModel bloodstained = this.add(new BloodstainedEntry(this).generate()
                .withCondition(RRResearchSubProvider.VAMPIRE_ROSE));
        BookEntryModel lifeInfused = this.add(new LifeInfusedEntry(this).generate()
                .withCondition(RRResearchSubProvider.VAMPIRE_ROSE));
        this.layout().entry(lifeInfused).rightOf(bloodstained, 5);

        // Bloodstained
        BookEntryModel earthlyIchor = this.add(new EarthlyIchorEntry(this).generate()
                .withParent(bloodstained));
        this.layout().entry(earthlyIchor).below(bloodstained, 2);
        BookEntryModel earthlyIchorUnlocked = this.add(new EarthlyIchorUnlockedEntry(this).generate()
                .withParent(earthlyIchor)
                .withCondition(RRResearchSubProvider.EARTHLY_ICHOR)
                .hideWhileLocked(true));
        this.layout().entry(earthlyIchorUnlocked).rightOf(earthlyIchor, 2);

        BookEntryModel etherealProtection = this.add(new EtherealProtectionEntry(this).generate()
                .withParent(earthlyIchor));
        this.layout().entry(etherealProtection).below(earthlyIchor, 2);
        BookEntryModel etherealProtectionUnlocked = this.add(new EtherealProtectionUnlockedEntry(this).generate()
                .withParent(etherealProtection)
                .withCondition(RRResearchSubProvider.ETHEREAL_PROTECTION)
                .hideWhileLocked(true));
        this.layout().entry(etherealProtectionUnlocked).rightOf(etherealProtection, 2);

        BookEntryModel finalBlessing = this.add(new FinalBlessingEntry(this).generate()
                .withParent(etherealProtection));
        this.layout().entry(finalBlessing).below(etherealProtection, 2);
        BookEntryModel finalBlessingUnlocked = this.add(new FinalBlessingUnlockedEntry(this).generate()
                .withParent(finalBlessing)
                .withCondition(RRResearchSubProvider.FINAL_BLESSING)
                .hideWhileLocked(true));
        this.layout().entry(finalBlessingUnlocked).rightOf(finalBlessing, 2);

        BookEntryModel healingBulb = this.add(new HealingBulbEntry(this).generate()
                .withParent(finalBlessing));
        this.layout().entry(healingBulb).below(finalBlessing, 2);
        BookEntryModel healingBulbUnlocked = this.add(new HealingBulbUnlockedEntry(this).generate()
                .withParent(healingBulb)
                .withCondition(RRResearchSubProvider.HEALING_BULB)
                .hideWhileLocked(true));
        this.layout().entry(healingBulbUnlocked).rightOf(healingBulb, 2);
    }

    @Override
    protected String categoryName() { return ""; }

    @Override
    protected BookIconModel categoryIcon() { return BookIconModel.create(InitItems.CRAFTING_TABLE_OF_RED_QUEEN); }

    @Override
    public String categoryId() { return ID; }
}
