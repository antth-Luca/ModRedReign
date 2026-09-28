package io.github.anttluca.red_reign.init;

import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.entities.red_queens_avatar.RedQueensAvatar;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class InitEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(
            BuiltInRegistries.ENTITY_TYPE, RedReign.MODID);

    // Types
    public static final DeferredHolder<EntityType<?>, EntityType<RedQueensAvatar>> RED_QUEENS_AVATAR = ENTITY_TYPES.register(
        "red_queens_avatar", () -> EntityType.Builder.of(RedQueensAvatar::new, MobCategory.MONSTER)
                .fireImmune()
                .immuneTo(Blocks.WITHER_ROSE)
                .sized(0.9F, 3.5F)
                .clientTrackingRange(10)
                .notInPeaceful()
                .build(ResourceKey.create(
                    Registries.ENTITY_TYPE,
                    Identifier.fromNamespaceAndPath(RedReign.MODID, "red_queens_avatar")
                ))
    );
}
