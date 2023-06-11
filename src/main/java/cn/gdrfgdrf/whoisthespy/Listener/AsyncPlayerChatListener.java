package cn.gdrfgdrf.whoisthespy.Listener;

import cn.gdrfgdrf.whoisthespy.Game.Game;
import cn.gdrfgdrf.whoisthespy.Player.PlayerInfo;
import cn.gdrfgdrf.whoisthespy.WhoIsTheSpy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChatListener implements Listener {

    private final WhoIsTheSpy whoIsTheSpy;

    public AsyncPlayerChatListener(WhoIsTheSpy whoIsTheSpy) {
        this.whoIsTheSpy = whoIsTheSpy;

        Bukkit.getPluginManager().registerEvents(this, whoIsTheSpy.getPlugin());
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        PlayerInfo playerInfo = PlayerInfo.getFromPlayer(player);

        if (playerInfo != null) {
            if (playerInfo.isInGame()) {
                Game game = playerInfo.getCurrentGame();

                if (whoIsTheSpy.getGameChatFormat() != null) {
                    String format = whoIsTheSpy.getGameChatFormat()
                            .replace("%GAME%", game.getName())
                            .replace("%PLAYER%", player.getDisplayName())
                            .replace("%MESSAGE%", event.getMessage());

                    event.setFormat(format);
                }

                event.getRecipients().clear();

                for (PlayerInfo info : game.getPlayersInGame()) {
                    event.getRecipients().add(info.getPlayer());
                }
            } else {
                for (Player recipients : Bukkit.getOnlinePlayers()) {
                    if (PlayerInfo.isInGame(recipients)) {
                        event.getRecipients().remove(recipients);
                    }
                }
            }
        }
    }

}
