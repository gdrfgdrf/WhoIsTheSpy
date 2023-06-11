package cn.gdrfgdrf.whoisthespy.Phase;

import cn.gdrfgdrf.whoisthespy.Game.Game;
import cn.gdrfgdrf.whoisthespy.Player.PlayerInfo;
import cn.gdrfgdrf.whoisthespy.WhoIsTheSpy;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;

import static cn.gdrfgdrf.whoisthespy.Locale.WhoIsTheSpyLocale.*;

public class WaitingPhase implements Runnable {

    private int countdown;
    private final WhoIsTheSpy whoIsTheSpy;
    private final Game game;

    public WaitingPhase(WhoIsTheSpy whoIsTheSpy, Game game) {
        this.whoIsTheSpy = whoIsTheSpy;
        this.game = game;
        this.countdown = game.getCountdown();
    }

    @Override
    public void run() {
        if (countdown < 0) {
            game.getPhaseHandler().cancelWaitingPhase();
            return;
        }

        if (countdown == 0) {
            game.getPhaseHandler().callGameStartEvent();
            game.getPhaseHandler().cancelWaitingPhase();
            return;
        }

        if (game.getPlayersInGame().size() >= game.getMinPlayer()) {
            List<Player> players = new LinkedList<>();
            for (PlayerInfo playerInfo : game.getPlayersInGame()) {
                players.add(playerInfo.getPlayer());
            }

            if (countdown == 30 ||
                    countdown == 20 ||
                    countdown == 10 ||
                    countdown == 5 ||
                    countdown == 4 ||
                    countdown == 3 ||
                    countdown == 2 ||
                    countdown == 1) {

                game.broadcast(PREFIX, TIME_LEFT, "%TIME%", String.valueOf(countdown));
            }

            for (Player player : players) {
                player.setLevel(countdown);
                float exp = (float) countdown / (float) game.getCountdown();
                player.setExp(exp);
            }
        } else {
            game.broadcast(PREFIX, COUNTDOWN_ABORTED);

            for (PlayerInfo playerInfo : game.getPlayersInGame()) {
                Player player = playerInfo.getPlayer();
                player.setExp(0);
                player.setLevel(0);
            }

            countdown = -1;
        }

        this.whoIsTheSpy.getDisplayScoreboard().setWaitingScoreboard(countdown, game);

        countdown--;
    }
}
