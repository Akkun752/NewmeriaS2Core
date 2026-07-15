package fr.akkun.newmerias2core.mixin.client;

import fr.akkun.newmerias2core.rpg.RpgAttachments;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.EnchantmentScreen;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.EnchantmentMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * The click itself already works without Lapis for Magic 2+ (see {@code EnchantmentMenuMixin}),
 * but the UI still reads the real slot via {@code EnchantmentMenu#getGoldCount()} to decide whether
 * to render each option as disabled and whether the lapis-cost tooltip line is red - both call
 * sites go through that one method, so redirecting it here fixes both: for Magic 2+ players it
 * always reports a full stack, exactly as if the maximum possible Lapis were always present,
 * regardless of whatever's actually (if anything) in the slot - and without ever touching it.
 */
@Mixin(EnchantmentScreen.class)
public abstract class EnchantmentScreenMixin {
    @Redirect(method = "extractBackground", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/inventory/EnchantmentMenu;getGoldCount()I"))
    private int newmerias2core$fakeMaxLapisInBackground(EnchantmentMenu menu) {
        return newmerias2core$fakeGoldCount(menu);
    }

    @Redirect(method = "extractRenderState", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/inventory/EnchantmentMenu;getGoldCount()I"))
    private int newmerias2core$fakeMaxLapisInTooltip(EnchantmentMenu menu) {
        return newmerias2core$fakeGoldCount(menu);
    }

    private static int newmerias2core$fakeGoldCount(EnchantmentMenu menu) {
        Player player = Minecraft.getInstance().player;
        if (player != null && player.getData(RpgAttachments.RPG_DATA).magicLevel() >= 2) {
            return 64;
        }
        return menu.getGoldCount();
    }
}
