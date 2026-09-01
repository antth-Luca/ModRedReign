package io.github.anttluca.red_reign.items;

import io.github.anttluca.red_reign.components.StolenLifeDataComponent;
import io.github.anttluca.red_reign.init.InitDataComponentTypes;
import io.github.anttluca.red_reign.utils.components.StolenLifeDataComponentUtils;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;

public class ChaliceOfTheBloodbladeItem extends Item {
    public ChaliceOfTheBloodbladeItem(Properties props) {
        super(props
                .sword(ToolMaterial.DIAMOND, 3.0F, -2.4F)
                .component(DataComponents.UNBREAKABLE, Unit.INSTANCE)
                .component(InitDataComponentTypes.STOLEN_LIFE.get(), StolenLifeDataComponent.EMPTY)
        );
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return StolenLifeDataComponentUtils.getLife(stack) > 0;
    }

    public float getWidthForBar(ItemStack stack) {
        float stolen = StolenLifeDataComponentUtils.getLife(stack);
        if (stolen == 0) return 1;

        return (float) (1 - stolen / StolenLifeDataComponentUtils.MAX_STOLEN_LIFE);
    }

    public int getScaledBarWidth(ItemStack stack) {
        return Math.round(13.0F - 13.0F * getWidthForBar(stack));
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return getScaledBarWidth(stack);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return 0x24292EFF;
    }

    @Override
    public void hurtEnemy(ItemStack stack, LivingEntity mob, LivingEntity attacker) {
        if (!(attacker instanceof Player player)
            || attacker.level().isClientSide()) return;

        // TODO: Renovar esse método!
        StolenLifeDataComponentUtils.setLife(stack,
            StolenLifeDataComponentUtils.getLife(stack) + 2.0F
        );
    }
}
