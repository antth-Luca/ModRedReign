package io.github.anttluca.red_reign.contexts;

import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public final class RRTooltipContext {
    private static ItemStack currentStack = ItemStack.EMPTY;
    private static List<ClientTooltipComponent> lines = List.of();

    public static ItemStack getStack() {
        return currentStack;
    }

    public static List<ClientTooltipComponent> getLines() {
        return lines;
    }

    public static void setStack(ItemStack stack) {
        currentStack = stack;
    }

    public static void setLines(List<ClientTooltipComponent> newLines) {
        lines = newLines;
    }

    public static void set(ItemStack stack, List<ClientTooltipComponent> newLines) {
        setStack(stack);
        setLines(newLines);
    }

    public static void clear() {
        currentStack = ItemStack.EMPTY;
        lines = List.of();
    }
}
