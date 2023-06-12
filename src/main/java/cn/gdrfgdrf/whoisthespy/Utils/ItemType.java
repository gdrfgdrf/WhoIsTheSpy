package cn.gdrfgdrf.whoisthespy.Utils;

import cn.gdrfgdrf.whoisthespy.WhoIsTheSpy;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

import static cn.gdrfgdrf.whoisthespy.Locale.WhoIsTheSpyLocale.*;

public enum ItemType {

    LEAVE_GAME(ITEM_LEAVE_GAME.toString(), "Item.LEAVE_GAME", Material.RED_BED, ITEM_LEAVE_GAME_LORE.getValues()),
    NAVIGATION_BACKGROUND(ITEM_NAVIGATION_BACKGROUND.toString(), "Item.NAVIGATION_BACKGROUND", Material.BLACK_STAINED_GLASS_PANE, ITEM_NAVIGATION_BACKGROUND_LORE.getValues()),
    NAVIGATION_PREVIOUS(ITEM_NAVIGATION_PREVIOUS.toString(), "Item.NAVIGATION_PREVIOUS", Material.RED_WOOL, ITEM_NAVIGATION_PREVIOUS_LORE.getValues()),
    NAVIGATION_COUNTDOWN(ITEM_NAVIGATION_COUNTDOWN.toString(), "Item.NAVIGATION_COUNTDOWN", Material.NETHER_STAR, ITEM_NAVIGATION_COUNTDOWN_LORE.getValues()),
    NAVIGATION_COUNTDOWN_VOTE(ITEM_NAVIGATION_COUNTDOWN_VOTE.toString(), "Item.NAVIGATION_COUNTDOWN_VOTE", Material.NETHER_STAR, ITEM_NAVIGATION_COUNTDOWN_VOTE_LORE.getValues()),
    NAVIGATION_NEXT(ITEM_NAVIGATION_NEXT.toString(), "Item.NAVIGATION_NEXT", Material.GREEN_WOOL, ITEM_NAVIGATION_NEXT_LORE.getValues()),
    END_ANSWER(ITEM_END_ANSWER.toString(), "Item.END_ANSWER", Material.REDSTONE_BLOCK, ITEM_END_ANSWER_LORE.getValues()),
    INIT_VOTE(ITEM_INIT_VOTE.toString(), "Item.INIT_VOTE", Material.ANVIL, ITEM_INIT_VOTE_LORE.getValues()),
    INIT_GUESS_WORD(ITEM_INIT_GUESS_WORD.toString(), null, Material.ANVIL, ITEM_INIT_GUESS_WORD_LORE.getValues()),
    GUESS_WORD_GUI_PLACEHOLDER("", "Item.GUESS_WORD_GUI_PLACEHOLDER", Material.PAPER, "");

    public static final String errorMessage = "The value of %PATH% is not an enumeration of Material.class, the default Material.class enumeration of %DEFAULT_MATERIAL% is used";
    private final String name;
    private final String[] lore;
    private final String path;
    private final Material defaultMaterial;
    private Material type;

    ItemType(String name, String path, Material defaultMaterial, String... lore) {
        this.path = path;
        this.defaultMaterial = defaultMaterial;
        this.name = name;

        lore = Util.translateAlternateColorCodes(lore);

        this.lore = lore;
    }

    public ItemStack getItemStack() {
        ItemStack itemStack = new ItemStack(this.type);
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (this.lore != null && checkLore()) {
            itemMeta.setLore(Arrays.asList(lore));
        }

        itemMeta.setDisplayName(this.name);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public boolean checkLore() {
        if (lore.length == 1) {
            return !StringUtils.isEmpty(lore[0]);
        } else {
            return lore.length > 1;
        }
    }

    public String getName() {
        return name;
    }

    public Material getType() {
        return type;
    }

    public String[] getLore() {
        return lore;
    }

    public void reload() {
        if (path == null) {
            type = defaultMaterial;
            return;
        }

        if (WhoIsTheSpy.getInstance().getConfig().getConfiguration().isString(path)) {
            try {
                type = Material.valueOf(WhoIsTheSpy.getInstance().getConfig().getConfiguration().getString(path));
            } catch (IllegalArgumentException e) {
                type = defaultMaterial;
                WhoIsTheSpy.sendMessageToConsole(errorMessage.replace("%PATH%", path).replace("%DEFAULT_MATERIAL%", defaultMaterial.name()));
            }
        } else {
            type = defaultMaterial;
        }
    }
}
