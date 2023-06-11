package cn.gdrfgdrf.whoisthespy.Command;

import cn.gdrfgdrf.whoisthespy.Game.Game;
import cn.gdrfgdrf.whoisthespy.Locale.WhoIsTheSpyLocale;
import cn.gdrfgdrf.whoisthespy.Player.PlayerInfo;
import cn.gdrfgdrf.whoisthespy.Player.PlayerState;
import cn.gdrfgdrf.whoisthespy.Utils.de.pauhull.utils.locale.storage.LocaleString;
import cn.gdrfgdrf.whoisthespy.WhoIsTheSpy;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;

public class WhoIsTheSpyLeaveGameCommand extends SubCommand {
    public static final String SYNTAX = "/who leave";

    @Getter
    private final LocaleString description = WhoIsTheSpyLocale.COMMAND_LEAVE_GAME;

    public WhoIsTheSpyLeaveGameCommand(WhoIsTheSpy whoIsTheSpy) {
        super(true, 1, 1, "leave", WhoIsTheSpyCommand.PERMISSION_USER_PREFIX + "leave", whoIsTheSpy);
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        PlayerInfo playerInfo = PlayerInfo.getFromPlayer(player);

        if (playerInfo == null || playerInfo.getCurrentGame() == null || playerInfo.getPlayerState() == PlayerState.DEFAULT) {
            WhoIsTheSpyLocale.ERROR_NOT_IN_GAME.message(WhoIsTheSpyLocale.PREFIX, sender);
            return;
        }

        Game game = playerInfo.getCurrentGame();
        game.removePlayer(playerInfo);
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
