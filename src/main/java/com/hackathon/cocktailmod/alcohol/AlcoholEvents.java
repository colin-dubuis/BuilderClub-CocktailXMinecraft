package com.hackathon.cocktailmod.alcohol;

import com.hackathon.cocktailmod.CocktailMod;
import com.hackathon.cocktailmod.network.AlcoholSyncPayload;
import com.hackathon.cocktailmod.registry.ModAttachments;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerWakeUpEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

public class AlcoholEvents {

    public static final ResourceKey<DamageType> ALCOHOL_POISONING = ResourceKey.create(
            Registries.DAMAGE_TYPE,
            ResourceLocation.fromNamespaceAndPath(CocktailMod.MODID, "alcohol_poisoning")
    );

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            int alcohol = player.getData(ModAttachments.ALCOHOL);

            // 1. Decay over time: 1 point every ~7 seconds (140 ticks)
            if (alcohol > 0 && player.tickCount % 140 == 0) {
                alcohol = Math.max(0, alcohol - 1);
                player.setData(ModAttachments.ALCOHOL, alcohol);
                PacketDistributor.sendToPlayer(player, new AlcoholSyncPayload(alcohol));
            }

            // 2. Warning zone: 40-59 points
            // Gives recurring Nausea/dizziness swirl
            if (alcohol >= 40) {
                if (player.tickCount % 100 == 0) {
                    player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 160, 0, false, false, true));
                }
            }

            // 3. Critical zone: 60+ points
            // Lose half a heart (1.0 damage) every 2 seconds (40 ticks)
            if (alcohol >= 60) {
                if (player.tickCount % 40 == 0) {
                    player.hurt(player.level().damageSources().source(ALCOHOL_POISONING), 1.0f);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerWakeUp(PlayerWakeUpEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            int current = player.getData(ModAttachments.ALCOHOL);
            if (current > 0) {
                // Sleeping through the night clears a large chunk (50 points)
                int reduced = Math.max(0, current - 50);
                player.setData(ModAttachments.ALCOHOL, reduced);
                PacketDistributor.sendToPlayer(player, new AlcoholSyncPayload(reduced));
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            int alcohol = player.getData(ModAttachments.ALCOHOL);
            PacketDistributor.sendToPlayer(player, new AlcoholSyncPayload(alcohol));
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            int alcohol = player.getData(ModAttachments.ALCOHOL);
            PacketDistributor.sendToPlayer(player, new AlcoholSyncPayload(alcohol));
        }
    }

    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            int alcohol = player.getData(ModAttachments.ALCOHOL);
            PacketDistributor.sendToPlayer(player, new AlcoholSyncPayload(alcohol));
        }
    }
}
