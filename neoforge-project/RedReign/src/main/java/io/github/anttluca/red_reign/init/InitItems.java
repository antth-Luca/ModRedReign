package io.github.anttluca.red_reign.init;

import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.items.relics.custom.VampireRoseItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

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
}
