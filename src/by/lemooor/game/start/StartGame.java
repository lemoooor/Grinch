package by.lemooor.game.start;

import by.lemooor.Main;
import by.lemooor.event.Break;
import by.lemooor.event.Interact;
import by.lemooor.game.score.AddScore;
import by.lemooor.game.score.Scores;
import by.lemooor.help.GetPoints;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.io.File;
import java.util.*;

public class StartGame {
    private GetPoints getPoints;
    private AddScore addScore;
    private final Scores scores;

    private final List<String> topList = new ArrayList<>();
    private final Map<String, Integer> top = new HashMap<>();
    private final File file;

    public StartGame(File file) {
        this.file = file;

        scores = new Scores();
    }

    public void setting() {
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();

        getPoints = new GetPoints(file);
        getPoints.setting();

        addScore = new AddScore();

        for (Player player : players) {
            top.put(player.getName(), 0);
            topList.add(player.getName());
        }

        for (Player player : players) {
            createBoard(player);
            player.sendTitle(ChatColor.GREEN + "НАЧАЛО ИГРЫ", null);
        }

        PluginManager manager = Bukkit.getPluginManager();
        Main plugin = Main.getPlugin(Main.class);

        manager.registerEvents(new Break(), plugin);

        manager.registerEvents(new Interact(
                getPoints.getPoints(),
                top,
                topList,
                scores
        ), plugin);
    }

    public void createBoard(Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("statistic", "dummy");

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.YELLOW + "  GRINCH SIMULATOR  ");

        player.setScoreboard(scoreboard);

        String[] entries = {
                "3. " + ChatColor.DARK_GRAY + topList.get(2) + ChatColor.WHITE + " (" +
                        ChatColor.YELLOW + 0 + ChatColor.WHITE + ")",
                "2. " + ChatColor.DARK_GRAY + topList.get(1) + ChatColor.WHITE + " (" +
                        ChatColor.YELLOW + 0 + ChatColor.WHITE + ")",
                "1. " + ChatColor.DARK_GRAY + topList.get(0) + ChatColor.WHITE + " (" +
                        ChatColor.YELLOW + 0 + ChatColor.WHITE + ")",
                "   ",
                "Место в топе >> " + ChatColor.GREEN + (topList.indexOf(player.getName()) + 1),
                "Собрано подарков >> " + ChatColor.GREEN + 0,
                "  ",
                "Осталось времени >> " + ChatColor.GREEN + "03:00",
                "Осталось подарков >> " + ChatColor.GREEN + getPoints.getPoints().size(),
                " "
        };

        for (int index = 0; index < entries.length; index++) {
            String entry = entries[index];

            addScore.addScore(player, entry);
            scores.setScore(entry, index);
        }
    }
}