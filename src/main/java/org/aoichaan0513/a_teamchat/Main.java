package org.aoichaan0513.a_teamchat;

import org.aoichaan0513.a_teamchat.API.MainAPI;
import org.aoichaan0513.a_teamchat.Commands.Command.TCCmd;
import org.aoichaan0513.a_teamchat.Listeners.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class Main extends JavaPlugin {

    public static List<UUID> teamChatList = new ArrayList<>();
    public static List<UUID> teamChatList2 = new ArrayList<>();

    private static JavaPlugin javaPlugin;

    @Override
    public void onEnable() {
        javaPlugin = this;

        Bukkit.getConsoleSender().sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + "プラグインを起動しました。");

        getCommand("team").setExecutor(new TCCmd("team"));

        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + "プラグインを起動しました。");
    }

    public static JavaPlugin getInstance() {
        return javaPlugin;
    }

    public static String getMessage(Player p, String str) {
        return ChatColor.BLUE + "[" + (teamChatList.contains(p.getUniqueId()) ? "T" : "G") + "] " + (Bukkit.getScoreboardManager().getMainScoreboard().getEntryTeam(p.getName()) != null ? Bukkit.getScoreboardManager().getMainScoreboard().getEntryTeam(p.getName()).getPrefix() : ChatColor.RESET) + p.getName() + ChatColor.RESET + ChatColor.GREEN + ": " + ChatColor.RESET + ChatColor.translateAlternateColorCodes('&', str);
     }
}
