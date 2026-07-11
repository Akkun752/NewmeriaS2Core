package fr.akkun.newmerias2core.rpg.network;

import fr.akkun.newmerias2core.NewmeriaS2Core;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record CastSpellPayload() implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<CastSpellPayload> TYPE =
            new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(NewmeriaS2Core.MOD_ID, "cast_spell"));

    public static final StreamCodec<RegistryFriendlyByteBuf, CastSpellPayload> STREAM_CODEC = StreamCodec.unit(new CastSpellPayload());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
