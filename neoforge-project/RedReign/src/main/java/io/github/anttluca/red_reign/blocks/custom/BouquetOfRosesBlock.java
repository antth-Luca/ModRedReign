package io.github.anttluca.red_reign.blocks.custom;

import com.mojang.serialization.MapCodec;
import io.github.anttluca.red_reign.RedReign;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.common.IShearable;

public class BouquetOfRosesBlock extends FallingBlock implements BonemealableBlock, IShearable {
    public static final MapCodec<BouquetOfRosesBlock> CODEC = simpleCodec(BouquetOfRosesBlock::new);
    public static final BooleanProperty AFFECTED_BY_GRAVITY = BooleanProperty.create("affected_by_gravity");
    public static final ResourceKey<LootTable> BOUQUET_OF_ROSES_LOOT_TABLE = ResourceKey.create(
            Registries.LOOT_TABLE, Identifier.fromNamespaceAndPath(RedReign.MODID, "blocks/bouquet_of_roses.json"));

    public BouquetOfRosesBlock(Properties props) {
        super(props
            .mapColor(MapColor.PLANT)
            .strength(0.2F)
            .sound(SoundType.GRASS)
            .noOcclusion()
            .isSuffocating(BouquetOfRosesBlock::never)
            .isViewBlocking(BouquetOfRosesBlock::never)
            .ignitedByLava()
            .pushReaction(PushReaction.DESTROY)
            .isRedstoneConductor(BouquetOfRosesBlock::never));
        this.registerDefaultState(this.getStateDefinition().any().setValue(
                AFFECTED_BY_GRAVITY, false));
    }

    @Override
    protected MapCodec<? extends FallingBlock> codec() { return CODEC; }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AFFECTED_BY_GRAVITY);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide()) return InteractionResult.SUCCESS;

        if (isFree(level.getBlockState(pos.below()))) {
                BlockState newState = state.setValue(AFFECTED_BY_GRAVITY, true);
                level.setBlock(pos, newState, 3);

                level.scheduleTick(pos, this, this.getDelayAfterPlace());
                return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(AFFECTED_BY_GRAVITY)) super.tick(state, level, pos, random);
    }

    @Override
    public int getDustColor(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return 0;
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction directionToNeighbour, BlockPos neighbourPos, BlockState neighbourState, RandomSource random) {
        if (state.getValue(AFFECTED_BY_GRAVITY))
            return super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);

        return state;
    }

    @Override
    public void onLand(Level level, BlockPos pos, BlockState state, BlockState replacedBlock, FallingBlockEntity entity) {
        if (level.isClientSide())
            level.setBlock(pos, state.setValue(AFFECTED_BY_GRAVITY, false), 3);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
        popResource(serverLevel, blockPos, new ItemStack(this));
    }

    private static boolean never(BlockState state, BlockGetter blockGetter, BlockPos blockPos) {
        return false;
    }
}
