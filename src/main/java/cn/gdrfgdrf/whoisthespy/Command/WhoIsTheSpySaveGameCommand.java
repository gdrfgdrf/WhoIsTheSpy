package cn.gdrfgdrf.whoisthespy.Command;

import cn.gdrfgdrf.whoisthespy.Game.Game;
import cn.gdrfgdrf.whoisthespy.Locale.WhoIsTheSpyLocale;
import cn.gdrfgdrf.whoisthespy.Utils.de.pauhull.utils.locale.storage.LocaleString;
import cn.gdrfgdrf.whoisthespy.WhoIsTheSpy;
import lombok.Getter;
import org.bukkit.command.CommandSender;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class WhoIsTheSpySaveGameCommand extends SubCommand {
    public static final String SYNTAX;

    static {
        SYNTAX = "/who save %START%%NAME%%END%".replace("%START%", WhoIsTheSpyLocale.ARGUMENT_PLACEHOLDER_START.toString())
                .replace("%END%", WhoIsTheSpyLocale.ARGUMENT_PLACEHOLDER_END.toString())
                .replace("%NAME%", WhoIsTheSpyLocale.NAME.toString());
    }

    @Getter
    private final LocaleString description = WhoIsTheSpyLocale.COMMAND_SAVE;

    public WhoIsTheSpySaveGameCommand(WhoIsTheSpy whoIsTheSpy) {
        super(false, 2, 2, "save", WhoIsTheSpyCommand.PERMISSION_ADMINISTRATOR_PREFIX + "save", whoIsTheSpy);
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Game game = Game.getByName(args[1]);

        if (game == null) {
            WhoIsTheSpyLocale.ERROR_GAME_NOT_EXIST.message(WhoIsTheSpyLocale.PREFIX, sender);
            return;
        }

        try {
            game.saveData();
            WhoIsTheSpyLocale.SUCCESS_SAVE_GAME.message(WhoIsTheSpyLocale.PREFIX, sender, "%GAME%", args[1]);
        } catch (IOException e) {
            e.printStackTrace();
            WhoIsTheSpyLocale.ERROR_SAVE_GAME.message(WhoIsTheSpyLocale.PREFIX, sender);
        }
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
