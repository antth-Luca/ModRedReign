package io.github.anttluca.red_reign.datagen;

import com.mojang.datafixers.util.Pair;
import io.github.anttluca.red_reign.utils.RRResourceKeyUtils.WorldGenKeys;
import io.github.anttluca.red_reign.world.processors.RedLadyRuinsProcessor;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

import java.util.List;
import java.util.Map;

public class RRWorldGenProvider {
    public static void pools(BootstrapContext<StructureTemplatePool> ctx) {
        HolderGetter<StructureTemplatePool> pools = ctx.lookup(Registries.TEMPLATE_POOL);

        Holder<StructureProcessorList> processors = Holder.direct(
                new StructureProcessorList(List.of(new RedLadyRuinsProcessor())));

        ctx.register(
            WorldGenKeys.RUINS_POOL,
            new StructureTemplatePool(
                pools.getOrThrow(Pools.EMPTY),
                List.of(Pair.of(
                    StructurePoolElement.legacy("red_reign:red_lady_ruins", processors)
                            .apply(StructureTemplatePool.Projection.RIGID),
                    1
                ))
            )
        );
    }

    public static void structures(BootstrapContext<Structure> ctx) {
        HolderGetter<Biome> biomes = ctx.lookup(Registries.BIOME);
        HolderGetter<StructureTemplatePool> pools = ctx.lookup(Registries.TEMPLATE_POOL);

        ctx.register(
            WorldGenKeys.RUINS,
            new JigsawStructure(
                new Structure.StructureSettings(
                    biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                    Map.of(),
                    GenerationStep.Decoration.SURFACE_STRUCTURES,
                    TerrainAdjustment.NONE
                ),
                pools.getOrThrow(WorldGenKeys.RUINS_POOL),
                13,
                ConstantHeight.of(VerticalAnchor.absolute(0)),
                false,
                Heightmap.Types.OCEAN_FLOOR_WG
            )
        );
    }

    public static void structureSets(BootstrapContext<StructureSet> ctx) {
        HolderGetter<Structure> structures = ctx.lookup(Registries.STRUCTURE);

        ctx.register(WorldGenKeys.RUINS_SET, new StructureSet(
                List.of(StructureSet.entry(structures.getOrThrow(WorldGenKeys.RUINS))),
                new RandomSpreadStructurePlacement(32, 8, RandomSpreadType.LINEAR, 1743110324)));
    }
}
