package cn.gdrfgdrf.whoisthespy.Listener;

import cn.gdrfgdrf.whoisthespy.Game.Game;
import cn.gdrfgdrf.whoisthespy.Player.PlayerInfo;
import cn.gdrfgdrf.whoisthespy.WhoIsTheSpy;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

public class InventoryCloseListener implements Listener {
    private final WhoIsTheSpy whoIsTheSpy;

    public InventoryCloseListener(WhoIsTheSpy whoIsTheSpy) {
        this.whoIsTheSpy = whoIsTheSpy;

        Bukkit.getPluginManager().registerEvents(this, whoIsTheSpy.getPlugin());
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        Inventory inventory = event.getInventory();
        PlayerInfo playerInfo = PlayerInfo.getFromPlayer(player);

        if (playerInfo != null && playerInfo.isInGame()) {
            Game game = playerInfo.getCurrentGame();

            if (game.getGood().contains(playerInfo)) {
                ChestGui voteGui = game.getPlayerInfoAndVoteInventory().get(playerInfo).getChestGui();

                if (game.getInitVotePlayers().contains(playerInfo) &&
                        !game.getVotedPlayers().contains(playerInfo) &&
                        inventory == voteGui.getInventory() &&
                        !game.getAbstainVotePlayer().contains(playerInfo)
                ) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            voteGui.show(player);
                        }
                    }.runTaskLater(whoIsTheSpy.getPlugin(), 1);
                } else {
                    checkQuestionInventory(player, inventory, playerInfo, game);
                }
            } else {
                checkQuestionInventory(player, inventory, playerInfo, game);
            }
        }
    }

    private void checkQuestionInventory(
            Player player,
            Inventory inventory,
            PlayerInfo playerInfo,
            Game game
    ) {
        PlayerInfo questioner = game.getQuestioner();

        if (questioner != null &&
                questioner == playerInfo &&
                game.getBeQuestioned() == null &&
                (inventory == game.getQUESTIONER_SELECT_BE_QUESTIONED().getInventory() ||
                        inventory == game.getPlayerInfoAndVoteInventory().get(playerInfo).getChestGui().getInventory())
        ) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (game.getVotedPlayers().contains(playerInfo) ||
                            game.getAbstainVotePlayer().contains(playerInfo)) {
                        game.getQUESTIONER_SELECT_BE_QUESTIONED().show(player);
                    } else {
                        if (!game.getInitVotePlayers().contains(playerInfo)) {
                            game.getQUESTIONER_SELECT_BE_QUESTIONED().show(player);
                        }
                    }
                }
            }.runTaskLater(whoIsTheSpy.getPlugin(), 1);
        }
    }

}
