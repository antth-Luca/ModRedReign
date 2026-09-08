package io.github.anttluca.red_reign.items.relics.custom;

import io.github.anttluca.red_reign.components.AdoptableDataComponent;
import io.github.anttluca.red_reign.init.InitDataComponentTypes;
import io.github.anttluca.red_reign.init.InitItems;
import io.github.anttluca.red_reign.items.custom.RRBaseRelic;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.puffish.attributesmod.api.PuffishAttributes;
import top.theillusivec4.curios.api.CurioAttributeModifiers;

public class VampireRoseItem extends RRBaseRelic {
    public VampireRoseItem(Properties props) {
        super(props);
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
