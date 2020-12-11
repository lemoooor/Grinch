package by.lemooor.event;

import by.lemooor.Main;
import by.lemooor.game.start.StartGame;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

public class Join implements Listener {
    private boolean isFirst = true;
    private final File file;

    public Join(File file) {
        this.file = file;
    }

    @EventHandler
    public void join(PlayerJoinEvent event) {
        if (isFirst) {
            AtomicInteger counter = new AtomicInteger(5);

            int[] taskId = {0};
            taskId[0] = Bukkit.getScheduler().runTaskTimer(Main.getPlugin(Main.class), () -> {
                Bukkit.broadcastMessage(ChatColor.GOLD + "Осталось " + counter + " секунд !");

                if (counter.get() == 1) {
                    Bukkit.getScheduler().cancelTask(taskId[0]);
                }

                counter.getAndDecrement();
            }, 20, 20).getTaskId();

            Bukkit.getScheduler().runTaskLater(Main.getPlugin(Main.class), () -> new StartGame(file).setting(), 100);
        }

        isFirst = false;
    }
}