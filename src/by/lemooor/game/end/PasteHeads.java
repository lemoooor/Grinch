package by.lemooor.game.end;

import by.lemooor.Main;
import by.lemooor.MyWorldEdit;
import org.bukkit.Location;

import java.util.Random;
import java.util.Set;

public class PasteHeads {
    public PasteHeads(Set<Location> points) {
        Random random = new Random();
        Main plugin = Main.getPlugin(Main.class);

        for (Location location : points) {
            MyWorldEdit.pasteSchematic(
                    "present_" + (random.nextInt(7) + 1),
                    location, false, plugin
            );
        }
    }
}
