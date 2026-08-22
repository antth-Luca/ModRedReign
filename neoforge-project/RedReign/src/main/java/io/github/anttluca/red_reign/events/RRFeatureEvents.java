package io.github.anttluca.red_reign.events;

import io.github.anttluca.red_reign.RedReign;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;

@EventBusSubscriber(modid = RedReign.MODID)
public class RRFeatureEvents {
    @SubscribeEvent
    public static void onEat(LivingEntityUseItemEvent.Finish event) {
        // When player eats meat, regen HP too
        if (event.getEntity() instanceof Player player
            && !player.level().isClientSide()) {
                ServerLevel serverLevel = (ServerLevel) player.level();
                ItemStack stack = event.getItem();
                FoodProperties food = stack.get(DataComponents.FOOD);

                if (food != null
                    && stack.is(ItemTags.MEAT)) {
                        float healAmount = food.nutrition() / 2.0F;

                        player.heal(healAmount);
                }

        }
    }
}
