package fr.akkun.newmerias2core.rpg;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fr.akkun.newmerias2core.NewmeriaS2Core;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.PermissionCheck;
import net.minecraft.server.permissions.Permissions;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

/**
 * Server-side "/rpg add ..." admin subcommands. The plain "/rpg" (opening the stats screen)
 * is handled client-side in {@code RpgClientCommands} and never reaches the server.
 */
@EventBusSubscriber(modid = NewmeriaS2Core.MOD_ID)
public class RpgCommand {
    private static final PermissionCheck ADMIN_PERMISSION = new PermissionCheck.Require(Permissions.COMMANDS_GAMEMASTER);

    @SubscribeEvent
    static void onRegisterCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("rpg")
                .then(Commands.literal("add")
                        .requires(Commands.hasPermission(ADMIN_PERMISSION))
                        .then(Commands.literal("points")
                                .then(Commands.argument("amount", IntegerArgumentType.integer(1))
                                        .executes(ctx -> addPoints(ctx.getSource(), IntegerArgumentType.getInteger(ctx, "amount")))))
                        .then(Commands.literal("level")
                                .then(Commands.argument("amount", IntegerArgumentType.integer(1))
                                        .executes(ctx -> addLevels(ctx.getSource(), IntegerArgumentType.getInteger(ctx, "amount")))))));
    }

    private static int addPoints(CommandSourceStack source, int amount) throws CommandSyntaxException {
        ServerPlayer player = source.getPlayerOrException();
        RpgData updated = player.getData(RpgAttachments.RPG_DATA).addPoints(amount);
        player.setData(RpgAttachments.RPG_DATA, updated);
        RpgAttributeModifiers.apply(player, updated);
        source.sendSuccess(() -> Component.translatable("commands.newmerias2core.rpg.add_points",
                amount, updated.level(), updated.unspentStatPoints()), true);
        return 1;
    }

    private static int addLevels(CommandSourceStack source, int amount) throws CommandSyntaxException {
        ServerPlayer player = source.getPlayerOrException();
        RpgData updated = player.getData(RpgAttachments.RPG_DATA).addLevels(amount);
        player.setData(RpgAttachments.RPG_DATA, updated);
        RpgAttributeModifiers.apply(player, updated);
        source.sendSuccess(() -> Component.translatable("commands.newmerias2core.rpg.add_level",
                amount, updated.level(), updated.unspentStatPoints()), true);
        return 1;
    }
}
