package io.github.anttluca.red_reign.items.custom;

import io.github.anttluca.red_reign.handlers.CurioItemsHandler;
import io.github.anttluca.red_reign.init.InitDataComponentTypes;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class RRBaseRelic extends RRBaseItem implements ICurioItem {
    public RRBaseRelic(Properties props) {
        super(props
                .stacksTo(1)
        );
    }

    @Override
    public boolean canEquip(SlotContext context, ItemStack stack) {
        if (!ICurioItem.super.canEquip(context, stack)
            || CurioItemsHandler.hasCurio(context.entity(), this)) {
                return false;
        }

        var adoptable = stack.get(InitDataComponentTypes.ADOPTABLE.get());
        if (adoptable == null) return true;

        return adoptable.is(context.entity().getUUID());
    }
}
