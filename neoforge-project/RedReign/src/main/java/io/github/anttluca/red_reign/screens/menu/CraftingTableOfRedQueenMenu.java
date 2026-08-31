// CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
// must assign a slot number to each of the slots used by the GUI.
// For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
// Each time we add a Slot to the container, it automatically increases the slotIndex, which means
//  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
//  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
//  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)

package io.github.anttluca.red_reign.screens.menu;

import io.github.anttluca.red_reign.blocks.entity.CraftingTableOfRedQueenBlockEntity;
import io.github.anttluca.red_reign.init.InitBlocks;
import io.github.anttluca.red_reign.init.InitItems;
import io.github.anttluca.red_reign.init.InitMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.Optional;

public class CraftingTableOfRedQueenMenu extends AbstractContainerMenu {
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
    private static final int TE_INVENTORY_SLOT_COUNT = 11;

    private static final int CRAFT_WIDTH = 3;
    private static final int CRAFT_HEIGHT = 3;

    public static final int INPUT_SLOTS_START = 0;
    public static final int INPUT_SLOTS_COUNT = CRAFT_WIDTH * CRAFT_HEIGHT;

    public static final int HP_RESOURCE_SLOT_ID = 9;
    public static final int RESULT_SLOT_ID = 10;

    private final Player player;
    private final ContainerLevelAccess access;
    private final int hpCost = 0;

    protected final CraftingContainer inputSlots;
    protected final SimpleContainer resourceSlots;
    protected final ResultContainer resultSlots = new ResultContainer();

    public CraftingTableOfRedQueenMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv,
             (CraftingTableOfRedQueenBlockEntity) inv.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public CraftingTableOfRedQueenMenu(int pContainerId, Inventory inv, CraftingTableOfRedQueenBlockEntity blockEntity) {
        super(InitMenuTypes.CRAFTING_TABLE_OF_RED_QUEEN_MENU.get(), pContainerId);
        this.player = inv.player;
        this.access = ContainerLevelAccess.create(
                this.player.level(),
                blockEntity.getBlockPos()
        );
        this.inputSlots = new TransientCraftingContainer(this, CRAFT_WIDTH, CRAFT_HEIGHT);
        this.resourceSlots = new SimpleContainer(1);

        addGridInputSlots();
        addHPResourceSlot();
        addResultSlot(this.player);
        addPlayerInventory(inv);
        addPlayerHotbar(inv);
    }

    public int getHPCost() {
        return hpCost;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(access, player, InitBlocks.CRAFTING_TABLE_OF_RED_QUEEN.get());
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (pIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + pIndex);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    public void removed(Player player) {
        super.removed(player);
        this.access.execute((level, pos) -> this.clearContainer(player, this.inputSlots));
    }

    public boolean canTakeItemForPickAll(ItemStack carried, Slot target) {
        return target.container != this.resultSlots && super.canTakeItemForPickAll(carried, target);
    }

    public void slotsChanged(Container container) {
        this.access.execute((level, pos) -> {
            if (level instanceof ServerLevel serverLevel) {
                CraftingInput input = inputSlots.asCraftInput();
                ServerPlayer serverPlayer = (ServerPlayer) player;
                ItemStack result = ItemStack.EMPTY;
                RecipeHolder<CraftingRecipe> recipeHint = (RecipeHolder) null;
                Optional<RecipeHolder<CraftingRecipe>> maybeRecipe = level.getServer().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, input, level, recipeHint);
                if (maybeRecipe.isPresent()) {
                    RecipeHolder<CraftingRecipe> recipeHolder = (RecipeHolder) maybeRecipe.get();
                    CraftingRecipe craftingRecipe = (CraftingRecipe) recipeHolder.value();
                    if (resultSlots.setRecipeUsed(serverPlayer, recipeHolder)) {
                        ItemStack recipeResult = craftingRecipe.assemble(input);
                        if (recipeResult.isItemEnabled(level.enabledFeatures())) {
                            result = recipeResult;
                        }
                    }
                }

                resultSlots.setItem(0, result);
                this.setRemoteSlot(0, result);
                serverPlayer.connection.send(new ClientboundContainerSetSlotPacket(this.containerId, this.incrementStateId(), 0, result));
            }

        });
    }

    // INVENTORY CONSTRUCTORS
    private void addGridInputSlots() {
        for (int i = 0; i < CRAFT_WIDTH; i++) {
            for (int l = 0; l < CRAFT_HEIGHT; l++) {
                this.addSlot(new Slot(inputSlots, l + i * CRAFT_WIDTH, 30 + l * 18, 7 + i * 18));
            }
        }
    }

    private void addHPResourceSlot() {
        this.addSlot(new HPResourceSlot(resourceSlots, 0, 8, 25));
    }

    private void addResultSlot(Player player) {
        this.addSlot(new ResultSlot(player, inputSlots, resultSlots, 0, 124, 25));
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 94 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 152));
        }
    }

    public static class HPResourceSlot extends Slot {
        public HPResourceSlot(Container container, int index, int x, int y) {
            super(container, index, x, y);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return stack.is(InitItems.CHALICE_OF_THE_BLOODBLADE.get())
                && super.mayPlace(stack);
        }
    }
}
