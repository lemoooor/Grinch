package by.lemooor.game.score;

import by.lemooor.Main;
import by.lemooor.game.end.SendMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;

public class MyScore {
    private final AddScore addScore;
    private final Scores scores;

    private Map<String, Integer> topMap;
    private List<String> topList;

    private int gifts;

    public MyScore(Scores scores) {
        addScore = new AddScore();
        this.scores = scores;
    }

    public void reload() {
        int[] taskID = {0};
        taskID[0] = Bukkit.getScheduler().runTaskTimer(
                Main.getPlugin(Main.class), () -> {
                    reloadStats();

                    if(scores.getScore(7).contains("00:00")) {
                        Bukkit.getScheduler().cancelTask(taskID[0]);

                        new SendMessage(topMap, topList);
                    }

                    Collection<? extends Player> players = Bukkit.getOnlinePlayers();

                    for (Player player : players) {
                        addScore.deleteScore(player);

                        for (int top = 3; top >= 1; top--) {
                            addScore.addScore(
                                    player,
                                    top
                                            + ". "
                                            + ChatColor.DARK_GRAY
                                            + topList.get(top - 1)
                                            + ChatColor.WHITE
                                            + " ("
                                            + ChatColor.YELLOW
                                            + topMap.get(topList.get(top - 1))
                                            + ChatColor.WHITE + ")"

                            );
                        }

                        addScore.addScore(player, scores.getScore(3));

                        addScore.addScore(
                                player,
                                "Место в топе >> "
                                        + ChatColor.GREEN
                                        + (topList.indexOf(player.getName()) + 1)
                        );

                        addScore.addScore(
                                player,
                                "Собрано подарков >> "
                                        + ChatColor.GREEN
                                        + (topMap.get(player.getName()) + 1)
                        );

                        String[] entries = {
                                scores.getScore(6),
                                scores.getScore(7),
                                "Осталось подарков >> " + ChatColor.GREEN + gifts,
                                scores.getScore(9)
                        };

                        for (String entry : entries) {
                            addScore.addScore(player, entry);
                        }
                    }
                }, 0, 15
        ).getTaskId();
    }

    public void reloadStats() {
        for (int first = 0; first < topList.size(); first++) {
            for (int second = first + 1; second < topList.size(); second++) {
                String nameFirst = topList.get(first),
                        nameSecond = topList.get(second);

                if (topMap.get(nameFirst) < topMap.get(nameSecond)) {
                    topList.set(second, nameFirst);
                    topList.set(first, nameSecond);
                }
            }
        }
    }

    public void setTop(Map<String, Integer> topMap, List<String> topList, int gifts) {
        this.topMap = topMap;
        this.gifts = gifts;

        if (this.topList == null) {
            this.topList = topList;
        }
    }
}