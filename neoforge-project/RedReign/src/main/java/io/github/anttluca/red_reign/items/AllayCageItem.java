package io.github.anttluca.red_reign.items;

import io.github.anttluca.red_reign.handlers.RRItemTooltipsHandler;
import io.github.anttluca.red_reign.init.InitItems;
import io.github.anttluca.red_reign.items.custom.RRBaseItem;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class AllayCageItem extends RRBaseItem {
    public AllayCageItem(Properties props) {
        super(props
                .stacksTo(1)
        );
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext ctx, TooltipDisplay display, Consumer<Component> builder, TooltipFlag flag) {
        super.appendHoverText(stack, ctx, display, builder, flag);
        RRItemTooltipsHandler.addSpace(builder);
        RRItemTooltipsHandler.addLore(InitItems.ALLAY_CAGE.getId().getPath(), builder);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity target, InteractionHand hand) {
        if (!(target instanceof Allay allay)) {
            return InteractionResult.PASS;
        }

        if (player.level().isClientSide()) return InteractionResult.SUCCESS;

        allay.discard();
        player.level().playSound(
            null,
            allay.blockPosition(),
            SoundEvents.TRIAL_SPAWNER_CLOSE_SHUTTER,
            SoundSource.BLOCKS,
            1.0F, 1.0F
        );
        player.setItemInHand(
            hand,
            new ItemStack(
                InitItems.ETHEREAL_PROTECTION.get()
            )
        );
        return InteractionResult.SUCCESS;
    }
}
