package io.github.anttluca.red_reign.items.custom;

import io.github.anttluca.red_reign.components.AdoptableDataComponent;
import io.github.anttluca.red_reign.handlers.CurioItemsHandler;
import io.github.anttluca.red_reign.init.InitDataComponentTypes;
import io.github.anttluca.red_reign.world.data.RedReignWorldData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;

public class RRBaseRelic extends RRBaseItem implements ICurioItem {
    public RRBaseRelic(Properties props) {
        super(props
                .stacksTo(1)
        );
    }

    @Override
    public boolean canEquip(SlotContext context, ItemStack stack) {
        if (!ICurioItem.super.canEquip(context, stack)) return false;

        if (!(context.entity() instanceof Player player)) return false;

        RedReignWorldData worldIsRR = RedReignWorldData.get(player.level(), Level.OVERWORLD);
        if (worldIsRR == null || !worldIsRR.isActive()) return false;

        if (CurioItemsHandler.hasCurio(player, this)) return false;

        @Nullable AdoptableDataComponent adoptable = stack.get(InitDataComponentTypes.ADOPTABLE.get());
        return adoptable == null || adoptable.is(player.getUUID());
    }
}
