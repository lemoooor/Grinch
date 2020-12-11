package by.lemooor.event;

import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class Break implements Listener {
    public void nope(BlockBreakEvent event) {
        event.setCancelled(true);
    }
}
