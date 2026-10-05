package io.github.anttluca.red_reign.datagen;

import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.init.InitItems;
import io.github.anttluca.red_reign.tags.RRItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import top.theillusivec4.curios.api.CuriosTags;

import java.util.concurrent.CompletableFuture;

public class RRItemTagsProvider extends ItemTagsProvider {
    public RRItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, RedReign.MODID);
    }

    public static RRItemTagsProvider create(PackOutput out, CompletableFuture<HolderLookup.Provider> lookup) {
        return new RRItemTagsProvider(out, lookup);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(RRItemTags.BLOODSTAINED_RELICS)
                .add(InitItems.FINAL_BLESSING.get())
                .add(InitItems.ETHEREAL_PROTECTION.get())
                .add(InitItems.EARTHLY_ICHOR.get())
                .add(InitItems.HEALING_BULB.get())
                .add(InitItems.LAZULI_PROVIDENCE.get())
                .add(InitItems.DAISY_SILVER_METEOR.get())
                .add(InitItems.CORAL_GAUNTLET.get())
                .add(InitItems.ROSE_ANCHOR.get())
                .add(InitItems.VORTEX_PEARL.get())
                .add(InitItems.AMETHYST_RESONATOR.get())
                .add(InitItems.RED_IDENTITY.get())
                .add(InitItems.RED_SIGNET.get());

        // Curios
        tag(CuriosTags.HEAD)
                .add(InitItems.LAZULI_PROVIDENCE.get())
                .add(InitItems.RED_IDENTITY.get());
        tag(CuriosTags.CHARM)
                .add(InitItems.VAMPIRE_ROSE.get())
                .add(InitItems.DAISY_SILVER_METEOR.get())
                .add(InitItems.AMETHYST_RESONATOR.get());
        tag(CuriosTags.NECKLACE).add(InitItems.VORTEX_PEARL.get());
        tag(CuriosTags.BACK).add(InitItems.HEALING_BULB.get());
        tag(CuriosTags.BODY).add(InitItems.EARTHLY_ICHOR.get());
        tag(CuriosTags.HANDS)
                .add(InitItems.ETHEREAL_PROTECTION.get())
                .add(InitItems.FINAL_BLESSING.get())
                .add(InitItems.CORAL_GAUNTLET.get());
        tag(CuriosTags.RING).add(InitItems.RED_SIGNET.get());
        tag(CuriosTags.BELT).add(InitItems.ROSE_ANCHOR.get());
    }
}
