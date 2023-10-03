package cn.gdrfgdrf.whoisthespy.Player;

import cn.gdrfgdrf.whoisthespy.Locale.WhoIsTheSpyLocale;
import cn.gdrfgdrf.whoisthespy.Utils.Util;
import cn.gdrfgdrf.whoisthespy.WhoIsTheSpy;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class PlayerHead {

    @Setter
    @Getter
    private Player player;

    @Setter
    private ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);

    public PlayerHead(Player player) {
        this.player = player;
    }

    public ItemStack getItemStack() {
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta == null) {
            return itemStack;
        }

        itemMeta.setDisplayName(
                WhoIsTheSpyLocale.PLAYER_HEAD_PREFIX.toString()
                        .replace("%PLAYER%", player.getDisplayName())
        );

        String[] values = WhoIsTheSpyLocale.PLAYER_HEAD_LORE.getValues();
        if (values != null && checkLore(values)) {
            values = Util.translateAlternateColorCodes(values);

            itemMeta.setLore(Arrays.asList(values));
        }

        NamespacedKey namespacedKey = new NamespacedKey(
                WhoIsTheSpy.getInstance().getPlugin(),
                WhoIsTheSpy.NAME_SPACED_KEY
        );
        itemMeta.getPersistentDataContainer().set(
                namespacedKey,
                new UUIDDataType(),
                player.getUniqueId()
        );

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public boolean checkLore(String[] lore) {
        if (lore.length == 1) {
            return !StringUtils.isEmpty(lore[0]);
        } else {
            return lore.length > 1;
        }
    }
}
