package org.aoichaan0513.a_teamchat.Commands.Command;

import org.aoichaan0513.a_teamchat.API.MainAPI;
import org.aoichaan0513.a_teamchat.Commands.ICommand;
import org.aoichaan0513.a_teamchat.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.List;

public class TCCmd extends ICommand {

    public TCCmd(String name) {
        super(name);
    }

    @Override
    public void onPlayerCommand(Player sp, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("team")) {
            if (args.length != 0) {
                if (args[0].equalsIgnoreCase("global") || args[0].equalsIgnoreCase("g")) {
                    if (Main.teamChatList.contains(sp.getUniqueId())) {
                        Main.teamChatList.remove(sp.getUniqueId());
                        sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + "チャットモードを" + ChatColor.GREEN + ChatColor.BOLD + ChatColor.UNDERLINE + (Main.teamChatList.contains(sp.getUniqueId()) ? "チーム" : "グローバル") + ChatColor.RESET + ChatColor.GRAY + "に変更しました。");
                        return;
                    } else {
                        sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + "すでにグローバルチャットになっています。\n" +
                                MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + "チームチャットに変更するには" + ChatColor.RED + ChatColor.BOLD + ChatColor.UNDERLINE + "/" + label + " team" + ChatColor.RESET + ChatColor.GRAY + "と実行してください。");
                        return;
                    }
                } else if (args[0].equalsIgnoreCase("team") || args[0].equalsIgnoreCase("t")) {
                    if (!Main.teamChatList.contains(sp.getUniqueId())) {
                        Main.teamChatList.add(sp.getUniqueId());
                        sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + "チャットモードを" + ChatColor.GREEN + ChatColor.BOLD + ChatColor.UNDERLINE + (Main.teamChatList.contains(sp.getUniqueId()) ? "チーム" : "グローバル") + ChatColor.RESET + ChatColor.GRAY + "に変更しました。");
                        return;
                    } else {
                        sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + "すでにチームチャットになっています。\n" +
                                MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + "グローバルチャットに変更するには" + ChatColor.RED + ChatColor.BOLD + ChatColor.UNDERLINE + "/" + label + " global" + ChatColor.RESET + ChatColor.GRAY + "と実行してください。");
                        return;
                    }
                } else {
                    sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + MainAPI.ErrorType.ARGS.getMessage());
                    return;
                }
            } else {
                if (!Main.teamChatList.contains(sp.getUniqueId()))
                    Main.teamChatList.add(sp.getUniqueId());
                else
                    Main.teamChatList.remove(sp.getUniqueId());

                sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + "チャットモードを" + ChatColor.GREEN + ChatColor.BOLD + ChatColor.UNDERLINE + (Main.teamChatList.contains(sp.getUniqueId()) ? "チーム" : "グローバル") + ChatColor.RESET + ChatColor.GRAY + "に変更しました。");
                return;
            }
        } else if (label.equalsIgnoreCase("g")) {
            if (args.length > 0) {
                if (Main.teamChatList.contains(sp.getUniqueId())) {
                    if (!Main.teamChatList2.contains(sp.getUniqueId()))
                        Main.teamChatList2.add(sp.getUniqueId());
                    Main.teamChatList.remove(sp.getUniqueId());
                }

                StringBuffer stringBuffer = new StringBuffer();
                for (String str : args)
                    stringBuffer.append(str + " ");

                Bukkit.broadcastMessage(Main.getMessage(sp, stringBuffer.toString().trim()));

                if (Main.teamChatList2.contains(sp.getUniqueId())) {
                    if (!Main.teamChatList.contains(sp.getUniqueId()))
                        Main.teamChatList.add(sp.getUniqueId());
                    Main.teamChatList2.remove(sp.getUniqueId());
                }
                return;
            } else {
                if (Main.teamChatList.contains(sp.getUniqueId())) {
                    Main.teamChatList.remove(sp.getUniqueId());
                    sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + "チャットモードを" + ChatColor.GREEN + ChatColor.BOLD + ChatColor.UNDERLINE + (Main.teamChatList.contains(sp.getUniqueId()) ? "チーム" : "グローバル") + ChatColor.RESET + ChatColor.GRAY + "に変更しました。");
                    return;
                } else {
                    sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + "すでにグローバルチャットになっています。\n" +
                            MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + "チームチャットに変更するには" + ChatColor.RED + ChatColor.BOLD + ChatColor.UNDERLINE + "/" + label + " team" + ChatColor.RESET + ChatColor.GRAY + "と実行してください。");
                    return;
                }
            }
        } else if (label.equalsIgnoreCase("t")) {
            if (args.length > 0) {
                Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
                if (board.getEntryTeam(sp.getName()) != null) {
                    Team team = board.getEntryTeam(sp.getName());

                    if (!Main.teamChatList.contains(sp.getUniqueId())) {
                        if (!Main.teamChatList2.contains(sp.getUniqueId()))
                            Main.teamChatList2.add(sp.getUniqueId());
                        Main.teamChatList.add(sp.getUniqueId());
                    }

                    StringBuffer stringBuffer = new StringBuffer();
                    for (String str : args)
                        stringBuffer.append(str + " ");

                    final String msg = Main.getMessage(sp, stringBuffer.toString().trim());

                    Bukkit.getConsoleSender().sendMessage(msg);
                    for (Player player : Bukkit.getOnlinePlayers())
                        if (MainAPI.isAdmin(player) || (board.getEntryTeam(player.getName()) != null && team.getName().equals(board.getEntryTeam(player.getName()).getName())))
                            player.sendMessage(msg);

                    if (Main.teamChatList2.contains(sp.getUniqueId())) {
                        if (!Main.teamChatList.contains(sp.getUniqueId()))
                            Main.teamChatList.add(sp.getUniqueId());
                        Main.teamChatList.remove(sp.getUniqueId());
                    }
                    return;
                } else {
                    sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + "チームに参加していないため実行できません。");
                    return;
                }
            } else {
                if (!Main.teamChatList.contains(sp.getUniqueId())) {
                    Main.teamChatList.add(sp.getUniqueId());
                    sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + "チャットモードを" + ChatColor.GREEN + ChatColor.BOLD + ChatColor.UNDERLINE + (Main.teamChatList.contains(sp.getUniqueId()) ? "チーム" : "グローバル") + ChatColor.RESET + ChatColor.GRAY + "に変更しました。");
                    return;
                } else {
                    sp.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + "すでにチームチャットになっています。\n" +
                            MainAPI.getPrefix(MainAPI.PrefixType.SECONDARY) + "グローバルチャットに変更するには" + ChatColor.RED + ChatColor.BOLD + ChatColor.UNDERLINE + "/" + label + " global" + ChatColor.RESET + ChatColor.GRAY + "と実行してください。");
                    return;
                }
            }
        }
    }

    @Override
    public void onBlockCommand(BlockCommandSender bs, Command cmd, String label, String[] args) {
        bs.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + MainAPI.ErrorType.PLAYER.getMessage());
        return;
    }

    @Override
    public void onConsoleCommand(ConsoleCommandSender cs, Command cmd, String label, String[] args) {
        cs.sendMessage(MainAPI.getPrefix(MainAPI.PrefixType.ERROR) + MainAPI.ErrorType.PLAYER.getMessage());
        return;
    }

    @Override
    public List<String> onPlayerTabComplete(Player sp, Command cmd, String alias, String[] args) {
        return null;
    }

    @Override
    public List<String> onBlockTabComplete(BlockCommandSender bs, Command cmd, String alias, String[] args) {
        return null;
    }

    @Override
    public List<String> onConsoleTabComplete(ConsoleCommandSender cs, Command cmd, String alias, String[] args) {
        return null;
    }
}
