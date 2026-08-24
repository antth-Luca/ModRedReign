package io.github.anttluca.red_reign.handlers;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.Optional;

public class CurioItemsHandler {
    public static boolean hasCurio(final LivingEntity entity, final Item curio) {
        return CuriosApi.getCuriosInventory(entity)
                .map(inv -> inv.isEquipped(curio))
                .orElse(false);
    }

    public static ItemStack removeCurio(final LivingEntity entity, final Item curio) {
        Optional<ICuriosItemHandler> curiosHandler = CuriosApi.getCuriosInventory(entity);
        if (curiosHandler.isPresent()) {
            ICuriosItemHandler handler = curiosHandler.get();

            Optional<SlotResult> found = handler.findFirstCurio(curio);
            if (found.isPresent()) {
                var slotResult = found.get();

                var slotContext = slotResult.slotContext();

                Optional<ICurioStacksHandler> stacksHandler = handler.getStacksHandler(slotContext.identifier());
                if (stacksHandler.isPresent()) {
                    ItemStack stackInSlot = stacksHandler.get().getStacks().getStackInSlot(slotContext.index());

                    stacksHandler.get().getStacks().setStackInSlot(slotContext.index(), ItemStack.EMPTY);

                    return stackInSlot;
                }
            }
        }

        return ItemStack.EMPTY;
    }
}
