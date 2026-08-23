package io.github.anttluca.red_reign.world.processors;

import com.mojang.serialization.MapCodec;
import io.github.anttluca.red_reign.blocks.custom.AltarOfRedLadyBlock;
import io.github.anttluca.red_reign.init.InitStructureProcessors;
import io.github.anttluca.red_reign.world.data.RedReignWorldData;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jspecify.annotations.Nullable;

public class RedLadyRuinsProcessor extends StructureProcessor {
    public static final MapCodec<RedLadyRuinsProcessor> CODEC = MapCodec.unit(RedLadyRuinsProcessor::new);

    private RedLadyRuinsProcessor() { }

    @Override
    public StructureTemplate.@Nullable StructureBlockInfo process(LevelReader reader, BlockPos targetPosition, BlockPos referencePos, StructureTemplate.StructureBlockInfo originalBlockInfo, StructureTemplate.StructureBlockInfo processedBlockInfo, StructurePlaceSettings settings, @Nullable StructureTemplate template) {
        BlockState origState = originalBlockInfo.state();
        if (origState.getBlock() instanceof AltarOfRedLadyBlock) {
            ServerLevel serverLevel = null;
            if (reader instanceof ServerLevel directLevel) {
                serverLevel = directLevel;
            } else if (reader instanceof WorldGenLevel worldGenLevel) {
                serverLevel = worldGenLevel.getLevel();
            }

            if (serverLevel != null) {
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
        }

        return processedBlockInfo;
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return InitStructureProcessors.RED_LADY_RUINS.get();
    }
}
