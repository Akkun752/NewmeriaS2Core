package fr.akkun.newmerias2core.rpg;

import net.minecraft.network.chat.Component;

public enum RpgStat {
    FORCE("rpg.newmerias2core.stat.force") {
        @Override
        public int level(RpgData data) {
            return data.forceLevel();
        }

        @Override
        public RpgData withLevel(RpgData data, int level) {
            return data.withForceLevel(level);
        }
    },
    RESISTANCE("rpg.newmerias2core.stat.resistance") {
        @Override
        public int level(RpgData data) {
            return data.resistanceLevel();
        }

        @Override
        public RpgData withLevel(RpgData data, int level) {
            return data.withResistanceLevel(level);
        }
    },
    SPEED("rpg.newmerias2core.stat.speed") {
        @Override
        public int level(RpgData data) {
            return data.speedLevel();
        }

        @Override
        public RpgData withLevel(RpgData data, int level) {
            return data.withSpeedLevel(level);
        }
    },
    MAGIC("rpg.newmerias2core.stat.magic") {
        @Override
        public int level(RpgData data) {
            return data.magicLevel();
        }

        @Override
        public RpgData withLevel(RpgData data, int level) {
            return data.withMagicLevel(level);
        }
    };

    private final String translationKey;

    RpgStat(String translationKey) {
        this.translationKey = translationKey;
    }

    public Component displayName() {
        return Component.translatable(translationKey);
    }

    public abstract int level(RpgData data);

    public abstract RpgData withLevel(RpgData data, int level);
}
