package io.github.anttluca.red_reign.handlers;

import io.github.anttluca.red_reign.components.AdoptableDataComponent;
import io.github.anttluca.red_reign.components.StolenLifeDataComponent;
import io.github.anttluca.red_reign.init.InitDataComponentTypes;
import io.github.anttluca.red_reign.screens.CraftingTableOfRedQueenScreen;
import io.github.anttluca.red_reign.utils.components.StolenLifeDataComponentUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class RRItemTooltipsHandler {
    public static final Component RR_STAMP = Component.literal(String.format("[ %s ]", Component.translatable("itemGroup.red_reign").getString()));
    public static final Component HOLD_SHIFT = Component.translatable("item.red_reign.common.hold_shift").withStyle(ChatFormatting.DARK_PURPLE);
    public static final String LIST_ITEM = "– ";
    public static final Component BELONGS_TO = Component.translatable("component.red_reign.adoptable").append(": ");
    public static final Component LIFE_STOLEN = Component.translatable("component.red_reign.stolen_life").append(": ");

    public static void addSpace(Consumer<Component> builder) {
        builder.accept(Component.empty());
    }

    public static void addLoreAndEffects (String itemName, int cont, Consumer<Component> builder) {
        if (isShiftPressed()) {
            addAbilities(itemName, cont, builder);
        } else {
            addLore(itemName, builder);
            builder.accept(HOLD_SHIFT);
        }
    }

    public static void addLore(String itemName, Consumer<Component> builder) {
        builder.accept(Component.translatable("item.red_reign." + itemName + ".lore").withStyle(ChatFormatting.GRAY));
    }

    public static void addAbilities(String itemName, int cont, Consumer<Component> builder) {
        String baseKey = "item.red_reign." + itemName + ".ability";

        builder.accept(Component.translatable("item.red_reign.common.abilities").append(":").withStyle(ChatFormatting.LIGHT_PURPLE));

        for (int c = 1; c <= cont; c++) {
            builder.accept(
                Component.literal(LIST_ITEM)
                    .append(Component.translatable(baseKey + c))
                    .withStyle(ChatFormatting.GRAY)
            );
        }
    }

    public static boolean isShiftPressed() {
        return Minecraft.getInstance().hasShiftDown();
    }

    // DATA COMPONENTS
    public static void indicateOwner(ItemStack stack, Item.TooltipContext ctx, Consumer<Component> builder) {
        @Nullable AdoptableDataComponent adoptable = stack.get(InitDataComponentTypes.ADOPTABLE.get());
        if (adoptable == null) return;

        Component name;

        Level level = ctx.level();
        if (level == null) {
            name = AdoptableDataComponent.UNKNOWN_NAME;
        } else {

            Entity owner = level.getEntity(adoptable.owner());
            if (owner == null) {
                name = AdoptableDataComponent.UNKNOWN_NAME;
            } else {

                name = owner.getDisplayName().copy().withStyle(ChatFormatting.LIGHT_PURPLE);
            }
        }

        builder.accept(BELONGS_TO.copy().append(name));
    }

    public static void indicateStolen(ItemStack stack, Consumer<Component> builder) {
        @Nullable StolenLifeDataComponent stolenLife = stack.get(InitDataComponentTypes.STOLEN_LIFE.get());
        if (stolenLife == null) return;

        String format = String.format("%.2f / %.2f", stolenLife.life(), StolenLifeDataComponentUtils.MAX_STOLEN_LIFE);

        builder.accept(LIFE_STOLEN.copy().append(
            Component.literal(format)
                    .withColor(CraftingTableOfRedQueenScreen.LIFE_COLOR)
        ));
    }
}
