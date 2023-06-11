package cn.gdrfgdrf.whoisthespy.Events;

import cn.gdrfgdrf.whoisthespy.Game.Game;
import cn.gdrfgdrf.whoisthespy.Player.PlayerInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@RequiredArgsConstructor
public class PlayerJoinGameEvent extends Event implements Cancellable {
    @Getter
    private static final HandlerList handlerList = new HandlerList();

    @Setter
    @Getter
    private boolean cancelled;

    @Setter
    @Getter
    private String cancelMessage = null;

    @Getter
    private Game game;

    @Getter
    private Player player;

    @Getter
    private PlayerInfo playerInfo;

    public PlayerJoinGameEvent(Game game, Player player, PlayerInfo playerInfo) {
        this.game = game;
        this.player = player;
        this.playerInfo = playerInfo;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
