package cn.gdrfgdrf.whoisthespy.Command;

import cn.gdrfgdrf.whoisthespy.Game.Game;
import cn.gdrfgdrf.whoisthespy.Locale.WhoIsTheSpyLocale;
import cn.gdrfgdrf.whoisthespy.Utils.de.pauhull.utils.locale.storage.LocaleString;
import cn.gdrfgdrf.whoisthespy.WhoIsTheSpy;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;

public class WhoIsTheSpyJoinGameCommand extends SubCommand {

    public static final String SYNTAX;

    static {
        SYNTAX = "/who join %START%%NAME%%END%"
                .replace("%START%", WhoIsTheSpyLocale.ARGUMENT_PLACEHOLDER_START.toString())
                .replace("%END%", WhoIsTheSpyLocale.ARGUMENT_PLACEHOLDER_END.toString())
                .replace("%NAME%", WhoIsTheSpyLocale.NAME.toString());
    }

    @Getter
    private final LocaleString description = WhoIsTheSpyLocale.COMMAND_JOIN_GAME;

    public WhoIsTheSpyJoinGameCommand(WhoIsTheSpy whoIsTheSpy) {
        super(true, 2, 2, "join", WhoIsTheSpyCommand.PERMISSION_USER_PREFIX + "join", whoIsTheSpy);
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (whoIsTheSpy.getWords() != null && !whoIsTheSpy.getWords().isEmpty()) {
            Player player = (Player) sender;
            Game game = Game.getByName(args[1]);

            if (game == null) {
                WhoIsTheSpyLocale.ERROR_GAME_NOT_EXIST.message(WhoIsTheSpyLocale.PREFIX, sender);
                return;
            }

            if (!game.isEnabled()) {
                WhoIsTheSpyLocale.ERROR_GAME_IS_DISABLED.message(WhoIsTheSpyLocale.PREFIX, sender);
                return;
            }

            game.addPlayer(player);
        } else {
            WhoIsTheSpyLocale.ERROR_NOT_FOUND_WORD.message(WhoIsTheSpyLocale.PREFIX, sender);
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> result = new LinkedList<>();

        for (Game game : WhoIsTheSpy.getInstance().getGames()) {
            if (game.isEnabled()) {
                result.add(game.getName());
            }
        }

        return result;
    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }
}
