package by.lemooor.game.end;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class SendMessage {
    public SendMessage(Map<String, Integer> topMap, List<String> topList) {
        sendTitle(topMap, topList);

        String[] messages = {
                ChatColor.GREEN + "-----------------------------------",
                " ",
                ChatColor.YELLOW + "         GRINCH SIMULATOR          ",
                " "
        };

        for (String message : messages) {
            Bukkit.broadcastMessage(message);
        }

        sendMessages(topMap, topList);

        Bukkit.broadcastMessage(messages[1]);
        Bukkit.broadcastMessage(messages[0]);
    }

    private void sendTitle(Map<String, Integer> topMap, List<String> topList) {
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();

        for (Player player : players) {
            String name = player.getName();

            if (topList.indexOf(player.getName()) == 0) {
                player.sendTitle(
                        ChatColor.GREEN + "Вы победили",
                        ChatColor.DARK_GREEN
                                + "собрав "
                                + topMap.get(name)
                                + " подарков"
                );
            } else {
                player.sendTitle(
                        ChatColor.RED + "Игра окончена",
                        ChatColor.RED + "ваше место - " + (topList.indexOf(name) + 1)
                );
            }
        }
    }

    private void sendMessages(Map<String, Integer> topMap, List<String> topList) {
        for (int i = 0; i < 3; i++) {
            String player = topList.get(i);

            Bukkit.broadcastMessage(
                    ChatColor.YELLOW + ""
                            + (i + 1) + "-e место " + ChatColor.DARK_GRAY + player
                            + " " + ChatColor.YELLOW + topMap.get(player)
            );
        }
    }
}