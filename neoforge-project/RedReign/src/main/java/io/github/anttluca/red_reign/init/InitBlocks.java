package io.github.anttluca.red_reign.init;

import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.blocks.AltarOfRedLadyBlock;
import io.github.anttluca.red_reign.blocks.BouquetOfPoppiesBlock;
import io.github.anttluca.red_reign.blocks.CraftingTableOfRedQueenBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.lighting.BlockLightEngine;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class InitBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(RedReign.MODID);

    // Blocks
    public static final DeferredBlock<Block> BOUQUET_OF_POPPIES = BLOCKS.registerBlock(
        "bouquet_of_poppies", BouquetOfPoppiesBlock::new);

    public static final DeferredBlock<Block> ALTAR_OF_RED_LADY = BLOCKS.registerBlock(
        "altar_of_red_lady", AltarOfRedLadyBlock::new);

    public static final DeferredBlock<Block> CRAFTING_TABLE_OF_RED_QUEEN = BLOCKS.registerBlock(
        "crafting_table_of_red_queen", CraftingTableOfRedQueenBlock::new);

    // Fluids
    public static final DeferredBlock<LiquidBlock> MELTED_BEESWAX = BLOCKS.registerBlock(
        "melted_beeswax",
        props -> new LiquidBlock(
                InitFluids.MELTED_BEESWAX.get(),
                props
        ),
        () -> BlockBehaviour.Properties.ofFullCopy(Blocks.WATER));
}
