package fr.akkun.newmerias2core.rpg;

import net.minecraft.network.chat.Component;

/** Order matters: this is the unlock order shown in the Magic menu (by required Magic level). */
public enum RpgSpell {
    LIGHTNING(2, 40 * 20, "rpg.newmerias2core.spell.lightning"),
    TELEPORT(3, 60 * 20, "rpg.newmerias2core.spell.teleport"),
    FIREBALL(5, 20 * 20, "rpg.newmerias2core.spell.fireball");

    private final int requiredMagicLevel;
    private final int cooldownTicks;
    private final String translationKey;

    RpgSpell(int requiredMagicLevel, int cooldownTicks, String translationKey) {
        this.requiredMagicLevel = requiredMagicLevel;
        this.cooldownTicks = cooldownTicks;
        this.translationKey = translationKey;
    }

    public int requiredMagicLevel() {
        return requiredMagicLevel;
    }

    public int cooldownTicks() {
        return cooldownTicks;
    }

    public boolean isUnlocked(int magicLevel) {
        return magicLevel >= requiredMagicLevel;
    }

    public Component displayName() {
        return Component.translatable(translationKey);
    }
}
