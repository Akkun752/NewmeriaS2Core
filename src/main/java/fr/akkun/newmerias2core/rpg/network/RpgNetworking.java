package fr.akkun.newmerias2core.rpg.network;

import fr.akkun.newmerias2core.rpg.RpgAttachments;
import fr.akkun.newmerias2core.rpg.RpgAttributeModifiers;
import fr.akkun.newmerias2core.rpg.RpgData;
import fr.akkun.newmerias2core.rpg.RpgStat;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class RpgNetworking {
    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(RpgNetworking::onRegisterPayloadHandlers);
    }

    private static void onRegisterPayloadHandlers(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");
        registrar.playToServer(SpendStatPointPayload.TYPE, SpendStatPointPayload.STREAM_CODEC, RpgNetworking::handleSpendStatPoint);
    }

    private static void handleSpendStatPoint(SpendStatPointPayload payload, IPayloadContext context) {
        if (!(context.player() instanceof ServerPlayer player)) {
            return;
        }
        RpgStat stat = payload.stat();
        RpgData data = player.getData(RpgAttachments.RPG_DATA);
        if (data.unspentStatPoints() <= 0 || stat.level(data) >= RpgData.MAX_STAT_LEVEL) {
            return;
        }
        RpgData updated = stat.withLevel(data, stat.level(data) + 1)
                .withUnspentStatPoints(data.unspentStatPoints() - 1);
        player.setData(RpgAttachments.RPG_DATA, updated);
        RpgAttributeModifiers.apply(player, updated);
    }
}
