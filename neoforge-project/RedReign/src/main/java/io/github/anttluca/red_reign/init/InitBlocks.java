package io.github.anttluca.red_reign.init;

import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.blocks.custom.BouquetOfPoppiesBlock;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class InitBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(RedReign.MODID);

    // Blocks
    public static final DeferredBlock<Block> BOUQUET_OF_POPPIES = BLOCKS.registerBlock(
            "bouquet_of_poppies", BouquetOfPoppiesBlock::new);
}
