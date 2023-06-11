package cn.gdrfgdrf.whoisthespy.Utils;

import cn.gdrfgdrf.whoisthespy.Player.PlayerHead;
import cn.gdrfgdrf.whoisthespy.Player.PlayerInfo;
import cn.gdrfgdrf.whoisthespy.Utils.de.pauhull.utils.message.type.ActionBarMessage;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Util {
    public static boolean isWindows() {
        return System.getProperties().getProperty("os.name").toUpperCase().contains("WINDOWS");
    }

    public static String getSeparator(int length, boolean console) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("§8").append(!console ? "§m" : "");
        for (int i = 0; i < length; i++)
            stringBuilder.append(console ? "*" : "=");
        stringBuilder.append("§r");

        return stringBuilder.toString();
    }

    public static boolean verifyStringToInteger(String i) {
        try {
            Integer.parseInt(i);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public static boolean isValidFileName(String fileName) {
        if (fileName == null || fileName.length() > 255) {
            return false;
        } else {
            if (isWindows()) {
                return fileName.matches("^[^\\\\/:*?\"<>|\\r\\n]+$");
            } else {
                return fileName.matches("^[^/]+$");
            }
        }
    }

    public static void showActionBar(String message, Player player) {
        ActionBarMessage actionBarMessage = new ActionBarMessage(ChatColor.translateAlternateColorCodes('&', message));
        actionBarMessage.send(player);
    }

    public static List<ItemStack> PlayerHeadListToItemStackList(List<PlayerHead> playerHeads) {
        List<ItemStack> result = new LinkedList<>();

        for (PlayerHead playerHead : playerHeads) {
            result.add(playerHead.getItemStack());
        }

        return result;
    }

    public static void clearItemStack(Player player, ItemStack itemStack) {
        while (true) {
            if (player.getInventory().contains(itemStack)) {
                int index = player.getInventory().first(itemStack);

                player.getInventory().setItem(index, new ItemStack(Material.AIR));
            } else {
                break;
            }
        }
    }

    public static String[] translateAlternateColorCodes(String[] str) {
        String[] result = new String[str.length];

        for (int i = 0; i < str.length; i++) {
            result[i] = ChatColor.translateAlternateColorCodes('&', str[i]);
        }

        return result;
    }

    public static void sendTitle(Player player, String title, String subtitle) {
        player.sendTitle(title, subtitle, 10, 70, 20);
    }

    public static void sendTitleForPlayerInfoList(List<PlayerInfo> list, String title, String subtitle) {
        for (PlayerInfo player : list) {
            sendTitle(player.getPlayer(), title, subtitle);
        }
    }

    public static void initPlayerStatus(Player player, int slot) {
        player.getInventory().clear();

        player.setHealth(20);
        player.setFoodLevel(20);
        player.setLevel(0);
        player.setExp(0);
        player.setGameMode(GameMode.ADVENTURE);

        player.getInventory().setItem(slot, ItemType.LEAVE_GAME.getItemStack());
        player.updateInventory();
    }

    public static void initPlayerStatus(List<PlayerInfo> list, int slot) {
        for (PlayerInfo playerInfo : list) {
            initPlayerStatus(playerInfo.getPlayer(), slot);
        }
    }

    public static ItemStack getHead(Player player) {
        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);

        String value = getHeadValue(player);

        if (value == null || value.isEmpty()) {
            return itemStack;
        }

        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        Field profileField = null;

        profile.getProperties().put("textures", new Property("textures", value));

        ItemMeta itemMeta = itemStack.getItemMeta();

        try {
            profileField = itemMeta.getClass().getDeclaredField("profile");
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }

        profileField.setAccessible(true);

        try {
            profileField.set(itemMeta, profile);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }

        itemStack.setItemMeta(itemMeta);
        return itemStack;

    }

    public static String getHeadValue(Player player) {
        try {
            String name = player.getName();

            String result = getURLContent("https://api.mojang.com/users/profiles/minecraft/" + name);

            if (result == null || result.isEmpty()) {
                return null;
            }

            JsonObject obj = (JsonObject) JsonParser.parseString(result);
            String uid = obj.get("id").toString().replace("\"", "");

            String sign = getURLContent("https://sessionserver.mojang.com/session/minecraft/profile/" + uid);
            if (sign != null) {
                obj = (JsonObject) JsonParser.parseString(sign);
                return obj.getAsJsonArray("properties").get(0).getAsJsonObject().get("value").getAsString();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getURLContent(String urlStr) {
        URL url;
        BufferedReader in = null;
        StringBuilder sb = new StringBuilder();
        try {
            url = new URL(urlStr);
            in = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
            String str;
            while ((str = in.readLine()) != null) {
                sb.append(str);
            }
        } catch (Exception e) {
            return null;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
}
