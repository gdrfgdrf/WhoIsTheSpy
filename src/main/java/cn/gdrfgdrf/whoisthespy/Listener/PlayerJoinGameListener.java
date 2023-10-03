package cn.gdrfgdrf.whoisthespy.Listener;

import cn.gdrfgdrf.whoisthespy.Events.PlayerJoinGameEvent;
import cn.gdrfgdrf.whoisthespy.Game.Game;
import cn.gdrfgdrf.whoisthespy.Game.GameState;
import cn.gdrfgdrf.whoisthespy.Locale.WhoIsTheSpyLocale;
import cn.gdrfgdrf.whoisthespy.Player.PlayerData;
import cn.gdrfgdrf.whoisthespy.Player.PlayerInfo;
import cn.gdrfgdrf.whoisthespy.Player.PlayerState;
import cn.gdrfgdrf.whoisthespy.Utils.Util;
import cn.gdrfgdrf.whoisthespy.WhoIsTheSpy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerJoinGameListener implements Listener {
    private final WhoIsTheSpy whoIsTheSpy;

    public PlayerJoinGameListener(WhoIsTheSpy whoIsTheSpy) {
        this.whoIsTheSpy = whoIsTheSpy;

        Bukkit.getPluginManager().registerEvents(this, whoIsTheSpy.getPlugin());
    }

    @EventHandler
    public void onPlayerJoinGame(PlayerJoinGameEvent event) {
        Game game = event.getGame();
        Player player = event.getPlayer();
        PlayerInfo playerInfo = event.getPlayerInfo();

        if (playerInfo.isInGame()) {
            WhoIsTheSpyLocale.ERROR_ALREADY_JOINED_GAME.message(
                    WhoIsTheSpyLocale.PREFIX,
                    player
            );
            event.setCancelMessage(WhoIsTheSpyLocale.ERROR_ALREADY_JOINED_GAME.toString());
            event.setCancelled(true);
            return;
        }

        if (game.getPlayersInGame().size() >= game.getMaxPlayer()) {
            WhoIsTheSpyLocale.ERROR_GAME_FULL.message(
                    WhoIsTheSpyLocale.PREFIX,
                    player
            );
            event.setCancelMessage(WhoIsTheSpyLocale.ERROR_GAME_FULL.toString());
            event.setCancelled(true);
            return;
        }

        if (game.getGameState() == GameState.STARTED ||
                game.getGameState() == GameState.PREPARING) {
            WhoIsTheSpyLocale.ERROR_GAME_STARTED.message(
                    WhoIsTheSpyLocale.PREFIX,
                    player
            );
            event.setCancelMessage(WhoIsTheSpyLocale.ERROR_GAME_STARTED.toString());
            event.setCancelled(true);
            return;
        }

        if (!game.isEnabled()) {
            WhoIsTheSpyLocale.ERROR_GAME_IS_DISABLED.message(
                    WhoIsTheSpyLocale.PREFIX,
                    player
            );
            event.setCancelMessage(WhoIsTheSpyLocale.ERROR_GAME_IS_DISABLED.toString());
            event.setCancelled(true);
            return;
        }

        playerInfo.setPlayerState(PlayerState.INGAME);
        playerInfo.setCurrentGame(game);
        game.getPlayersInGame().add(playerInfo);
        game.broadcast(
                WhoIsTheSpyLocale.PREFIX,
                WhoIsTheSpyLocale.PLAYER_JOINED_GAME,
                "%PLAYER%",
                player.getDisplayName()
        );
        game.getPhaseHandler().startWaitingPhase();

        PlayerData data = PlayerData.create(player);
        playerInfo.setPlayerData(data);

        if (whoIsTheSpy.getConfig().getConfiguration().isInt("ItemSlot.LEAVE_GAME")) {
            Util.initPlayerStatus(player, whoIsTheSpy.getConfig().getConfiguration().getInt("ItemSlot.LEAVE_GAME"));
        } else {
            Util.initPlayerStatus(player, 8);
        }

        this.whoIsTheSpy.getDisplayScoreboard()
                .setWaitingScoreboard(0, game);
    }

}
