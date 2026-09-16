package io.github.anttluca.red_reign.events.runtime;

import io.github.anttluca.red_reign.init.InitItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;

// Register only RedReignWorldData changed
public class RRItemsWorksEvent {
    // Drop: Final Blessing
    @SubscribeEvent
    public static void onVillagerDrops(LivingDropsEvent event) {
        if (
            event.getEntity() instanceof Villager villager
              && villager.level() instanceof ServerLevel serverLevel
              && villager.getVillagerData().profession().is(VillagerProfession.CLERIC)
              && serverLevel.getRandom().nextFloat() < 0.2F
        ) {
                event.getDrops().add(new ItemEntity(
                    serverLevel,
                    villager.getX(),
                    villager.getY(),
                    villager.getZ(),
                    new ItemStack(
                        InitItems.FINAL_BLESSING.get()
                    )
                ));
        }
    }
}
