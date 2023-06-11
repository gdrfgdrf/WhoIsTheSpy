package cn.gdrfgdrf.whoisthespy.Events;

import cn.gdrfgdrf.whoisthespy.Game.Game;
import cn.gdrfgdrf.whoisthespy.Utils.de.pauhull.utils.locale.storage.LocaleString;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@RequiredArgsConstructor
public class GameEndEvent extends Event {

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    @NonNull
    @Getter
    private Game game;

    @NonNull
    @Getter
    private LocaleString reason;

    @NonNull
    @Getter
    private String[] placeholder;

    @NonNull
    @Getter
    private boolean calculateVote;

    @Override
    public @NonNull HandlerList getHandlers() {
        return handlerList;
    }

}
