package cn.gdrfgdrf.whoisthespy.Command;

import cn.gdrfgdrf.whoisthespy.Locale.WhoIsTheSpyLocale;
import cn.gdrfgdrf.whoisthespy.Utils.de.pauhull.utils.locale.storage.LocaleString;
import cn.gdrfgdrf.whoisthespy.WhoIsTheSpy;
import lombok.Getter;
import org.bukkit.command.CommandSender;

import java.util.LinkedList;
import java.util.List;

public class ReloadCommand extends SubCommand {
    public static final String SYNTAX = "/who reload";

    @Getter
    private final LocaleString description = WhoIsTheSpyLocale.COMMAND_RELOAD;

    public ReloadCommand(WhoIsTheSpy whoIsTheSpy) {
        super(
                false,
                1,
                1,
                "reload",
                WhoIsTheSpyCommand.PERMISSION_ADMINISTRATOR_PREFIX + "reload",
                whoIsTheSpy
        );
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        whoIsTheSpy.reload();

        WhoIsTheSpyLocale.SUCCESS_RELOAD.message(
                WhoIsTheSpyLocale.PREFIX,
                sender
        );
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
