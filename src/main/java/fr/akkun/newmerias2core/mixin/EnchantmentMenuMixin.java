package fr.akkun.newmerias2core.mixin;

import fr.akkun.newmerias2core.rpg.RpgAttachments;
import fr.akkun.newmerias2core.rpg.RpgAttributeModifiers;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.EnchantmentMenu;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Discounts the enchanting table's displayed/required level cost ({@code costs[]}) for players
 * with a high Magic stat, WITHOUT weakening which enchantments get rolled.
 * <p>
 * Vanilla's {@code EnchantmentMenu#slotsChanged} computes {@code costs[]} and immediately uses that
 * same value to pick the enchantments (higher cost -> stronger enchantment pool). There is no clean
 * event to intercept between those two uses, so this mixin lets vanilla run entirely unmodified
 * (full enchant power) and only shrinks {@code costs[]} for display/gating right after it's done.
 */
@Mixin(EnchantmentMenu.class)
public abstract class EnchantmentMenuMixin {
    @Unique
    private Player newmerias2core$player;

    @Shadow
    @Final
    public int[] costs;

    @Inject(method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V", at = @At("TAIL"))
    private void newmerias2core$captureOwner(int containerId, Inventory inventory, ContainerLevelAccess access, CallbackInfo ci) {
        this.newmerias2core$player = inventory.player;
    }

    @Inject(method = "slotsChanged", at = @At("TAIL"))
    private void newmerias2core$discountCosts(Container container, CallbackInfo ci) {
        if (this.newmerias2core$player == null) {
            return;
        }
        double divisor = RpgAttributeModifiers.enchantCostDivisor(
                this.newmerias2core$player.getData(RpgAttachments.RPG_DATA).magicLevel());
        if (divisor <= 1.0) {
            return;
        }
        for (int i = 0; i < this.costs.length; i++) {
            if (this.costs[i] > 0) {
                this.costs[i] = Math.max(1, Math.round(this.costs[i] / (float) divisor));
            }
        }
    }
}
