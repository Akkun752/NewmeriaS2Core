package fr.akkun.newmerias2core.rpg;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record RpgData(int level, int pointsIntoLevel, int unspentStatPoints,
                       int forceLevel, int resistanceLevel, int speedLevel, int magicLevel) {
    public static final int MIN_RPG_LEVEL = 1;
    public static final int MAX_RPG_LEVEL = 20;
    public static final int MIN_STAT_LEVEL = 1;
    public static final int MAX_STAT_LEVEL = 6;

    public static final RpgData DEFAULT = new RpgData(MIN_RPG_LEVEL, 0, 0,
            MIN_STAT_LEVEL, MIN_STAT_LEVEL, MIN_STAT_LEVEL, MIN_STAT_LEVEL);

    public static final MapCodec<RpgData> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.INT.fieldOf("level").forGetter(RpgData::level),
            Codec.INT.fieldOf("points_into_level").forGetter(RpgData::pointsIntoLevel),
            Codec.INT.fieldOf("unspent_stat_points").forGetter(RpgData::unspentStatPoints),
            Codec.INT.fieldOf("force_level").forGetter(RpgData::forceLevel),
            Codec.INT.fieldOf("resistance_level").forGetter(RpgData::resistanceLevel),
            Codec.INT.fieldOf("speed_level").forGetter(RpgData::speedLevel),
            Codec.INT.fieldOf("magic_level").forGetter(RpgData::magicLevel)
    ).apply(instance, RpgData::new));

    public static final StreamCodec<ByteBuf, RpgData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, RpgData::level,
            ByteBufCodecs.VAR_INT, RpgData::pointsIntoLevel,
            ByteBufCodecs.VAR_INT, RpgData::unspentStatPoints,
            ByteBufCodecs.VAR_INT, RpgData::forceLevel,
            ByteBufCodecs.VAR_INT, RpgData::resistanceLevel,
            ByteBufCodecs.VAR_INT, RpgData::speedLevel,
            ByteBufCodecs.VAR_INT, RpgData::magicLevel,
            RpgData::new
    );

    public static int pointsToReachNextLevel(int level) {
        return (level + 1) * 100;
    }

    public RpgData withForceLevel(int newLevel) {
        return new RpgData(level, pointsIntoLevel, unspentStatPoints, newLevel, resistanceLevel, speedLevel, magicLevel);
    }

    public RpgData withResistanceLevel(int newLevel) {
        return new RpgData(level, pointsIntoLevel, unspentStatPoints, forceLevel, newLevel, speedLevel, magicLevel);
    }

    public RpgData withSpeedLevel(int newLevel) {
        return new RpgData(level, pointsIntoLevel, unspentStatPoints, forceLevel, resistanceLevel, newLevel, magicLevel);
    }

    public RpgData withMagicLevel(int newLevel) {
        return new RpgData(level, pointsIntoLevel, unspentStatPoints, forceLevel, resistanceLevel, speedLevel, newLevel);
    }

    public RpgData withUnspentStatPoints(int newUnspent) {
        return new RpgData(level, pointsIntoLevel, newUnspent, forceLevel, resistanceLevel, speedLevel, magicLevel);
    }

    /**
     * Adds RPG points, cascading level-ups (each granting one stat point) using the
     * (level+1)*100 threshold. Points never reduce the level and never reset on death.
     */
    public RpgData addPoints(int gained) {
        if (gained <= 0 || level >= MAX_RPG_LEVEL) {
            return this;
        }
        int newLevel = level;
        int newPoints = pointsIntoLevel + gained;
        int newUnspent = unspentStatPoints;
        while (newLevel < MAX_RPG_LEVEL) {
            int threshold = pointsToReachNextLevel(newLevel);
            if (newPoints < threshold) {
                break;
            }
            newPoints -= threshold;
            newLevel++;
            newUnspent++;
        }
        if (newLevel >= MAX_RPG_LEVEL) {
            newPoints = 0;
        }
        return new RpgData(newLevel, newPoints, newUnspent, forceLevel, resistanceLevel, speedLevel, magicLevel);
    }

    /**
     * Directly raises the RPG level by {@code amount} (capped at {@link #MAX_RPG_LEVEL}),
     * granting one stat point per level gained.
     */
    public RpgData addLevels(int amount) {
        if (amount <= 0 || level >= MAX_RPG_LEVEL) {
            return this;
        }
        int newLevel = Math.min(MAX_RPG_LEVEL, level + amount);
        int gained = newLevel - level;
        int newPoints = newLevel >= MAX_RPG_LEVEL ? 0 : pointsIntoLevel;
        return new RpgData(newLevel, newPoints, unspentStatPoints + gained, forceLevel, resistanceLevel, speedLevel, magicLevel);
    }
}
