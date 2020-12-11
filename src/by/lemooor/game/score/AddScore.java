package by.lemooor.game.score;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Set;

public class AddScore {
    public void addScore(Player player, String after) {
        Scoreboard scoreboard = player.getScoreboard();
        Objective objective = scoreboard.getObjective(DisplaySlot.SIDEBAR);

        Score score = objective.getScore(after);
        score.setScore(scoreboard.getEntries().size() + 1);

        player.setScoreboard(scoreboard);
    }

    public void deleteScore(Player player) {
        Scoreboard scoreboard = player.getScoreboard();

        Set<String> entries = scoreboard.getEntries();

        for (String entry : entries) {
            scoreboard.resetScores(entry);
        }

        player.setScoreboard(scoreboard);
    }
}