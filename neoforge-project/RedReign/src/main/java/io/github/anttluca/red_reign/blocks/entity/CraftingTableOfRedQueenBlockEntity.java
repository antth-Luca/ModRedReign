package io.github.anttluca.red_reign.blocks.entity;

import io.github.anttluca.red_reign.init.InitBlockEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.world.Nameable;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;

public class CraftingTableOfRedQueenBlockEntity extends BlockEntity implements Nameable {
    private static final Component DEFAULT_NAME = Component.translatable("container.crafting_table_of_red_queen");

    private @Nullable Component name;

    public CraftingTableOfRedQueenBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(
            InitBlockEntityType.CRAFTING_TABLE_OF_RED_QUEEN_BE.get(),
            worldPosition, blockState
        );
    }

    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.storeNullable("CustomName", ComponentSerialization.CODEC, this.name);
    }

    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.name = parseCustomNameSafe(input, "CustomName");
    }

    public Component getName() {
        return this.name != null ? this.name : DEFAULT_NAME;
    }

    public void setCustomName(@Nullable Component name) {
        this.name = name;
    }

    public @Nullable Component getCustomName() {
        return this.name;
    }

    protected void applyImplicitComponents(DataComponentGetter components) {
        super.applyImplicitComponents(components);
        this.name = (Component) components.get(DataComponents.CUSTOM_NAME);
    }

    protected void collectImplicitComponents(DataComponentMap.Builder components) {
        super.collectImplicitComponents(components);
        components.set(DataComponents.CUSTOM_NAME, this.name);
    }

    public void removeComponentsFromTag(ValueOutput output) {
        output.discard("CustomName");
    }
}
