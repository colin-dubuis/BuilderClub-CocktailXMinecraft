package com.hackathon.cocktailmod.item;

import com.hackathon.cocktailmod.network.AlcoholSyncPayload;
import com.hackathon.cocktailmod.registry.ModAttachments;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;
import java.util.function.Supplier;

public class DrinkItem extends Item {
    private static final int DRINK_DURATION = 32;
    private final int alcoholPoints;

    public DrinkItem(Properties properties, int alcoholPoints) {
        super(properties.stacksTo(16));
        this.alcoholPoints = alcoholPoints;
    }

    public static Properties createDrinkProperties(int nutrition, float saturation, Supplier<MobEffectInstance>... effects) {
        FoodProperties.Builder foodBuilder = new FoodProperties.Builder()
                .nutrition(nutrition)
                .saturationModifier(saturation)
                .alwaysEdible();

        if (effects != null) {
            for (Supplier<MobEffectInstance> effect : effects) {
                foodBuilder.effect(effect, 1.0f);
            }
        }

        return new Item.Properties().food(foodBuilder.build());
    }

    public int getAlcoholPoints() {
        return alcoholPoints;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving) {
        super.finishUsingItem(stack, level, entityLiving);

        if (entityLiving instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
            serverPlayer.awardStat(Stats.ITEM_USED.get(this));

            if (alcoholPoints > 0) {
                int current = serverPlayer.getData(ModAttachments.ALCOHOL);
                int updated = Math.min(100, current + alcoholPoints);
                serverPlayer.setData(ModAttachments.ALCOHOL, updated);
                PacketDistributor.sendToPlayer(serverPlayer, new AlcoholSyncPayload(updated));
            }
        }

        if (stack.isEmpty()) {
            return new ItemStack(Items.GLASS_BOTTLE);
        } else {
            if (entityLiving instanceof Player player && !player.hasInfiniteMaterials()) {
                ItemStack emptyBottle = new ItemStack(Items.GLASS_BOTTLE);
                if (!player.getInventory().add(emptyBottle)) {
                    player.drop(emptyBottle, false);
                }
            }
            return stack;
        }
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return DRINK_DURATION;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public SoundEvent getDrinkingSound() {
        return SoundEvents.GENERIC_DRINK;
    }

    @Override
    public SoundEvent getEatingSound() {
        return SoundEvents.GENERIC_DRINK;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);

        if (alcoholPoints <= 0) {
            tooltipComponents.add(Component.translatable("tooltip.cocktailmod.alcohol_free").withStyle(ChatFormatting.GREEN));
        } else {
            ChatFormatting color = alcoholPoints < 16 ? ChatFormatting.GOLD : ChatFormatting.RED;
            tooltipComponents.add(Component.translatable("tooltip.cocktailmod.alcohol_content", alcoholPoints).withStyle(color));
        }
    }
}
