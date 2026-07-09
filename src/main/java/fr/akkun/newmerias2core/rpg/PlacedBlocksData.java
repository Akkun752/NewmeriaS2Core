package fr.akkun.newmerias2core.rpg;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Tracks, per chunk, the positions of tracked blocks (logs/crops) that a player placed there,
 * so breaking them can be told apart from breaking a naturally generated block.
 */
public record PlacedBlocksData(Set<BlockPos> positions) {
    public static final MapCodec<PlacedBlocksData> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            BlockPos.CODEC.listOf().xmap(list -> (Set<BlockPos>) new HashSet<>(list), ArrayList::new)
                    .fieldOf("positions").forGetter(PlacedBlocksData::positions)
    ).apply(instance, PlacedBlocksData::new));

    public static PlacedBlocksData empty() {
        return new PlacedBlocksData(new HashSet<>());
    }
}
