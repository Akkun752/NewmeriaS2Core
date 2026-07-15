package fr.akkun.newmerias2core.rpg.companion;

import fr.akkun.newmerias2core.NewmeriaS2Core;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Enforces the companion combat rules on top of whatever the chosen form's own AI would otherwise
 * do: never attacks its owner, always attacks hostile mobs, and otherwise only attacks whoever its
 * owner strikes or whoever strikes its owner - it never retaliates against its own attacker unless
 * the owner also targeted them. That "provoked" entity is tracked here, in memory only (ephemeral
 * combat state, doesn't need to survive a save/reload or be synced).
 */
@EventBusSubscriber(modid = NewmeriaS2Core.MOD_ID)
public class CompanionEvents {
    private static final Map<UUID, UUID> PROVOKED_TARGET = new HashMap<>();

    @SubscribeEvent
    public static void onChangeTarget(LivingChangeTargetEvent event) {
        LivingEntity entity = event.getEntity();
        var data = entity.getExistingData(CompanionAttachments.COMPANION_DATA);
        if (data.isEmpty()) {
            return;
        }
        LivingEntity proposed = event.getNewAboutToBeSetTarget();
        if (proposed == null) {
            return;
        }
        if (proposed.getUUID().equals(data.get().ownerId())) {
            event.setCanceled(true);
            return;
        }
        boolean isHostile = proposed instanceof Monster || proposed instanceof Enemy;
        boolean isProvoked = proposed.getUUID().equals(PROVOKED_TARGET.get(entity.getUUID()));
        if (!isHostile && !isProvoked) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onDamage(LivingDamageEvent.Post event) {
        LivingEntity victim = event.getEntity();
        Entity attackerEntity = event.getSource().getEntity();

        if (attackerEntity instanceof ServerPlayer owner) {
            provoke(owner, victim);
        }
        if (victim instanceof ServerPlayer owner && attackerEntity instanceof LivingEntity attacker) {
            provoke(owner, attacker);
        }
    }

    private static void provoke(ServerPlayer owner, LivingEntity target) {
        Mob companion = CompanionManager.findCompanion(owner);
        if (companion == null || companion == target) {
            return;
        }
        PROVOKED_TARGET.put(companion.getUUID(), target.getUUID());
        companion.setTarget(target);
    }

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        if (event.getEntity().getExistingData(CompanionAttachments.COMPANION_DATA).isPresent()) {
            PROVOKED_TARGET.remove(event.getEntity().getUUID());
        }
    }
}
