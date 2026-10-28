package com.hackathon.cocktailmod.network;

import com.hackathon.cocktailmod.CocktailMod;
import com.hackathon.cocktailmod.client.ClientAlcoholData;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * Payload sent from server to client to synchronize the player's alcohol level (0-100 grams).
 */
public record AlcoholSyncPayload(int level) implements CustomPacketPayload {
    public static final Type<AlcoholSyncPayload> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(CocktailMod.MODID, "alcohol_sync"));

    public static final StreamCodec<ByteBuf, AlcoholSyncPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.VAR_INT,
                    AlcoholSyncPayload::level,
                    AlcoholSyncPayload::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(AlcoholSyncPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> ClientAlcoholData.setLevel(payload.level()));
    }
}
