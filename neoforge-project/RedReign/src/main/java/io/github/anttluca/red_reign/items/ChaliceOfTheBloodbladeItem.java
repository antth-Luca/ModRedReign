package io.github.anttluca.red_reign.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;

public class ChaliceOfTheBloodbladeItem extends Item {
    public ChaliceOfTheBloodbladeItem(Properties props) {
        super(props
                .sword(ToolMaterial.DIAMOND, 3.0F, -2.4F)
        );
    }
}
