package fr.akkun.newmerias2core.rpg.companion.network;

import fr.akkun.newmerias2core.NewmeriaS2Core;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

/** Server -> client: tells the client to open the Ink Friend companion-selection menu. */
public record OpenCompanionMenuPayload() implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<OpenCompanionMenuPayload> TYPE =
            new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(NewmeriaS2Core.MOD_ID, "open_companion_menu"));

    public static final StreamCodec<RegistryFriendlyByteBuf, OpenCompanionMenuPayload> STREAM_CODEC =
            StreamCodec.unit(new OpenCompanionMenuPayload());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
