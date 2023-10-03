package cn.gdrfgdrf.whoisthespy.Command;

import cn.gdrfgdrf.whoisthespy.Locale.WhoIsTheSpyLocale;
import cn.gdrfgdrf.whoisthespy.Utils.Util;
import cn.gdrfgdrf.whoisthespy.Utils.de.pauhull.utils.locale.storage.LocaleString;
import cn.gdrfgdrf.whoisthespy.WhoIsTheSpy;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;

public class HelpCommand extends SubCommand {
    public static final String SYNTAX = "/who help";

    @Getter
    private final LocaleString description = WhoIsTheSpyLocale.COMMAND_HELP;

    public HelpCommand(WhoIsTheSpy whoIsTheSpy) {
        super(
                false,
                1,
                1,
                "help",
                WhoIsTheSpyCommand.PERMISSION_USER_PREFIX + "help",
                whoIsTheSpy
        );
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        boolean console = !(sender instanceof Player);

        String separator = Util.getSeparator(12, console);
        String header = separator + " " + WhoIsTheSpyLocale.HEADER_HELP + " " + separator;
        String template = WhoIsTheSpyLocale.COMMAND_FORMAT.toString();

        if (!console) {
            sender.sendMessage(" ");
        }
        sender.sendMessage(header);

        for (SubCommand command : WhoIsTheSpyCommand.commands) {
            if (!command.getPermission().startsWith(
                    WhoIsTheSpyCommand.PERMISSION_USER_PREFIX
            )) {
                continue;
            }

            sender.sendMessage(
                    template.replace("%SYNTAX%",
                                    command.getSyntax())
                            .replace("%DESCRIPTION%",
                                    command.getDescription().toString())
            );
        }

        if (sender.hasPermission(WhoIsTheSpyCommand.PERMISSION_ADMINISTRATOR_PREFIX)) {
            sender.sendMessage(
                    template.replace("%SYNTAX%",
                                    AdminHelpCommand.SYNTAX)
                            .replace("%DESCRIPTION%",
                                    WhoIsTheSpyLocale.COMMAND_ADMIN.toString())
            );
        }

        sender.sendMessage(header);
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
