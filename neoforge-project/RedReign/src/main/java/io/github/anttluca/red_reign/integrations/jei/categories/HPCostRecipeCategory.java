package io.github.anttluca.red_reign.integrations.jei.categories;

import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.blocks.entity.CraftingTableOfRedQueenBlockEntity;
import io.github.anttluca.red_reign.integrations.jei.RedReignJEIPlugin;
import io.github.anttluca.red_reign.init.InitBlocks;
import io.github.anttluca.red_reign.init.InitItems;
import io.github.anttluca.red_reign.recipes.HPCostRecipe;
import io.github.anttluca.red_reign.screens.CraftingTableOfRedQueenScreen;
import io.github.anttluca.red_reign.screens.menu.CraftingTableOfRedQueenMenu;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
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
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class HPCostRecipeCategory implements IRecipeCategory<RecipeHolder<HPCostRecipe>> {
    public static final Identifier GUI_TEXTURE = Identifier.fromNamespaceAndPath(RedReign.MODID,
            "textures/gui/jei/crafting_table_of_red_queen.png");

    private static final int WIDTH = 170;
    private static final int HEIGHT = 93;

    private final IDrawable icon;
    private final IDrawable overlay;

    public HPCostRecipeCategory(IGuiHelper helper) {
        this.overlay = helper.createDrawable(GUI_TEXTURE, 0, 0, WIDTH, HEIGHT);
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
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<HPCostRecipe> recipe, IFocusGroup focuses) {
        // Inputs
        ShapedRecipePattern pattern = recipe.value().pattern();
        List<Optional<Ingredient>> ingredients = recipe.value().getIngredients();

        int offsetX = pattern.width() == 1 ? 1 : 0;
        int offsetY = pattern.height() == 1 ? 1 : 0;

        for (int y = 0; y < CraftingTableOfRedQueenMenu.CRAFT_HEIGHT; y++) {
            for (int x = 0; x < CraftingTableOfRedQueenMenu.CRAFT_WIDTH; x++) {
                // Always create slot
                IRecipeSlotBuilder slot = builder.addSlot(
                        RecipeIngredientRole.INPUT, 27 + x * 18, 14 + y * 18
                );
                // Real size considering offset
                int px = x - offsetX;
                int py = y - offsetY;
                if (px >= 0 && px < pattern.width() && py >= 0 && py < pattern.height()) {
                    ingredients.get(px + py * pattern.width()).ifPresent(slot::add);
                }
            }
        }

        // HP Resource
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 5, 32)
                .add(new ItemStack(InitItems.CHALICE_OF_THE_BLOODBLADE.get()));

        // Result
        builder.addSlot(RecipeIngredientRole.OUTPUT, 121, 32)
                .add(recipe.value().getOutput().create());
    }

    @Override
    public void draw(RecipeHolder<HPCostRecipe> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        this.overlay.draw(guiGraphics, 0, 0);

        Component costText = CraftingTableOfRedQueenScreen.HP_COST.copy()
                .append(String.valueOf(recipe.value().getHpCost()));
        guiGraphics.text(
            Minecraft.getInstance().font,
            costText,
            19,
            71,
            CraftingTableOfRedQueenScreen.LIFE_COLOR,
            false
        );
    }
}
