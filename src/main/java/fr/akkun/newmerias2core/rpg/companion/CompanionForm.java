package fr.akkun.newmerias2core.rpg.companion;

import fr.akkun.newmerias2core.NewmeriaS2Core;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;

import java.util.Locale;
import java.util.function.Supplier;

/**
 * The 5 possible Ink Friend companion shapes. Each reuses the real vanilla entity type and model
 * unchanged, just with a special texture (see {@link #texture()}) - {@code CompanionManager} applies
 * the shared companion rules (health, ownership, targeting) on top, regardless of form.
 *
 * <p>Required special-texture pixel dimensions (must match the vanilla model's own UV layout):
 * WOLF 64x32, ZOMBIE 64x64, HORSE 64x64, IRON_GOLEM 128x128, NAUTILUS 128x128.
 */
public enum CompanionForm {
    WOLF(() -> EntityTypes.WOLF, "rpg.newmerias2core.companion.wolf"),
    ZOMBIE(() -> EntityTypes.ZOMBIE, "rpg.newmerias2core.companion.zombie"),
    HORSE(() -> EntityTypes.HORSE, "rpg.newmerias2core.companion.horse"),
    IRON_GOLEM(() -> EntityTypes.IRON_GOLEM, "rpg.newmerias2core.companion.iron_golem"),
    NAUTILUS(() -> EntityTypes.NAUTILUS, "rpg.newmerias2core.companion.nautilus");

    private final Supplier<EntityType<?>> entityType;
    private final String translationKey;
    private final Identifier icon;
    private final Identifier texture;

    CompanionForm(Supplier<EntityType<?>> entityType, String translationKey) {
        this.entityType = entityType;
        this.translationKey = translationKey;
        String name = name().toLowerCase(Locale.ROOT);
        this.icon = Identifier.fromNamespaceAndPath(NewmeriaS2Core.MOD_ID, "textures/gui/companion/" + name + ".png");
        this.texture = Identifier.fromNamespaceAndPath(NewmeriaS2Core.MOD_ID, "textures/entity/companion/" + name + ".png");
    }

    public EntityType<?> entityType() {
        return entityType.get();
    }

    public Component displayName() {
        return Component.translatable(translationKey);
    }

    /** 16x16 icon, expected at {@code assets/newmerias2core/textures/gui/companion/<name>.png}. */
    public Identifier icon() {
        return icon;
    }

    /** Full mob skin reusing the vanilla model's UV layout - see the class javadoc for the required
     *  pixel dimensions per form. Expected at {@code assets/newmerias2core/textures/entity/companion/<name>.png}. */
    public Identifier texture() {
        return texture;
    }
}
