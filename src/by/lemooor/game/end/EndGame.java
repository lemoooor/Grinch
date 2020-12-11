package by.lemooor.game.end;

import by.lemooor.Main;
import by.lemooor.game.score.Scores;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class EndGame {
    private final Scores scores;

    public EndGame(Scores scores) {
        this.scores = scores;
    }

    public void end(Set<Location> points) {
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();

        AtomicInteger counter = new AtomicInteger(180);

        int[] taskID = {0};
        taskID[0] = Bukkit.getScheduler().runTaskTimer(Main.getPlugin(Main.class), () -> {
            int now = counter.get();

            String second = String.format("%02d:%02d", (now / 60), (now % 60 - 1));

            if (now % 60 == 0) {
                second = String.format("%02d:%02d", now / 60 - 1, 59);
            }

            scores.setScore(
                    "Осталось времени >> " + ChatColor.GREEN + second, 7
            );

            if (now == 0) {
                Bukkit.getScheduler().cancelTask(taskID[0]);

                Bukkit.getScheduler().runTaskLater(Main.getPlugin(Main.class), () -> {

                    //Заменить на действие перекидывания в лобби

                    for (Player player : players) {
                        player.kickPlayer(null);
                    }

                    new PasteHeads(points);
                }, 240);
            }

            if ((now <= 10 && now != 0) || now == 30 || now == 60) {
                for (Player player : players) {
                    player.sendTitle(
                            ChatColor.RED + "Осталось ",
                            ChatColor.DARK_RED + "" + now  + " секунд"
                    );
                }
            }

            counter.getAndDecrement();
        }, 0, 20).getTaskId();
    }
}