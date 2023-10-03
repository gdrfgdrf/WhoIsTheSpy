package cn.gdrfgdrf.whoisthespy.Listener;

import cn.gdrfgdrf.whoisthespy.Command.WhoIsTheSpyCommand;
import cn.gdrfgdrf.whoisthespy.Game.Game;
import cn.gdrfgdrf.whoisthespy.Locale.WhoIsTheSpyLocale;
import cn.gdrfgdrf.whoisthespy.WhoIsTheSpy;
import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import java.io.IOException;

public class SignChangeListener implements Listener {
    private final WhoIsTheSpy whoIsTheSpy;

    public SignChangeListener(WhoIsTheSpy whoIsTheSpy) {
        this.whoIsTheSpy = whoIsTheSpy;

        Bukkit.getPluginManager().registerEvents(this, whoIsTheSpy.getPlugin());
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        Player player = event.getPlayer();
        Sign sign = (Sign) event.getBlock().getState();

        String[] lines = event.getLines();

        if (player.hasPermission(WhoIsTheSpyCommand.PERMISSION_ADMINISTRATOR_PREFIX + "addSign") &&
                lines[0].equals("[WhoIsTheSpy]")) {
            Game game = Game.getByName(lines[1]);

            if (game == null) {
                WhoIsTheSpyLocale.ERROR_GAME_NOT_EXIST.message(
                        WhoIsTheSpyLocale.PREFIX,
                        player
                );
                return;
            }

            try {
                game.addSign(sign.getLocation());
                WhoIsTheSpyLocale.SUCCESS_ADD_SIGN.message(
                        WhoIsTheSpyLocale.PREFIX,
                        player,
                        "%GAME%",
                        lines[1]
                );
            } catch (IOException e) {
                e.printStackTrace();
                WhoIsTheSpyLocale.ERROR_ADD_SIGN.message(
                        WhoIsTheSpyLocale.PREFIX,
                        player
                );
            }
        }

    }

}
