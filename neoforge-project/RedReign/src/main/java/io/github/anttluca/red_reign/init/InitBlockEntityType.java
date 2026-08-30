package io.github.anttluca.red_reign.init;

import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.blocks.entity.CraftingTableOfRedQueenBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class InitBlockEntityType {
    public static final DeferredRegister<BlockEntityType<?>> BE_TYPES = DeferredRegister.create(
            BuiltInRegistries.BLOCK_ENTITY_TYPE, RedReign.MODID);

    // Types
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> CRAFTING_TABLE_OF_RED_QUEEN_BE = BE_TYPES.register(
        "crafting_table_of_red_queen_be", () -> new BlockEntityType<>(
                CraftingTableOfRedQueenBlockEntity::new,
                InitBlocks.CRAFTING_TABLE_OF_RED_QUEEN.get()));
}
