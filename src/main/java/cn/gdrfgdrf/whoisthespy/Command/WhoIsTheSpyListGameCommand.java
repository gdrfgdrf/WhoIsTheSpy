package cn.gdrfgdrf.whoisthespy.Command;

import cn.gdrfgdrf.whoisthespy.Game.Game;
import cn.gdrfgdrf.whoisthespy.Locale.WhoIsTheSpyLocale;
import cn.gdrfgdrf.whoisthespy.Utils.Util;
import cn.gdrfgdrf.whoisthespy.Utils.de.pauhull.utils.locale.storage.LocaleString;
import cn.gdrfgdrf.whoisthespy.WhoIsTheSpy;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;

public class WhoIsTheSpyListGameCommand extends SubCommand {

    public static String SYNTAX = "/who list";

    @Getter
    private final LocaleString description = WhoIsTheSpyLocale.COMMAND_LIST_GAME;

    public WhoIsTheSpyListGameCommand(WhoIsTheSpy whoIsTheSpy) {
        super(false, 1, 1, "list", WhoIsTheSpyCommand.PERMISSION_ADMINISTRATOR_PREFIX + "list", whoIsTheSpy);
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (whoIsTheSpy.getGames().isEmpty()) {
            WhoIsTheSpyLocale.ERROR_NO_GAME.message(WhoIsTheSpyLocale.PREFIX, sender);
        } else {
            boolean console = !(sender instanceof Player);
            String separator = Util.getSeparator(12, console);
            String header = separator + " " + WhoIsTheSpyLocale.HEADER_GAMES_LIST + " " + separator;
            String bulletPoint = console ? "§8 - §7" : "§8 • §7";

            if (!console) {
                sender.sendMessage(" ");
            }

            sender.sendMessage(header);

            for (Game game : whoIsTheSpy.getGames()) {
                sender.sendMessage(bulletPoint + game.getName() + ": " + (game.isEnabled() ? WhoIsTheSpyLocale.ENABLED : WhoIsTheSpyLocale.DISABLED));
            }

            sender.sendMessage(header);
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
