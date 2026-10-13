package io.github.anttluca.red_reign.datagen.guide_book;

import com.klikli_dev.modonomicon.api.datagen.research.ResearchNodeRef;
import com.klikli_dev.modonomicon.api.datagen.research.SingleResearchSubProvider;
import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.init.InitItems;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

public class RRResearchSubProvider extends SingleResearchSubProvider {
    public static final ResearchNodeRef VAMPIRE_ROSE = node(InitItems.VAMPIRE_ROSE);

    public RRResearchSubProvider() {
        super("book", RedReign.MODID);
    }

    @Override
    protected void generateResearch() { }

    static ResearchNodeRef node(DeferredItem<? extends Item> item) {
        return ResearchNodeRef.of(Identifier.fromNamespaceAndPath(RedReign.MODID,
            "research/item/%s".formatted(item.getId().getPath())));
    }
}
