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
import net.minecraft.world.item.crafting.*;

import java.util.List;
import java.util.Optional;

public class CraftingTableOfRedQueenMenu extends AbstractContainerMenu {
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;

    public static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;

    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    public  static final int TE_INVENTORY_SLOT_COUNT = 11;  // 3 * 3 + 2

    public static final int CRAFT_WIDTH = 3;
    public static final int CRAFT_HEIGHT = 3;

    public static final int INPUT_SLOTS_START = 0;
    public static final int INPUT_SLOTS_COUNT = CRAFT_WIDTH * CRAFT_HEIGHT;

    public static final int HP_RESOURCE_SLOT_ID = INPUT_SLOTS_COUNT;
    public static final int RESULT_SLOT_ID = HP_RESOURCE_SLOT_ID + 1;

    private final Player player;
    private final ContainerLevelAccess access;

    protected final CraftingContainer inputSlots;
    protected final SimpleContainer resourceSlots;
    protected final ResultContainer resultSlots = new ResultContainer();
    protected final DataSlot hpCost = DataSlot.standalone();

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
        this.addDataSlot(this.hpCost);

        addGridInputSlots();
        addHPResourceSlot();
        addResultSlot(this.player);
        addPlayerInventory(inv);
        addPlayerHotbar(inv);
    }

    public float getHPCost() {
        return hpCost.get() / 10.0F;
    }

    public void setHpCost(float newCost) {
        hpCost.set((int) newCost * 10);
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(access, player, InitBlocks.CRAFTING_TABLE_OF_RED_QUEEN.get());
    }

    @Override
    public ItemStack quickMoveStack(Player player, int idx) {
        if (idx < 0 || idx >= this.slots.size()) return ItemStack.EMPTY;

        Slot sourceSlot = this.slots.get(idx);
        if (!sourceSlot.hasItem()) return ItemStack.EMPTY;

        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack originalStack = sourceStack.copy();

        if (idx >= TE_INVENTORY_SLOT_COUNT
            && idx < TE_INVENTORY_SLOT_COUNT + VANILLA_SLOT_COUNT) {
                if (sourceStack.has(InitDataComponentTypes.STOLEN_LIFE.get())) {
                    if (this.moveItemStackTo(
                            sourceStack,
                            HP_RESOURCE_SLOT_ID,
                            HP_RESOURCE_SLOT_ID + 1,
                            false
                    )) {
                        sourceSlot.setChanged();

                        if (sourceStack.isEmpty()) {
                            sourceSlot.set(ItemStack.EMPTY);
                        }

                        return originalStack;
                    }
                }

            if (!this.moveItemStackTo(
                    sourceStack,
                    INPUT_SLOTS_START,
                    INPUT_SLOTS_START + INPUT_SLOTS_COUNT,
                    false
            )) return ItemStack.EMPTY;
        } else if (
                idx >= INPUT_SLOTS_START
                    && idx <= RESULT_SLOT_ID
        ) {
            if (!this.moveItemStackTo(
                    sourceStack,
                    TE_INVENTORY_SLOT_COUNT,
                    TE_INVENTORY_SLOT_COUNT + VANILLA_SLOT_COUNT,
                    false
            )) return ItemStack.EMPTY;
        } else return ItemStack.EMPTY;

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

    private Optional<? extends Recipe<CraftingInput>> getCurrentRecipe() {
        if (!(this.player.level() instanceof ServerLevel serverLevel)) {
            return Optional.empty();
        }

        CraftingInput input = this.inputSlots.asCraftInput();

        Optional<RecipeHolder<HPCostRecipe>> hpRecipe = serverLevel.recipeAccess()
                .getRecipeFor(
                    InitRecipes.HP_COST_TYPE.get(),
                    input,
                    this.player.level()
                );
        if (hpRecipe.isPresent()) return hpRecipe.map(RecipeHolder::value);

        return serverLevel.recipeAccess()
                .getRecipeFor(
                    RecipeType.CRAFTING,
                    input,
                    this.player.level()
                )
                .map(RecipeHolder::value);
    }

    private void updateRecipeResult() {
        this.hpCost.set(0);

        if (this.player.level() instanceof ServerLevel) {
            CraftingInput input = this.inputSlots.asCraftInput();

            Optional<? extends Recipe<CraftingInput>> maybeRecipe = getCurrentRecipe();

            ItemStack result = ItemStack.EMPTY;

            if (maybeRecipe.isPresent()) {
                Recipe<CraftingInput> recipe = maybeRecipe.get();

                if (recipe instanceof HPCostRecipe hpCostRecipe) {
                    setHpCost(hpCostRecipe.getHpCost());
                }

                result = recipe.assemble(input);
            }

            this.resultSlots.setItem(0, result);
            this.broadcastChanges();
        }
    }

    public boolean canPayHPCost() {
        if (!(this.player.level() instanceof ServerLevel)) return false;

        if (this.player.hasInfiniteMaterials()) return true;

        Optional<? extends Recipe<CraftingInput>> maybeRecipe = getCurrentRecipe();

        Recipe<CraftingInput> recipe = maybeRecipe.get();
        if (!(recipe instanceof HPCostRecipe hpCostRecipe)) return true;

        float cost = hpCostRecipe.getHpCost();
        if (cost <= 0.0F) return true;

        ItemStack resourceStack = this.resourceSlots.getItem(0);
        float stolenLife = StolenLifeDataComponentUtils.getLife(resourceStack);

        float playerHealth = this.player.getHealth();

        float remainingCost = Math.max(cost - stolenLife, 0.0F);

        return playerHealth >= remainingCost;
    }

    public boolean payHPCost() {
        if (!(this.player.level() instanceof ServerLevel serverLevel)) return false;

        if (this.player.hasInfiniteMaterials()) return true;

        if (!this.canPayHPCost()) {
            this.player.kill(serverLevel);
            return false;
        }

        CraftingInput input = this.inputSlots.asCraftInput();

        Optional<RecipeHolder<CraftingRecipe>> maybeRecipe = serverLevel.recipeAccess()
                .getRecipeFor(RecipeType.CRAFTING, input, this.player.level());
        if (maybeRecipe.isEmpty()) return false;

        Recipe<CraftingInput> recipe = maybeRecipe.get().value();
        if (!(recipe instanceof HPCostRecipe hpCostRecipe)) return true;

        float cost = hpCostRecipe.getHpCost();
        if (cost <= 0.0F) return true;

        ItemStack resourceStack = this.resourceSlots.getItem(0);
        float stolenLife = StolenLifeDataComponentUtils.getLife(resourceStack);

        float itemPayment = Math.min(cost, stolenLife);

        float remainingCost = cost - itemPayment;

        if (itemPayment > 0.0F) {
            float newStolenLife = stolenLife - itemPayment;

            StolenLifeDataComponentUtils.setLife(resourceStack, newStolenLife);

            this.resourceSlots.setChanged();
        }

        if (remainingCost > 0.0F) {
            this.player.hurt(
                serverLevel.damageSources().magic(),
                remainingCost
            );
        }

        return true;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.access.execute((level, pos) -> {
            this.clearContainer(player, this.inputSlots);
            this.clearContainer(player, this.resourceSlots);
        });
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
            return stack.has(InitDataComponentTypes.STOLEN_LIFE.get());
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
        public ItemStack remove(int amount) {
            if (!this.hasItem()) return ItemStack.EMPTY;

            Player player = menu.player;
            if (!player.level().isClientSide()) {
                if (!menu.canPayHPCost()) {
                    if (player.level() instanceof ServerLevel serverLevel) {
                        player.kill(serverLevel);
                    }

                    return ItemStack.EMPTY;
                }

                if (!menu.payHPCost()) return ItemStack.EMPTY;
            }

            return super.remove(amount);
        }
    }
}
