package io.github.anttluca.red_reign.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.anttluca.red_reign.handlers.CurioItemsHandler;
import io.github.anttluca.red_reign.init.InitItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(AnvilMenu.class)
public class RRAnvilMenuMixin {
    // Relic: DaisySilverMeteor
    @ModifyConstant(
            method = "lambda$onTake$0",
            constant = @org.spongepowered.asm.mixin.injection.Constant(floatValue = 0.12F)
    )
    private static float red_reign$modifyAnvilDamageChance(float originalChance, @Local(argsOnly = true) Player player) {
        if (CurioItemsHandler.hasCurio(player, InitItems.DAISY_SILVER_METEOR.get())) {
            return originalChance * 0.5F;
        }

        return originalChance;
    }
}