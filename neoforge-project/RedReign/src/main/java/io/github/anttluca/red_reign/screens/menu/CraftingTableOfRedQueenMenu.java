package io.github.anttluca.red_reign.screens.menu;

import io.github.anttluca.red_reign.blocks.entity.CraftingTableOfRedQueenBlockEntity;
import io.github.anttluca.red_reign.init.InitBlocks;
import io.github.anttluca.red_reign.init.InitMenuTypes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class CraftingTableOfRedQueenMenu extends AbstractContainerMenu {
    public final CraftingTableOfRedQueenBlockEntity blockEntity;

    private final Level level;

    public CraftingTableOfRedQueenMenu(int pContainerId, Inventory inv, BlockEntity entity) {
        super(InitMenuTypes.CRAFTING_TABLE_OF_RED_QUEEN_MENU.get(), pContainerId);
        this.blockEntity = ((CraftingTableOfRedQueenBlockEntity) entity);
        this.level = inv.player.level();

        addPlayerInventory(inv);
        addPlayerHotbar(inv);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                pPlayer, InitBlocks.CRAFTING_TABLE_OF_RED_QUEEN.get());
    }

    // MAIN
    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 88 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 146));
        }
    }
}
