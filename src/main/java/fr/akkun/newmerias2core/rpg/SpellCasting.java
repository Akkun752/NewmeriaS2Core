package fr.akkun.newmerias2core.rpg;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/** Server-side spell execution and (in-memory, non-persisted) per-player cooldown tracking. */
public class SpellCasting {
    private static final Map<UUID, EnumMap<RpgSpell, Long>> COOLDOWN_END_TICK = new HashMap<>();
    // Damage/blast size multiplier over vanilla lightning/ghast-fireball defaults.
    private static final float POWER_MULTIPLIER = 3.0F;

    public static void cast(ServerPlayer player) {
        RpgData data = player.getData(RpgAttachments.RPG_DATA);
        if (data.selectedSpell() == RpgData.NO_SPELL) {
            return;
        }
        RpgSpell spell = RpgSpell.values()[data.selectedSpell()];
        if (!spell.isUnlocked(data.magicLevel())) {
            return;
        }

        if (!player.isCreative()) {
            long now = player.level().getGameTime();
            Map<RpgSpell, Long> cooldowns = COOLDOWN_END_TICK.computeIfAbsent(player.getUUID(), k -> new EnumMap<>(RpgSpell.class));
            Long endTick = cooldowns.get(spell);
            if (endTick != null && now < endTick) {
                long remainingSeconds = (endTick - now) / 20 + 1;
                player.sendSystemMessage(Component.translatable("rpg.newmerias2core.spell.on_cooldown", spell.displayName(), remainingSeconds), true);
                return;
            }
            cooldowns.put(spell, now + spell.cooldownTicks());
        }

        switch (spell) {
            case LIGHTNING -> castLightning(player);
            case TELEPORT -> castTeleport(player);
            case FIREBALL -> castFireball(player);
        }
    }

    private static void castLightning(ServerPlayer player) {
        ServerLevel level = player.level();
        HitResult hit = player.pick(100.0, 1.0F, false);
        Vec3 target = hit.getLocation();
        LightningBolt bolt = EntityTypes.LIGHTNING_BOLT.create(level, EntitySpawnReason.TRIGGERED);
        if (bolt == null) {
            return;
        }
        bolt.setPos(target.x, target.y, target.z);
        bolt.setCause(player);
        bolt.setDamage(bolt.getDamage() * POWER_MULTIPLIER);
        level.addFreshEntity(bolt);
    }

    private static void castTeleport(ServerPlayer player) {
        TeleportTransition transition = player.findRespawnPositionAndUseSpawnBlock(false, TeleportTransition.DO_NOTHING);
        ServerPlayer teleported = player.teleport(transition);
        if (teleported == null) {
            return;
        }
        ServerLevel level = teleported.level();
        Vec3 pos = teleported.position();
        level.sendParticles(ParticleTypes.PORTAL, pos.x, pos.y + 1.0, pos.z, 32, 0.5, 1.0, 0.5, 0.3);
        level.playSound(null, pos.x, pos.y, pos.z, SoundEvents.PLAYER_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);
    }

    private static void castFireball(ServerPlayer player) {
        ServerLevel level = player.level();
        Vec3 direction = player.getLookAngle();
        // Vanilla ghast fireballs: explosion power 1, 6.0F direct-hit damage.
        int explosionPower = Math.round(1 * POWER_MULTIPLIER);
        float directHitDamage = 6.0F * POWER_MULTIPLIER;
        RpgLargeFireball fireball = new RpgLargeFireball(level, player, direction, explosionPower, directHitDamage);
        fireball.setPos(player.getX(), player.getEyeY(), player.getZ());
        level.addFreshEntity(fireball);
    }
}
