package org.aoichaan0513.a_teamchat.Listeners;

import org.aoichaan0513.a_teamchat.API.MainAPI;
import org.aoichaan0513.a_teamchat.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class PlayerListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onAsyncChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();

        final String msg = Main.getMessage(p, e.getMessage());

        Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
        if (board.getEntryTeam(p.getName()) != null) {
            e.setCancelled(true);
            if (Main.teamChatList.contains(p.getUniqueId())) {
                Team team = board.getEntryTeam(p.getName());

                Bukkit.getConsoleSender().sendMessage(msg);
                for (Player player : Bukkit.getOnlinePlayers())
                    if (MainAPI.isAdmin(player) || (board.getEntryTeam(player.getName()) != null && team.getName().equals(board.getEntryTeam(player.getName()).getName())))
                        player.sendMessage(msg);
                return;
            } else {
                Bukkit.broadcastMessage(msg);
                return;
            }
        } else {
            e.setCancelled(true);
            Bukkit.broadcastMessage(msg);
            return;
        }
    }
}
