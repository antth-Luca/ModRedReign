package io.github.anttluca.red_reign.fluids;


import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.init.InitBlocks;
import io.github.anttluca.red_reign.init.InitFluids;
import io.github.anttluca.red_reign.init.InitItems;
import io.github.anttluca.red_reign.init.InitMobEffects;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.environment.FogEnvironment;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ARGB;
import net.minecraft.util.RandomSource;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import org.joml.Vector4f;
import org.jspecify.annotations.Nullable;

import java.util.*;

public abstract class MeltedBeeswaxFluid extends BaseFlowingFluid {
    public static final float TICKS_TO_REGEN = 200.0F;
    public static final Map<UUID, Long> EXPOSURE_TICKS = new HashMap<>();
    public static final Map<UUID, Long> LAST_EXPOSURE_TICKS = new HashMap<>();

    private static final int fluidColor = 0xFFFADE29;

    protected MeltedBeeswaxFluid(Properties props) {
        super(props);
    }

    public static Properties getDefaultProperties() {
        return new Properties(
            InitFluids.MELTED_BEESWAX_TYPE,
            InitFluids.MELTED_BEESWAX,
            InitFluids.FLOWING_MELTED_BEESWAX
        )
            .bucket(InitItems.MELTED_BEESWAX_BUCKET)
            .block(InitBlocks.MELTED_BEESWAX)
            .explosionResistance(100.0F);
    }

    public static FluidType getType() {
        return new FluidType(
            FluidType.Properties.create()
                    .density(2000)
                    .viscosity(4500)
                    .temperature(1000)
                    .motionScale(0.01D)
                    .canPushEntity(false)
                    .canSwim(false)
                    .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)
                    .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)
                    .sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH)
        ) {
            // move() is based in LivingEntity.travelInLava()
            @Override
            public boolean move(LivingEntity entity, Vec3 input, double baseGravity) {
                entity.moveRelative(0.02F, input);
                entity.move(MoverType.SELF, entity.getDeltaMovement());

                boolean isFalling = entity.getDeltaMovement().y <= (double) 0.0F;
                double oldY = entity.getY();
                if (entity.getFluidHeight(InitFluids.MELTED_BEESWAX_TYPE.get()) <= entity.getFluidJumpThreshold()) {
                    entity.setDeltaMovement(entity.getDeltaMovement().multiply((double) 0.5F, (double) 0.8F, (double) 0.5F));
                    Vec3 movement = entity.getFluidFallingAdjustedMovement(baseGravity, isFalling, entity.getDeltaMovement());
                    entity.setDeltaMovement(movement);
                } else {
                    entity.setDeltaMovement(entity.getDeltaMovement().scale((double) 0.5F));
                }

                if (baseGravity != (double) 0.0F) {
                    entity.setDeltaMovement(entity.getDeltaMovement().add((double) 0.0F, -baseGravity / (double) 4.0F, (double) 0.0F));
                }

                // LivingEntity.jumpOutOfFluid() starts
                Vec3 movement = entity.getDeltaMovement();
                if (entity.horizontalCollision && entity.isFree(movement.x, movement.y + (double) 0.6F - entity.getY() + oldY, movement.z)) {
                    entity.setDeltaMovement(movement.x, (double)0.3F, movement.z);
                }
                // LivingEntity.jumpOutOfFluid() end

                return true;
            }
        };
    }

    public static IClientFluidTypeExtensions getTypeExtension() {
        return new IClientFluidTypeExtensions() {
            @Override
            public void modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector4f fluidFogColor) {
                fluidFogColor.set(
                    ARGB.redFloat(fluidColor),
                    ARGB.greenFloat(fluidColor),
                    ARGB.blueFloat(fluidColor)
                );
            }

            @Override
            public void modifyFogRender(Camera camera, @Nullable FogEnvironment environment, float renderDistance, float partialTick, FogData fog) {
                fog.environmentalStart = camera.attributeProbe().getValue(EnvironmentAttributes.WATER_FOG_START_DISTANCE, partialTick);
                fog.environmentalEnd = camera.attributeProbe().getValue(EnvironmentAttributes.WATER_FOG_END_DISTANCE, partialTick);

                fog.skyEnd = fog.environmentalEnd;
                fog.cloudEnd = fog.environmentalEnd;
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

    @Override
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

    @Override
    public int getDropOff(LevelReader level) {
        return isFastMelted(level) ? 1 : 2;
    }

    @Override
    public boolean canBeReplacedWith(FluidState state, BlockGetter level, BlockPos pos, Fluid other, Direction direction) {
        return state.getHeight(level, pos) >= 0.44444445F && other.is(FluidTags.WATER);
    }

    @Override
    public int getTickDelay(LevelReader level) {
        return isFastMelted(level) ? 10 : 30;
    }

    @Override
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

    @Override
    protected void spreadTo(LevelAccessor level, BlockPos pos, BlockState state, Direction direction, FluidState target) {
        if (target.is(FluidTags.WATER)) {
            level.setBlock(pos, EventHooks.fireFluidPlaceBlockEvent(level, pos, pos, Blocks.HONEYCOMB_BLOCK.defaultBlockState()), 3);

            this.fizz(level, pos);
            return;
        }

        super.spreadTo(level, pos, state, direction, target);
    }

    @Override
    protected boolean isRandomlyTicking() {
        return true;
    }

    private static boolean isFastMelted(LevelReader level) {
        return (Boolean) level.environmentAttributes().getDimensionValue(EnvironmentAttributes.FAST_LAVA);
    }

    @Override
    public BlockState createLegacyBlock(FluidState fluidState) {
        return (BlockState) InitBlocks.MELTED_BEESWAX.get().defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(fluidState));
    }

    @Override
    public boolean isSame(Fluid other) {
        return other == InitFluids.MELTED_BEESWAX.get() || other == InitFluids.FLOWING_MELTED_BEESWAX.get();
    }

    @Override
    public int getSlopeFindDistance(LevelReader level) {
        return isFastMelted(level) ? 4 : 2;
    }

    @Override
    protected void entityInside(Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier) {
        effectApplier.apply(InsideBlockEffectType.CLEAR_FREEZE);

        if (!(entity instanceof LivingEntity living)) return;

        if (living.hasEffect(InitMobEffects.SENSITIVE_SKIN)) {
            effectApplier.apply(InsideBlockEffectType.LAVA_IGNITE);
            effectApplier.runAfter(InsideBlockEffectType.LAVA_IGNITE, Entity::lavaHurt);
        } else {
            effectApplier.apply(InsideBlockEffectType.EXTINGUISH);

            if (level.getBlockState(pos).getFluidState().isSource()) {
                UUID uuid = living.getUUID();
                long gameTick = level.getGameTime();

                long start = EXPOSURE_TICKS.computeIfAbsent(uuid, key -> gameTick);
                LAST_EXPOSURE_TICKS.put(uuid, gameTick);

                if ((gameTick - start) >= TICKS_TO_REGEN) {
                    living.heal(living.getMaxHealth() / 2);
                    living.addEffect(new MobEffectInstance(InitMobEffects.SENSITIVE_SKIN, 6000));

                    EXPOSURE_TICKS.remove(uuid);
                    LAST_EXPOSURE_TICKS.remove(uuid);
                }
            }
        }
    }

    @Override
    protected void beforeDestroyingBlock(LevelAccessor level, BlockPos pos, BlockState state) {
        this.fizz(level, pos);
        BlockEntity blockEntity = state.hasBlockEntity() ? level.getBlockEntity(pos) : null;
        Block.dropResources(state, level, pos, blockEntity);
    }

    // Subclasses
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
