package io.github.anttluca.red_reign.handlers;

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

public class RRRelicsAddHPHandler {
    public static final float ADD_HEALTH = 10.0F;

    public static Item.Properties addProps(Item.Properties props) {
        return props
                .component(InitDataComponentTypes.TOOLTIP_IMAGE.get(), TooltipImageDataComponent.BLOODSTAINED);
    }

    public static void addTooltips(Identifier id, Consumer<Component> builder) {
        RRItemTooltipsHandler.addSpace(builder);
        RRItemTooltipsHandler.addLore(id.getPath(), builder);
    }

    public static CurioAttributeModifiers getAttrMod(Identifier id, CuriosSlotTypes.Preset presetSlot) {
        return CurioAttributeModifiers.builder()
                .addModifier(
                        Attributes.MAX_HEALTH,
                        new AttributeModifier(
                                id,
                                ADD_HEALTH,
                                AttributeModifier.Operation.ADD_VALUE
                        ),
                        presetSlot.id()
                ).build();
    }
}
