package io.github.anttluca.red_reign.datagen;

import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.blocks.AltarOfRedLadyBlock;
import io.github.anttluca.red_reign.init.InitBlocks;
import io.github.anttluca.red_reign.init.InitItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.block.dispatch.Variant;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.client.model.generators.template.ExtendedModelTemplateBuilder;

import java.util.List;
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

    private static final ModelTemplate CRAFTING_TABLE_OF_RQ = ExtendedModelTemplateBuilder.builder()
            .parent(Identifier.withDefaultNamespace("block/block"))
            .requiredTextureSlot(TextureSlot.PARTICLE)
            .requiredTextureSlot(TextureSlot.BOTTOM)
            .requiredTextureSlot(TextureSlot.TOP)
            .requiredTextureSlot(TextureSlot.SIDE)
            .element(e -> {
                e.from(0, 0, 0).to(16, 12, 16);

                e.face(Direction.DOWN, f -> f.uvs(0, 0, 16, 16).texture(TextureSlot.BOTTOM).cullface(Direction.DOWN));
                e.face(Direction.UP, f -> f.uvs(0, 0, 16, 16).texture(TextureSlot.TOP));

                for (Direction side : List.of(Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST)) {
                    e.face(side, f -> f.uvs(0, 4, 16, 16).texture(TextureSlot.SIDE).cullface(side));
                }
            }).build();

    private static final ModelTemplate PARTICLE_ONLY = new ModelTemplate(
        Optional.empty(), Optional.empty(), TextureSlot.PARTICLE);

    public RRModelsProvider(PackOutput output) {
        super(output, RedReign.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blocks, ItemModelGenerators items) {
        registerBlocks(blocks);
        registerItems(items);
    }

    @Override
    protected Stream<? extends Holder<Item>> getKnownItems() {
        return Stream.of(
            InitItems.ALTAR_OF_RED_LADY,
            InitItems.BOUQUET_OF_POPPIES,
            InitItems.ROSE_QUARTZ_BLOCK,
            InitItems.CRAFTING_TABLE_OF_RED_QUEEN
        );
    }

    private void registerBlocks(BlockModelGenerators blocks) {
        // Fluids
        // Melted Beeswax
        Block beeswax = InitBlocks.MELTED_BEESWAX.get();
        Identifier beeswaxModel = PARTICLE_ONLY.create(beeswax,
            TextureMapping.particle(TextureMapping.getBlockTexture(beeswax, "_still")),
            blocks.modelOutput);
        blocks.blockStateOutput.accept(
            MultiVariantGenerator.dispatch(beeswax,
                BlockModelGenerators.variant(new Variant(beeswaxModel))));

        // Special
        // Altar of teh Red Lady
        Block altar = InitBlocks.ALTAR_OF_RED_LADY.get();
        TextureMapping altarTextures = TextureMapping.cubeBottomTop(altar);
        Identifier altarOff = ModelTemplates.CUBE_BOTTOM_TOP.create(altar, altarTextures, blocks.modelOutput);
        Identifier altarOn = ModelTemplates.CUBE_BOTTOM_TOP.createWithSuffix(altar, "_on",
            altarTextures
                    .copyAndUpdate(TextureSlot.TOP, TextureMapping.getBlockTexture(altar, "_top_on"))
                    .copyAndUpdate(TextureSlot.SIDE, TextureMapping.getBlockTexture(altar, "_side_on")),
            blocks.modelOutput);
        blocks.blockStateOutput.accept(
            MultiVariantGenerator.dispatch(altar)
                    .with(PropertyDispatch.initial(AltarOfRedLadyBlock.WORLD_IN_RED_REIGN)
                            .select(false, BlockModelGenerators.variant(new Variant(altarOff)))
                            .select(true, BlockModelGenerators.variant(new Variant(altarOn)))));
        // Crafting Table of the Red Queen
        Block table = InitBlocks.CRAFTING_TABLE_OF_RED_QUEEN.get();
        TextureMapping textures = new TextureMapping()
                .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(Blocks.BLACKSTONE))
                .put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(Blocks.BLACKSTONE))
                .put(TextureSlot.TOP, TextureMapping.getBlockTexture(table, "_top"))
                .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(table, "_side"));
        Identifier model = CRAFTING_TABLE_OF_RQ.create(table, textures, blocks.modelOutput);
        blocks.blockStateOutput.accept(
            MultiVariantGenerator.dispatch(table,
                BlockModelGenerators.variant(new Variant(model))));

        // Leaves
        blocks.createTrivialBlock(InitBlocks.BOUQUET_OF_POPPIES.get(), TexturedModel.LEAVES);

        // Simples
        blocks.createTrivialCube(InitBlocks.ROSE_QUARTZ_BLOCK.get());
    }

    private void registerItems(ItemModelGenerators items) { }
}
