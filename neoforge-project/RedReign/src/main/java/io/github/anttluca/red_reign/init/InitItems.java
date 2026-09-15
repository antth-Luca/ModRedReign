package io.github.anttluca.red_reign.init;

import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.handlers.RRItemTooltipsHandler;
import io.github.anttluca.red_reign.items.AllayCageItem;
import io.github.anttluca.red_reign.items.ChaliceOfTheBloodbladeItem;
import io.github.anttluca.red_reign.items.TotemOfTheRedQueenItem;
import io.github.anttluca.red_reign.items.relics.custom.*;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.TooltipDisplay;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Consumer;

public class InitItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(RedReign.MODID);

    // Items
    public static final DeferredItem<Item> HONEYCOMB_BUCKET = ITEMS.registerSimpleItem(
        "honeycomb_bucket");

    public static final DeferredItem<BucketItem> MELTED_BEESWAX_BUCKET = ITEMS.registerItem(
        "melted_beeswax_bucket",
        props -> new BucketItem(
            InitFluids.MELTED_BEESWAX.get(),
            props.craftRemainder(Items.BUCKET).stacksTo(1)
        ));

    public static final DeferredItem<Item> REDSTONE_CRYSTAL = ITEMS.registerSimpleItem(
        "redstone_crystal");

    public static final DeferredItem<Item> INTRINSIC_MECHANISM = ITEMS.registerItem(
        "intrinsic_mechanism", (props) -> new Item(props) {
            @Override
            public void appendHoverText(ItemStack stack, TooltipContext ctx, TooltipDisplay display, Consumer<Component> builder, TooltipFlag flag) {
                super.appendHoverText(stack, ctx, display, builder, flag);
                RRItemTooltipsHandler.addSpace(builder);
                RRItemTooltipsHandler.addLore(INTRINSIC_MECHANISM.getId().getPath(), builder);
            }
        });

    public static final DeferredItem<Item> ALLAY_CAGE = ITEMS.registerItem(
        "allay_cage", AllayCageItem::new);

    public static final DeferredItem<Item> CHALICE_OF_THE_BLOODBLADE = ITEMS.registerItem(
        "chalice_of_the_bloodblade", ChaliceOfTheBloodbladeItem::new);

    public static final DeferredItem<Item> TOTEM_OF_THE_RED_QUEEN = ITEMS.registerItem(
        "totem_of_the_red_queen", TotemOfTheRedQueenItem::new);

    // Block Items
    public static final DeferredItem<BlockItem> BOUQUET_OF_POPPIES = ITEMS.registerSimpleBlockItem(
        InitBlocks.BOUQUET_OF_POPPIES);

    public static final DeferredItem<BlockItem> ALTAR_OF_RED_LADY = ITEMS.registerSimpleBlockItem(
        InitBlocks.ALTAR_OF_RED_LADY);

    public static final DeferredItem<BlockItem> CRAFTING_TABLE_OF_RED_QUEEN = ITEMS.registerSimpleBlockItem(
        InitBlocks.CRAFTING_TABLE_OF_RED_QUEEN);

    // Relics
    public static final DeferredItem<Item> VAMPIRE_ROSE = ITEMS.registerItem(
        "vampire_rose", VampireRoseItem::new);

    public static final DeferredItem<Item> FINAL_BLESSING = ITEMS.registerItem(
        "final_blessing", FinalBlessingItem::new);

    public static final DeferredItem<Item> ETHEREAL_PROTECTION = ITEMS.registerItem(
        "ethereal_protection", EtherealProtectionItem::new);

    public static final DeferredItem<Item> EARTHLY_ICHOR = ITEMS.registerItem(
        "earthly_ichor", EarthlyIchorItem::new);

    public static final DeferredItem<Item> HEALING_BULB = ITEMS.registerItem(
        "healing_bulb", HealingBulbItem::new);
}
