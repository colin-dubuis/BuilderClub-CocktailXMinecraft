package com.hackathon.cocktailmod.registry;

import com.hackathon.cocktailmod.CocktailMod;
import com.mojang.serialization.Codec;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, CocktailMod.MODID);

    // Alcohol attachment: stores the player's alcohol level (0-100 grams)
    // Saved with the world, but does NOT copy on death (resets to 0 upon death)
    public static final Supplier<AttachmentType<Integer>> ALCOHOL = ATTACHMENT_TYPES.register(
            "alcohol",
            () -> AttachmentType.builder(() -> 0)
                    .serialize(Codec.INT)
                    .build()
    );
}
