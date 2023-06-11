package cn.gdrfgdrf.whoisthespy.Events;

import cn.gdrfgdrf.whoisthespy.Game.Game;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@RequiredArgsConstructor
public class GameStartEvent extends Event {

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    @NonNull
    @Getter
    private Game game;

    @Override
    public @NonNull HandlerList getHandlers() {
        return handlerList;
    }

}
