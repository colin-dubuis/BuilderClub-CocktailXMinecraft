package com.hackathon.cocktailmod.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class MelonCocktailItem extends DrinkItem {
    public MelonCocktailItem() {
        super(createDrinkProperties(
                4,
                0.3f,
                () -> new MobEffectInstance(MobEffects.CONFUSION, 300, 0)
        ), 0);
    }
}
