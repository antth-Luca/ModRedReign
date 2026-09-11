package io.github.anttluca.red_reign.contexts;

import net.minecraft.world.item.ItemStack;

public final class RRTooltipContext {
    private static ItemStack currentStack = ItemStack.EMPTY;

    public static ItemStack getStack() {
        return currentStack;
    }

    public static void setStack(ItemStack stack) {
        currentStack = stack;
    }

    public static void set(ItemStack stack) {
        setStack(stack);
    }

    public static void clear() {
        currentStack = ItemStack.EMPTY;
    }
}
