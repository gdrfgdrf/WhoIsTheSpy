package cn.gdrfgdrf.whoisthespy.Listener;

import cn.gdrfgdrf.whoisthespy.Command.WhoIsTheSpyCommand;
import cn.gdrfgdrf.whoisthespy.Game.Game;
import cn.gdrfgdrf.whoisthespy.Locale.WhoIsTheSpyLocale;
import cn.gdrfgdrf.whoisthespy.WhoIsTheSpy;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.io.IOException;
import java.util.Iterator;

public class BlockBreakListener implements Listener {
    private final WhoIsTheSpy whoIsTheSpy;

    public BlockBreakListener(WhoIsTheSpy whoIsTheSpy) {
        this.whoIsTheSpy = whoIsTheSpy;

        Bukkit.getPluginManager().registerEvents(this, whoIsTheSpy.getPlugin());
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (!block.getType().name().contains("SIGN")) {
            return;
        }

        for (Game game : whoIsTheSpy.getGames()) {
            Iterator<Location> iterator = game.getSigns().iterator();

            while (iterator.hasNext()) {
                Location location = iterator.next();

                if (block.getLocation().equals(location)) {
                    if (!player.hasPermission(
                            WhoIsTheSpyCommand.PERMISSION_ADMINISTRATOR_PREFIX + "removeSign"
                    )) {
                        event.setCancelled(true);
                        return;
                    }

                    iterator.remove();

                    try {
                        game.saveData();
                        WhoIsTheSpyLocale.SUCCESS_REMOVE_SIGN.message(
                                WhoIsTheSpyLocale.PREFIX,
                                player,
                                "%GAME%",
                                game.getName()
                        );
                    } catch (IOException e) {
                        WhoIsTheSpyLocale.ERROR_REMOVE_SIGN.message(
                                WhoIsTheSpyLocale.PREFIX,
                                player
                        );
                    }

                    return;
                }
            }
        }
    }

}
