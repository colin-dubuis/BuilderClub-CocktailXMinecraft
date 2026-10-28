package com.hackathon.cocktailmod.client;

import com.hackathon.cocktailmod.CocktailMod;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

public class ClientSetup {

    public static void onRegisterGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAbove(
                VanillaGuiLayers.HOTBAR,
                ResourceLocation.fromNamespaceAndPath(CocktailMod.MODID, "alcohol_meter"),
                new AlcoholHudLayer()
        );
    }
}
