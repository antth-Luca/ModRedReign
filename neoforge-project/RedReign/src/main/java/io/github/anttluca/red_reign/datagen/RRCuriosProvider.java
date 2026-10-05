package io.github.anttluca.red_reign.datagen;

import io.github.anttluca.red_reign.RedReign;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import top.theillusivec4.curios.api.CuriosDataProvider;
import top.theillusivec4.curios.api.CuriosSlotTypes;

import java.util.concurrent.CompletableFuture;

public class RRCuriosProvider extends CuriosDataProvider {
    public RRCuriosProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(RedReign.MODID, output, registries);
    }

    @Override
    public void generate(HolderLookup.Provider registries) {
        createEntities("player_slots")
                .addPlayer()
                .addPresetSlots(CuriosSlotTypes.Preset.HEAD)
                .addPresetSlots(CuriosSlotTypes.Preset.CHARM)
                .addPresetSlots(CuriosSlotTypes.Preset.NECKLACE)
                .addPresetSlots(CuriosSlotTypes.Preset.BACK)
                .addPresetSlots(CuriosSlotTypes.Preset.BODY)
                .addPresetSlots(CuriosSlotTypes.Preset.HANDS)
                .addPresetSlots(CuriosSlotTypes.Preset.RING)
                .addPresetSlots(CuriosSlotTypes.Preset.BELT);

        createSlot(CuriosSlotTypes.Preset.HANDS.id()).size(2);
    }
}
