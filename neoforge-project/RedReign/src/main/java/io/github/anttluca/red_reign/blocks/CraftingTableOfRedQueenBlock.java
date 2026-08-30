package io.github.anttluca.red_reign.blocks;

import com.mojang.serialization.MapCodec;
import io.github.anttluca.red_reign.blocks.entity.CraftingTableOfRedQueenBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.EnchantingTableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

public class CraftingTableOfRedQueenBlock extends BaseEntityBlock {
    private static final VoxelShape SHAPE = Block.column(16.0, 0.0, 12.0);

    public static final MapCodec<CraftingTableOfRedQueenBlock> CODEC = simpleCodec(CraftingTableOfRedQueenBlock::new);

    public CraftingTableOfRedQueenBlock(Properties props) {
        super(props
                .mapColor(MapColor.COLOR_LIGHT_GRAY)
                .instrument(NoteBlockInstrument.BASEDRUM)
                .requiresCorrectToolForDrops()
                .lightLevel(statex -> 9)
                .strength(5.0F, 1200.0F)
        );
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new CraftingTableOfRedQueenBlockEntity(blockPos, blockState);
    }

//    @Override
//    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
//        if (!level.isClientSide()) {
//            player.openMenu(state.getMenuProvider(level, pos));
//        }
//
//        return InteractionResult.SUCCESS;
//    }
//
//    @Override
//    protected @Nullable MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
//        if (level.getBlockEntity(pos) instanceof EnchantingTableBlockEntity enchantingTable) {
//            Component title = enchantingTable.getDisplayName();
//            return new SimpleMenuProvider(
//                    (containerId, inventory, player) -> new EnchantmentMenu(containerId, inventory, ContainerLevelAccess.create(level, pos)), title
//            );
//        } else {
//            return null;
//        }
//    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
    }
}
