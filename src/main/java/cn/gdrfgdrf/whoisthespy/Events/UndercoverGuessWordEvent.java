package cn.gdrfgdrf.whoisthespy.Events;

import cn.gdrfgdrf.whoisthespy.Game.Game;
import cn.gdrfgdrf.whoisthespy.Player.PlayerInfo;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@RequiredArgsConstructor
public class UndercoverGuessWordEvent extends Event {

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    @NonNull
    @Getter
    private Game game;

    @NonNull
    @Getter
    private PlayerInfo playerInfo;

    @NonNull
    @Getter
    private String guess;

    @Override
    public @NonNull HandlerList getHandlers() {
        return handlerList;
    }


}
