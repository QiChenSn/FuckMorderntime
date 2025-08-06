package com.qichen.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class FuckEggCommand {
    // 布尔变量
    public static boolean isEnabled = false;
    
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("fuckmord")
            .requires(source -> source.hasPermissionLevel(2)) // 需要OP权限
            .executes(FuckEggCommand::toggle)
            .then(argument("value", BoolArgumentType.bool())
                .executes(FuckEggCommand::setValue)));
    }
    
    private static int toggle(CommandContext<ServerCommandSource> context) {
        isEnabled = !isEnabled;
        ServerCommandSource source = context.getSource();
        source.sendMessage(Text.literal("§a[FuckMorderntime] 功能已" + (isEnabled ? "启用" : "禁用")));
        return 1;
    }
    
    private static int setValue(CommandContext<ServerCommandSource> context) {
        boolean value = BoolArgumentType.getBool(context, "value");
        isEnabled = value;
        ServerCommandSource source = context.getSource();
        source.sendMessage(Text.literal("§a[FuckMorderntime] 功能已设置为: " + (isEnabled ? "启用" : "禁用")));
        return 1;
    }
} 