package fr.akkun.newmerias2core.rpg.network;

import fr.akkun.newmerias2core.NewmeriaS2Core;
import fr.akkun.newmerias2core.rpg.RpgData;
import fr.akkun.newmerias2core.rpg.RpgSpell;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

/** {@code spell} is the ordinal of the {@link RpgSpell} to select, or {@link RpgData#NO_SPELL} to deselect. */
public record SelectSpellPayload(int spell) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SelectSpellPayload> TYPE =
            new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(NewmeriaS2Core.MOD_ID, "select_spell"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SelectSpellPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, SelectSpellPayload::spell,
            SelectSpellPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
