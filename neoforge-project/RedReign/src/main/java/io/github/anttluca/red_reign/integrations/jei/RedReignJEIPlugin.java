package io.github.anttluca.red_reign.integrations.jei;

import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.init.InitItems;
import io.github.anttluca.red_reign.init.InitMenuTypes;
import io.github.anttluca.red_reign.integrations.jei.categories.HPCostRecipeCategory;
import io.github.anttluca.red_reign.init.InitRecipes;
import io.github.anttluca.red_reign.recipes.custom.HPCostRecipe;
import io.github.anttluca.red_reign.screens.CraftingTableOfRedQueenScreen;
import io.github.anttluca.red_reign.screens.menu.CraftingTableOfRedQueenMenu;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandlerHelper;
import mezz.jei.api.recipe.types.IRecipeType;
import mezz.jei.api.registration.*;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RecipesReceivedEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;

import java.util.List;

@JeiPlugin
public class RedReignJEIPlugin implements IModPlugin {
    private static RecipeMap syncedRecipes = RecipeMap.EMPTY;

    public static final IRecipeType<RecipeHolder<HPCostRecipe>> HP_COST_JEI_TYPE = holderRecipeType(
        InitRecipes.HP_COST_TYPE.getId(), HPCostRecipe.class
    );

    @Override
    public Identifier getPluginUid() {
        return Identifier.fromNamespaceAndPath(RedReign.MODID, "jei");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new HPCostRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(HP_COST_JEI_TYPE, this.getRecipes(syncedRecipes, InitRecipes.HP_COST_TYPE.get()));
    }

    // From Occultism
    // Under MIT License
    private <I extends RecipeInput, T extends Recipe<I>> List<RecipeHolder<T>> getRecipes(RecipeMap recipeMap, RecipeType<T> type) {
        return (List) recipeMap.byType(type);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(CraftingTableOfRedQueenScreen.class, 74, 30, 22, 20, HP_COST_JEI_TYPE);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        // Vanilla
        registration.addCraftingStation(RecipeTypes.CRAFTING, InitItems.CRAFTING_TABLE_OF_RED_QUEEN);
        // Mod
        registration.addCraftingStation(HP_COST_JEI_TYPE, InitItems.CRAFTING_TABLE_OF_RED_QUEEN);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        // Vanilla
        registration.addRecipeTransferHandler(
            CraftingTableOfRedQueenMenu.class,
            InitMenuTypes.CRAFTING_TABLE_OF_RED_QUEEN_MENU.get(),
            RecipeTypes.CRAFTING,
            0, 9,
            11, 36
        );
        // Mod
        registration.addRecipeTransferHandler(
            CraftingTableOfRedQueenMenu.class,
            InitMenuTypes.CRAFTING_TABLE_OF_RED_QUEEN_MENU.get(),
            HP_COST_JEI_TYPE,
            0, 9,
            11, 36
        );
    }

    // Method from Occultism.create() : https://github.com/klikli-dev/occultism/blob/version/26.1.2/src/main/java/com/klikli_dev/occultism/integration/jei/impl/JeiRecipeTypes.java
    // Under MIT-License
    public static <R extends Recipe<?>> IRecipeType<RecipeHolder<R>> holderRecipeType(Identifier uid, Class<? extends R> recipeClass) {
        Class<? extends RecipeHolder<R>> holderClass = (Class<? extends RecipeHolder<R>>) (Object) RecipeHolder.class;
        return IRecipeType.create(uid, holderClass);
    }

    @EventBusSubscriber(modid = RedReign.MODID)
    public static class ServerRecipeSync {
        @SubscribeEvent
        public static void onDatapackSync(OnDatapackSyncEvent event) {
            event.sendRecipes(InitRecipes.HP_COST_TYPE.get());
        }
    }

    @EventBusSubscriber(modid = RedReign.MODID, value = Dist.CLIENT)
    public static class ClientRecipeSync {
        @SubscribeEvent
        public static void onRecipeReceived(RecipesReceivedEvent event) {
            syncedRecipes = event.getRecipeMap();
        }
    }
}
