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
    public static final String RESEARCH = "book";
    // Item Acquired
    public static final String ID_CRYSTALLIZED_TEAR = id(InitItems.CRYSTALLIZED_TEAR);
    public static final ResearchNodeRef CRYSTALLIZED_TEAR = node(ID_CRYSTALLIZED_TEAR);

    public static final String ID_VAMPIRE_ROSE = id(InitItems.VAMPIRE_ROSE);
    public static final ResearchNodeRef VAMPIRE_ROSE = node(ID_VAMPIRE_ROSE);

    public static final String ID_RED_SIGNET = id(InitItems.RED_SIGNET);
    public static final ResearchNodeRef RED_SIGNET = node(ID_RED_SIGNET);

    public static final String ID_EARTHLY_ICHOR = id(InitItems.EARTHLY_ICHOR);
    public static final ResearchNodeRef EARTHLY_ICHOR = node(ID_EARTHLY_ICHOR);

    public static final String ID_ETHEREAL_PROTECTION = id(InitItems.ETHEREAL_PROTECTION);
    public static final ResearchNodeRef ETHEREAL_PROTECTION = node(ID_ETHEREAL_PROTECTION);

    public static final String ID_FINAL_BLESSING = id(InitItems.FINAL_BLESSING);
    public static final ResearchNodeRef FINAL_BLESSING = node(ID_FINAL_BLESSING);

    public static final String ID_HEALING_BULB = id(InitItems.HEALING_BULB);
    public static final ResearchNodeRef HEALING_BULB = node(ID_HEALING_BULB);

    public static final String ID_AMETHYST_RESONATOR = id(InitItems.AMETHYST_RESONATOR);
    public static final ResearchNodeRef AMETHYST_RESONATOR = node(ID_AMETHYST_RESONATOR);

    public static final String ID_CORAL_GAUNTLET = id(InitItems.CORAL_GAUNTLET);
    public static final ResearchNodeRef CORAL_GAUNTLET = node(ID_CORAL_GAUNTLET);

    public static final String ID_DAISY_SILVER_METEOR = id(InitItems.DAISY_SILVER_METEOR);
    public static final ResearchNodeRef DAISY_SILVER_METEOR = node(ID_DAISY_SILVER_METEOR);

    public static final String ID_LAZULI_PROVIDENCE = id(InitItems.LAZULI_PROVIDENCE);
    public static final ResearchNodeRef LAZULI_PROVIDENCE = node(ID_LAZULI_PROVIDENCE);

    public static final String ID_RED_IDENTITY = id(InitItems.RED_IDENTITY);
    public static final ResearchNodeRef RED_IDENTITY = node(ID_RED_IDENTITY);

    public static final String ID_ROSE_ANCHOR = id(InitItems.ROSE_ANCHOR);
    public static final ResearchNodeRef ROSE_ANCHOR = node(ID_ROSE_ANCHOR);

    public static final String ID_VORTEX_PEARL = id(InitItems.VORTEX_PEARL);
    public static final ResearchNodeRef VORTEX_PEARL = node(ID_VORTEX_PEARL);

    public RRResearchSubProvider() {
        super(RESEARCH, RedReign.MODID);
    }

    @Override
    protected void generateResearch() {
        // Item Acquired
        itemAcquired(InitItems.CRYSTALLIZED_TEAR, CRYSTALLIZED_TEAR, ID_CRYSTALLIZED_TEAR);
        itemAcquired(InitItems.VAMPIRE_ROSE, VAMPIRE_ROSE, ID_VAMPIRE_ROSE);
        itemAcquired(InitItems.RED_SIGNET, RED_SIGNET, ID_RED_SIGNET);

        itemAcquired(InitItems.EARTHLY_ICHOR, EARTHLY_ICHOR, ID_EARTHLY_ICHOR);
        itemAcquired(InitItems.ETHEREAL_PROTECTION, ETHEREAL_PROTECTION, ID_ETHEREAL_PROTECTION);
        itemAcquired(InitItems.FINAL_BLESSING, FINAL_BLESSING, ID_FINAL_BLESSING);
        itemAcquired(InitItems.HEALING_BULB, HEALING_BULB, ID_HEALING_BULB);

        itemAcquired(InitItems.AMETHYST_RESONATOR, AMETHYST_RESONATOR, ID_AMETHYST_RESONATOR);
        itemAcquired(InitItems.CORAL_GAUNTLET, CORAL_GAUNTLET, ID_CORAL_GAUNTLET);
        itemAcquired(InitItems.DAISY_SILVER_METEOR, DAISY_SILVER_METEOR, ID_DAISY_SILVER_METEOR);
        itemAcquired(InitItems.LAZULI_PROVIDENCE, LAZULI_PROVIDENCE, ID_LAZULI_PROVIDENCE);
        itemAcquired(InitItems.RED_IDENTITY, RED_IDENTITY, ID_RED_IDENTITY);
        itemAcquired(InitItems.ROSE_ANCHOR, ROSE_ANCHOR, ID_ROSE_ANCHOR);
        itemAcquired(InitItems.VORTEX_PEARL, VORTEX_PEARL, ID_VORTEX_PEARL);
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

    private void itemAcquired(DeferredItem<Item> item, ResearchNodeRef node,  String id) {
        ItemStackTemplate itemTemplate = new ItemStackTemplate(item);
        String toast = toastTitle(id);
        this.node(node, this.ingress()
                .onItemAcquired(itemTemplate)
                .declareFact(id));
        this.research().toast(node, new ResearchToastDefinition(
                toast,
                List.of(),
                toast,
                new BookIcon(itemTemplate)
        ));
    }

    public static String toastTitle(String id) {
        return id.replace('/', '.') + ".title";
    }
}
