package io.github.anttluca.red_reign.handlers;

import io.github.anttluca.red_reign.components.AdoptableDataComponent;
import io.github.anttluca.red_reign.components.TooltipImageDataComponent;
import io.github.anttluca.red_reign.init.InitDataComponentTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import top.theillusivec4.curios.api.CurioAttributeModifiers;
import top.theillusivec4.curios.api.CuriosSlotTypes;

import java.util.function.Consumer;

public class RRRelicsPropsHandler {
    public static final float HEALTH_SUPPLIER = 10.0F;

    public static Item.Properties addHPSupplierProps(Item.Properties props) {
        return props
                .component(InitDataComponentTypes.TOOLTIP_IMAGE.get(), TooltipImageDataComponent.BLOODSTAINED);
    }

    public static Item.Properties addHPCostProps(Item.Properties props) {
        return props
                .component(InitDataComponentTypes.TOOLTIP_IMAGE.get(), TooltipImageDataComponent.LIFE_INFUSED)
                .component(InitDataComponentTypes.ADOPTABLE.get(), AdoptableDataComponent.EMPTY);
    }

    public static void addLoreTooltip(Identifier id, Consumer<Component> builder) {
        RRItemTooltipsHandler.addSpace(builder);
        RRItemTooltipsHandler.addLore(id.getPath(), builder);
    }

    public static void addDefaultHealthModifier(CurioAttributeModifiers.Builder builder, Identifier id, CuriosSlotTypes.Preset presetSlot) {
        builder.addModifier(
            Attributes.MAX_HEALTH,
            new AttributeModifier(
                id,
                HEALTH_SUPPLIER,
                AttributeModifier.Operation.ADD_VALUE
            ),
            presetSlot.id()
        );
    }
}
