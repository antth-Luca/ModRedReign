package io.github.anttluca.red_reign.integrations.jei.categories;

import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.integrations.jei.RedReignJEIPlugin;
import io.github.anttluca.red_reign.recipes.TransmutationRecipe;
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
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.object.book.BookModel;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.joml.Matrix3x2f;
import org.joml.Vector2f;
import org.jspecify.annotations.Nullable;

public class TransmutationRecipeCategory implements IRecipeCategory<RecipeHolder<TransmutationRecipe>> {
    public static final Identifier GUI_TEXTURE = Identifier.fromNamespaceAndPath(RedReign.MODID,
        "textures/gui/jei/transmutation.png");
    public static final Component TITLE = Component.translatable("jei.red_reign.category.transmutation");

    private static final int WIDTH = 170;
    private static final int HEIGHT = 80;
    private static final Identifier ENCHANTING_BOOK_LOCATION = Identifier.withDefaultNamespace("textures/entity/enchantment/enchanting_table_book.png");

    private final IDrawable icon;
    private final IDrawable overlay;
    private final BookModel bookModel;

    public TransmutationRecipeCategory(IGuiHelper helper) {
        this.overlay = helper.createDrawable(GUI_TEXTURE, 0, 0, WIDTH, HEIGHT);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(Items.ENCHANTING_TABLE));
        this.bookModel = new BookModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayers.BOOK));
    }

    @Override
    public IRecipeType<RecipeHolder<TransmutationRecipe>> getRecipeType() { return RedReignJEIPlugin.TRANSMUTATION_JEI_TYPE; }

    @Override
    public Component getTitle() { return TITLE; }

    @Override
    public int getWidth() { return WIDTH; }

    @Override
    public int getHeight() { return HEIGHT; }

    @Override
    public @Nullable IDrawable getIcon() { return icon; }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<TransmutationRecipe> recipe, IFocusGroup focuses) {
        // Input
        builder.addSlot(RecipeIngredientRole.INPUT, 12, 44)
                .add(recipe.value().getIngredient().get());

        // Lapis Lazuli
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 32, 44)
                .add(new ItemStack(Items.LAPIS_LAZULI, 3));

        // Result
        builder.addSlot(RecipeIngredientRole.OUTPUT, 140, 12)
                .add(recipe.value().getOutput().create());
    }

    @Override
    public void draw(RecipeHolder<TransmutationRecipe> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        // Background
        this.overlay.draw(guiGraphics, 0, 0);

        // Book
        float ticks = Util.getMillis() / 50.0F;
        Matrix3x2f pose = guiGraphics.pose();
        Vector2f p0 = pose.transformPosition(10, 8, new Vector2f());
        Vector2f p1 = pose.transformPosition(48, 39, new Vector2f());
        guiGraphics.book(
            bookModel, ENCHANTING_BOOK_LOCATION,
            40.0F, 1.0F, ticks * 0.02F,
            (int) p0.x, (int) p0.y,
            (int) p1.x, (int) p1.y
        );

        // Required Level text
        Component costText = Component.translatable("jei.red_reign.category.transmutation.level",
            String.valueOf(recipe.value().getLevelRequired()));
        guiGraphics.textWithWordWrap(
            Minecraft.getInstance().font,
            costText,
            77,
            46,
            66,
            0xFF80FF20,
            true
        );
    }
}
