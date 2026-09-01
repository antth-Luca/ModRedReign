package io.github.anttluca.red_reign.items.relics.custom;

import io.github.anttluca.red_reign.handlers.CurioItemsHandler;
import io.github.anttluca.red_reign.init.InitItems;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.puffish.attributesmod.api.PuffishAttributes;
import top.theillusivec4.curios.api.CurioAttributeModifiers;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class VampireRoseItem extends Item implements ICurioItem {
    public VampireRoseItem(Properties props) {
        super(props
                .stacksTo(1)
        );
    }

    @Override
    public boolean canEquip(SlotContext context, ItemStack stack) {
        return ICurioItem.super.canEquip(context, stack)
                && !CurioItemsHandler.hasCurio(context.entity(), InitItems.VAMPIRE_ROSE.get());
    }

    @Override
    public CurioAttributeModifiers getDefaultCurioAttributeModifiers(ItemStack stack) {
        return CurioAttributeModifiers.builder()
                .addModifier(
                        PuffishAttributes.LIFE_STEAL,
                        new AttributeModifier(
                                InitItems.VAMPIRE_ROSE.getId(),
                                0.05F,  // 5%
                                AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                        ),
                        "charm"
                ).build();
    }
}
