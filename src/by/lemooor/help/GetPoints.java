package by.lemooor.help;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GetPoints {
    private final File file;
    private final Set<Location> points = new HashSet<>();

    public GetPoints(File file) {
        this.file = file;
    }

    public void setting() {
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        List<String> strings = config.getStringList("points");

        World world = Bukkit.getWorld("world");

        for (String location : strings) {
            String[] blocks = location.split(" ");

            points.add(new Location(
                    world,
                    Double.parseDouble(blocks[0]),
                    Double.parseDouble(blocks[1]),
                    Double.parseDouble(blocks[2])
            ));
        }
    }

    public Set<Location> getPoints() {
        return points;
    }
}