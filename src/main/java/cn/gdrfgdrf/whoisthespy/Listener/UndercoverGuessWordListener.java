package cn.gdrfgdrf.whoisthespy.Listener;

import cn.gdrfgdrf.whoisthespy.Events.UndercoverGuessWordEvent;
import cn.gdrfgdrf.whoisthespy.Game.Game;
import cn.gdrfgdrf.whoisthespy.Locale.WhoIsTheSpyLocale;
import cn.gdrfgdrf.whoisthespy.Player.PlayerInfo;
import cn.gdrfgdrf.whoisthespy.Utils.Util;
import cn.gdrfgdrf.whoisthespy.WhoIsTheSpy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class UndercoverGuessWordListener implements Listener {

    private final WhoIsTheSpy whoIsTheSpy;

    public UndercoverGuessWordListener(WhoIsTheSpy whoIsTheSpy) {
        this.whoIsTheSpy = whoIsTheSpy;

        Bukkit.getPluginManager().registerEvents(this, whoIsTheSpy.getPlugin());
    }

    @EventHandler
    public void onUndercoverGuessWord(UndercoverGuessWordEvent event) {
        Game game = event.getGame();
        PlayerInfo playerInfo = event.getPlayerInfo();
        Player player = playerInfo.getPlayer();
        String word = event.getGuess();

        if (!Util.isEmpty(word)) {
            if (game.getUndercoverGuess() == null) {
                game.setUndercoverGuess(word);

                game.broadcast(WhoIsTheSpyLocale.PREFIX, WhoIsTheSpyLocale.UNDERCOVER_GUESS_WORD_MESSAGE, "%WORD%", word);

                if (word.equals(game.getWord())) {
                    game.getPhaseHandler().getGamePhase().finishGameForUndercoverWin();
                } else {
                    game.getPhaseHandler().getGamePhase().finishGameForUndercoverLose();
                }
            } else {
                WhoIsTheSpyLocale.ALREADY_GUESS_WORD.message(WhoIsTheSpyLocale.PREFIX, player);
            }
        } else {
            WhoIsTheSpyLocale.GUESS_WORD_TYPE_FULL.message(WhoIsTheSpyLocale.PREFIX, player);
        }
    }

}
