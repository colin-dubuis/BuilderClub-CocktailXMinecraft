package com.hackathon.cocktailmod.registry;

import com.hackathon.cocktailmod.CocktailMod;
import com.hackathon.cocktailmod.item.DrinkItem;
import com.hackathon.cocktailmod.item.MelonCocktailItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CocktailMod.MODID);

    // ==========================================
    // INGREDIENTS: FRUITS, HERBS & PLANTS
    // ==========================================
    public static final DeferredItem<Item> LIME = ITEMS.register("lime",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationModifier(0.2f).build())));

    public static final DeferredItem<Item> MINT = ITEMS.register("mint",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> AGAVE = ITEMS.register("agave",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> GINGER = ITEMS.register("ginger",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationModifier(0.1f).build())));

    public static final DeferredItem<Item> PINEAPPLE = ITEMS.register("pineapple",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationModifier(0.4f).build())));

    public static final DeferredItem<Item> COCONUT = ITEMS.register("coconut",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.3f).build())));

    public static final DeferredItem<Item> ORANGE = ITEMS.register("orange",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(0.3f).build())));

    public static final DeferredItem<Item> JUNIPER_BERRIES = ITEMS.register("juniper_berries",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(1).saturationModifier(0.1f).build())));

    // ==========================================
    // BREWING STEP 1: WASHES (FERMENTED)
    // ==========================================
    public static final DeferredItem<DrinkItem> SUGAR_CANE_WASH = ITEMS.register("sugar_cane_wash",
            () -> new DrinkItem(DrinkItem.createDrinkProperties(1, 0.1f), 2));

    public static final DeferredItem<DrinkItem> POTATO_WASH = ITEMS.register("potato_wash",
            () -> new DrinkItem(DrinkItem.createDrinkProperties(1, 0.1f), 2));

    public static final DeferredItem<DrinkItem> GRAIN_WASH = ITEMS.register("grain_wash",
            () -> new DrinkItem(DrinkItem.createDrinkProperties(1, 0.1f), 2));

    public static final DeferredItem<DrinkItem> AGAVE_WASH = ITEMS.register("agave_wash",
            () -> new DrinkItem(DrinkItem.createDrinkProperties(1, 0.1f), 2));

    // ==========================================
    // BREWING STEP 2: DISTILLED SPIRITS
    // ==========================================
    public static final DeferredItem<DrinkItem> WHITE_RUM = ITEMS.register("white_rum",
            () -> new DrinkItem(DrinkItem.createDrinkProperties(1, 0.1f,
                    () -> new MobEffectInstance(MobEffects.CONFUSION, 60, 0)), 13));

    public static final DeferredItem<DrinkItem> VODKA = ITEMS.register("vodka",
            () -> new DrinkItem(DrinkItem.createDrinkProperties(1, 0.1f,
                    () -> new MobEffectInstance(MobEffects.CONFUSION, 60, 0)), 13));

    public static final DeferredItem<DrinkItem> WHISKY = ITEMS.register("whisky",
            () -> new DrinkItem(DrinkItem.createDrinkProperties(1, 0.1f,
                    () -> new MobEffectInstance(MobEffects.CONFUSION, 60, 0)), 13));

    public static final DeferredItem<DrinkItem> TEQUILA = ITEMS.register("tequila",
            () -> new DrinkItem(DrinkItem.createDrinkProperties(1, 0.1f,
                    () -> new MobEffectInstance(MobEffects.CONFUSION, 60, 0)), 13));

    public static final DeferredItem<DrinkItem> GIN = ITEMS.register("gin",
            () -> new DrinkItem(DrinkItem.createDrinkProperties(1, 0.1f,
                    () -> new MobEffectInstance(MobEffects.CONFUSION, 60, 0)), 13));

    // ==========================================
    // INTERMEDIATES, MIXERS & LIQUEURS
    // ==========================================
    public static final DeferredItem<DrinkItem> TRIPLE_SEC = ITEMS.register("triple_sec",
            () -> new DrinkItem(DrinkItem.createDrinkProperties(2, 0.2f), 10));

    public static final DeferredItem<DrinkItem> SUGAR_SYRUP = ITEMS.register("sugar_syrup",
            () -> new DrinkItem(DrinkItem.createDrinkProperties(2, 0.2f), 0));

    public static final DeferredItem<DrinkItem> SODA_WATER = ITEMS.register("soda_water",
            () -> new DrinkItem(DrinkItem.createDrinkProperties(1, 0.1f), 0));

    public static final DeferredItem<DrinkItem> COLA = ITEMS.register("cola",
            () -> new DrinkItem(DrinkItem.createDrinkProperties(2, 0.2f,
                    () -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100, 0)), 0));

    public static final DeferredItem<DrinkItem> GINGER_BEER = ITEMS.register("ginger_beer",
            () -> new DrinkItem(DrinkItem.createDrinkProperties(2, 0.2f), 0));

    public static final DeferredItem<DrinkItem> COCONUT_CREAM = ITEMS.register("coconut_cream",
            () -> new DrinkItem(DrinkItem.createDrinkProperties(3, 0.3f), 0));

    // ==========================================
    // MVP COCKTAILS (6)
    // ==========================================
    // Mojito: 14 alcohol points, short Regeneration
    public static final DeferredItem<DrinkItem> MOJITO = ITEMS.register("mojito",
            () -> new DrinkItem(DrinkItem.createDrinkProperties(4, 0.4f,
                    () -> new MobEffectInstance(MobEffects.REGENERATION, 200, 0)), 14));

    // Daiquiri: 19 alcohol points, brief Nausea
    public static final DeferredItem<DrinkItem> DAIQUIRI = ITEMS.register("daiquiri",
            () -> new DrinkItem(DrinkItem.createDrinkProperties(4, 0.3f,
                    () -> new MobEffectInstance(MobEffects.CONFUSION, 100, 0)), 19));

    // Cuba Libre: 16 alcohol points, Speed boost
    public static final DeferredItem<DrinkItem> CUBA_LIBRE = ITEMS.register("cuba_libre",
            () -> new DrinkItem(DrinkItem.createDrinkProperties(5, 0.4f,
                    () -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 300, 0)), 16));

    // Margarita: 22 alcohol points, brief Nausea & Absorption
    public static final DeferredItem<DrinkItem> MARGARITA = ITEMS.register("margarita",
            () -> new DrinkItem(DrinkItem.createDrinkProperties(4, 0.3f,
                    () -> new MobEffectInstance(MobEffects.CONFUSION, 160, 0),
                    () -> new MobEffectInstance(MobEffects.ABSORPTION, 400, 0)), 22));

    // Moscow Mule: 14 alcohol points, Resistance
    public static final DeferredItem<DrinkItem> MOSCOW_MULE = ITEMS.register("moscow_mule",
            () -> new DrinkItem(DrinkItem.createDrinkProperties(4, 0.4f,
                    () -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 300, 0)), 14));

    // Piña Colada: 16 alcohol points, Regeneration & Haste
    public static final DeferredItem<DrinkItem> PINA_COLADA = ITEMS.register("pina_colada",
            () -> new DrinkItem(DrinkItem.createDrinkProperties(6, 0.5f,
                    () -> new MobEffectInstance(MobEffects.REGENERATION, 200, 0),
                    () -> new MobEffectInstance(MobEffects.DIG_SPEED, 300, 0)), 16));

    // ==========================================
    // MOCKTAILS (ALCOHOL-FREE)
    // ==========================================
    public static final DeferredItem<DrinkItem> VIRGIN_MOJITO = ITEMS.register("virgin_mojito",
            () -> new DrinkItem(DrinkItem.createDrinkProperties(4, 0.4f,
                    () -> new MobEffectInstance(MobEffects.REGENERATION, 160, 0)), 0));

    public static final DeferredItem<DrinkItem> VIRGIN_PINA_COLADA = ITEMS.register("virgin_pina_colada",
            () -> new DrinkItem(DrinkItem.createDrinkProperties(6, 0.5f,
                    () -> new MobEffectInstance(MobEffects.ABSORPTION, 300, 0)), 0));

    public static final DeferredItem<MelonCocktailItem> MELON_COCKTAIL = ITEMS.register("melon_cocktail",
            MelonCocktailItem::new);
}
