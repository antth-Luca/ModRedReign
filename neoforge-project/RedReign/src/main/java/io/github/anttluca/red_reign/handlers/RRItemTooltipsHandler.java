package io.github.anttluca.red_reign.handlers;

import io.github.anttluca.red_reign.components.AdoptableDataComponent;
import io.github.anttluca.red_reign.components.StolenLifeDataComponent;
import io.github.anttluca.red_reign.init.InitDataComponentTypes;
import io.github.anttluca.red_reign.screens.CraftingTableOfRedQueenScreen;
import io.github.anttluca.red_reign.utils.components.StolenLifeDataComponentUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class RRItemTooltipsHandler {
    public static final Component BELONGS_TO = Component.translatable("component.red_reign.adoptable").append(": ");
    public static final Component LIFE_STOLEN = Component.translatable("component.red_reign.stolen_life").append(": ");

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
