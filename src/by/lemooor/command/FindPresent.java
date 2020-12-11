package by.lemooor.command;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FindPresent implements CommandExecutor {
    private List<String> locations = new ArrayList<>();
    private File points;

    public FindPresent(File points) {
        this.points = points;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            FileConfiguration config = YamlConfiguration.loadConfiguration(points);

            Location Position = ((Player) commandSender).getLocation();

            for (double x = Position.getX() - 100; x < Position.getX() + 100; x++) {
                for (double y = Position.getY() - 20; y < Position.getY() + 20; y++) {
                    for (double z = Position.getZ() - 100; z < Position.getZ() + 100; z++) {
                        if (new Location(
                                Position.getWorld(), x, y, z
                        ).getBlock().getType() == Material.SPONGE) {
                            locations.add(
                                    (int) x + " " + (int) y + " " + (int) z
                            );
                        }
                    }
                }
            }

            config.set("points", locations);

            try {
                config.save(points);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }
}