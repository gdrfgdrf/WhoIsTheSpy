package cn.gdrfgdrf.whoisthespy.Listener;

import cn.gdrfgdrf.whoisthespy.Game.Game;
import cn.gdrfgdrf.whoisthespy.Locale.WhoIsTheSpyLocale;
import cn.gdrfgdrf.whoisthespy.Player.PlayerInfo;
import cn.gdrfgdrf.whoisthespy.Player.UUIDDataType;
import cn.gdrfgdrf.whoisthespy.Utils.InventorySet;
import cn.gdrfgdrf.whoisthespy.Utils.ItemType;
import cn.gdrfgdrf.whoisthespy.Utils.Util;
import cn.gdrfgdrf.whoisthespy.WhoIsTheSpy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.UUID;

public class InventoryClickListener implements Listener {

    private final WhoIsTheSpy whoIsTheSpy;

    public InventoryClickListener(WhoIsTheSpy whoIsTheSpy) {
        this.whoIsTheSpy = whoIsTheSpy;

        Bukkit.getPluginManager().registerEvents(this, whoIsTheSpy.getPlugin());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        PlayerInfo playerInfo = PlayerInfo.getFromPlayer(player);
        Inventory inventory = event.getClickedInventory();
        ItemStack itemStack = event.getCurrentItem();

        if (playerInfo != null && playerInfo.isInGame()) {
            event.setCancelled(true);
        }

        if (itemStack == null || itemStack.getItemMeta() == null || inventory == null) {
            return;
        }

        if (playerInfo != null && playerInfo.isInGame()) {
            Game game = playerInfo.getCurrentGame();

            if (game.getQUESTIONER_SELECT_BE_QUESTIONED().getInventory() == inventory) {
                if (game.getQuestioner() != playerInfo) {
                    WhoIsTheSpyLocale.NOT_QUESTIONER.message(WhoIsTheSpyLocale.PREFIX, player);
                    return;
                }

                if (itemStack.getType() == Material.PLAYER_HEAD) {
                    ItemMeta itemMeta = itemStack.getItemMeta();

                    NamespacedKey namespacedKey = new NamespacedKey(whoIsTheSpy.getPlugin(), WhoIsTheSpy.NAME_SPACED_KEY);
                    PersistentDataContainer container = itemMeta.getPersistentDataContainer();
                    UUID uuid = container.get(namespacedKey, new UUIDDataType());

                    if (uuid != null) {
                        Player clicked_player = Bukkit.getPlayer(uuid);

                        if (clicked_player != null) {
                            PlayerInfo clicked_playerInfo = PlayerInfo.getFromPlayer(clicked_player);

                            if (clicked_playerInfo != null) {
                                if (clicked_playerInfo == playerInfo) {
                                    WhoIsTheSpyLocale.CANNOT_SELECT_SELF.message(WhoIsTheSpyLocale.PREFIX, player);
                                    return;
                                }

                                if (clicked_playerInfo.isInGame()) {
                                    if (game.getBeQuestioned() == null) {
                                        game.getPhaseHandler().getGamePhase().setAnswerCountdown(game.getAnswerDuration());

                                        game.setBeQuestioned(clicked_playerInfo);

                                        Util.sendTitleForPlayerInfoList(game.getPlayersInGame(), WhoIsTheSpyLocale.QUESTIONER_TO_BE_QUESTIONED_TITLE.toString(), clicked_player.getDisplayName());
                                        game.broadcast(WhoIsTheSpyLocale.PREFIX, WhoIsTheSpyLocale.QUESTIONER_TO_BE_QUESTIONED_MESSAGE, "%QUESTIONER%", player.getDisplayName(), "%BE_QUESTIONED%", clicked_player.getDisplayName());

                                        player.closeInventory();
                                        Util.clearItemStack(player, ItemType.END_ANSWER.getItemStack());

                                        if (whoIsTheSpy.getConfig().getConfiguration().isInt("ItemSlot.END_ANSWER")) {
                                            clicked_player.getInventory().setItem(whoIsTheSpy.getConfig().getConfiguration().getInt("ItemSlot.END_ANSWER"), ItemType.END_ANSWER.getItemStack());
                                        } else {
                                            clicked_player.getInventory().setItem(4, ItemType.END_ANSWER.getItemStack());
                                        }
                                    } else {
                                        WhoIsTheSpyLocale.ALREADY_SELECT_BE_QUESTIONED.message(WhoIsTheSpyLocale.PREFIX, player);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            InventorySet inventorySet = game.getPlayerInfoAndVoteInventory().get(playerInfo);

            if (inventorySet != null && inventorySet.getChestGui().getInventory() == inventory) {
                if (game.getGood().contains(playerInfo)) {
                    if (!game.getInitVotePlayers().contains(playerInfo)) {
                        game.getInitVotePlayers().add(playerInfo);
                    }

                    if (itemStack.getType() == Material.PLAYER_HEAD) {
                        ItemMeta itemMeta = itemStack.getItemMeta();

                        NamespacedKey namespacedKey = new NamespacedKey(whoIsTheSpy.getPlugin(), WhoIsTheSpy.NAME_SPACED_KEY);
                        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
                        UUID uuid = container.get(namespacedKey, new UUIDDataType());

                        if (uuid != null) {
                            Player clicked_player = Bukkit.getPlayer(uuid);

                            if (clicked_player != null) {
                                PlayerInfo clicked_playerInfo = PlayerInfo.getFromPlayer(clicked_player);

                                if (clicked_playerInfo != null) {
                                    if (clicked_playerInfo == playerInfo) {
                                        WhoIsTheSpyLocale.CANNOT_SELECT_SELF.message(WhoIsTheSpyLocale.PREFIX, player);
                                        return;
                                    }

                                    if (clicked_playerInfo.isInGame()) {
                                        if (game.getVotedPlayers().contains(playerInfo)) {
                                            WhoIsTheSpyLocale.ALREADY_VOTED.message(WhoIsTheSpyLocale.PREFIX, player);
                                            return;
                                        }

                                        game.getVotedPlayers().add(playerInfo);
                                        game.getVOTER_AND_BE_VOTED().put(playerInfo, clicked_playerInfo);

                                        Util.sendTitleForPlayerInfoList(game.getPlayersInGame(), WhoIsTheSpyLocale.VOTE_TITLE.toString(), WhoIsTheSpyLocale.VOTE_SUBTITLE.toString()
                                                .replace("%VOTER%", player.getDisplayName())
                                                .replace("%BE_VOTED%", clicked_player.getDisplayName()));
                                        game.broadcast(WhoIsTheSpyLocale.PREFIX, WhoIsTheSpyLocale.VOTE_MESSAGE, "%VOTER%", player.getDisplayName(), "%BE_VOTED%", clicked_player.getDisplayName());

                                        player.closeInventory();
                                        Util.clearItemStack(player, ItemType.INIT_VOTE.getItemStack());
                                    }
                                }
                            }
                        }
                    }
                } else {
                    WhoIsTheSpyLocale.NOT_GOOD.message(WhoIsTheSpyLocale.PREFIX, player);
                }
            }
        }

    }

}
