package io.github.anttluca.red_reign.entities.red_queen_power;

import io.github.anttluca.red_reign.init.InitEntityTypes;
import io.github.anttluca.red_reign.init.InitMobEffects;
import io.github.anttluca.red_reign.utils.RRResourceKeyUtils.DamageTypeKeys;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.hurtingprojectile.AbstractHurtingProjectile;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class RedQueenPower extends AbstractHurtingProjectile {
    public int time;

    public RedQueenPower(EntityType<? extends RedQueenPower> type, Level level) {
        super(type, level);
        this.time = this.random.nextInt(100000);
    }

    public RedQueenPower(Level level, LivingEntity mob, Vec3 direction) {
        super(InitEntityTypes.RED_QUEEN_POWER.get(), mob, direction, level);
        this.time = this.random.nextInt(100000);
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    public float getBlockExplosionResistance(Explosion explosion, BlockGetter level, BlockPos pos, BlockState block, FluidState fluid, float resistance) {
        return block.canEntityDestroy(level, pos, this) ? Math.min(0.8F, resistance) : resistance;
    }

    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
        Level level = this.level();
        if (level instanceof ServerLevel serverLevel) {
            Entity entity = hitResult.getEntity();
            Entity witherOwner = this.getOwner();
            boolean wasHurt;
            if (witherOwner instanceof LivingEntity livingOwner) {
                DamageSource damageSource = this.damageSources().source(DamageTypeKeys.RQ_POWER, this, livingOwner);
                wasHurt = entity.hurtServer(serverLevel, damageSource, 4.0F);
                if (wasHurt) {
                    if (entity.isAlive()) {
                        EnchantmentHelper.doPostAttackEffects(serverLevel, entity, damageSource);
                    } else {
                        livingOwner.heal(2.5F);
                    }
                }
            } else {
                wasHurt = entity.hurtServer(serverLevel, this.damageSources().magic(), 2.5F);
            }

            if (wasHurt && entity instanceof LivingEntity livingEntity) {
                int witherSeconds = 0;
                if (this.level().getDifficulty() == Difficulty.NORMAL) {
                    witherSeconds = 10;
                } else if (this.level().getDifficulty() == Difficulty.HARD) {
                    witherSeconds = 30;
                }

                if (witherSeconds > 0) {
                    livingEntity.addEffect(
                        new MobEffectInstance(
                            InitMobEffects.ARMOR_CORROSION,
                            witherSeconds * 20, 1
                        ),
                        this.getEffectSource()
                    );
                    livingEntity.addEffect(
                        new MobEffectInstance(
                            InitMobEffects.BLEEDING,
                            witherSeconds * 10, 1
                        ),
                        this.getEffectSource()
                    );
                }
            }
        }
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (!this.level().isClientSide()) {
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), 1.0F, false, Level.ExplosionInteraction.MOB);
            this.discard();
        }
    }

    @Override
    protected boolean shouldBurn() { return false; }
}
