package cn.gdrfgdrf.whoisthespy.Command;

import cn.gdrfgdrf.whoisthespy.Game.Game;
import cn.gdrfgdrf.whoisthespy.Locale.WhoIsTheSpyLocale;
import cn.gdrfgdrf.whoisthespy.Utils.Util;
import cn.gdrfgdrf.whoisthespy.Utils.de.pauhull.utils.locale.storage.LocaleString;
import cn.gdrfgdrf.whoisthespy.WhoIsTheSpy;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.command.CommandSender;

import java.util.LinkedList;
import java.util.List;

public class WhoIsTheSpySetGameVoteDurationCommand extends SubCommand {
    public static final String SYNTAX;

    static {
        SYNTAX = "/who setVoteDuration %START%%NAME%%END% %START%%INTEGER%%END%"
                .replace("%START%", WhoIsTheSpyLocale.ARGUMENT_PLACEHOLDER_START.toString())
                .replace("%END%", WhoIsTheSpyLocale.ARGUMENT_PLACEHOLDER_END.toString())
                .replace("%NAME%", WhoIsTheSpyLocale.NAME.toString())
                .replace("%INTEGER%", WhoIsTheSpyLocale.INTEGER.toString());
    }

    @Getter
    private final LocaleString description = WhoIsTheSpyLocale.COMMAND_SET_GAME_VOTE_DURATION;

    public WhoIsTheSpySetGameVoteDurationCommand(WhoIsTheSpy whoIsTheSpy) {
        super(false, 3, 3, "setVoteDuration", WhoIsTheSpyCommand.PERMISSION_ADMINISTRATOR_PREFIX + "setVoteDuration", whoIsTheSpy);
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Game game = Game.getByName(args[1]);

        if (game == null) {
            WhoIsTheSpyLocale.ERROR_GAME_NOT_EXIST.message(WhoIsTheSpyLocale.PREFIX, sender);
            return;
        }

        if (Util.verifyStringToInteger(args[2])) {
            if (Integer.parseInt(args[2]) > 0) {
                if (!game.isEnabled()) {
                    game.setAnswerDuration(Integer.parseInt(args[2]));
                    WhoIsTheSpyLocale.SUCCESS_SET_GAME_VOTE_DURATION.message(WhoIsTheSpyLocale.PREFIX, sender, "%GAME%", args[1], "%TIME%", args[2]);
                } else {
                    WhoIsTheSpyLocale.ERROR_GAME_IS_ENABLED.message(WhoIsTheSpyLocale.PREFIX, sender);
                }
            } else {
                if (Integer.parseInt(args[2]) == 0) {
                    WhoIsTheSpyLocale.ERROR_IS_ZERO_INTEGER.message(WhoIsTheSpyLocale.PREFIX, sender);
                    return;
                }

                if (Integer.parseInt(args[2]) < 0) {
                    WhoIsTheSpyLocale.ERROR_IS_NEGATIVE_INTEGER.message(WhoIsTheSpyLocale.PREFIX, sender);
                }
            }
        } else {
            if (!StringUtils.isNumeric(args[2])) {
                WhoIsTheSpyLocale.ERROR_ONLY_INTEGER.message(WhoIsTheSpyLocale.PREFIX, sender);
                return;
            }

            WhoIsTheSpyLocale.ERROR_OUT_OF_INTEGER_LIMITED.message(WhoIsTheSpyLocale.PREFIX, sender, "%MIN%", String.valueOf(Integer.MIN_VALUE), "%MAX%", String.valueOf(Integer.MAX_VALUE));
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
