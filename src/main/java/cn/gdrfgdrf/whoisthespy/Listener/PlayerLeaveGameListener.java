package cn.gdrfgdrf.whoisthespy.Listener;

import cn.gdrfgdrf.whoisthespy.Events.PlayerLeaveGameEvent;
import cn.gdrfgdrf.whoisthespy.Game.Game;
import cn.gdrfgdrf.whoisthespy.Game.GameState;
import cn.gdrfgdrf.whoisthespy.Player.PlayerInfo;
import cn.gdrfgdrf.whoisthespy.Player.PlayerState;
import cn.gdrfgdrf.whoisthespy.WhoIsTheSpy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static cn.gdrfgdrf.whoisthespy.Locale.WhoIsTheSpyLocale.*;

public class PlayerLeaveGameListener implements Listener {

    private final WhoIsTheSpy whoIsTheSpy;

    public PlayerLeaveGameListener(WhoIsTheSpy whoIsTheSpy) {
        this.whoIsTheSpy = whoIsTheSpy;

        Bukkit.getPluginManager().registerEvents(this, whoIsTheSpy.getPlugin());
    }

    @EventHandler
    public void onPlayerLeaveGame(PlayerLeaveGameEvent event) {
        Game game = event.getGame();
        Player player = event.getPlayer();
        PlayerInfo playerInfo = event.getPlayerInfo();

        if (game.getPlayersInGame().size() < game.getMinPlayer()) {
            game.setGameState(GameState.WAITING);
            game.getPhaseHandler().startWaitingPhase();
        }

        if (playerInfo.getPlayerData() != null) {
            playerInfo.getPlayerData().apply(player);
            playerInfo.setPlayerData(null);
        }

        playerInfo.setPlayerState(PlayerState.DEFAULT);
        playerInfo.setCurrentGame(null);

        player.setExp(0);
        player.setLevel(0);

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(whoIsTheSpy.getPlugin(), () -> player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard()), 20L);

        game.broadcast(PREFIX, PLAYER_LEAVED_GAME, "%PLAYER%", player.getDisplayName());

        this.whoIsTheSpy.getDisplayScoreboard().setWaitingScoreboard(0, game);

        PLAYER_LEFT_GAME.message(PREFIX, player, "%GAME%", game.getName());
    }

}
