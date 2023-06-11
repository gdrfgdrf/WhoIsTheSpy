package cn.gdrfgdrf.whoisthespy.Command;

import cn.gdrfgdrf.whoisthespy.Utils.de.pauhull.utils.locale.storage.LocaleString;
import cn.gdrfgdrf.whoisthespy.WhoIsTheSpy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandSender;

import java.util.List;

@AllArgsConstructor
public abstract class SubCommand {

    @Setter
    @Getter
    protected boolean onlyPlayers;

    @Setter
    @Getter
    protected int minArgs;

    @Setter
    @Getter
    protected int maxArgs;

    @Getter
    @Setter
    protected String name, permission;

    @Setter
    @Getter
    protected WhoIsTheSpy whoIsTheSpy;

    public abstract void onCommand(CommandSender sender, String[] args);

    public abstract List<String> onTabComplete(CommandSender sender, String[] args);

    public abstract String getSyntax();

    public abstract LocaleString getDescription();

}