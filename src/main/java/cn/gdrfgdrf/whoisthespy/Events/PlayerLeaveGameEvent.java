package cn.gdrfgdrf.whoisthespy.Events;

import cn.gdrfgdrf.whoisthespy.Game.Game;
import cn.gdrfgdrf.whoisthespy.Player.PlayerInfo;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@RequiredArgsConstructor
public class PlayerLeaveGameEvent extends Event implements Cancellable {
    @Getter
    private static final HandlerList handlerList = new HandlerList();

    @Setter
    @Getter
    private boolean cancelled;

    @NonNull
    @Getter
    private Game game;

    @NonNull
    @Getter
    private Player player;

    @NonNull
    @Getter
    private PlayerInfo playerInfo;

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
