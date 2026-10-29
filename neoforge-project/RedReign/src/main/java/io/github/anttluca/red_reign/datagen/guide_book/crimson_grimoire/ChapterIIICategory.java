package io.github.anttluca.red_reign.datagen.guide_book.crimson_grimoire;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookEntryModel;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import io.github.anttluca.red_reign.datagen.guide_book.RRResearchSubProvider;
import io.github.anttluca.red_reign.datagen.guide_book.crimson_grimoire.chapter_iii.BloodstainedEntry;
import io.github.anttluca.red_reign.datagen.guide_book.crimson_grimoire.chapter_iii.LifeInfusedEntry;
import io.github.anttluca.red_reign.datagen.guide_book.crimson_grimoire.chapter_iii.bloodstained.*;
import io.github.anttluca.red_reign.datagen.guide_book.crimson_grimoire.chapter_iii.life_infused.*;
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
        this.layout().entry(bloodstained).at(-3, -3);
        BookEntryModel lifeInfused = this.add(new LifeInfusedEntry(this).generate()
                .withCondition(RRResearchSubProvider.VAMPIRE_ROSE));
        this.layout().entry(lifeInfused).rightOf(bloodstained, 5);

        // Bloodstained
        BookEntryModel earthlyIchor = this.add(new EarthlyIchorEntry(this).generate()
                .withParent(bloodstained)
                .withCondition(RRResearchSubProvider.VAMPIRE_ROSE)
                .hideWhileLocked(true));
        this.layout().entry(earthlyIchor).below(bloodstained, 2);
        BookEntryModel earthlyIchorUnlocked = this.add(new EarthlyIchorUnlockedEntry(this).generate()
                .withParent(earthlyIchor)
                .withCondition(RRResearchSubProvider.EARTHLY_ICHOR)
                .hideWhileLocked(true));
        this.layout().entry(earthlyIchorUnlocked).rightOf(earthlyIchor, 2);

        BookEntryModel etherealProtection = this.add(new EtherealProtectionEntry(this).generate()
                .withParent(earthlyIchor)
                .withCondition(RRResearchSubProvider.VAMPIRE_ROSE)
                .hideWhileLocked(true));
        this.layout().entry(etherealProtection).below(earthlyIchor, 2);
        BookEntryModel etherealProtectionUnlocked = this.add(new EtherealProtectionUnlockedEntry(this).generate()
                .withParent(etherealProtection)
                .withCondition(RRResearchSubProvider.ETHEREAL_PROTECTION)
                .hideWhileLocked(true));
        this.layout().entry(etherealProtectionUnlocked).rightOf(etherealProtection, 2);

        BookEntryModel finalBlessing = this.add(new FinalBlessingEntry(this).generate()
                .withParent(etherealProtection)
                .withCondition(RRResearchSubProvider.VAMPIRE_ROSE)
                .hideWhileLocked(true));
        this.layout().entry(finalBlessing).below(etherealProtection, 2);
        BookEntryModel finalBlessingUnlocked = this.add(new FinalBlessingUnlockedEntry(this).generate()
                .withParent(finalBlessing)
                .withCondition(RRResearchSubProvider.FINAL_BLESSING)
                .hideWhileLocked(true));
        this.layout().entry(finalBlessingUnlocked).rightOf(finalBlessing, 2);

        BookEntryModel healingBulb = this.add(new HealingBulbEntry(this).generate()
                .withParent(finalBlessing)
                .withCondition(RRResearchSubProvider.VAMPIRE_ROSE)
                .hideWhileLocked(true));
        this.layout().entry(healingBulb).below(finalBlessing, 2);
        BookEntryModel healingBulbUnlocked = this.add(new HealingBulbUnlockedEntry(this).generate()
                .withParent(healingBulb)
                .withCondition(RRResearchSubProvider.HEALING_BULB)
                .hideWhileLocked(true));
        this.layout().entry(healingBulbUnlocked).rightOf(healingBulb, 2);

        // Life Infused
        BookEntryModel lazuliProvidence = this.add(new LazuliProvidenceEntry(this).generate()
                .withParent(lifeInfused)
                .withCondition(RRResearchSubProvider.VAMPIRE_ROSE)
                .hideWhileLocked(true));
        this.layout().entry(lazuliProvidence).below(lifeInfused, 2);
        BookEntryModel lazuliProvidenceUnlocked = this.add(new LazuliProvidenceUnlockedEntry(this).generate()
                .withParent(lazuliProvidence)
                .withCondition(RRResearchSubProvider.LAZULI_PROVIDENCE)
                .hideWhileLocked(true));
        this.layout().entry(lazuliProvidenceUnlocked).rightOf(lazuliProvidence, 2);

        BookEntryModel daisySilverMeteor = this.add(new DaisySilverMeteorEntry(this).generate()
                .withParent(lazuliProvidence)
                .withCondition(RRResearchSubProvider.VAMPIRE_ROSE)
                .hideWhileLocked(true));
        this.layout().entry(daisySilverMeteor).below(lazuliProvidence, 2);
        BookEntryModel daisySilverMeteorUnlocked = this.add(new DaisySilverMeteorUnlockedEntry(this).generate()
                .withParent(daisySilverMeteor)
                .withCondition(RRResearchSubProvider.DAISY_SILVER_METEOR)
                .hideWhileLocked(true));
        this.layout().entry(daisySilverMeteorUnlocked).rightOf(daisySilverMeteor, 2);

        BookEntryModel coralGauntlet = this.add(new CoralGauntletEntry(this).generate()
                .withParent(daisySilverMeteor)
                .withCondition(RRResearchSubProvider.VAMPIRE_ROSE)
                .hideWhileLocked(true));
        this.layout().entry(coralGauntlet).below(daisySilverMeteor, 2);
        BookEntryModel coralGauntletUnlocked = this.add(new CoralGauntletUnlockedEntry(this).generate()
                .withParent(coralGauntlet)
                .withCondition(RRResearchSubProvider.CORAL_GAUNTLET)
                .hideWhileLocked(true));
        this.layout().entry(coralGauntletUnlocked).rightOf(coralGauntlet, 2);

        BookEntryModel roseAnchor = this.add(new RoseAnchorEntry(this).generate()
                .withParent(coralGauntlet)
                .withCondition(RRResearchSubProvider.VAMPIRE_ROSE)
                .hideWhileLocked(true));
        this.layout().entry(roseAnchor).below(coralGauntlet, 2);
        BookEntryModel roseAnchorUnlocked = this.add(new RoseAnchorUnlockedEntry(this).generate()
                .withParent(roseAnchor)
                .withCondition(RRResearchSubProvider.ROSE_ANCHOR)
                .hideWhileLocked(true));
        this.layout().entry(roseAnchorUnlocked).rightOf(roseAnchor, 2);

        BookEntryModel vortexPearl = this.add(new VortexPearlEntry(this).generate()
                .withParent(roseAnchor)
                .withCondition(RRResearchSubProvider.VAMPIRE_ROSE)
                .hideWhileLocked(true));
        this.layout().entry(vortexPearl).below(roseAnchor, 2);
        BookEntryModel vortexPearlUnlocked = this.add(new VortexPearlUnlockedEntry(this).generate()
                .withParent(vortexPearl)
                .withCondition(RRResearchSubProvider.VORTEX_PEARL)
                .hideWhileLocked(true));
        this.layout().entry(vortexPearlUnlocked).rightOf(vortexPearl, 2);

        BookEntryModel amethystResonator = this.add(new AmethystResonatorEntry(this).generate()
                .withParent(vortexPearl)
                .withCondition(RRResearchSubProvider.VAMPIRE_ROSE)
                .hideWhileLocked(true));
        this.layout().entry(amethystResonator).below(vortexPearl, 2);
        BookEntryModel amethystResonatorUnlocked = this.add(new AmethystResonatorUnlockedEntry(this).generate()
                .withParent(amethystResonator)
                .withCondition(RRResearchSubProvider.AMETHYST_RESONATOR)
                .hideWhileLocked(true));
        this.layout().entry(amethystResonatorUnlocked).rightOf(amethystResonator, 2);

        BookEntryModel redIdentity = this.add(new RedIdentityEntry(this).generate()
                .withParent(amethystResonator)
                .withCondition(RRResearchSubProvider.VAMPIRE_ROSE)
                .hideWhileLocked(true));
        this.layout().entry(redIdentity).below(amethystResonator, 2);
        BookEntryModel redIdentityUnlocked = this.add(new RedIdentityUnlockedEntry(this).generate()
                .withParent(redIdentity)
                .withCondition(RRResearchSubProvider.RED_IDENTITY)
                .hideWhileLocked(true));
        this.layout().entry(redIdentityUnlocked).rightOf(redIdentity, 2);

        BookEntryModel redSignet = this.add(new RedSignetEntry(this).generate()
                .withParent(redIdentity)
                .withCondition(RRResearchSubProvider.VAMPIRE_ROSE)
                .hideWhileLocked(true));
        this.layout().entry(redSignet).below(redIdentity, 2);
        BookEntryModel redSignetUnlocked = this.add(new RedSignetUnlockedEntry(this).generate()
                .withParent(redSignet)
                .withCondition(RRResearchSubProvider.RED_SIGNET)
                .hideWhileLocked(true));
        this.layout().entry(redSignetUnlocked).rightOf(redSignet, 2);
    }

    @Override
    protected String categoryName() { return ""; }

    @Override
    protected BookIconModel categoryIcon() { return BookIconModel.create(InitItems.CRAFTING_TABLE_OF_RED_QUEEN); }

    @Override
    public String categoryId() { return ID; }
}
