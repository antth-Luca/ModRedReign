package io.github.anttluca.red_reign.integrations.jei.categories;

import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.init.InitItems;
import io.github.anttluca.red_reign.integrations.jei.RedReignJEIPlugin;
import io.github.anttluca.red_reign.recipes.PurificationRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jspecify.annotations.Nullable;

public class PurificationRecipeCategory implements IRecipeCategory<RecipeHolder<PurificationRecipe>> {
    public static final Identifier GUI_TEXTURE = Identifier.fromNamespaceAndPath(RedReign.MODID,
        "textures/gui/jei/purification.png");
    public static final Component TITLE = Component.translatable("jei.red_reign.category.purification");

    private static final int WIDTH = 170;
    private static final int HEIGHT = 93;

    private final IDrawable overlay;

    public PurificationRecipeCategory(IGuiHelper helper) {
        this.overlay = helper.createDrawable(GUI_TEXTURE, 0, 0, WIDTH, HEIGHT);
    }

    @Override
    public IRecipeType<RecipeHolder<PurificationRecipe>> getRecipeType() { return RedReignJEIPlugin.PURIFICATION_JEI_TYPE; }

    @Override
    public Component getTitle() { return TITLE; }

    @Override
    public int getWidth() { return WIDTH; }

    @Override
    public int getHeight() { return HEIGHT; }

    @Override
    public @Nullable IDrawable getIcon() { return null; }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<PurificationRecipe> recipe, IFocusGroup focuses) {
        // Input
        builder.addSlot(RecipeIngredientRole.INPUT, 36, 20)
                .add(recipe.value().getIngredient().get());

        // Purification Spell
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 118, 20)
                .add(new ItemStack(InitItems.PURIFICATION_SPELL.get()));

        // Result
        builder.addSlot(RecipeIngredientRole.OUTPUT, 79, 67)
                .add(recipe.value().getOutput().create());
    }

    @Override
    public void draw(RecipeHolder<PurificationRecipe> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        // Background
        this.overlay.draw(guiGraphics, 0, 0);
    }
}
