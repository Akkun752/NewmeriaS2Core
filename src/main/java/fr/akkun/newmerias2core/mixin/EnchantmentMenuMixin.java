package fr.akkun.newmerias2core.mixin;

import fr.akkun.newmerias2core.rpg.RpgAttachments;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.EnchantmentMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Magic 2+ players don't need Lapis Lazuli to enchant. Vanilla's
 * {@code EnchantmentMenu#clickMenuButton} gates the Lapis requirement behind
 * {@code !player.hasInfiniteMaterials()} (already bypassed for creative players) - redirecting
 * only the FIRST call to that method (the Lapis check; the second call, further down, gates the
 * XP-level requirement and must stay untouched) lets Magic 2+ players skip just that one check.
 */
@Mixin(EnchantmentMenu.class)
public abstract class EnchantmentMenuMixin {
    @Redirect(method = "clickMenuButton", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/entity/player/Player;hasInfiniteMaterials()Z", ordinal = 0))
    private boolean newmerias2core$noLapisNeeded(Player player) {
        return player.hasInfiniteMaterials() || player.getData(RpgAttachments.RPG_DATA).magicLevel() >= 2;
    }
}
