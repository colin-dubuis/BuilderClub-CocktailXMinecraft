package com.hackathon.cocktailmod.registry;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.crafting.DataComponentIngredient;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;

public class ModBrewing {

    @SubscribeEvent
    public static void onRegisterBrewingRecipes(RegisterBrewingRecipesEvent event) {
        PotionBrewing.Builder builder = event.getBuilder();

        // Water bottle: minecraft:potion with minecraft:water potion contents
        Ingredient waterBottle = DataComponentIngredient.of(
                false,
                PotionContents.createItemStack(Items.POTION, Potions.WATER)
        );

        // Step 1: Ferment into washes
        // Water bottle + Sugar Cane -> Sugar Cane Wash
        builder.addRecipe(waterBottle, Ingredient.of(Items.SUGAR_CANE), new ItemStack(ModItems.SUGAR_CANE_WASH.get()));
        // Water bottle + Potato -> Potato Wash
        builder.addRecipe(waterBottle, Ingredient.of(Items.POTATO), new ItemStack(ModItems.POTATO_WASH.get()));
        // Water bottle + Wheat -> Grain Wash
        builder.addRecipe(waterBottle, Ingredient.of(Items.WHEAT), new ItemStack(ModItems.GRAIN_WASH.get()));
        // Water bottle + Agave -> Agave Wash
        builder.addRecipe(waterBottle, Ingredient.of(ModItems.AGAVE.get()), new ItemStack(ModItems.AGAVE_WASH.get()));

        // Step 2: Distil into spirits using Blaze Powder (heat / distillation)
        // Sugar Cane Wash + Blaze Powder -> White Rum
        builder.addRecipe(Ingredient.of(ModItems.SUGAR_CANE_WASH.get()), Ingredient.of(Items.BLAZE_POWDER), new ItemStack(ModItems.WHITE_RUM.get()));
        // Potato Wash + Blaze Powder -> Vodka
        builder.addRecipe(Ingredient.of(ModItems.POTATO_WASH.get()), Ingredient.of(Items.BLAZE_POWDER), new ItemStack(ModItems.VODKA.get()));
        // Grain Wash + Blaze Powder -> Whisky
        builder.addRecipe(Ingredient.of(ModItems.GRAIN_WASH.get()), Ingredient.of(Items.BLAZE_POWDER), new ItemStack(ModItems.WHISKY.get()));
        // Agave Wash + Blaze Powder -> Tequila
        builder.addRecipe(Ingredient.of(ModItems.AGAVE_WASH.get()), Ingredient.of(Items.BLAZE_POWDER), new ItemStack(ModItems.TEQUILA.get()));

        // Botanical distillation: Vodka + Juniper Berries -> Gin
        builder.addRecipe(Ingredient.of(ModItems.VODKA.get()), Ingredient.of(ModItems.JUNIPER_BERRIES.get()), new ItemStack(ModItems.GIN.get()));
    }
}
