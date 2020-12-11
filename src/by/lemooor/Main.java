package by.lemooor;

import by.lemooor.event.Join;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        File file = new File(getDataFolder() + File.separator + "grinch.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Bukkit.getPluginManager().registerEvents(new Join(file), this);
    }
}