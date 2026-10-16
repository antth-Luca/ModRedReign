package io.github.anttluca.red_reign.datagen.guide_book;

import com.klikli_dev.modonomicon.api.datagen.research.ResearchNodeRef;
import com.klikli_dev.modonomicon.api.datagen.research.SingleResearchSubProvider;
import com.klikli_dev.modonomicon.book.BookIcon;
import com.klikli_dev.modonomicon.research.data.ResearchToastDefinition;
import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.init.InitItems;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.List;

public class RRResearchSubProvider extends SingleResearchSubProvider {
    private static final String RESEARCH = "book";
    // Item Acquired
    public static final String ID_CRYSTALLIZED_TEAR = id(InitItems.CRYSTALLIZED_TEAR);
    public static final ResearchNodeRef CRYSTALLIZED_TEAR = node(ID_CRYSTALLIZED_TEAR);
    public static final String ID_VAMPIRE_ROSE = id(InitItems.VAMPIRE_ROSE);
    public static final ResearchNodeRef VAMPIRE_ROSE = node(ID_VAMPIRE_ROSE);
    public static final String ID_RED_SIGNET = id(InitItems.RED_SIGNET);
    public static final ResearchNodeRef RED_SIGNET = node(ID_RED_SIGNET);

    public RRResearchSubProvider() {
        super(RESEARCH, RedReign.MODID);
    }

    @Override
    protected void generateResearch() {
        // Item Acquired
        ItemStackTemplate crystallizedTear = new ItemStackTemplate(InitItems.CRYSTALLIZED_TEAR);
        String crystallizedTearToast = toastTitle(ID_CRYSTALLIZED_TEAR);
        this.node(CRYSTALLIZED_TEAR, this.ingress()
                .onItemAcquired(crystallizedTear)
                .declareFact(ID_CRYSTALLIZED_TEAR));
        this.research().toast(CRYSTALLIZED_TEAR, new ResearchToastDefinition(
            crystallizedTearToast,
            List.of(),
            crystallizedTearToast,
            new BookIcon(crystallizedTear)
        ));

        ItemStackTemplate vampireRose = new ItemStackTemplate(InitItems.VAMPIRE_ROSE);
        String vampireRoseToast = toastTitle(ID_VAMPIRE_ROSE);
        this.node(VAMPIRE_ROSE, this.ingress()
                .onItemAcquired(vampireRose)
                .declareFact(ID_VAMPIRE_ROSE));
        this.research().toast(VAMPIRE_ROSE, new ResearchToastDefinition(
                vampireRoseToast,
                List.of(),
                vampireRoseToast,
                new BookIcon(vampireRose)
        ));

        ItemStackTemplate redSignet = new ItemStackTemplate(InitItems.RED_SIGNET);
        String redSignetToast = toastTitle(ID_RED_SIGNET);
        this.node(RED_SIGNET, this.ingress()
                .onItemAcquired(redSignet)
                .declareFact(ID_RED_SIGNET));
        this.research().toast(RED_SIGNET, new ResearchToastDefinition(
                redSignetToast,
                List.of(),
                redSignetToast,
                new BookIcon(redSignet)
        ));
    }

    private static String id(DeferredItem<? extends Item> item) {
        return id(item.getId().getPath());
    }

    private static String id(String path) {
        return "%s/%s".formatted(RESEARCH, path);
    }

    private static ResearchNodeRef node(String id) {
        return ResearchNodeRef.of(Identifier.fromNamespaceAndPath(
            RedReign.MODID, id));
    }

    public static String toastTitle(String id) {
        return id + ".title";
    }
}
