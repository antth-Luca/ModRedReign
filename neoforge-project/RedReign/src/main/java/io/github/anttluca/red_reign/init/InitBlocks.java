package io.github.anttluca.red_reign.init;

import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.blocks.custom.AltarOfRedLadyBlock;
import io.github.anttluca.red_reign.blocks.custom.BouquetOfPoppiesBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class InitBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(RedReign.MODID);

    // Blocks
    public static final DeferredBlock<Block> BOUQUET_OF_POPPIES = BLOCKS.registerBlock(
        "bouquet_of_poppies", BouquetOfPoppiesBlock::new);

    public static final DeferredBlock<Block> ALTAR_OF_RED_LADY = BLOCKS.registerBlock(
        "altar_of_red_lady", AltarOfRedLadyBlock::new);

    // Fluids
    public static final DeferredBlock<LiquidBlock> MELTED_BEESWAX = BLOCKS.register(
        "melted_beeswax",
        () -> new LiquidBlock(
                InitFluids.MELTED_BEESWAX.get(),
                BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)
        ));
}
