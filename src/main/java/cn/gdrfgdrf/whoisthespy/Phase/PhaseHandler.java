package cn.gdrfgdrf.whoisthespy.Phase;

import cn.gdrfgdrf.whoisthespy.Events.GameStartEvent;
import cn.gdrfgdrf.whoisthespy.Game.Game;
import cn.gdrfgdrf.whoisthespy.Game.GameState;
import cn.gdrfgdrf.whoisthespy.WhoIsTheSpy;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;

public class PhaseHandler {

    private final WhoIsTheSpy whoIsTheSpy;
    private final Game game;
    private final BukkitScheduler scheduler;
    private int waitingPhaseId, gamePhaseId;
    @Getter
    private WaitingPhase waitingPhase;

    @Getter
    private GamePhase gamePhase;

    public PhaseHandler(WhoIsTheSpy whoIsTheSpy, Game game) {
        this.whoIsTheSpy = whoIsTheSpy;
        this.game = game;
        this.waitingPhase = new WaitingPhase(whoIsTheSpy, game);
        this.gamePhase = new GamePhase(whoIsTheSpy, game);
        this.scheduler = whoIsTheSpy.getPlugin().getServer().getScheduler();
    }

    public boolean startWaitingPhase() {
        if (game.getPlayersInGame().size() >= game.getMinPlayer() && !scheduler.isCurrentlyRunning(waitingPhaseId) && !scheduler.isQueued(waitingPhaseId)) {
            this.waitingPhase = new WaitingPhase(whoIsTheSpy, game);
            this.waitingPhaseId = scheduler.scheduleSyncRepeatingTask(whoIsTheSpy.getPlugin(), waitingPhase, 0L, 20L);
            return true;
        } else {
            game.setGameState(GameState.WAITING);
            return false;
        }
    }

    public void callGameStartEvent() {
        GameStartEvent event = new GameStartEvent(game);
        Bukkit.getPluginManager().callEvent(event);
    }

    public boolean startGamePhase() {
        if (!scheduler.isCurrentlyRunning(gamePhaseId) && !scheduler.isQueued(gamePhaseId)) {
            this.gamePhase = new GamePhase(whoIsTheSpy, game);
            this.gamePhaseId = scheduler.scheduleSyncRepeatingTask(whoIsTheSpy.getPlugin(), gamePhase, 0L, 20L);
            this.cancelWaitingPhase();
            return true;
        } else {
            return false;
        }
    }

    public void cancelWaitingPhase() {
        Bukkit.getScheduler().cancelTask(waitingPhaseId);
    }

    public void cancelGamePhase() {
        Bukkit.getScheduler().cancelTask(gamePhaseId);
    }

}
