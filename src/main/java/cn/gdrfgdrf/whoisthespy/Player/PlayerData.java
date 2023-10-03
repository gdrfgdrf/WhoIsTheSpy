package cn.gdrfgdrf.whoisthespy.Player;

import cn.gdrfgdrf.whoisthespy.WhoIsTheSpy;
import lombok.*;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scoreboard.Scoreboard;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PlayerData {

    @Getter
    @NonNull
    double health;

    @Getter
    @NonNull
    int foodLevel, level, fireTicks;

    @Getter
    @NonNull
    boolean flying, allowFlight;

    @Getter
    @NonNull
    float exp;

    @Getter
    @NonNull
    ItemStack[] contents, armorContents;

    @Getter
    @NonNull
    GameMode gameMode;

    @Getter
    @NonNull
    Scoreboard scoreboard;

    @Getter
    @NonNull
    Collection<PotionEffect> activePotionEffects;

    @Setter
    @Getter
    private File file = null;

    public PlayerData() {

    }

    public static PlayerData create(Player player) {
        double health = player.getHealth();
        int foodLevel = player.getFoodLevel();
        int level = player.getLevel();
        int fireTicks = player.getFireTicks();
        boolean flying = player.isFlying();
        boolean allowFlight = player.getAllowFlight();
        float exp = player.getExp();

        ItemStack[] contents = player.getInventory().getContents();
        ItemStack[] armorContents = player.getInventory().getArmorContents();
        GameMode gameMode = player.getGameMode();
        Scoreboard scoreboard = player.getScoreboard();
        Collection<PotionEffect> activePotionEffects = player.getActivePotionEffects();

        PlayerData playerData = new PlayerData(
                health,
                foodLevel,
                level,
                fireTicks,
                flying,
                allowFlight,
                exp,
                contents,
                armorContents,
                gameMode,
                scoreboard,
                activePotionEffects
        );

        playerData.saveToFile(new File(
                WhoIsTheSpy.PLUGIN_FOLDER + "/PlayerData",
                player.getUniqueId() + ".yml"
        ));
        return playerData;
    }

    private static String capitalizeWord(String name) {
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }

    @SuppressWarnings("unchecked")
    public static PlayerData getFromFile(File file) {
        try {

            FileConfiguration config = YamlConfiguration.loadConfiguration(file);
            PlayerData playerData = new PlayerData();

            for (Field field : playerData.getClass().getDeclaredFields()) {

                String name = capitalizeWord(field.getName());
                if (field.getType() == GameMode.class) {
                    GameMode gameMode = GameMode.valueOf(config.getString(name));
                    field.set(playerData, gameMode);
                } else if (field.getType() == Scoreboard.class || field.getType() == File.class) {
                    //ignore
                } else if (field.getType() == float.class) {
                    field.set(playerData, (float) config.getDouble(name));
                } else if (field.getType() == ItemStack[].class) {
                    field.set(playerData, ((List<ItemStack>) config.get(name)).toArray(new ItemStack[0]));
                } else {
                    field.set(playerData, config.get(name));
                }
            }

            playerData.file = file;
            return playerData;

        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }

    }

    public void saveToFile(File file) {
        try {

            this.file = file;

            file.getParentFile().mkdirs();

            FileConfiguration config = new YamlConfiguration();

            for (Field field : this.getClass().getDeclaredFields()) {

                String name = capitalizeWord(field.getName());
                if (field.getType() == GameMode.class) {
                    GameMode gameMode = (GameMode) field.get(this);
                    config.set(name, gameMode.name());
                } else if (field.getType() == Scoreboard.class || field.getType() == File.class) {
                    //ignore
                } else {
                    config.set(name, field.get(this));
                }
            }

            config.save(file);

        } catch (IOException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("all")
    public void apply(Player player) {
        if (file != null) {
            file.delete();
        }

        player.setHealth(health);
        player.setFoodLevel(foodLevel);
        player.setLevel(level);
        player.setFireTicks(fireTicks);
        player.setExp(exp);
        player.setAllowFlight(allowFlight);
        player.setFlying(flying && allowFlight);
        player.getInventory().setContents(contents);
        player.getInventory().setArmorContents(armorContents);
        player.setGameMode(gameMode);
        player.addPotionEffects(activePotionEffects);

        if (scoreboard != null) {
            player.setScoreboard(scoreboard);
        }
    }

}
