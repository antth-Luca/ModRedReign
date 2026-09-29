package io.github.anttluca.red_reign.entities.red_queens_avatar;

import io.github.anttluca.red_reign.init.InitItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.hurtingprojectile.WitherSkull;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.level.gamerules.GameRules;
import net.neoforged.neoforge.event.EventHooks;

public class RedQueensAvatar extends Monster implements RangedAttackMob {
    private static final EntityDataAccessor<Integer> DATA_TARGET = SynchedEntityData.defineId(RedQueensAvatar.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_DEATH_TICKS = SynchedEntityData.defineId(RedQueensAvatar.class, EntityDataSerializers.INT);
    private static final TargetingConditions.Selector LIVING_ENTITY_SELECTOR = (target, _) ->
            !target.is(EntityTypeTags.UNDEAD) && target.attackable();
    private static final TargetingConditions TARGETING_CONDITIONS = TargetingConditions.forCombat().range(20.0).selector(LIVING_ENTITY_SELECTOR);

    private final ServerBossEvent bossEvent = Util.make(
        new ServerBossEvent(
            Mth.createInsecureUUID(this.random),
            this.getDisplayName(),
            BossEvent.BossBarColor.RED,
            BossEvent.BossBarOverlay.PROGRESS
        ),
        e -> e.setDarkenScreen(true)
    );

    private int nextHeadUpdate;
    private int idleHeadUpdates;
    private int destroyBlocksTick;

    public RedQueensAvatar(EntityType<? extends RedQueensAvatar> type, Level level) {
        super(type, level);
        this.moveControl = new FlyingMoveControl(this, 10, false);
        this.setHealth(this.getMaxHealth());
        this.xpReward = 250;
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        FlyingPathNavigation flyingPathNavigation = new FlyingPathNavigation(this, level);
        flyingPathNavigation.setCanOpenDoors(false);
        flyingPathNavigation.setCanFloat(true);
        return flyingPathNavigation;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new RedQueensAvatar.DoNothingGoal());
        this.goalSelector.addGoal(2, new RangedAttackGoal(this, 1.0, 40, 20.0F));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomFlyingGoal(this, 1.0));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 0, false, false, LIVING_ENTITY_SELECTOR));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder entityData) {
        super.defineSynchedData(entityData);
        entityData.define(DATA_TARGET, 0);
        entityData.define(DATA_DEATH_TICKS, 0);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        if (this.hasCustomName()) {
            this.bossEvent.setName(this.getDisplayName());
        }
    }

    @Override
    public void setCustomName(@Nullable Component name) {
        super.setCustomName(name);
        this.bossEvent.setName(this.getDisplayName());
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.WITHER_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.WITHER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.WITHER_DEATH;
    }

    @Override
    public void aiStep() {
        Vec3 deltaMovement = this.getDeltaMovement().multiply(1.0, 0.6, 1.0);
        if (!this.level().isClientSide() && this.getAlternativeTarget() > 0) {
            Entity entity = this.level().getEntity(this.getAlternativeTarget());
            if (entity != null) {
                double yd = deltaMovement.y;
                if (this.getY() < entity.getY() && this.getY() < entity.getY() + 5.0) {
                    yd = Math.max(0.0, yd);
                    yd += 0.3 - yd * 0.6F;
                }

                deltaMovement = new Vec3(deltaMovement.x, yd, deltaMovement.z);
                Vec3 delta = new Vec3(entity.getX() - this.getX(), 0.0, entity.getZ() - this.getZ());
                if (delta.horizontalDistanceSqr() > 9.0) {
                    Vec3 scale = delta.normalize();
                    deltaMovement = deltaMovement.add(scale.x * 0.3 - deltaMovement.x * 0.6, 0.0, scale.z * 0.3 - deltaMovement.z * 0.6);
                }
            }
        }

        this.setDeltaMovement(deltaMovement);
        if (deltaMovement.horizontalDistanceSqr() > 0.05) {
            this.setYRot((float)Mth.atan2(deltaMovement.z, deltaMovement.x) * (180.0F / (float)Math.PI) - 90.0F);
        }

        super.aiStep();

        double hx = this.getHeadX();
        double hy = this.getHeadY();
        double hz = this.getHeadZ();
        float radius = 0.3F * this.getScale();
        this.level()
                .addParticle(
                        ParticleTypes.SMOKE,
                        hx + this.random.nextGaussian() * radius,
                        hy + this.random.nextGaussian() * radius,
                        hz + this.random.nextGaussian() * radius,
                        0.0,
                        0.0,
                        0.0
                );
        if (this.level().getRandom().nextInt(4) == 0) {
            this.level()
                    .addParticle(
                            ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT, 0.7F, 0.7F, 0.5F),
                            hx + this.random.nextGaussian() * radius,
                            hy + this.random.nextGaussian() * radius,
                            hz + this.random.nextGaussian() * radius,
                            0.0,
                            0.0,
                            0.0
                    );
        }
    }

    @Override
    protected void customServerAiStep(ServerLevel level) {
        super.customServerAiStep(level);

        if (this.tickCount >= this.nextHeadUpdate) {
            this.nextHeadUpdate = this.tickCount + 10 + this.random.nextInt(10);
            if ((level.getDifficulty() == Difficulty.NORMAL || level.getDifficulty() == Difficulty.HARD) && this.idleHeadUpdates++ > 15) {
                float hRange = 10.0F;
                float vRange = 5.0F;
                double xt = Mth.nextDouble(this.random, this.getX() - hRange, this.getX() + hRange);
                double yt = Mth.nextDouble(this.random, this.getY() - vRange, this.getY() + vRange);
                double zt = Mth.nextDouble(this.random, this.getZ() - hRange, this.getZ() + hRange);
                this.performRangedAttack(xt, yt, zt, true);
                this.idleHeadUpdates = 0;
            }

            int headTarget = this.getAlternativeTarget();
            if (headTarget > 0) {
                LivingEntity current = (LivingEntity)level.getEntity(headTarget);
                if (current != null && this.canAttack(current) && !(this.distanceToSqr(current) > 900.0) && this.hasLineOfSight(current)) {
                    this.performRangedAttack(current);
                    this.nextHeadUpdate = this.tickCount + 40 + this.random.nextInt(20);
                    this.idleHeadUpdates = 0;
                } else {
                    this.setAlternativeTarget(0);
                }
            } else {
                List<LivingEntity> entities = level.getNearbyEntities(
                        LivingEntity.class, TARGETING_CONDITIONS, this, this.getBoundingBox().inflate(20.0, 8.0, 20.0)
                );
                if (!entities.isEmpty()) {
                    LivingEntity selected = entities.get(this.random.nextInt(entities.size()));
                    this.setAlternativeTarget(selected.getId());
                }
            }
        }

        if (this.getTarget() != null) {
            this.setAlternativeTarget(this.getTarget().getId());
        } else {
            this.setAlternativeTarget(0);
        }

        if (this.destroyBlocksTick > 0) {
            this.destroyBlocksTick--;
            if (this.destroyBlocksTick == 0 && net.neoforged.neoforge.event.EventHooks.canEntityGrief(level, this)) {
                boolean destroyed = false;
                int width = Mth.floor(this.getBbWidth() / 2.0F + 1.0F);
                int height = Mth.floor(this.getBbHeight());

                for (BlockPos blockPos : BlockPos.betweenClosed(
                        this.getBlockX() - width,
                        this.getBlockY(),
                        this.getBlockZ() - width,
                        this.getBlockX() + width,
                        this.getBlockY() + height,
                        this.getBlockZ() + width
                )) {
                    BlockState state = level.getBlockState(blockPos);
                    if (state.canEntityDestroy(this.level(), blockPos, this) && net.neoforged.neoforge.event.EventHooks.onEntityDestroyBlock(this, blockPos, state)) {
                        destroyed = level.destroyBlock(blockPos, true, this) || destroyed;
                    }
                }

                if (destroyed) {
                    level.levelEvent(null, 1022, this.blockPosition(), 0);
                }
            }
        }

        if (this.tickCount % 20 == 0) {
            this.heal(1.0F);
        }

        this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
    }

    @Override
    public void makeStuckInBlock(BlockState blockState, Vec3 speedMultiplier) {
        // Existential void
    }

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        this.bossEvent.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.bossEvent.removePlayer(player);
    }

    @Override
    public void performRangedAttack(LivingEntity target, float p) {
        this.performRangedAttack(target);
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float damage) {
        if (this.isInvulnerableTo(level, source)) {
            return false;
        } else if (source.is(DamageTypeTags.WITHER_IMMUNE_TO) || source.getEntity() instanceof WitherBoss) {
            return false;
        } else {
            Entity sourceEntity = source.getEntity();
            if (sourceEntity != null && sourceEntity.is(EntityTypeTags.UNDEAD)) {
                return false;
            } else {
                if (this.destroyBlocksTick <= 0) {
                    this.destroyBlocksTick = 20;
                }

                return super.hurtServer(level, source, damage);
            }
        }
    }

    @Override
    public void checkDespawn() {
        if (net.neoforged.neoforge.event.EventHooks.checkMobDespawn(this)) return;
        if (this.level().getDifficulty() == Difficulty.PEACEFUL && !this.getType().isAllowedInPeaceful()) {
            this.discard();
        } else {
            this.noActionTime = 0;
        }
    }

    @Override
    public boolean addEffect(MobEffectInstance newEffect, @Nullable Entity source) {
        return false;
    }

    @Override
    protected boolean canRide(Entity vehicle) { return false; }

    @Override
    public boolean canUsePortal(boolean ignorePassenger) {
        return false;
    }

    @Override
    public boolean canBeAffected(MobEffectInstance newEffect) {
        return !newEffect.is(MobEffects.WITHER) && super.canBeAffected(newEffect);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 300.0)
                .add(Attributes.MOVEMENT_SPEED, 0.6F)
                .add(Attributes.FLYING_SPEED, 0.6F)
                .add(Attributes.FOLLOW_RANGE, 40.0)
                .add(Attributes.ARMOR, 4.0);
    }

    public int getAlternativeTarget() {
        return this.entityData.get(DATA_TARGET);
    }

    public void setAlternativeTarget(int entityId) {
        this.entityData.set(DATA_TARGET, entityId);
    }

    public int getDeathTicks() {
        return this.entityData.get(DATA_DEATH_TICKS);
    }

    public void setDeathTicks(int deathTicks) {
        this.entityData.set(DATA_DEATH_TICKS, deathTicks);
    }

    @Override
    protected void tickDeath() {
        int deathTicks = this.getDeathTicks() + 1;
        this.setDeathTicks(deathTicks);

        // Particle effects: Explosions and Red Dust Particles
        if (this.level().isClientSide() || this.level() instanceof ServerLevel) {
            if (deathTicks % 2 == 0) {
                double xo = (this.random.nextFloat() - 0.5F) * this.getBbWidth() * 2.0F;
                double yo = this.random.nextFloat() * this.getBbHeight();
                double zo = (this.random.nextFloat() - 0.5F) * this.getBbWidth() * 2.0F;
                this.level().addParticle(
                        ParticleTypes.EXPLOSION,
                        this.getX() + xo,
                        this.getY() + yo,
                        this.getZ() + zo,
                        0.0, 0.0, 0.0
                );
            }

            for (int i = 0; i < 3; ++i) {
                double xo = (this.random.nextFloat() - 0.5F) * this.getBbWidth() * 2.5F;
                double yo = this.random.nextFloat() * this.getBbHeight();
                double zo = (this.random.nextFloat() - 0.5F) * this.getBbWidth() * 2.5F;
                this.level().addParticle(
                        new DustParticleOptions(0xFF0000, 1.5F),
                        this.getX() + xo,
                        this.getY() + yo,
                        this.getZ() + zo,
                        this.random.nextGaussian() * 0.02,
                        this.random.nextGaussian() * 0.02,
                        this.random.nextGaussian() * 0.02
                );
            }
        }

        // Explosion sound effect periodically
        if (deathTicks % 20 == 0 && !this.isSilent()) {
            this.level().playSound(
                    null,
                    this.getX(), this.getY(), this.getZ(),
                    SoundEvents.GENERIC_EXPLODE.value(),
                    SoundSource.HOSTILE,
                    2.0F,
                    (1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F) * 0.7F
            );
        }

        // Massive emitter explosions near the end of death sequence
        if (deathTicks >= 180 && deathTicks <= 200) {
            double xo = (this.random.nextFloat() - 0.5F) * this.getBbWidth() * 3.0F;
            double yo = this.random.nextFloat() * this.getBbHeight();
            double zo = (this.random.nextFloat() - 0.5F) * this.getBbWidth() * 3.0F;
            this.level().addParticle(
                    ParticleTypes.EXPLOSION_EMITTER,
                    this.getX() + xo,
                    this.getY() + yo,
                    this.getZ() + zo,
                    0.0, 0.0, 0.0
            );
        }

        // Award XP and final loot
        if (!this.level().isClientSide() && this.level() instanceof ServerLevel serverLevel) {
            if (deathTicks > 150 && deathTicks % 5 == 0 && serverLevel.getGameRules().get(GameRules.MOB_DROPS)) {
                int award = EventHooks.getExperienceDrop(this, null, Mth.floor(this.xpReward * 0.08F));
                ExperienceOrb.award(serverLevel, this.position(), award);
            }

            if (deathTicks >= 200) {
                if (serverLevel.getGameRules().get(GameRules.MOB_DROPS)) {
                    int award = EventHooks.getExperienceDrop(this, null, Mth.floor(this.xpReward * 0.2F));
                    ExperienceOrb.award(serverLevel, this.position(), award);
                }
                this.dropPinkEmbryo(serverLevel, this.getLastDamageSource() != null ? this.getLastDamageSource() : this.damageSources().generic(), true);
                this.remove(RemovalReason.KILLED);
                this.gameEvent(net.minecraft.world.level.gameevent.GameEvent.ENTITY_DIE);
            }
        }
    }

    private double getHeadX() {
        return this.getX();
    }

    private double getHeadY() {
        return this.getY() + 3.0F * this.getScale();
    }

    private double getHeadZ() {
        return this.getZ();
    }

    private void performRangedAttack(LivingEntity target) {
        this.performRangedAttack(target.getX(), target.getY() + target.getEyeHeight() * 0.5, target.getZ(), this.random.nextFloat() < 0.001F);
    }

    private void performRangedAttack(double tx, double ty, double tz, boolean dangerous) {
        if (!this.isSilent()) {
            this.level().levelEvent(null, 1024, this.blockPosition(), 0);
        }

        double hx = this.getHeadX();
        double hy = this.getHeadY();
        double hz = this.getHeadZ();
        double xd = tx - hx;
        double yd = ty - hy;
        double zd = tz - hz;
        Vec3 direction = new Vec3(xd, yd, zd);
        WitherSkull entity = new WitherSkull(this.level(), this, direction.normalize());
        entity.setOwner(this);
        if (dangerous) {
            entity.setDangerous(true);
        }

        entity.setPos(hx, hy, hz);
        this.level().addFreshEntity(entity);
    }

    protected void dropPinkEmbryo(ServerLevel level, DamageSource source, boolean killedByPlayer) {
        ItemEntity pinkEmbryo = this.spawnAtLocation(level, InitItems.PINK_EMBRYO.get());
        if (pinkEmbryo != null) pinkEmbryo.setExtendedLifetime();
    }

    private class DoNothingGoal extends Goal {
        public DoNothingGoal() {
            super();
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
        }

        public boolean canUse() {
            return RedQueensAvatar.this.getDeathTicks() > 0;
        }
    }
}
