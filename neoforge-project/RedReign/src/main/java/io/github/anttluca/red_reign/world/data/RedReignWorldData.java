package io.github.anttluca.red_reign.world.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.anttluca.red_reign.RedReign;
import io.github.anttluca.red_reign.events.runtime.RRWorldEffetcsWorks;
import io.github.anttluca.red_reign.events.runtime.WhiteQueenDeathStory;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import net.minecraft.world.level.storage.SavedDataStorage;
import net.neoforged.neoforge.common.NeoForge;

public class RedReignWorldData extends SavedData {
    public static final SavedDataType<RedReignWorldData> TYPE = new SavedDataType<>(
            Identifier.fromNamespaceAndPath(RedReign.MODID, "red_reign"),
            RedReignWorldData::new,
            RecordCodecBuilder.create(instance -> instance.group(
                    Codec.BOOL.fieldOf("isActive").forGetter(sd -> sd.isActive)
            ).apply(instance, RedReignWorldData::new))
    );

    private boolean isActive;

    public RedReignWorldData() {
        isActive = false;
        setDirty();
    }

    public RedReignWorldData(boolean active) {
        isActive = active;
        setDirty();
    }

    public static RedReignWorldData get(Level level, ResourceKey<Level> dim) {
        if (level instanceof ServerLevel serverLevel) {
            ServerLevel targetLevel = serverLevel.getServer().getLevel(dim);
            if (targetLevel == null) {
                targetLevel = serverLevel.getServer().overworld();
            }
            SavedDataStorage storage = targetLevel.getDataStorage();
            RedReignWorldData data = storage.computeIfAbsent(TYPE);
            if (data != null) {
                data.setDirty();
            }
            return data;
        }
        return null;
    }

    public boolean isActive() {
        return isActive;
    }

    public void activate(ServerLevel serverLevel) {
        isActive = true;
        setDirty();

        NeoForge.EVENT_BUS.register(RRWorldEffetcsWorks.class);
        RRWorldEffetcsWorks.disablePlayerNaturalRegen(serverLevel);

        NeoForge.EVENT_BUS.register(WhiteQueenDeathStory.class);
        WhiteQueenDeathStory.startStorySequence();
    }
}
