package cn.gdrfgdrf.whoisthespy.Phase;

import cn.gdrfgdrf.whoisthespy.Display.DisplayVoteInventory;
import cn.gdrfgdrf.whoisthespy.Events.GameEndEvent;
import cn.gdrfgdrf.whoisthespy.Game.Game;
import cn.gdrfgdrf.whoisthespy.Game.GameState;
import cn.gdrfgdrf.whoisthespy.Player.PlayerInfo;
import cn.gdrfgdrf.whoisthespy.Utils.ItemType;
import cn.gdrfgdrf.whoisthespy.Utils.Util;
import cn.gdrfgdrf.whoisthespy.Utils.de.pauhull.utils.locale.storage.LocaleString;
import cn.gdrfgdrf.whoisthespy.WhoIsTheSpy;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static cn.gdrfgdrf.whoisthespy.Locale.WhoIsTheSpyLocale.*;

public class GamePhase implements Runnable {

    private final WhoIsTheSpy whoIsTheSpy;
    private final Game game;

    private final DisplayVoteInventory displayVoteInventory;

    @Getter
    @Setter
    private int endCountdown, selectBeQuestionedCountdown, answerCountdown;

    public GamePhase(WhoIsTheSpy whoIsTheSpy, Game game) {
        this.whoIsTheSpy = whoIsTheSpy;
        this.game = game;
        this.displayVoteInventory = new DisplayVoteInventory();
        this.endCountdown = game.getGameDuration();
        this.selectBeQuestionedCountdown = game.getSelectBeQuestionedDuration();
        this.answerCountdown = game.getAnswerDuration();
    }

    public void finishGameForPlayerLeave() {
        finishGame(PREFIX, GAME_END_PLAYER_LEAVE, null, true);

        setGameState();
    }

    public void finishGameForNotFoundWord() {
        finishGame(PREFIX, ERROR_NOT_FOUND_WORD, null, false);

        setGameState();
    }

    public void finishGameForForceStop() {
        finishGame(PREFIX, ADMINISTRATOR_FORCE_STOP, null, true);

        setGameState();
    }

    public void finishGameForSwitchToDisabled() {
        finishGame(PREFIX, ADMINISTRATOR_DISABLED_GAME, null, true);

        setGameState();
    }

    public void finishGameForEndCountdown() {
        finishGame(PREFIX, GAME_DRAW_MESSAGE, GAME_DRAW_SUBTITLE, true);

        setGameState();
    }

    public void finishGameForUndercoverWin() {
        finishGame(PREFIX, UNDERCOVER_WIN_MESSAGE, UNDERCOVER_WIN_SUBTITLE, true);

        setGameState();
    }

    public void finishGameForUndercoverLose() {
        finishGame(PREFIX, UNDERCOVER_GUESS_WRONG_LOSE_MESSAGE, UNDERCOVER_GUESS_WRONG_LOSE_SUBTITLE, true);

        setGameState();
    }

    public void finishGameForGoodWin() {
        finishGame(PREFIX, GOOD_WIN_MESSAGE, GOOD_WIN_SUBTITLE, true);

        setGameState();
    }

    public void finishGameForGoodLose() {
        finishGame(PREFIX, GOOD_VOTE_WRONG_LOSE_MESSAGE, GOOD_VOTE_WRONG_LOSE_SUBTITLE, true);

        setGameState();
    }

    public void setGameState() {
        if (game.getGameState() == GameState.WAITING) {
            game.getPhaseHandler().cancelWaitingPhase();
        }

        if (game.getGameState() == GameState.STARTED) {
            game.setGameState(GameState.WAITING);
            game.getPhaseHandler().cancelGamePhase();
        }
    }

    public void finishGame(LocaleString prefix, LocaleString reason, LocaleString subtitle, boolean showInfo, String... placeholder) {
        if (showInfo) {
            showGameInfo();
        }

        Iterator<PlayerInfo> iterator = game.getPlayersInGame().iterator();
        while (iterator.hasNext()) {
            PlayerInfo playerInfo = iterator.next();
            game.callPlayerLeaveGameEvent(playerInfo);
            iterator.remove();

            reason.message(prefix, playerInfo.getPlayer(), placeholder);

            if (subtitle != null) {
                Util.sendTitle(playerInfo.getPlayer(), GAME_END_TITLE.toString(), subtitle.toString());
            } else {
                Util.sendTitle(playerInfo.getPlayer(), GAME_END_TITLE.toString(), "");
            }
        }

        game.getGood().clear();

        callGameEndEvent(false, reason);
    }

