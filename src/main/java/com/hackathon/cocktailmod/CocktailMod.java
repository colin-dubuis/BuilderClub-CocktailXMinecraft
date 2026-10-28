package com.hackathon.cocktailmod;

import com.hackathon.cocktailmod.alcohol.AlcoholCommand;
import com.hackathon.cocktailmod.alcohol.AlcoholEvents;
import com.hackathon.cocktailmod.client.ClientSetup;
import com.hackathon.cocktailmod.item.DrinkItem;
import com.hackathon.cocktailmod.network.AlcoholSyncPayload;
import com.hackathon.cocktailmod.registry.ModAttachments;
import com.hackathon.cocktailmod.registry.ModBrewing;
import com.hackathon.cocktailmod.registry.ModItems;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

@Mod(CocktailMod.MODID)
public class CocktailMod {
    public static final String MODID = "cocktailmod";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // Register a custom Creative Mode Tab for all cocktail mod items
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> COCKTAILS_TAB =
            CREATIVE_MODE_TABS.register("cocktails_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.cocktailmod.cocktails"))
                    .icon(() -> ModItems.MOJITO.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        ModItems.ITEMS.getEntries().forEach(item -> output.accept(item.get()));
                    })
                    .build());

    public CocktailMod(IEventBus modEventBus) {
        LOGGER.info("Initializing Cocktail Mod (NeoForge 1.21.1)!");

        // Register deferred registers on mod event bus
        ModItems.ITEMS.register(modEventBus);
        ModAttachments.ATTACHMENT_TYPES.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);

        // Register mod bus events
        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(this::registerPayloadHandlers);

        if (FMLEnvironment.dist.isClient()) {
            modEventBus.addListener(ClientSetup::onRegisterGuiLayers);
        }

        // Register game bus events
        NeoForge.EVENT_BUS.register(AlcoholEvents.class);
        NeoForge.EVENT_BUS.register(AlcoholCommand.class);
        NeoForge.EVENT_BUS.register(ModBrewing.class);
    }

    private void registerPayloadHandlers(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1.0.0");
        registrar.playToClient(
                AlcoholSyncPayload.TYPE,
                AlcoholSyncPayload.STREAM_CODEC,
                AlcoholSyncPayload::handle
        );
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            ModItems.ITEMS.getEntries().forEach(item -> {
                if (item.get() instanceof DrinkItem || item.get().components().has(net.minecraft.core.component.DataComponents.FOOD)) {
                    event.accept(item.get());
                }
            });
        } else if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.MINT.get());
            event.accept(ModItems.AGAVE.get());
            event.accept(ModItems.JUNIPER_BERRIES.get());
            event.accept(ModItems.GINGER.get());
        }
    }
}
