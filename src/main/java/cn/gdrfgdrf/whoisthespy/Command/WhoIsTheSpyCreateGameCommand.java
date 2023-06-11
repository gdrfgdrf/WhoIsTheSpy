package cn.gdrfgdrf.whoisthespy.Command;

import cn.gdrfgdrf.whoisthespy.Game.Game;
import cn.gdrfgdrf.whoisthespy.Locale.WhoIsTheSpyLocale;
import cn.gdrfgdrf.whoisthespy.Utils.Util;
import cn.gdrfgdrf.whoisthespy.Utils.de.pauhull.utils.locale.storage.LocaleString;
import cn.gdrfgdrf.whoisthespy.WhoIsTheSpy;
import lombok.Getter;
import org.bukkit.command.CommandSender;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class WhoIsTheSpyCreateGameCommand extends SubCommand {

    public static final String SYNTAX;

    static {
        SYNTAX = "/who create %START%%NAME%%END%"
                .replace("%START%", WhoIsTheSpyLocale.ARGUMENT_PLACEHOLDER_START.toString())
                .replace("%END%", WhoIsTheSpyLocale.ARGUMENT_PLACEHOLDER_END.toString())
                .replace("%NAME%", WhoIsTheSpyLocale.NAME.toString());
    }

    @Getter
    private final LocaleString description = WhoIsTheSpyLocale.COMMAND_CREATE_GAME;

    public WhoIsTheSpyCreateGameCommand(WhoIsTheSpy whoIsTheSpy) {
        super(false, 2, 2, "create", WhoIsTheSpyCommand.PERMISSION_ADMINISTRATOR_PREFIX + "create", whoIsTheSpy);
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        try {
            if (Util.isValidFileName(args[1])) {
                if (Game.create(args[1])) {
                    WhoIsTheSpyLocale.SUCCESS_CREATE_GAME.message(WhoIsTheSpyLocale.PREFIX, sender, "%GAME%", args[1]);
                } else {
                    WhoIsTheSpyLocale.ERROR_CREATE_GAME.message(WhoIsTheSpyLocale.PREFIX, sender, "%GAME%", args[1]);
                }
            } else {
                WhoIsTheSpyLocale.ERROR_GAME_NAME_RULES.message(WhoIsTheSpyLocale.PREFIX, sender);
            }
        } catch (IOException e) {
            e.printStackTrace();
            WhoIsTheSpyLocale.ERROR_CREATE_GAME.message(WhoIsTheSpyLocale.PREFIX, sender, "%GAME%", args[1]);
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return new LinkedList<>();
    }

    @Override
    public String getSyntax() {
        return SYNTAX;
    }
}