    public void showGameInfo() {
        AtomicReference<StringBuffer> goodList = new AtomicReference<>(new StringBuffer());
        AtomicReference<StringBuffer> voteList = new AtomicReference<>(new StringBuffer());
        AtomicReference<StringBuffer> notVoteList = new AtomicReference<>(new StringBuffer());
        AtomicReference<StringBuffer> abstainList = new AtomicReference<>(new StringBuffer());

        for (PlayerInfo playerInfo : game.getGood()) {
            goodList.get().append(SPACE.toString()).append(GOOD_LIST_FORMAT.toString()
                    .replace("%PLAYER%", playerInfo.getPlayer().getDisplayName()));
        }

        if (game.getVOTER_AND_BE_VOTED().isEmpty()) {
            voteList.get().append(SPACE.toString()).append(NULL.toString());
        } else {
            for (Map.Entry<PlayerInfo, PlayerInfo> map : game.getVOTER_AND_BE_VOTED().entrySet()) {
                voteList.get().append(SPACE.toString()).append(VOTE_LIST_FORMAT.toString()
                        .replace("%VOTER%", map.getKey().getPlayer().getDisplayName())
                        .replace("%BE_VOTED%", map.getValue().getPlayer().getDisplayName()));
            }
        }

        for (PlayerInfo playerInfo : game.getGood()) {
            if (!game.getVotedPlayers().contains(playerInfo) && !game.getAbstainVotePlayer().contains(playerInfo)) {
                notVoteList.get().append(SPACE.toString()).append(NOT_VOTE_LIST_FORMAT.toString()
                        .replace("%PLAYER%", playerInfo.getPlayer().getDisplayName()));
            }

            if (game.getAbstainVotePlayer().contains(playerInfo)) {
                abstainList.get().append(SPACE.toString()).append(ABSTAIN_LIST_FORMAT.toString()
                        .replace("%PLAYER%", playerInfo.getPlayer().getDisplayName()));
            }
        }

        if (notVoteList.get().length() == 0) {
            notVoteList.get().append(SPACE.toString()).append(NULL.toString());
        }

        if (abstainList.get().length() == 0) {
            abstainList.get().append(SPACE.toString()).append(NULL.toString());
        }

        String word;

        if (game.getWord() == null) {
            word = NULL.toString();
        } else {
            word = game.getWord();
        }

        String guessWord;

        if (game.getUndercoverGuess() == null) {
            guessWord = NULL.toString();
        } else {
            guessWord = game.getUndercoverGuess();
        }

        game.broadcast(PREFIX, SHOW_GAME_INFO,
                "%GAME%", game.getName(),
                "%SPEND_TIME%", String.valueOf(game.getGameDuration() - endCountdown),
                "%WORD%", word,
                "%GUESS_WORD%", guessWord,
                "%GOOD_LIST%", goodList.get().toString(),
                "%VOTE_LIST%", voteList.get().toString(),
                "%NOT_VOTE_LIST%", notVoteList.get().toString(),
                "%ABSTAIN_LIST%", abstainList.get().toString(),
                "%UNDERCOVER%", game.getUndercover().getPlayer().getDisplayName());
    }

    public void callGameEndEvent(boolean calculateVote, LocaleString reason, String... placeholder) {
        GameEndEvent gameEndEvent = new GameEndEvent(game, reason, placeholder, calculateVote);
        Bukkit.getPluginManager().callEvent(gameEndEvent);
    }

    @Override
    public void run() {
        if (endCountdown <= 0) {
            finishGameForEndCountdown();
            return;
        }

        if (game.getPlayersInGame().size() < game.getMinPlayer()) {
            finishGameForPlayerLeave();
            return;
        }

        if (game.getVotedPlayers().size() + game.getAbstainVotePlayer().size() >= game.getGood().size()) {
            showGameInfo();
            callGameEndEvent(true, GAME_END_VOTE_FINISHED);
            return;
        }

        displayVoteInventory.setCountdown(game);
        this.whoIsTheSpy.getDisplayScoreboard().setInGameScoreboard(endCountdown, game);
        this.whoIsTheSpy.getDisplaySelectBeQuestionedInventory().setCountdown(selectBeQuestionedCountdown, game);

        if (game.getBeQuestioned() != null) {
            PlayerInfo playerInfo = game.getBeQuestioned();

            Util.showActionBar(ANSWER_COUNTDOWN_ACTIONBAR.toString().replace("%TIME%", String.valueOf(answerCountdown)), playerInfo.getPlayer());

            if (answerCountdown <= 0) {
                game.broadcast(PREFIX, ANSWER_COUNTDOWN_TIMEOUT, "%BE_QUESTIONED%", playerInfo.getPlayer().getDisplayName());

                game.setQuestioner(playerInfo);
                game.setBeQuestioned(null);
                game.getPhaseHandler().getGamePhase().setSelectBeQuestionedCountdown(game.getSelectBeQuestionedDuration());

                Util.clearItemStack(playerInfo.getPlayer(), ItemType.END_ANSWER.getItemStack());

                game.getQUESTIONER_SELECT_BE_QUESTIONED().show(playerInfo.getPlayer());
            }
        }

        selectBeQuestionedCountdown--;
        answerCountdown--;
        endCountdown--;
    }
}
