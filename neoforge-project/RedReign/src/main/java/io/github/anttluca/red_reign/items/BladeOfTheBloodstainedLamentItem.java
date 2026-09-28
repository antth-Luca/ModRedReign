package io.github.anttluca.red_reign.items;

import io.github.anttluca.red_reign.components.TooltipImageDataComponent;
import io.github.anttluca.red_reign.entities.red_queens_avatar.RedQueensAvatar;
import io.github.anttluca.red_reign.handlers.RRItemTooltipsHandler;
import io.github.anttluca.red_reign.init.InitDataComponentTypes;
import io.github.anttluca.red_reign.init.InitEntityTypes;
import io.github.anttluca.red_reign.init.InitItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Unit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.enchantment.Repairable;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;
import java.util.function.Consumer;

public class BladeOfTheBloodstainedLamentItem extends Item {
    public BladeOfTheBloodstainedLamentItem(Properties props) {
        super(props
                .sword(ToolMaterial.NETHERITE, 5.0F, -2.4F)
                .fireResistant()
                .component(DataComponents.REPAIRABLE, new Repairable(HolderSet.empty()))
                .component(DataComponents.UNBREAKABLE, Unit.INSTANCE)
                .component(InitDataComponentTypes.TOOLTIP_IMAGE.get(), TooltipImageDataComponent.LIFE_INFUSED)
        );
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext ctx, TooltipDisplay display, Consumer<Component> builder, TooltipFlag flag) {
        super.appendHoverText(stack, ctx, display, builder, flag);
        RRItemTooltipsHandler.addLoreAndEffects(InitItems.BLADE_OF_THE_BLOODSTAINED_LAMENT.getId().getPath(), 1, builder);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResult.CONSUME;
    }

    @Override
    public int getUseDuration(ItemStack itemStack, LivingEntity user) {return 72000;}

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack itemStack) {
        return ItemUseAnimation.BOW;
    }

    @Override
    public boolean releaseUsing(ItemStack stack, Level level, LivingEntity entity, int remainingTime) {
        if (!(level instanceof ServerLevel serverLevel)) return false;

        int timeHeld = this.getUseDuration(stack, entity) - remainingTime;
        if (timeHeld < 0) return false;

        RedQueensAvatar boss = InitEntityTypes.RED_QUEENS_AVATAR.get().create(serverLevel, EntitySpawnReason.TRIGGERED);
        if (boss == null) return false;

        Vec3 look = entity.getLookAngle();
        double x = entity.getX() + look.x * 3.0;
        double y = entity.getY();
        double z = entity.getZ() + look.z * 3.0;
        boss.snapTo(
            x, y, z,
            entity.getYRot(),
            0.0F
        );

        serverLevel.addFreshEntity(boss);
        for (ServerPlayer player : level.getEntitiesOfClass(ServerPlayer.class, boss.getBoundingBox().inflate(50.0))) {
            CriteriaTriggers.SUMMONED_ENTITY.trigger(player, boss);
        }

        return true;
    }
}
