package cn.gdrfgdrf.whoisthespy.Command;

import cn.gdrfgdrf.whoisthespy.Game.Game;
import cn.gdrfgdrf.whoisthespy.Locale.WhoIsTheSpyLocale;
import cn.gdrfgdrf.whoisthespy.Utils.Util;
import cn.gdrfgdrf.whoisthespy.Utils.de.pauhull.utils.locale.storage.LocaleString;
import cn.gdrfgdrf.whoisthespy.WhoIsTheSpy;
import lombok.Getter;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class SetGameNameCommand extends SubCommand {
    public static final String SYNTAX;

    static {
        SYNTAX = "/who setName %START%%OLD_NAME%%END% %START%%NEW_NAME%%END%"
                .replace("%START%",
                        WhoIsTheSpyLocale.ARGUMENT_PLACEHOLDER_START.toString())
                .replace("%END%",
                        WhoIsTheSpyLocale.ARGUMENT_PLACEHOLDER_END.toString())
                .replace("%OLD_NAME%",
                        WhoIsTheSpyLocale.OLD_NAME.toString())
                .replace("%NEW_NAME%",
                        WhoIsTheSpyLocale.NEW_NAME.toString());
    }

    @Getter
    private final LocaleString description = WhoIsTheSpyLocale.COMMAND_SET_GAME_NAME;

    public SetGameNameCommand(WhoIsTheSpy whoIsTheSpy) {
        super(
                false,
                3,
                3,
                "setName",
                WhoIsTheSpyCommand.PERMISSION_ADMINISTRATOR_PREFIX + "setName",
                whoIsTheSpy
        );
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Game game = Game.getByName(args[1]);

        if (game == null) {
            WhoIsTheSpyLocale.ERROR_GAME_NOT_EXIST.message(
                    WhoIsTheSpyLocale.PREFIX,
                    sender
            );
            return;
        }

        Game game1 = Game.getByName(args[2]);

        if (game1 != null) {
            WhoIsTheSpyLocale.ERROR_NAME_ALREADY_EXIST.message(
                    WhoIsTheSpyLocale.PREFIX,
                    sender,
                    "%NAME%",
                    args[2]
            );
            return;
        }

        if (Util.isValidFileName(args[2])) {
            if (!game.isEnabled()) {
                game.getGameDataManager().setRename(true);
                game.getGameDataManager()
                        .setBeforeRenameFile(game.getGameDataManager().getFile());

                game.setName(args[2]);

                File dest = new File(WhoIsTheSpy.PLUGIN_FOLDER + "Games/" + game.getName() + ".yml");
                game.getGameDataManager().setFile(dest);

                WhoIsTheSpyLocale.SUCCESS_SET_GAME_NAME.message(
                        WhoIsTheSpyLocale.PREFIX,
                        sender,
                        "%OLD_NAME%",
                        args[1],
                        "%NEW_NAME%",
                        args[2]
                );
            } else {
                WhoIsTheSpyLocale.ERROR_GAME_IS_ENABLED.message(
                        WhoIsTheSpyLocale.PREFIX,
                        sender
                );
            }
        } else {
            WhoIsTheSpyLocale.ERROR_GAME_NAME_RULES.message(
                    WhoIsTheSpyLocale.PREFIX,
                    sender
            );
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> result = new LinkedList<>();

        if (args.length == 2) {
            for (Game game : WhoIsTheSpy.getInstance().getGames()) {
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
