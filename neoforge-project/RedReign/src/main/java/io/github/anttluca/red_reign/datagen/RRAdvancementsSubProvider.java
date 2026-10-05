package io.github.anttluca.red_reign.datagen;

import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.init.InitEntityTypes;
import io.github.anttluca.red_reign.init.InitItems;
import io.github.anttluca.red_reign.init.InitTriggers;
import io.github.anttluca.red_reign.tags.RRItemTags;
import io.github.anttluca.red_reign.triggers.custom.ActivateAltarOfRedLadyTrigger;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.SummonedEntityTrigger;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class RRAdvancementsSubProvider implements AdvancementSubProvider {
    public static final String ACTIVATE_RL_ALTAR = "activate_altar_of_red_lady";

    private static final String PRE = "advancement.%s.".formatted(RedReign.MODID);
    private static final String TITLE = ".title";
    private static final String DESC = ".description";
    private static final String LIFE_COST_CRAFT = "life_cost_crafting";
    private static final String BLOODSTAINED_RELIC = "get_bloodstained_relic";
    private static final String RED_SIGNET = "get_red_signet";
    private static final String LAMENT_BLADE = "get_blade_of_the_bloodstained_lament";
    private static final String CHALLENGE_RQA = "challenge_red_queens_avatar";
    private static final String RR_ADV_KEY = RedReign.MODID + "/";

    public static AdvancementProvider create(PackOutput out, CompletableFuture<HolderLookup.Provider> lookup) {
        return new AdvancementProvider(out, lookup, List.of(new RRAdvancementsSubProvider()));
    }

    @Override
    public void generate(HolderLookup.Provider provider, Consumer<AdvancementHolder> consumer) {
        HolderGetter<Item> items = provider.lookupOrThrow(Registries.ITEM);
        HolderGetter<EntityType<?>> entities = provider.lookupOrThrow(Registries.ENTITY_TYPE);

        // Root
        AdvancementHolder root = Advancement.Builder.advancement()
                .display(
                    InitItems.BOUQUET_OF_POPPIES.get(),
                    Component.translatable("itemGroup.red_reign"),
                    Component.translatable(PRE + ACTIVATE_RL_ALTAR + DESC),
                    Identifier.withDefaultNamespace("block/nether_wart_block"),
                    AdvancementType.TASK,
                    true,
                    true,
                    false
                )
                .addCriterion(
                    ACTIVATE_RL_ALTAR,
                    InitTriggers.ACTIVATE_ALTAR_OF_RED_LADY.get().createCriterion(
                        new ActivateAltarOfRedLadyTrigger.TriggerInstance(Optional.empty()))
                )
                .rewards(AdvancementRewards.Builder.loot(
                    RRLootTablesProvider.ACTIVATE_RED_LADY_ALTAR
                ))
                .save(consumer, Identifier.fromNamespaceAndPath(RedReign.MODID, RR_ADV_KEY + "root"));

        // Life Cost Crafting
        AdvancementHolder lifeCostCrafting = Advancement.Builder.advancement()
                .parent(root)
                .display(
                    InitItems.CRAFTING_TABLE_OF_RED_QUEEN.get(),
                    Component.translatable(PRE + LIFE_COST_CRAFT + TITLE),
                    Component.translatable(PRE + LIFE_COST_CRAFT + DESC),
                    null,
                    AdvancementType.TASK,
                    true,
                    false,
                    false
                )
                .addCriterion(
                    "acquire_table",
                    InventoryChangeTrigger.TriggerInstance.hasItems(InitItems.CRAFTING_TABLE_OF_RED_QUEEN.get())
                )
                .save(consumer, Identifier.fromNamespaceAndPath(RedReign.MODID, RR_ADV_KEY + LIFE_COST_CRAFT));

        // Get Bloodstained Relic
        AdvancementHolder getBloodstainedRelic = Advancement.Builder.advancement()
                .parent(lifeCostCrafting)
                .display(
                    InitItems.CHALICE_OF_THE_BLOODBLADE.get(),
                    Component.translatable(PRE + BLOODSTAINED_RELIC + TITLE),
                    Component.translatable(PRE + BLOODSTAINED_RELIC + DESC),
                    null,
                    AdvancementType.TASK,
                    true,
                    false,
                    false
                )
                .addCriterion(
                    "bloodstained_relic",
                    InventoryChangeTrigger.TriggerInstance.hasItems(
                        ItemPredicate.Builder.item().of(items, RRItemTags.BLOODSTAINED_RELICS)
                    )
                )
                .save(consumer, Identifier.fromNamespaceAndPath(RedReign.MODID, RR_ADV_KEY + BLOODSTAINED_RELIC));

        // Get Red Signet
        AdvancementHolder getRedSignet = Advancement.Builder.advancement()
                .parent(getBloodstainedRelic)
                .display(
                    InitItems.RED_SIGNET.get(),
                    Component.translatable(PRE + RED_SIGNET + TITLE),
                    Component.translatable(PRE + RED_SIGNET + DESC),
                    null,
                    AdvancementType.GOAL,
                    true,
                    false,
                    false
                )
                .addCriterion(
                    "red_signet",
                    InventoryChangeTrigger.TriggerInstance.hasItems(InitItems.RED_SIGNET.get())
                )
                .save(consumer, Identifier.fromNamespaceAndPath(RedReign.MODID, RR_ADV_KEY + RED_SIGNET));

        // Get Blade of the Bloodstained Lament
        AdvancementHolder getLamentBlade = Advancement.Builder.advancement()
                .parent(getRedSignet)
                .display(
                    InitItems.BLADE_OF_THE_BLOODSTAINED_LAMENT.get(),
                    Component.translatable(PRE + LAMENT_BLADE + TITLE),
                    Component.translatable(PRE + LAMENT_BLADE + DESC),
                    null,
                    AdvancementType.TASK,
                    true,
                    false,
                    false
                )
                .addCriterion(
                    "blade_of_the_bloodstained_lament",
                    InventoryChangeTrigger.TriggerInstance.hasItems(InitItems.BLADE_OF_THE_BLOODSTAINED_LAMENT.get())
                )
                .save(consumer, Identifier.fromNamespaceAndPath(RedReign.MODID, RR_ADV_KEY + LAMENT_BLADE));

        // Challenge Red Queens Avatar
        Advancement.Builder.advancement()
                .parent(getLamentBlade)
                .display(
                    InitItems.PINK_EMBRYO.get(),
                    Component.translatable(PRE + CHALLENGE_RQA + TITLE),
                    Component.translatable(PRE + CHALLENGE_RQA + DESC),
                    null,
                    AdvancementType.CHALLENGE,
                    true,
                    true,
                    true
                )
                .addCriterion(
                    "summoned",
                    SummonedEntityTrigger.TriggerInstance.summonedEntity(
                        EntityPredicate.Builder.entity().of(entities, InitEntityTypes.RED_QUEENS_AVATAR.get()))
                )
                .addCriterion(
                    "get_pink_embryo",
                    InventoryChangeTrigger.TriggerInstance.hasItems(InitItems.PINK_EMBRYO.get())
                )
                .save(consumer, Identifier.fromNamespaceAndPath(RedReign.MODID, RR_ADV_KEY + CHALLENGE_RQA));
    }
}
