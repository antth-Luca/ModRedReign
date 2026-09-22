package io.github.anttluca.red_reign.items;

import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.handlers.RRItemTooltipsHandler;
import io.github.anttluca.red_reign.init.InitItems;
import io.github.anttluca.red_reign.items.custom.RRBaseItem;
import io.github.anttluca.red_reign.recipes.PurificationRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;

import java.util.Optional;
import java.util.function.Consumer;

public class PurificationSpellItem extends RRBaseItem {
    private static final String UNPURIFIED_KEY = "item." + RedReign.MODID + "." + InitItems.PURIFICATION_SPELL.getId().getPath() + ".unpurified";

    public PurificationSpellItem(Properties props) {
        super(props
                .stacksTo(1)
        );
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext ctx, TooltipDisplay display, Consumer<Component> builder, TooltipFlag flag) {
        super.appendHoverText(stack, ctx, display, builder, flag);
        RRItemTooltipsHandler.addLoreAndEffects(InitItems.PURIFICATION_SPELL.getId().getPath(), 1, builder);
    }

    @Override
    public boolean isFoil(ItemStack itemStack) {
        return true;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResult.CONSUME;
    }

    @Override
    public int getUseDuration(ItemStack itemStack, LivingEntity user) {return 72000;}

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack itemStack) {
        return ItemUseAnimation.BOW;
    }

    @Override
    public boolean releaseUsing(ItemStack stack, Level level, LivingEntity entity, int remainingTime) {
        if (!(entity instanceof ServerPlayer serverPlayer)) return false;

        int timeHeld = this.getUseDuration(stack, entity) - remainingTime;
        if (timeHeld < 0) return false;

        ItemStack offStack = serverPlayer.getOffhandItem();
        if (offStack.isEmpty()) return false;

        stack.shrink(1);

        Optional<PurificationRecipe> pfRecipe = PurificationRecipe.getCurrentRecipe(level, offStack);
        if (pfRecipe.isEmpty()) {
            Component txtComponent = RRItemTooltipsHandler.RR_STAMP.copy()
                    .append(Component.translatable(UNPURIFIED_KEY));
            serverPlayer.sendSystemMessage(txtComponent, true);

            return false;
        }

        // ItemStack consumedInputCopy = offStack.copyWithCount(1);

        ItemStack result = pfRecipe.get().getOutput().create();
        offStack.shrink(1);

        if (!serverPlayer.getInventory().add(result)) {
            serverPlayer.drop(result, false);
        }

        level.broadcastEntityEvent(entity, (byte) 35);
        return true;
    }
}
