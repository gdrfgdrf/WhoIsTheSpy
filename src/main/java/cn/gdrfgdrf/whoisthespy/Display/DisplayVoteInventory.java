package cn.gdrfgdrf.whoisthespy.Display;

import cn.gdrfgdrf.whoisthespy.Game.Game;
import cn.gdrfgdrf.whoisthespy.Player.PlayerInfo;
import cn.gdrfgdrf.whoisthespy.Utils.ItemType;
import cn.gdrfgdrf.whoisthespy.Utils.Util;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static cn.gdrfgdrf.whoisthespy.Locale.WhoIsTheSpyLocale.*;

public class DisplayVoteInventory {

    private final HashMap<PlayerInfo, Integer> playerInfoCountdown = new HashMap<>();

    public void setCountdown(Game game) {
        for (PlayerInfo playerInfo : game.getInitVotePlayers()) {
            if (!game.getVotedPlayers().contains(playerInfo) &&
                    !game.getAbstainVotePlayer().contains(playerInfo) &&
                    game.getGood().contains(playerInfo)) {
                if (!playerInfoCountdown.containsKey(playerInfo)) {
                    playerInfoCountdown.put(playerInfo, game.getVoteDuration());
                }
            } else {
                playerInfoCountdown.remove(playerInfo);
            }
        }

        playerInfoCountdown.replaceAll((playerInfo, countdown) -> countdown - 1);

        playerInfoCountdown.forEach((playerInfo, countdown) -> {
            Inventory inventory = game.getPlayerInfoAndVoteInventory()
                    .get(playerInfo)
                    .getChestGui()
                    .getInventory();
            Inventory openInventory = playerInfo.getPlayer().getOpenInventory().getTopInventory();

            if (openInventory == inventory) {
                ItemStack itemStack = inventory.getItem(49);

                if (itemStack != null) {
                    if (itemStack.getType() == ItemType.NAVIGATION_COUNTDOWN_VOTE.getType()) {
                        ItemMeta itemMeta = itemStack.getItemMeta();

                        if (itemMeta != null) {
                            itemMeta.setDisplayName(ITEM_NAVIGATION_COUNTDOWN_VOTE.toString());

                            String[] lore = ITEM_NAVIGATION_COUNTDOWN_VOTE_LORE.getValues();
                            if (lore != null && checkLore(lore)) {
                                itemMeta.setLore(toList(lore, countdown));
                            }

                            itemStack.setItemMeta(itemMeta);
                        }

                        if (countdown <= 0) {
                            if (!game.getAbstainVotePlayer().contains(playerInfo)) {
                                game.getAbstainVotePlayer().add(playerInfo);
                            }

                            playerInfo.getPlayer().closeInventory();
                            Util.clearItemStack(playerInfo.getPlayer(), ItemType.INIT_VOTE.getItemStack());

                            game.broadcast(
                                    PREFIX,
                                    VOTE_TIMEOUT,
                                    "%PLAYER%",
                                    playerInfo.getPlayer().getDisplayName()
                            );
                        }
                    }
                }
            }
        });
    }

    public boolean checkLore(String[] lore) {
        if (lore.length == 1) {
            return !StringUtils.isEmpty(lore[0]);
        } else {
            return lore.length > 1;
        }
    }

    public List<String> toList(String[] lore, int leftTime) {
        List<String> result = new LinkedList<>();

        for (String s : lore) {
            result.add(ChatColor.translateAlternateColorCodes('&', s).replace("%TIME%", String.valueOf(leftTime)));
        }

        return result;
    }

}
