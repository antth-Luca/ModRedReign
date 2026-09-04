package io.github.anttluca.red_reign.blocks.entity;

import io.github.anttluca.red_reign.init.InitBlockEntityType;
import io.github.anttluca.red_reign.screens.menu.CraftingTableOfRedQueenMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

public class CraftingTableOfRedQueenBlockEntity extends BlockEntity implements MenuProvider {
    public static final Component DEFAULT_NAME = Component.translatable("block.red_reign.crafting_table_of_red_queen");

    public CraftingTableOfRedQueenBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(
            InitBlockEntityType.CRAFTING_TABLE_OF_RED_QUEEN_BE.get(),
            worldPosition, blockState
        );
    }

    @Override
    public Component getDisplayName() {
        return DEFAULT_NAME;
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new CraftingTableOfRedQueenMenu(i, inventory, this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
