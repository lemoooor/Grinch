package by.lemooor.event;

import by.lemooor.game.end.EndGame;
import by.lemooor.game.score.MyScore;
import by.lemooor.game.score.Scores;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.*;

public class Interact implements Listener {
    private final MyScore score;

    private final Set<Location> points;
    private final Map<String, Integer> top;
    private final List<String> topList;

    private int gifts;

    public Interact(
            Set<Location> points,
            Map<String, Integer> top,
            List<String> topList,
            Scores scores
    ) {
        this.points = points;
        this.top = top;
        this.topList = topList;

        gifts = points.size();

        score = new MyScore(scores);
        score.setTop(top, topList, gifts);
        score.reload();

        new EndGame(scores).end(points);
    }

    @EventHandler
    public void touch(PlayerInteractEvent event) {
        if (
                event.getAction() == Action.RIGHT_CLICK_BLOCK &&
                        event.getHand() == EquipmentSlot.HAND &&
                        points.contains(event.getClickedBlock().getLocation())
        ) {
            Player player = event.getPlayer();

            gifts--;

            String name = player.getName();
            top.put(name, top.get(name) + 1);

            score.setTop(top, topList, gifts);

            player.sendMessage(
                    ChatColor.YELLOW
                            + "Поздравляем, вы нашли подарок! "
                            + ChatColor.GOLD
                            + "Теперь у вас их "
                            + top.get(player.getName())
            );

            Block clickedBlock = event.getClickedBlock();
            clickedBlock.setType(Material.AIR);

            playEffect(clickedBlock.getLocation());
        }
    }

    private void playEffect(Location location) {
        double locX = location.getX(),
                locZ = location.getZ(),
                helpX = 0.5,
                helpZ = 0.5;

        if (locX < 0 && locZ < 0) {
            helpX = -helpX;
            helpZ = -helpZ;
        } else {
            if (locZ < 0) {
                helpZ = -helpZ;
            }
            if (locX < 0) {
                helpX = -helpX;
            }

        }

        World world = location.getWorld();
        Location loc = new Location(
                world,
                locX + helpX,
                location.getY() + 0.25,
                locZ + helpZ
        );

        world.spawnParticle(Particle.HEART, loc, 4, 0.2, 0.2, 0.2, 0.01);
        world.spawnParticle(Particle.NOTE, loc, 4, 0.2, 0.2, 0.2, 0.01);

        world.playSound(
                location,
                Sound.ENTITY_EXPERIENCE_ORB_PICKUP,
                1,
                (float) 0.5
        );
    }
}