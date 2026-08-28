package io.github.anttluca.red_reign.fluids;


import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.init.InitBlocks;
import io.github.anttluca.red_reign.init.InitFluids;
import io.github.anttluca.red_reign.init.InitItems;
import io.github.anttluca.red_reign.init.InitMobEffects;
import io.github.anttluca.red_reign.tags.RRFluidTags;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.InsideBlockEffectType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import org.joml.Vector4f;

import java.util.Optional;

public abstract class MeltedBeeswaxFluid extends BaseFlowingFluid {
    public static final float TICKS_TO_REGEN = 120.0F;

    protected MeltedBeeswaxFluid(Properties props) {
        super(props
                .bucket(InitItems.MELTED_BEESWAX_BUCKET)
                .block(InitBlocks.MELTED_BEESWAX)
                .explosionResistance(100.0F)
        );
    }

    public static Properties getDefaultProperties() {
        return new Properties(
            InitFluids.MELTED_BEESWAX_TYPE,
            InitFluids.MELTED_BEESWAX,
            InitFluids.FLOWING_MELTED_BEESWAX
        );
    }

    public static FluidType getType() {
        return new FluidType(FluidType.Properties.create()
                .density(1000)
                .viscosity(600)
                .temperature(300)
        );
    }

    public static IClientFluidTypeExtensions getExtension() {
        return new IClientFluidTypeExtensions() {
            @Override
            public void modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector4f fluidFogColor) {
                fluidFogColor.set(0.83f, 0.16f, 0.16f);
                IClientFluidTypeExtensions.super.modifyFogColor(camera, partialTick, level, renderDistance, darkenWorldAmount, fluidFogColor);
            }
        };
    }

    public static FluidModel.Unbaked getModelUnbaked() {
        return new FluidModel.Unbaked(
                new Material(Identifier.fromNamespaceAndPath(RedReign.MODID, "block/melted_beeswax_still")),
                new Material(Identifier.fromNamespaceAndPath(RedReign.MODID, "block/melted_beeswax_flow")),
                null, null
        );
    }

    public Optional<SoundEvent> getPickupSound() {
        return Optional.of(SoundEvents.BUCKET_FILL_LAVA);
    }

    public void animateTick(Level level, BlockPos pos, FluidState fluidState, RandomSource random) {
        BlockPos above = pos.above();
        if (level.getBlockState(above).isAir() && !level.getBlockState(above).isSolidRender()) {
            if (random.nextInt(100) == 0) {
                double xx = (double)pos.getX() + random.nextDouble();
                double yy = (double)pos.getY() + (double)1.0F;
                double zz = (double)pos.getZ() + random.nextDouble();
                level.playLocalSound(xx, yy, zz, SoundEvents.LAVA_POP, SoundSource.AMBIENT, 0.2F + random.nextFloat() * 0.2F, 0.9F + random.nextFloat() * 0.15F, false);
            }
        }
    }

    public int getDropOff(LevelReader level) {
        return isFastMelted(level) ? 1 : 2;
    }

    public boolean canBeReplacedWith(FluidState state, BlockGetter level, BlockPos pos, Fluid other, Direction direction) {
        return state.getHeight(level, pos) >= 0.44444445F && other.is(FluidTags.WATER);
    }

    public int getTickDelay(LevelReader level) {
        return isFastMelted(level) ? 10 : 30;
    }

    public int getSpreadDelay(Level level, BlockPos pos, FluidState oldFluidState, FluidState newFluidState) {
        int result = this.getTickDelay(level);
        if (!oldFluidState.isEmpty() && !newFluidState.isEmpty() && !(Boolean)oldFluidState.getValue(FALLING) && !(Boolean)newFluidState.getValue(FALLING) && newFluidState.getHeight(level, pos) > oldFluidState.getHeight(level, pos) && level.getRandom().nextInt(4) != 0) {
            result *= 4;
        }

        return result;
    }

    private void fizz(LevelAccessor level, BlockPos pos) {
        level.levelEvent(1501, pos, 0);
    }

    protected void spreadTo(LevelAccessor level, BlockPos pos, BlockState state, Direction direction, FluidState target) {
        if (direction == Direction.DOWN) {
            FluidState fluidState = level.getFluidState(pos);
            if (this.is(RRFluidTags.MELTED_BEESWAX) && fluidState.is(FluidTags.WATER)) {
                if (state.getBlock() instanceof LiquidBlock) {
                    level.setBlock(pos, EventHooks.fireFluidPlaceBlockEvent(level, pos, pos, Blocks.HONEYCOMB_BLOCK.defaultBlockState()), 3);
                }

                this.fizz(level, pos);
                return;
            }
        }

        super.spreadTo(level, pos, state, direction, target);
    }

    protected boolean isRandomlyTicking() {
        return true;
    }

    private static boolean isFastMelted(LevelReader level) {
        return (Boolean)level.environmentAttributes().getDimensionValue(EnvironmentAttributes.FAST_LAVA);
    }

    public BlockState createLegacyBlock(FluidState fluidState) {
        return (BlockState) InitBlocks.MELTED_BEESWAX.get().defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(fluidState));
    }

    public boolean isSame(Fluid other) {
        return other == InitFluids.MELTED_BEESWAX.get() || other == InitFluids.FLOWING_MELTED_BEESWAX.get();
    }

    public int getSlopeFindDistance(LevelReader level) {
        return isFastMelted(level) ? 4 : 2;
    }

    protected void entityInside(Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier) {
        effectApplier.apply(InsideBlockEffectType.EXTINGUISH);
        effectApplier.apply(InsideBlockEffectType.CLEAR_FREEZE);

        if (entity instanceof LivingEntity living
            && living.hasEffect(InitMobEffects.SENSITIVE_SKIN)) {
                effectApplier.apply(InsideBlockEffectType.LAVA_IGNITE);
                effectApplier.runAfter(InsideBlockEffectType.LAVA_IGNITE, Entity::lavaHurt);
        }
    }

    protected void beforeDestroyingBlock(LevelAccessor level, BlockPos pos, BlockState state) {
        this.fizz(level, pos);
        BlockEntity blockEntity = state.hasBlockEntity() ? level.getBlockEntity(pos) : null;
        Block.dropResources(state, level, pos, blockEntity);
    }

    // Classes
    public static class Flowing extends MeltedBeeswaxFluid {
        public Flowing(Properties props) {
            super(props);
        }

        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(new Property[]{LEVEL});
        }

        @Override
        public boolean isSource(FluidState fluidState) {
            return false;
        }

        @Override
        public int getAmount(FluidState fluidState) {
            return (Integer) fluidState.getValue(LEVEL);
        }
    }

    public static class Source extends MeltedBeeswaxFluid {
        public Source(Properties props) {
            super(props);
        }

        @Override
        public boolean isSource(FluidState fluidState) {
            return true;
        }

        @Override
        public int getAmount(FluidState fluidState) {
            return 8;
        }
    }
}
