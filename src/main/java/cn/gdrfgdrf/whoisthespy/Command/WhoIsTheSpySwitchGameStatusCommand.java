package cn.gdrfgdrf.whoisthespy.Command;

import cn.gdrfgdrf.whoisthespy.Game.Game;
import cn.gdrfgdrf.whoisthespy.Game.GameState;
import cn.gdrfgdrf.whoisthespy.Locale.WhoIsTheSpyLocale;
import cn.gdrfgdrf.whoisthespy.Player.PlayerInfo;
import cn.gdrfgdrf.whoisthespy.Utils.de.pauhull.utils.locale.storage.LocaleString;
import cn.gdrfgdrf.whoisthespy.WhoIsTheSpy;
import lombok.Getter;
import org.bukkit.command.CommandSender;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class WhoIsTheSpySwitchGameStatusCommand extends SubCommand {
    public static final String SYNTAX;

    static {
        SYNTAX = "/who switch %START%%NAME%%END%".replace("%START%", WhoIsTheSpyLocale.ARGUMENT_PLACEHOLDER_START.toString())
                .replace("%END%", WhoIsTheSpyLocale.ARGUMENT_PLACEHOLDER_END.toString())
                .replace("%NAME%", WhoIsTheSpyLocale.NAME.toString());
    }

    @Getter
    private final LocaleString description = WhoIsTheSpyLocale.COMMAND_SWITCH_GAME_STATUS;

    public WhoIsTheSpySwitchGameStatusCommand(WhoIsTheSpy whoIsTheSpy) {
        super(false, 2, 2, "switch", WhoIsTheSpyCommand.PERMISSION_ADMINISTRATOR_PREFIX + "switch", whoIsTheSpy);
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Game game = Game.getByName(args[1]);

        if (game == null) {
            WhoIsTheSpyLocale.ERROR_GAME_NOT_EXIST.message(WhoIsTheSpyLocale.PREFIX, sender);
            return;
        }

        game.setEnabled(!game.isEnabled());

        if (!game.isEnabled()) {
            Iterator<PlayerInfo> iterator = game.getPlayersInGame().iterator();
            while (iterator.hasNext()) {
                PlayerInfo playerInfo = iterator.next();
                game.callPlayerLeaveGameEvent(playerInfo);
                iterator.remove();

                WhoIsTheSpyLocale.ADMINISTRATOR_DISABLED_GAME.message(WhoIsTheSpyLocale.PREFIX, playerInfo.getPlayer());
            }

            if (game.getGameState() == GameState.WAITING) {
                game.getPhaseHandler().cancelWaitingPhase();
            }

            if (game.getGameState() == GameState.STARTED) {
                game.getPhaseHandler().getGamePhase().finishGameForSwitchToDisabled();
                game.setGameState(GameState.WAITING);
            }
        }

        WhoIsTheSpyLocale.SUCCESS_SWITCH_GAME_STATUS.message(WhoIsTheSpyLocale.PREFIX, sender, "%GAME%", args[1], "%STATUS%", (game.isEnabled() ? WhoIsTheSpyLocale.ENABLED.toString() : WhoIsTheSpyLocale.DISABLED.toString()));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> result = new LinkedList<>();

        for (Game game : WhoIsTheSpy.getInstance().getGames()) {
            result.add(game.getName());
        }

        return result;
    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }
}
