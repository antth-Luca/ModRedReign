package io.github.anttluca.red_reign.events.runtime;

import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.world.data.RedReignWorldData;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.gamerules.GameRules;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.level.LevelEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// Register only RedReignWorldData changed
public class RRWorldEffetcsWorks {
    public static void disablePlayerNaturalRegen(ServerLevel serverLevel) {
        // Player passive HP regen. disabled
        serverLevel.getGameRules().set(
            GameRules.NATURAL_HEALTH_REGENERATION,
            false,
            serverLevel.getServer()
        );
    }

    @SubscribeEvent
    public static void onLivingHeal(LivingHealEvent event) {
        // All entities heal reducerd in 60%
        event.setAmount(event.getAmount() * 0.4F);
    }

    @SubscribeEvent
    public static void onAnimalDrops(LivingDropsEvent event) {
        // Every meat drop has a 50% chance of rotten flesh
        List<ItemEntity> replaceCollection = null;
        if (event.getEntity() instanceof Animal animal
            && animal.level() instanceof ServerLevel serverLevel) {
                Collection<ItemEntity> drops = event.getDrops();
                replaceCollection = new ArrayList<>();

                for (ItemEntity drop : drops) {
                    ItemStack stack = drop.getItem();
                    if (stack.is(ItemTags.MEAT)
                        && serverLevel.getRandom().nextFloat() < 0.5F) {
                            replaceCollection.add(drop);
                    }
                }

                for (ItemEntity oldDrop : replaceCollection) {
                    drops.remove(oldDrop);

                    drops.add(new ItemEntity(
                        serverLevel,
                        oldDrop.getX(),
                        oldDrop.getY(),
                        oldDrop.getZ(),
                        new ItemStack(
                            Items.BONE,
                            oldDrop.getItem().getCount()
                        )
                    ));
                }
        }
    }

    @SubscribeEvent
    public static void onEat(LivingEntityUseItemEvent.Finish event) {
        // When player eats meat, regen HP too
        if (event.getEntity() instanceof Player player
            && !player.level().isClientSide()) {
                ItemStack stack = event.getItem();
                FoodProperties food = stack.get(DataComponents.FOOD);

                if (food != null
                    && stack.is(ItemTags.MEAT)) {
                        float healAmount = food.nutrition() / 2.0F;

                        player.heal(healAmount);
                }
        }
    }

    @EventBusSubscriber(modid = RedReign.MODID)
    public static class Bootstrap {
        @SubscribeEvent
        public static void onLoadWorld(LevelEvent.Load event) {
            if (event.getLevel() instanceof ServerLevel serverLevel) {
                if (RedReignWorldData.get(serverLevel, ServerLevel.OVERWORLD).isActive()) {
                    NeoForge.EVENT_BUS.register(RRWorldEffetcsWorks.class);
                }
            }
        }
    }
}
