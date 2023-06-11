package cn.gdrfgdrf.whoisthespy.Display;

import cn.gdrfgdrf.whoisthespy.Game.Game;
import cn.gdrfgdrf.whoisthespy.Locale.WhoIsTheSpyLocale;
import cn.gdrfgdrf.whoisthespy.Player.PlayerInfo;
import cn.gdrfgdrf.whoisthespy.Utils.ItemType;
import cn.gdrfgdrf.whoisthespy.Utils.Util;
import cn.gdrfgdrf.whoisthespy.WhoIsTheSpy;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static cn.gdrfgdrf.whoisthespy.Locale.WhoIsTheSpyLocale.ITEM_NAVIGATION_COUNTDOWN;
import static cn.gdrfgdrf.whoisthespy.Locale.WhoIsTheSpyLocale.ITEM_NAVIGATION_COUNTDOWN_LORE;

public class DisplaySelectBeQuestionedInventory {

    public void setCountdown(int leftTime, Game game) {
        Inventory inventory = game.getQUESTIONER_SELECT_BE_QUESTIONED().getInventory();
        PlayerInfo playerInfo = game.getQuestioner();
        Inventory openInventory = playerInfo.getPlayer().getOpenInventory().getTopInventory();

        if (openInventory == inventory && game.getBeQuestioned() == null) {
            ItemStack itemStack = inventory.getItem(49);

            if (itemStack != null) {
                if (itemStack.getType() == ItemType.NAVIGATION_COUNTDOWN.getType()) {
                    ItemMeta itemMeta = itemStack.getItemMeta();

                    itemMeta.setDisplayName(ITEM_NAVIGATION_COUNTDOWN.toString());

                    String[] lore = ITEM_NAVIGATION_COUNTDOWN_LORE.getValues();
                    if (lore != null && checkLore(lore)) {
                        itemMeta.setLore(toList(lore, leftTime));
                    }

                    itemStack.setItemMeta(itemMeta);

                    if (leftTime <= 0) {
                        game.getPhaseHandler().getGamePhase().setAnswerCountdown(game.getAnswerDuration());

                        PlayerInfo beQuestioned = getRandomBeQuestioned(game);
                        game.setBeQuestioned(beQuestioned);

                        Util.sendTitleForPlayerInfoList(game.getPlayersInGame(), WhoIsTheSpyLocale.QUESTIONER_TO_BE_QUESTIONED_TITLE.toString(), beQuestioned.getPlayer().getDisplayName());
                        game.broadcast(WhoIsTheSpyLocale.PREFIX, WhoIsTheSpyLocale.QUESTIONER_SELECT_BE_QUESTIONED_TIMEOUT, "%QUESTIONER%", playerInfo.getPlayer().getDisplayName(), "%BE_QUESTIONED%", beQuestioned.getPlayer().getDisplayName());

                        playerInfo.getPlayer().closeInventory();
                        Util.clearItemStack(playerInfo.getPlayer(), ItemType.END_ANSWER.getItemStack());

                        if (WhoIsTheSpy.getInstance().getConfig().getConfiguration().isInt("ItemSlot.END_ANSWER")) {
                            beQuestioned.getPlayer().getInventory().setItem(WhoIsTheSpy.getInstance().getConfig().getConfiguration().getInt("ItemSlot.END_ANSWER"), ItemType.END_ANSWER.getItemStack());
                        } else {
                            beQuestioned.getPlayer().getInventory().setItem(4, ItemType.END_ANSWER.getItemStack());
                        }
                    }
                }
            }
        }
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

    public PlayerInfo getRandomBeQuestioned(Game game) {
        Random random = new Random();

        while (true) {
            int index = random.nextInt(game.getPlayersInGame().size());
            PlayerInfo playerInfo = game.getPlayersInGame().get(index);

            if (playerInfo != game.getQuestioner()) {
                return playerInfo;
            }
        }
    }

}
