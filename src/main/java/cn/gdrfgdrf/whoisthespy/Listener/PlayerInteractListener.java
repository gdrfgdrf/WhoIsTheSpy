package cn.gdrfgdrf.whoisthespy.Listener;

import cn.gdrfgdrf.whoisthespy.Command.WhoIsTheSpyCommand;
import cn.gdrfgdrf.whoisthespy.Game.Game;
import cn.gdrfgdrf.whoisthespy.Locale.WhoIsTheSpyLocale;
import cn.gdrfgdrf.whoisthespy.Player.PlayerInfo;
import cn.gdrfgdrf.whoisthespy.Utils.ItemType;
import cn.gdrfgdrf.whoisthespy.Utils.Util;
import cn.gdrfgdrf.whoisthespy.WhoIsTheSpy;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {
    private final WhoIsTheSpy whoIsTheSpy;

    public PlayerInteractListener(WhoIsTheSpy whoIsTheSpy) {
        this.whoIsTheSpy = whoIsTheSpy;

        Bukkit.getPluginManager().registerEvents(this, whoIsTheSpy.getPlugin());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        PlayerInfo playerInfo = PlayerInfo.getFromPlayer(player);
        ItemStack itemStack = event.getItem();
        Block block = event.getClickedBlock();

        Action action = event.getAction();

        signCheck:
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (block == null || !block.getType().name().contains("SIGN")) {
                break signCheck;
            }

            for (Game game : whoIsTheSpy.getGames()) {
                for (Location location : game.getSigns().getLocations()) {
                    if (block.getLocation().equals(location)) {
                        if (player.hasPermission(
                                WhoIsTheSpyCommand.PERMISSION_USER_PREFIX + "join"
                        )) {
                            game.addPlayer(player);
                        } else {
                            WhoIsTheSpyLocale.ERROR_NO_PERMISSIONS.message(
                                    WhoIsTheSpyLocale.PREFIX,
                                    player
                            );
                        }

                        return;
                    }
                }
            }
        }

        if (itemStack == null) {
            return;
        }

        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            if (itemStack.equals(ItemType.LEAVE_GAME.getItemStack())) {
                if (playerInfo == null || !playerInfo.isInGame()) {
                    WhoIsTheSpyLocale.ERROR_NOT_IN_GAME.message(
                            WhoIsTheSpyLocale.PREFIX,
                            player
                    );
                    return;
                }

                player.getInventory().clear();

                Game game = playerInfo.getCurrentGame();
                game.removePlayer(playerInfo);

                event.setCancelled(true);
            }

            if (itemStack.equals(ItemType.END_ANSWER.getItemStack())) {
                if (playerInfo == null || !playerInfo.isInGame()) {
                    WhoIsTheSpyLocale.ERROR_NOT_IN_GAME.message(
                            WhoIsTheSpyLocale.PREFIX,
                            player
                    );
                    return;
                }

                Game game = playerInfo.getCurrentGame();

                if (playerInfo == game.getBeQuestioned()) {
                    game.setQuestioner(playerInfo);
                    game.setBeQuestioned(null);
                    game.getPhaseHandler()
                            .getGamePhase()
                            .setSelectBeQuestionedCountdown(
                                    game.getSelectBeQuestionedDuration()
                            );

                    Util.clearItemStack(
                            player,
                            ItemType.END_ANSWER.getItemStack()
                    );

                    game.broadcast(
                            WhoIsTheSpyLocale.PREFIX,
                            WhoIsTheSpyLocale.PLAYER_END_ANSWER,
                            "%PLAYER%",
                            player.getDisplayName()
                    );

                    game.getQUESTIONER_SELECT_BE_QUESTIONED().show(player);
                }
            }

            if (itemStack.equals(ItemType.INIT_VOTE.getItemStack())) {
                if (playerInfo == null || !playerInfo.isInGame()) {
                    WhoIsTheSpyLocale.ERROR_NOT_IN_GAME.message(
                            WhoIsTheSpyLocale.PREFIX,
                            player
                    );
                    return;
                }

                Game game = playerInfo.getCurrentGame();

                if (game.getGood().contains(playerInfo)) {
                    ChestGui voteGui = game.getPlayerInfoAndVoteInventory()
                            .get(playerInfo)
                            .getChestGui();

                    if (game.getAbstainVotePlayer().contains(playerInfo)) {
                        WhoIsTheSpyLocale.VOTE_ABSTAIN.message(
                                WhoIsTheSpyLocale.PREFIX,
                                player
                        );
                        return;
                    }

                    if (game.getVotedPlayers().contains(playerInfo)) {
                        WhoIsTheSpyLocale.ALREADY_VOTED.message(
                                WhoIsTheSpyLocale.PREFIX,
                                player
                        );
                        return;
                    }

                    if (game.getInitVotePlayers().contains(playerInfo)) {
                        WhoIsTheSpyLocale.ALREADY_INIT_VOTE.message(
                                WhoIsTheSpyLocale.PREFIX,
                                player
                        );
                        voteGui.show(player);
                        return;
                    }

                    game.getInitVotePlayers().add(playerInfo);

                    Util.sendTitleForPlayerInfoList(
                            game.getPlayersInGame(),
                            WhoIsTheSpyLocale.PLAYER_INIT_VOTE_TITLE.toString(),
                            WhoIsTheSpyLocale.PLAYER_INIT_VOTE_SUBTITLE.toString()
                                    .replace("%PLAYER%", player.getDisplayName()));
                    game.broadcast(
                            WhoIsTheSpyLocale.PREFIX,
                            WhoIsTheSpyLocale.PLAYER_INIT_VOTE,
                            "%PLAYER%",
                            player.getDisplayName()
                    );

                    voteGui.show(player);
                } else {
                    WhoIsTheSpyLocale.NOT_GOOD.message(
                            WhoIsTheSpyLocale.PREFIX,
                            player
                    );
                }
            }

            if (itemStack.equals(ItemType.INIT_GUESS_WORD.getItemStack())) {
                if (playerInfo == null || !playerInfo.isInGame()) {
                    WhoIsTheSpyLocale.ERROR_NOT_IN_GAME.message(
                            WhoIsTheSpyLocale.PREFIX,
                            player
                    );
                    return;
                }

                Game game = playerInfo.getCurrentGame();

                if (game.getUndercover() == playerInfo) {
                    if (game.getUndercoverGuess() == null) {
                        game.getGUESS_WORD_GUI().open(player);
                    } else {
                        WhoIsTheSpyLocale.ALREADY_GUESS_WORD.message(
                                WhoIsTheSpyLocale.PREFIX,
                                player
                        );
                    }
                } else {
                    WhoIsTheSpyLocale.NOT_UNDERCOVER.message(
                            WhoIsTheSpyLocale.PREFIX,
                            player
                    );
                }
            }
        }

    }

}
