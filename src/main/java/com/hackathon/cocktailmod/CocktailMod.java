package com.hackathon.cocktailmod;

import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import com.hackathon.cocktailmod.item.MelonCocktailItem;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(CocktailMod.MODID)
public class CocktailMod {
    public static final String MODID = "cocktailmod";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // Register our custom Melon Cocktail item
    public static final DeferredItem<MelonCocktailItem> MELON_COCKTAIL =
            ITEMS.register("melon_cocktail", MelonCocktailItem::new);

    // Register a custom Creative Mode Tab for cocktails
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> COCKTAILS_TAB =
            CREATIVE_MODE_TABS.register("cocktails_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.cocktailmod.cocktails"))
                    .icon(() -> MELON_COCKTAIL.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(MELON_COCKTAIL.get());
                    })
                    .build());

    public CocktailMod(IEventBus modEventBus) {
        LOGGER.info("Initializing Cocktail Mod!");

        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);

        modEventBus.addListener(this::addCreative);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        // Also add the cocktail to the standard vanilla Food & Drinks creative tab
        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.accept(MELON_COCKTAIL.get());
        }
    }
}
