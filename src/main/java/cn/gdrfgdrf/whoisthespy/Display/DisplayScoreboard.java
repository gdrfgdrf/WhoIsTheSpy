package cn.gdrfgdrf.whoisthespy.Display;

import cn.gdrfgdrf.whoisthespy.Game.Game;
import cn.gdrfgdrf.whoisthespy.Locale.WhoIsTheSpyLocale;
import cn.gdrfgdrf.whoisthespy.Player.PlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class DisplayScoreboard {
    public void setWaitingScoreboard(int timeLeft, Game game) {
        if (WhoIsTheSpyLocale.SCOREBOARD_WAITING.getValues().length < 2)
            return;

        Scoreboard playerboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = playerboard.registerNewObjective("Score", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        String text_value = WhoIsTheSpyLocale.SCOREBOARD_WAITING.getValue()
                .replace("%CURRENT_PLAYER%", game.getPlayersInGame().size() + "")
                .replace("%MAX_PLAYER%", game.getMaxPlayer() + "")
                .replace("%GAME%", game.getName());
        ;

        if (game.getPlayersInGame().size() >= game.getMinPlayer()) {
            text_value = text_value.replace("%TIME%", timeLeft + "");
        } else {
            text_value = text_value.replace("%TIME%", WhoIsTheSpyLocale.WAITING_FOR_PLAYER.toString());
        }

        objective.setDisplayName(text_value);

        int activePlayers = game.getPlayersInGame().size();

        for (int i = 0; i < WhoIsTheSpyLocale.SCOREBOARD_WAITING.getValues().length - 1; i++) {
            String text = WhoIsTheSpyLocale.SCOREBOARD_WAITING.getValue(i + 1)
                    .replace("%CURRENT_PLAYER%", activePlayers + "")
                    .replace("%MAX_PLAYER%", game.getMaxPlayer() + "")
                    .replace("%GAME%", game.getName());

            if (game.getPlayersInGame().size() >= game.getMinPlayer()) {
                text = text.replace("%TIME%", timeLeft + "");
            } else {
                text = text.replace("%TIME%", WhoIsTheSpyLocale.WAITING_FOR_PLAYER.toString());
            }

            Score score = objective.getScore(text);
            score.setScore(WhoIsTheSpyLocale.SCOREBOARD_WAITING.getValues().length - 1 - i);
        }

        for (PlayerInfo playerInfo : game.getPlayersInGame()) {
            playerInfo.getPlayer().setScoreboard(playerboard);
        }
    }

    public void setInGameScoreboard(int end_countdown, Game game) {
        boolean isQuestionNull = game.getQuestioner() == null;
        boolean isBeQuestionedNull = game.getBeQuestioned() == null;

        Scoreboard good_scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective good_objective = good_scoreboard.registerNewObjective("Good", "dummy");
        good_objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        String good_text_value = WhoIsTheSpyLocale.SCOREBOARD_INGAME_FOR_GOOD.getValue()
                .replace("%TIME%", String.valueOf(end_countdown))
                .replace("%CHARACTER%", WhoIsTheSpyLocale.GOOD.toString())
                .replace("%WORD%", game.getWord())
                .replace("%GAME%", game.getName());

        checkQuestionAndBeQuestioned(game, isQuestionNull, isBeQuestionedNull, good_objective, good_text_value);

        for (int i = 0; i < WhoIsTheSpyLocale.SCOREBOARD_INGAME_FOR_GOOD.getValues().length - 1; i++) {
            String text = WhoIsTheSpyLocale.SCOREBOARD_INGAME_FOR_GOOD.getValue(i + 1)
                    .replace("%TIME%", String.valueOf(end_countdown))
                    .replace("%CHARACTER%", WhoIsTheSpyLocale.GOOD.toString())
                    .replace("%WORD%", game.getWord())
                    .replace("%GAME%", game.getName());

            text = getString(game, isQuestionNull, isBeQuestionedNull, text);

            Score score = good_objective.getScore(text);
            score.setScore(WhoIsTheSpyLocale.SCOREBOARD_INGAME_FOR_GOOD.getValues().length - 1 - i);
        }

        for (PlayerInfo playerInfo : game.getGood()) {
            playerInfo.getPlayer().setScoreboard(good_scoreboard);
        }

        Scoreboard undercover_scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective undercover_objective = undercover_scoreboard.registerNewObjective("Undercover", "dummy");
        undercover_objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        String undercover_text_value = WhoIsTheSpyLocale.SCOREBOARD_INGAME_FOR_UNDERCOVER.getValue()
                .replace("%TIME%", String.valueOf(end_countdown))
                .replace("%CHARACTER%", WhoIsTheSpyLocale.UNDERCOVER.toString())
                .replace("%GAME%", game.getName());

        checkQuestionAndBeQuestioned(game, isQuestionNull, isBeQuestionedNull, undercover_objective, undercover_text_value);

        for (int i = 0; i < WhoIsTheSpyLocale.SCOREBOARD_INGAME_FOR_UNDERCOVER.getValues().length - 1; i++) {
            String text = WhoIsTheSpyLocale.SCOREBOARD_INGAME_FOR_UNDERCOVER.getValue(i + 1)
                    .replace("%TIME%", String.valueOf(end_countdown))
                    .replace("%CHARACTER%", WhoIsTheSpyLocale.UNDERCOVER.toString())
                    .replace("%GAME%", game.getName());

            text = getString(game, isQuestionNull, isBeQuestionedNull, text);

            Score score = undercover_objective.getScore(text);
            score.setScore(WhoIsTheSpyLocale.SCOREBOARD_INGAME_FOR_UNDERCOVER.getValues().length - 1 - i);
        }

        game.getUndercover().getPlayer().setScoreboard(undercover_scoreboard);
    }

    private void checkQuestionAndBeQuestioned(Game game, boolean isQuestionNull, boolean isBeQuestionedNull, Objective good_objective, String good_text_value) {
        good_text_value = getString(game, isQuestionNull, isBeQuestionedNull, good_text_value);

        good_objective.setDisplayName(good_text_value);
    }

    private String getString(Game game, boolean isQuestionNull, boolean isBeQuestionedNull, String good_text_value) {
        if (!isQuestionNull) {
            good_text_value = good_text_value.replace("%PLAYER%", game.getQuestioner().getPlayer().getDisplayName());
        } else {
            good_text_value = good_text_value.replace("%PLAYER%", WhoIsTheSpyLocale.NULL.toString());
        }

        if (!isBeQuestionedNull) {
            good_text_value = good_text_value.replace("%BE_QUESTIONED%", game.getBeQuestioned().getPlayer().getDisplayName());
        } else {
            good_text_value = good_text_value.replace("%BE_QUESTIONED%", WhoIsTheSpyLocale.NULL.toString());
        }
        return good_text_value;
    }
}
