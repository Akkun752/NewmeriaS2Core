package fr.akkun.newmerias2core.mixin;

import fr.akkun.newmerias2core.rpg.RpgAttachments;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.EnchantmentMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Magic 2+ players don't need Lapis Lazuli to enchant at all. Vanilla's
 * {@code EnchantmentMenu#clickMenuButton} gates on
 * {@code (currency.isEmpty() || currency.getCount() < enchantmentCost) && !player.hasInfiniteMaterials()}
 * - redirecting only the FIRST call to {@code hasInfiniteMaterials()} (the currency check; the
 * second call, further down, gates the XP-level requirement and must stay untouched) makes that
 * whole condition false for Magic 2+ players regardless of what's actually in the currency slot,
 * short-circuiting past the presence/count check entirely rather than needing to fake its contents.
 */
@Mixin(EnchantmentMenu.class)
public abstract class EnchantmentMenuMixin {
    @Redirect(method = "clickMenuButton", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/entity/player/Player;hasInfiniteMaterials()Z", ordinal = 0))
    private boolean newmerias2core$noLapisNeeded(Player player) {
        return player.hasInfiniteMaterials() || player.getData(RpgAttachments.RPG_DATA).magicLevel() >= 2;
    }
}
