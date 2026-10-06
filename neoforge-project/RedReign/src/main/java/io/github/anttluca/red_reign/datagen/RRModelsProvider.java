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
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.properties.numeric.Time;
import net.minecraft.client.renderer.item.properties.numeric.UseDuration;
import net.minecraft.client.renderer.item.properties.select.ContextDimension;
import net.minecraft.client.renderer.item.properties.select.DisplayContext;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.client.model.generators.template.ExtendedModelTemplateBuilder;

import java.util.List;
import java.util.Optional;

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

    private void registerItems(ItemModelGenerators items) {
        // Special
        // Chalice of the Bloodblade
        Item chalice = InitItems.CHALICE_OF_THE_BLOODBLADE.get();
        ItemModel.Unbaked chaliceBlade = ItemModelUtils.plainModel(
            items.createFlatItemModel(chalice, "", ModelTemplates.FLAT_ITEM));
        ItemModel.Unbaked chaliceNormal = ItemModelUtils.plainModel(
            items.createFlatItemModel(chalice, "_hand", ModelTemplates.FLAT_HANDHELD_ITEM));
        items.itemModelOutput.accept(chalice,
            ItemModelUtils.select(new DisplayContext(), chaliceBlade,
                ItemModelUtils.when(
                    List.of(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND,
                        ItemDisplayContext.THIRD_PERSON_RIGHT_HAND
                    ), chaliceNormal)));
        // Purification Spell
        Item spell = InitItems.PURIFICATION_SPELL.get();
        ItemModel.Unbaked spellBase = ItemModelUtils.plainModel(
            items.createFlatItemModel(spell, "", SPELL));
        ItemModel.Unbaked charge0 = ItemModelUtils.plainModel(
            items.createFlatItemModel(spell, "_charge_0", SPELL));
        ItemModel.Unbaked charge1 = ItemModelUtils.plainModel(
            items.createFlatItemModel(spell, "_charge_1", SPELL));
        ItemModel.Unbaked charge2 = ItemModelUtils.plainModel(
            items.createFlatItemModel(spell, "_charge_2", SPELL));
        items.itemModelOutput.accept(spell,
            ItemModelUtils.conditional(
                ItemModelUtils.isUsingItem(),
                ItemModelUtils.rangeSelect(new UseDuration(false), 0.05f, spellBase,
                    ItemModelUtils.override(charge0, 0.25f),
                    ItemModelUtils.override(charge1, 0.5f),
                    ItemModelUtils.override(charge2, 0.75f),
                    ItemModelUtils.override(spellBase, 1.0f)
                ), spellBase));
        // Vampire Rose
        Item rose = InitItems.VAMPIRE_ROSE.get();
        ItemModel.Unbaked day = ItemModelUtils.plainModel(
            items.createFlatItemModel(rose, "_day", ModelTemplates.FLAT_ITEM));
        ItemModel.Unbaked night = ItemModelUtils.plainModel(
            items.createFlatItemModel(rose, "_night", ModelTemplates.FLAT_ITEM));
        ItemModel.Unbaked overworld = ItemModelUtils.rangeSelect(
            new Time(true, Time.TimeSource.DAYTIME), 64f, day,
            ItemModelUtils.override(night, 16f),
            ItemModelUtils.override(day, 48f));
        items.itemModelOutput.accept(rose,
            ItemModelUtils.select(new ContextDimension(), night,
                ItemModelUtils.when(Level.OVERWORLD, overworld)));

        // Transformed
        items.generateFlatItem(InitItems.ALLAY_CAGE.get(), KEYCHAIN);
        items.generateFlatItem(InitItems.EARTHLY_ICHOR.get(), CYLINDRICAL);
        items.generateFlatItem(InitItems.ETHEREAL_PROTECTION.get(), KEYCHAIN);
        items.generateFlatItem(InitItems.FINAL_BLESSING.get(), KEYCHAIN);
        items.generateFlatItem(InitItems.HEALING_BULB.get(), CYLINDRICAL);

        // Simples
        items.generateFlatItem(InitItems.AMETHYST_RESONATOR.get(), ModelTemplates.FLAT_ITEM);
        items.generateFlatItem(InitItems.BLADE_OF_THE_BLOODSTAINED_LAMENT.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        items.generateFlatItem(InitItems.CATALYST_OF_EVERYTHING.get(), ModelTemplates.FLAT_ITEM);
        items.generateFlatItem(InitItems.CORAL_GAUNTLET.get(), ModelTemplates.FLAT_ITEM);
        items.generateFlatItem(InitItems.CRYSTALLIZED_TEAR.get(), ModelTemplates.FLAT_ITEM);
        items.generateFlatItem(InitItems.DAISY_SILVER_METEOR.get(), ModelTemplates.FLAT_ITEM);
        items.generateFlatItem(InitItems.HONEYCOMB_BUCKET.get(), ModelTemplates.FLAT_ITEM);
        items.generateFlatItem(InitItems.INTRINSIC_MECHANISM.get(), ModelTemplates.FLAT_ITEM);
        items.generateFlatItem(InitItems.LAZULI_PROVIDENCE.get(), ModelTemplates.FLAT_ITEM);
        items.generateFlatItem(InitItems.MELTED_BEESWAX_BUCKET.get(), ModelTemplates.FLAT_ITEM);
        items.generateFlatItem(InitItems.PALE_POPPY.get(), ModelTemplates.FLAT_ITEM);
        items.generateFlatItem(InitItems.PINK_EMBRYO.get(), ModelTemplates.FLAT_ITEM);
        items.generateFlatItem(InitItems.RED_IDENTITY.get(), ModelTemplates.FLAT_ITEM);
        items.generateFlatItem(InitItems.RED_SIGNET.get(), ModelTemplates.FLAT_ITEM);
        items.generateFlatItem(InitItems.REDSTONE_CRYSTAL.get(), ModelTemplates.FLAT_ITEM);
        items.generateFlatItem(InitItems.ROSE_ANCHOR.get(), ModelTemplates.FLAT_ITEM);
        items.generateFlatItem(InitItems.TOTEM_OF_THE_RED_QUEEN.get(), ModelTemplates.FLAT_ITEM);
        items.generateFlatItem(InitItems.VORTEX_PEARL.get(), ModelTemplates.FLAT_ITEM);
    }
}
