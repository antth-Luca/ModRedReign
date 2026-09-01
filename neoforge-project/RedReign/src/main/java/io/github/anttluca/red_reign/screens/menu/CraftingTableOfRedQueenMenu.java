// CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
// must assign a slot number to each of the slots used by the GUI.
// For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
// Each time we add a Slot to the container, it automatically increases the slotIndex, which means
//  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
//  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
//  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)

package io.github.anttluca.red_reign.screens.menu;

import io.github.anttluca.red_reign.blocks.entity.CraftingTableOfRedQueenBlockEntity;
import io.github.anttluca.red_reign.init.*;
import io.github.anttluca.red_reign.recipes.custom.HPCostRecipe;
import io.github.anttluca.red_reign.utils.components.StolenLifeDataComponentUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
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

    public static final float MIN_HEALTH_PLAYER = 1.0F;

    private final Player player;
    private final ContainerLevelAccess access;
    private float hpCost = 0;

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

    public float getHPCost() {
        return hpCost;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(access, player, InitBlocks.CRAFTING_TABLE_OF_RED_QUEEN.get());
    }

    @Override
    public ItemStack quickMoveStack(Player player, int idx) {
        Slot sourceSlot = this.slots.get(idx);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;

        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack originalStack = sourceStack.copy();

        // Player inventory / hotbar
        if (idx >= VANILLA_FIRST_SLOT_INDEX
            && idx < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
                // 1. Stolen Life -> HP Resource
                if (sourceStack.has(InitDataComponentTypes.STOLEN_LIFE.get())) {

                    if (this.moveItemStackTo(
                        sourceStack,
                        TE_INVENTORY_FIRST_SLOT_INDEX + HP_RESOURCE_SLOT_ID,
                        TE_INVENTORY_FIRST_SLOT_INDEX + HP_RESOURCE_SLOT_ID + 1,
                        false)) {

                            sourceSlot.setChanged();
                            return originalStack;
                    }
                }
                // 2. Normal item -> crafting grid
                if (!this.moveItemStackTo(
                    sourceStack,
                    TE_INVENTORY_FIRST_SLOT_INDEX,
                    TE_INVENTORY_FIRST_SLOT_INDEX + INPUT_SLOTS_COUNT,
                    false)) {

                        return ItemStack.EMPTY;
                }
        } else {
            // Container -> Player
            if (!this.moveItemStackTo(
                sourceStack,
                VANILLA_FIRST_SLOT_INDEX,
                VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT,
                false)) {

                    return ItemStack.EMPTY;
            }
        }

        if (sourceStack.isEmpty()) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }

        sourceSlot.onTake(player, sourceStack);
        return originalStack;
    }

    @Override
    public void slotsChanged(Container container) {
        super.slotsChanged(container);

        if (this.player.level().isClientSide()) return;

        this.updateRecipeResult();
    }

    private void updateRecipeResult() {
        this.hpCost = 0.0F;

        if (this.player.level() instanceof ServerLevel serverLevel) {
            CraftingInput input = this.inputSlots.asCraftInput();

            Optional<RecipeHolder<CraftingRecipe>> maybeRecipe = serverLevel.recipeAccess()
                    .getRecipeFor(RecipeType.CRAFTING, input, this.player.level());

            ItemStack result = ItemStack.EMPTY;

            if (maybeRecipe.isPresent()) {
                RecipeHolder<CraftingRecipe> holder = maybeRecipe.get();
                CraftingRecipe recipe = holder.value();

                if (recipe instanceof HPCostRecipe hpCostRecipe) {
                    this.hpCost = hpCostRecipe.getHpCost();
                }

                result = recipe.assemble(input);
            }

            this.resultSlots.setItem(0, result);
            this.broadcastChanges();
        }
    }

    public boolean payHPCost() {
        if (this.player.level() instanceof ServerLevel serverLevel) {
            if (this.player.hasInfiniteMaterials()) return true;

            CraftingInput input = this.inputSlots.asCraftInput();

            Optional<RecipeHolder<CraftingRecipe>> maybeRecipe = serverLevel.recipeAccess()
                    .getRecipeFor(RecipeType.CRAFTING, input, this.player.level());
            if (maybeRecipe.isEmpty()) return false;

            CraftingRecipe recipe = maybeRecipe.get().value();
            if (!(recipe instanceof HPCostRecipe hpCostRecipe)) return true;

            float cost = hpCostRecipe.getHpCost();
            if (cost <= 0.0F) return true;

            final float playerHp = this.player.getHealth();
            float playerPayment = Math.max(playerHp - MIN_HEALTH_PLAYER, 0.0F);
            float remainigCost = Math.max(cost - playerPayment, 0.0F);

            ItemStack resourceStack = this.resourceSlots.getItem(0);
            float stolenLife = StolenLifeDataComponentUtils.getLife(resourceStack);
            if (stolenLife < remainigCost) {
                this.player.kill(serverLevel);
                return false;
            }

            if (playerPayment > 0.0F) {
                this.player.hurt(serverLevel.damageSources().magic(), Math.max(playerHp - playerPayment, MIN_HEALTH_PLAYER));
            }

            if (remainigCost > 0.0F) {
                float newStolenLife = stolenLife - remainigCost;
                StolenLifeDataComponentUtils.setLife(resourceStack, newStolenLife);
                this.resourceSlots.setChanged();
            }

            return true;
        }

        return false;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.access.execute((level, pos) -> this.clearContainer(player, this.inputSlots));
    }

    @Override
    public boolean canTakeItemForPickAll(ItemStack carried, Slot target) {
        return target.container != this.resultSlots && super.canTakeItemForPickAll(carried, target);
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
        this.addSlot(new HPConsumeResultSlot(this, player, inputSlots, resultSlots, 0, 124, 25));
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

    // Slot subclasses
    public static class HPResourceSlot extends Slot {
        public HPResourceSlot(Container container, int index, int x, int y) {
            super(container, index, x, y);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return stack.has(InitDataComponentTypes.STOLEN_LIFE.get())
                && super.mayPlace(stack);
        }

        @Override
        public int getMaxStackSize() {
            return 1;
        }
    }

    public static class HPConsumeResultSlot extends ResultSlot {
        private final CraftingTableOfRedQueenMenu menu;

        public HPConsumeResultSlot(CraftingTableOfRedQueenMenu menu, Player player, CraftingContainer craftSlots, Container container, int id, int x, int y) {
            super(player, craftSlots, container, id, x, y);
            this.menu = menu;
        }

        @Override
        public void onTake(Player player, ItemStack stack) {
            if (!menu.payHPCost()) return;

            super.onTake(player, stack);
        }
    }
}
