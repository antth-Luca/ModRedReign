package io.github.anttluca.red_reign.compats.jei.categories;

import io.github.anttluca.red_reign.blocks.entity.CraftingTableOfRedQueenBlockEntity;
import io.github.anttluca.red_reign.compats.jei.RedReignJEIPlugin;
import io.github.anttluca.red_reign.init.InitBlocks;
import io.github.anttluca.red_reign.recipes.custom.HPCostRecipe;
import io.github.anttluca.red_reign.screens.CraftingTableOfRedQueenScreen;
import io.github.anttluca.red_reign.screens.menu.CraftingTableOfRedQueenMenu;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class HPCostRecipeCategory implements IRecipeCategory<RecipeHolder<HPCostRecipe>> {
    private final IDrawable icon;
    private final IDrawable overlay;

    public HPCostRecipeCategory(IGuiHelper helper) {
        this.overlay = helper.createDrawable(CraftingTableOfRedQueenScreen.GUI_TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(InitBlocks.CRAFTING_TABLE_OF_RED_QUEEN));
    }

    @Override
    public IRecipeType<RecipeHolder<HPCostRecipe>> getRecipeType() {
        return RedReignJEIPlugin.HP_COST_JEI_TYPE;
    }

    @Override
    public Component getTitle() {
        return CraftingTableOfRedQueenBlockEntity.DEFAULT_NAME;
    }

    @Override
    public int getWidth() {
        return 176;
    }

    @Override
    public int getHeight() {
        return 186;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<HPCostRecipe> recipe, IFocusGroup focuses) {
        // Inputs
        List<Optional<Ingredient>> ingredients = recipe.value().getIngredients();

        for (int i = 0; i < CraftingTableOfRedQueenMenu.CRAFT_WIDTH; i++) {
            int ci = i * CraftingTableOfRedQueenMenu.CRAFT_WIDTH;
            for (int l = 0; l < CraftingTableOfRedQueenMenu.CRAFT_HEIGHT; l++) {
                int idx = ci + l;

                if (idx < ingredients.size()) {
                    final int slotX = 30 + l * 18;
                    final int slotY = 7 + i * 18;

                    ingredients.get(idx).ifPresent(ingredient -> {
                        builder.addSlot(RecipeIngredientRole.INPUT, slotX, slotY).add(ingredient);
                    });
                }
            }
        }

        // Result
        builder.addSlot(RecipeIngredientRole.OUTPUT, 124, 25).add(recipe.value().getOutput());
    }

    @Override
    public void draw(RecipeHolder<HPCostRecipe> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        this.overlay.draw(guiGraphics, 0, 0);

        Component costText = Component.translatable(CraftingTableOfRedQueenScreen.HP_COST_KEY)
                .append(String.valueOf(recipe.value().getHpCost()))
                .withColor(CraftingTableOfRedQueenScreen.LIFE_COLOR);
        guiGraphics.setComponentTooltipForNextFrame(
            Minecraft.getInstance().font,
            List.of(costText),
            (int) mouseX, (int) mouseY + 110);
    }
}
