package io.github.anttluca.red_reign.datagen;

import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.init.InitBlocks;
import io.github.anttluca.red_reign.init.InitItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.Block;

import java.util.Optional;
import java.util.stream.Stream;

public class RRModelsProvider extends ModelProvider {
    private static final ModelTemplate KEYCHAIN = ModelTemplates.FLAT_ITEM.extend()
            .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, t -> t
                    .rotation(75, 0, 0)
                    .translation(0, 0, -2)
                    .scale(0.55F, 0.55F, 0.55F)
            )
            .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND, t -> t
                    .rotation(75, 0, 0)
                    .translation(0, 0, -2)
                    .scale(0.55F, 0.55F, 0.55F)
            ).build();

    private static final ModelTemplate CYLINDRICAL = ModelTemplates.FLAT_ITEM.extend()
            .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, t -> t
                    .rotation(0, 0, 45)
                    .translation(0, 0, 0)
                    .scale(0.55F, 0.55F, 0.55F)
            )
            .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND, t -> t
                    .rotation(0, 0, -45)
                    .translation(0, 0, 0)
                    .scale(0.55F, 0.55F, 0.55F)
            ).build();

    private static final ModelTemplate SPELL = ModelTemplates.FLAT_ITEM.extend()
            .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, t -> t
                    .rotation(0, 0, 0)
                    .translation(0, -2, -1.25F)
                    .scale(0.4F, 0.4F, 0.4F)
            )
            .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND, t -> t
                    .rotation(0, 0, 0)
                    .translation(0, -2, -1.25F)
                    .scale(0.4F, 0.4F, 0.4F)
            ).build();

    private static final ModelTemplate PARTICLE_ONLY = new ModelTemplate(
        Optional.empty(), Optional.empty(), TextureSlot.PARTICLE);

    public RRModelsProvider(PackOutput output) {
        super(output, RedReign.MODID);
    }

    @Override
    protected Stream<? extends Holder<Block>> getKnownBlocks() {
        return Stream.of(InitBlocks.ROSE_QUARTZ_BLOCK, InitBlocks.BOUQUET_OF_POPPIES);
    }

    @Override
    protected Stream<? extends Holder<Item>> getKnownItems() {
        return Stream.of(InitItems.BOUQUET_OF_POPPIES, InitItems.ROSE_QUARTZ_BLOCK);
    }

    @Override
    protected void registerModels(BlockModelGenerators blocks, ItemModelGenerators items) {
        registerBlocks(blocks);
        registerItems(items);
    }

    private void registerBlocks(BlockModelGenerators blocks) {
        // Special

        // Leaves
        blocks.createTrivialBlock(InitBlocks.BOUQUET_OF_POPPIES.get(), TexturedModel.LEAVES);

        // Simples
        blocks.createTrivialCube(InitBlocks.ROSE_QUARTZ_BLOCK.get());
    }

    private void registerItems(ItemModelGenerators items) { }
}
