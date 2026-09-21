package io.github.anttluca.red_reign.mixins;

import io.github.anttluca.red_reign.recipes.TransmutationRecipe;
import io.github.anttluca.red_reign.utils.RREnchantmentsUtils;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;

@Mixin(EnchantmentMenu.class)
public abstract class RREnchantingMenuMixin extends AbstractContainerMenu {
    @Shadow @Final private Container enchantSlots;
    @Shadow @Final private ContainerLevelAccess access;
    @Shadow @Final public int[] costs;
    @Shadow @Final public int[] enchantClue;
    @Shadow @Final public int[] levelClue;

    @Unique
    private static final int INFUSE_COST = 3;

    protected RREnchantingMenuMixin() { super(null, 0); }

    @Inject(
            method = "slotsChanged",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void red_reign$modifyCost(Container container, CallbackInfo cbInfo) {
        if (container != this.enchantSlots) return;

        ItemStack stack = container.getItem(0);
        if (stack.isEmpty()) return;

        this.access.execute((level, pos) -> {
            if (TransmutationRecipe.getCurrentRecipe(level, stack).isPresent()) {
                this.costs[0] = this.costs[1] = 0;
                this.costs[2] = INFUSE_COST;

                this.enchantClue[2] = level.registryAccess()
                        .lookupOrThrow(Registries.ENCHANTMENT)
                        .getId(level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT)
                                .getOrThrow(RREnchantmentsUtils.TRANSMUTATION_KEY).value());

                this.levelClue[2] = 1;

                this.broadcastChanges();
                cbInfo.cancel();
            }
        });
    }

    @Inject(
            method = "clickMenuButton",
            at = @At("HEAD"),
            cancellable = true
    )
    private void red_reign$transmuteItem(Player player, int btnId, CallbackInfoReturnable<Boolean> cbInfoR) {
        if (btnId != 2) return;

        ItemStack iStack = this.enchantSlots.getItem(0);
        if (iStack.isEmpty()) return;

        Optional<TransmutationRecipe> trRecipe = this.access
                .evaluate((wLevel, pos) -> TransmutationRecipe.getCurrentRecipe(wLevel, iStack))
                .flatMap(r -> r);
        if (trRecipe.isEmpty()) return;

        boolean creative = player.getAbilities().instabuild;

        ItemStack lapis = this.getSlot(1).getItem();
        if (!creative && (lapis.getCount() < INFUSE_COST || player.experienceLevel < INFUSE_COST)) {
            cbInfoR.setReturnValue(false);
            return;
        }

        this.access.execute((wLevel, pos) -> {
            this.enchantSlots.setItem(0, trRecipe.get().getOutput().create());

            if (!creative) {
                lapis.shrink(INFUSE_COST);
                this.getSlot(1).set(lapis);

                player.onEnchantmentPerformed(iStack, INFUSE_COST);
            }

            player.awardStat(Stats.ENCHANT_ITEM);

            this.enchantSlots.setChanged();
            this.slotsChanged(this.enchantSlots);

            wLevel.playSound(
                    null,
                    pos,
                    SoundEvents.ENCHANTMENT_TABLE_USE,
                    SoundSource.BLOCKS,
                    1.0F, 1.0F
            );
        });

        cbInfoR.setReturnValue(true);
    }
}
