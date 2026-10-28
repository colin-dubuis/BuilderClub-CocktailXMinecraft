package com.hackathon.cocktailmod.alcohol;

import com.hackathon.cocktailmod.network.AlcoholSyncPayload;
import com.hackathon.cocktailmod.registry.ModAttachments;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.network.PacketDistributor;

public class AlcoholCommand {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(
            Commands.literal("alcohol")
                // /alcohol get [target]
                .then(Commands.literal("get")
                    .executes(ctx -> getAlcohol(ctx.getSource(), ctx.getSource().getPlayerOrException()))
                    .then(Commands.argument("target", EntityArgument.player())
                        .requires(src -> src.hasPermission(2))
                        .executes(ctx -> getAlcohol(ctx.getSource(), EntityArgument.getPlayer(ctx, "target")))))
                // /alcohol set <0-100> [target]
                .then(Commands.literal("set")
                    .then(Commands.argument("value", IntegerArgumentType.integer(0, 100))
                        .executes(ctx -> setAlcohol(ctx.getSource(), ctx.getSource().getPlayerOrException(), IntegerArgumentType.getInteger(ctx, "value")))
                        .then(Commands.argument("target", EntityArgument.player())
                            .requires(src -> src.hasPermission(2))
                            .executes(ctx -> setAlcohol(ctx.getSource(), EntityArgument.getPlayer(ctx, "target"), IntegerArgumentType.getInteger(ctx, "value"))))))
                // /alcohol add <-100 to 100> [target]
                .then(Commands.literal("add")
                    .then(Commands.argument("value", IntegerArgumentType.integer(-100, 100))
                        .executes(ctx -> addAlcohol(ctx.getSource(), ctx.getSource().getPlayerOrException(), IntegerArgumentType.getInteger(ctx, "value")))
                        .then(Commands.argument("target", EntityArgument.player())
                            .requires(src -> src.hasPermission(2))
                            .executes(ctx -> addAlcohol(ctx.getSource(), EntityArgument.getPlayer(ctx, "target"), IntegerArgumentType.getInteger(ctx, "value"))))))
                // /alcohol reset [target]
                .then(Commands.literal("reset")
                    .executes(ctx -> setAlcohol(ctx.getSource(), ctx.getSource().getPlayerOrException(), 0))
                    .then(Commands.argument("target", EntityArgument.player())
                        .requires(src -> src.hasPermission(2))
                        .executes(ctx -> setAlcohol(ctx.getSource(), EntityArgument.getPlayer(ctx, "target"), 0))))
        );
    }

    private static int getAlcohol(CommandSourceStack source, ServerPlayer target) {
        int level = target.getData(ModAttachments.ALCOHOL);
        ChatFormatting color = level < 40 ? ChatFormatting.GREEN : (level < 60 ? ChatFormatting.YELLOW : ChatFormatting.RED);
        String zone = level < 40 ? "Safe" : (level < 60 ? "Warning" : "Critical");

        source.sendSuccess(() -> Component.literal(target.getGameProfile().getName() + "'s alcohol level: ")
                .append(Component.literal(level + "g (" + zone + ")").withStyle(color)), false);
        return level;
    }

    private static int setAlcohol(CommandSourceStack source, ServerPlayer target, int value) {
        int clamped = Math.max(0, Math.min(100, value));
        target.setData(ModAttachments.ALCOHOL, clamped);
        PacketDistributor.sendToPlayer(target, new AlcoholSyncPayload(clamped));

        ChatFormatting color = clamped < 40 ? ChatFormatting.GREEN : (clamped < 60 ? ChatFormatting.YELLOW : ChatFormatting.RED);
        source.sendSuccess(() -> Component.literal("Set " + target.getGameProfile().getName() + "'s alcohol level to ")
                .append(Component.literal(clamped + "g").withStyle(color)), true);
        return clamped;
    }

    private static int addAlcohol(CommandSourceStack source, ServerPlayer target, int delta) {
        int current = target.getData(ModAttachments.ALCOHOL);
        return setAlcohol(source, target, current + delta);
    }
}
