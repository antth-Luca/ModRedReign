package io.github.anttluca.red_reign.mixins;

import io.github.anttluca.red_reign.recipes.TransmutationRecipe;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Recipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(EnchantmentMenu.class)
public class RREnchantingMenuMixin {
    @Shadow
    private ContainerLevelAccess access;

    @Shadow
    public int[] costs;
    @Shadow
    public int[] enchantClue;
    @Shadow
    public int[] levelClue;

    @Inject(
            method = "slotsChanged",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/inventory/EnchantmentMenu;broadcastChanges()V"
            )
    )
    private void red_reign$modifyTransformationCost(Container container, CallbackInfo cbInfo) {
        ItemStack iStack = container.getItem(0);
        if (iStack.isEmpty()) return;

        if (this.costs[2] < 30) return;

        this.access.execute((level, pos) -> {
            Optional<TransmutationRecipe> maybeRecipe = TransmutationRecipe.getCurrentRecipe(level, iStack);
            if (maybeRecipe.isPresent()) {
                this.costs[2] = 30;
                this.enchantClue[2] = -1;
                this.levelClue[2] = -1;
            }
        });
    }
}
