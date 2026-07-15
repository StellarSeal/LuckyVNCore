package com.seul.luckyvncore;

import com.seul.luckyvncore.Hook.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Objects;

public final class LuckyVNCore extends JavaPlugin implements CommandExecutor {

    public static LuckyVNCore plugin;
    @Override
    public void onEnable() {

        plugin = this;
        Placeholder.instance = new Placeholder();
        Placeholder.instance.register();
        Objects.requireNonNull(getCommand("tb")).setExecutor(this);
        Objects.requireNonNull(getCommand("lkchat")).setExecutor(this);

    }

    @Override
    public void onDisable() {
        Placeholder.instance.unregister();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("luckyvncore." + command.getName())) {
            sender.sendMessage(ChatColor.RED + "Bạn không có quyền dùng lệnh này.");
            return true;
        }

        if (command.getName().equalsIgnoreCase("tb")) {
            if (args.length == 0) return false;
            String message = LKUtils.color(String.join(" ", args).replace("/n", "\n"));
            Bukkit.broadcastMessage(message);
            Bukkit.getConsoleSender().sendMessage(message);
            return true;
        }

        if (command.getName().equalsIgnoreCase("lkchat")) {
            if (args.length < 2) return false;
            Player player = Bukkit.getPlayerExact(args[0]);
            if (player == null) {
                sender.sendMessage(ChatColor.RED + "Người chơi không trực tuyến.");
                return true;
            }
            player.chat(String.join(" ", Arrays.copyOfRange(args, 1, args.length)));
            return true;
        }

        return false;
    }
}
