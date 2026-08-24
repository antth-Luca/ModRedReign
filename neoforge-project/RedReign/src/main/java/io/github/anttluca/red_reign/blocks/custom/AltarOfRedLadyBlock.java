package io.github.anttluca.red_reign.blocks.custom;

import io.github.anttluca.red_reign.init.InitBlocks;
import io.github.anttluca.red_reign.init.InitTriggers;
import io.github.anttluca.red_reign.world.data.RedReignWorldData;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;

public class AltarOfRedLadyBlock extends Block {
    public static final BooleanProperty WORLD_IN_RED_REIGN = BooleanProperty.create("world_in_red_reign");

    public AltarOfRedLadyBlock(Properties props) {
        super(props
            .mapColor(MapColor.COLOR_BLACK)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .requiresCorrectToolForDrops()
            .strength(1.5F, 6.0F));
        this.registerDefaultState(this.getStateDefinition().any().setValue(
                WORLD_IN_RED_REIGN, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WORLD_IN_RED_REIGN);
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);

        if (!level.isClientSide()
            && !state.is(oldState.getBlock())) {
                boolean worldIsRR = RedReignWorldData.get(level, Level.OVERWORLD).isActive();
                if (worldIsRR != state.getValue(WORLD_IN_RED_REIGN)) {
                    level.setBlock(pos, state.setValue(WORLD_IN_RED_REIGN, worldIsRR), 3);
                }
        }
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, @Nullable Orientation orientation, boolean movedByPiston) {
        if (level.isClientSide()) return;

        RedReignWorldData rrWorldData = RedReignWorldData.get(level, Level.OVERWORLD);

        BlockState aboveBlock = level.getBlockState(pos.above());
        if (aboveBlock.is(InitBlocks.BOUQUET_OF_POPPIES)) {
            AABB searchArea = new AABB(pos).inflate(4);
            for (ServerPlayer serverPlayer : level.getEntitiesOfClass(ServerPlayer.class, searchArea)) {
                InitTriggers.ACTIVATE_ALTAR_OF_RED_LADY.get().trigger(serverPlayer);
            }

            if (!rrWorldData.isActive()) {
                rrWorldData.activate((ServerLevel) level);
            }
        }

        if (!state.getValue(WORLD_IN_RED_REIGN)) {
            level.setBlock(pos, state.setValue(WORLD_IN_RED_REIGN, rrWorldData.isActive()), 3);
        }

        super.neighborChanged(state, level, pos, block, orientation, movedByPiston);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide()) return InteractionResult.SUCCESS;

        boolean worldIsRR = RedReignWorldData.get(level, Level.OVERWORLD).isActive();
        if (worldIsRR != state.getValue(WORLD_IN_RED_REIGN)) {
            level.setBlock(pos, state.setValue(WORLD_IN_RED_REIGN, worldIsRR), 3);
        }

        return super.useWithoutItem(state, level, pos, player, hitResult);
    }
}
