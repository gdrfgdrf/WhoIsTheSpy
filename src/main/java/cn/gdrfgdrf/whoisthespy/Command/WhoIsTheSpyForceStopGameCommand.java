package cn.gdrfgdrf.whoisthespy.Command;

import cn.gdrfgdrf.whoisthespy.Game.Game;
import cn.gdrfgdrf.whoisthespy.Game.GameState;
import cn.gdrfgdrf.whoisthespy.Locale.WhoIsTheSpyLocale;
import cn.gdrfgdrf.whoisthespy.Utils.de.pauhull.utils.locale.storage.LocaleString;
import cn.gdrfgdrf.whoisthespy.WhoIsTheSpy;
import lombok.Getter;
import org.bukkit.command.CommandSender;

import java.util.LinkedList;
import java.util.List;

public class WhoIsTheSpyForceStopGameCommand extends SubCommand {

    public static final String SYNYAX;

    static {
        SYNYAX = "/who stop %START%%NAME%%END%"
                .replace("%START%", WhoIsTheSpyLocale.ARGUMENT_PLACEHOLDER_START.toString())
                .replace("%END%", WhoIsTheSpyLocale.ARGUMENT_PLACEHOLDER_END.toString())
                .replace("%NAME%", WhoIsTheSpyLocale.NAME.toString());
    }

    @Getter
    private final LocaleString description = WhoIsTheSpyLocale.COMMAND_FORCE_STOP_GAME;

    public WhoIsTheSpyForceStopGameCommand(WhoIsTheSpy whoIsTheSpy) {
        super(false, 2, 2, "stop", WhoIsTheSpyCommand.PERMISSION_ADMINISTRATOR_PREFIX + "stop", whoIsTheSpy);
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Game game = Game.getByName(args[1]);

        if (game == null) {
            WhoIsTheSpyLocale.ERROR_GAME_NOT_EXIST.message(WhoIsTheSpyLocale.PREFIX, sender);
            return;
        }

        if (!game.isEnabled()) {
            WhoIsTheSpyLocale.ERROR_GAME_IS_DISABLED.message(WhoIsTheSpyLocale.PREFIX, sender);
            return;
        }

        if (game.getGameState() != GameState.STARTED) {
            WhoIsTheSpyLocale.ERROR_GAME_IS_NOT_STARTED.message(WhoIsTheSpyLocale.PREFIX, sender);
            return;
        }

        game.getPhaseHandler().getGamePhase().finishGameForForceStop();

        WhoIsTheSpyLocale.SUCCESS_FORCE_STOP_GAME.message(WhoIsTheSpyLocale.PREFIX, sender, "%GAME%", game.getName());
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> result = new LinkedList<>();

        for (Game game : WhoIsTheSpy.getInstance().getGames()) {
            if (game.isEnabled() && game.getGameState() == GameState.STARTED) {
                result.add(game.getName());
            }
        }

        return result;
    }

    @Override
    public String getSyntax() {
        return SYNYAX;
    }
}
