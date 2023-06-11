package cn.gdrfgdrf.whoisthespy.Listener;

import cn.gdrfgdrf.whoisthespy.Game.Game;
import cn.gdrfgdrf.whoisthespy.Player.PlayerInfo;
import cn.gdrfgdrf.whoisthespy.Player.PlayerState;
import cn.gdrfgdrf.whoisthespy.WhoIsTheSpy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final WhoIsTheSpy whoIsTheSpy;

    public PlayerQuitListener(WhoIsTheSpy whoIsTheSpy) {
        this.whoIsTheSpy = whoIsTheSpy;

        Bukkit.getPluginManager().registerEvents(this, whoIsTheSpy.getPlugin());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        PlayerInfo playerInfo = PlayerInfo.getFromPlayer(player);
        if (playerInfo == null || playerInfo.getPlayerState() == PlayerState.DEFAULT) {
            return;
        }

        Game game = playerInfo.getCurrentGame();

        if (game != null) {
            game.removePlayer(playerInfo);
        }

    }

}
