package io.github.anttluca.red_reign.world.processors;

import com.mojang.serialization.MapCodec;
import io.github.anttluca.red_reign.blocks.custom.AltarOfRedLadyBlock;
import io.github.anttluca.red_reign.init.InitStructureProcessors;
import io.github.anttluca.red_reign.world.data.RedReignWorldData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jspecify.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RedLadyRuinsProcessor extends StructureProcessor {
    public static final MapCodec<RedLadyRuinsProcessor> CODEC = MapCodec.unit(RedLadyRuinsProcessor::new);

    private static final int MAX_DEPTH = 15;
    private static final BlockState FILLER_STATE = Blocks.BLACKSTONE.defaultBlockState();

    private RedLadyRuinsProcessor() { }

    @Override
    public StructureTemplate.@Nullable StructureBlockInfo process(LevelReader reader, BlockPos targetPosition, BlockPos referencePos, StructureTemplate.StructureBlockInfo originalBlockInfo, StructureTemplate.StructureBlockInfo processedBlockInfo, StructurePlaceSettings settings, @Nullable StructureTemplate template) {
        ServerLevel serverLevel = null;
        if (reader instanceof ServerLevel directLevel) {
            serverLevel = directLevel;
        } else if (reader instanceof WorldGenLevel worldGenLevel) {
            serverLevel = worldGenLevel.getLevel();
        }

        if (serverLevel == null) return processedBlockInfo;

        BlockState origState = originalBlockInfo.state();
        if (origState.getBlock() instanceof AltarOfRedLadyBlock) {
            boolean worldIsRR = RedReignWorldData.get(serverLevel, Level.OVERWORLD).isActive();
            BlockState newState = processedBlockInfo.state();

            if (newState.hasProperty(AltarOfRedLadyBlock.WORLD_IN_RED_REIGN)) {
                return new StructureTemplate.StructureBlockInfo(
                        processedBlockInfo.pos(),
                        newState.setValue(AltarOfRedLadyBlock.WORLD_IN_RED_REIGN, worldIsRR),
                        processedBlockInfo.nbt()
                );
            }
        }

        return processedBlockInfo;
    }

    @Override
    public List<StructureTemplate.StructureBlockInfo> finalizeProcessing(ServerLevelAccessor level, BlockPos targetPosition, BlockPos referencePos, List<StructureTemplate.StructureBlockInfo> originalBlockInfos, List<StructureTemplate.StructureBlockInfo> processedBlockInfos, StructurePlaceSettings settings) {
        Set<BlockPos> existingPositions = new HashSet<>();

        for (StructureTemplate.StructureBlockInfo info : processedBlockInfos) {
            existingPositions.add(info.pos());
        }

        Set<BlockPos> foundationPositions = new HashSet<>();

        for (int i = 0; i < originalBlockInfos.size(); i++) {
            StructureTemplate.StructureBlockInfo original = originalBlockInfos.get(i);
            StructureTemplate.StructureBlockInfo processed = processedBlockInfos.get(i);

            if (original.pos().getY() != 0
                || original.state().isAir()
                    || original.state().is(BlockTags.BUTTONS)) {
                        continue;
            }

            BlockPos.MutableBlockPos pos = processed.pos().mutable().move(Direction.DOWN);

            int depth = 0;
            while (depth < MAX_DEPTH) {
                BlockPos absolutePos = pos.immutable();

                if (existingPositions.contains(absolutePos)
                    || foundationPositions.contains(absolutePos)) {
                        break;
                }

                BlockState terrainState = level.getBlockState(absolutePos);

                if (!terrainState.is(BlockTags.REPLACEABLE)) {
                    break;
                }

                foundationPositions.add(absolutePos);

                pos.move(Direction.DOWN);
                depth++;
            }
        }

        for (BlockPos pos : foundationPositions) {
            processedBlockInfos.add(
                    new StructureTemplate.StructureBlockInfo(
                            pos,
                            FILLER_STATE,
                            null
                    )
            );
        }

        return processedBlockInfos;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return InitStructureProcessors.RED_LADY_RUINS.get();
    }
}
