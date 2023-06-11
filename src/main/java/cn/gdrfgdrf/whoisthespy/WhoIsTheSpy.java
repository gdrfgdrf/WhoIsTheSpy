package cn.gdrfgdrf.whoisthespy;

import cn.gdrfgdrf.whoisthespy.Command.WhoIsTheSpyCommand;
import cn.gdrfgdrf.whoisthespy.Data.Config;
import cn.gdrfgdrf.whoisthespy.Display.DisplayScoreboard;
import cn.gdrfgdrf.whoisthespy.Display.DisplaySelectBeQuestionedInventory;
import cn.gdrfgdrf.whoisthespy.Game.Game;
import cn.gdrfgdrf.whoisthespy.Listener.*;
import cn.gdrfgdrf.whoisthespy.Locale.WhoIsTheSpyLocale;
import cn.gdrfgdrf.whoisthespy.Player.PlayerInfo;
import cn.gdrfgdrf.whoisthespy.Utils.ItemType;
import cn.gdrfgdrf.whoisthespy.Utils.de.pauhull.utils.file.FileUtils;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

public class WhoIsTheSpy {
    public static final boolean DEBUG = false;
    public static final String PLUGIN_NAME = "WhoIsTheSpy";
    public static final String PLUGIN_FOLDER = "plugins/" + PLUGIN_NAME + "/";

    public static final String NAME_SPACED_KEY = "WhoIsTheSpy";

    public static final String LOGGER_PREFIX = "§c[WhoIsTheSpy] ";

    @Getter
    private static WhoIsTheSpy instance;

    @Getter
    private final JavaPlugin plugin;

    @Getter
    private final DisplayScoreboard displayScoreboard;

    @Getter
    private final DisplaySelectBeQuestionedInventory displaySelectBeQuestionedInventory;

    @Getter
    private final Config config;

    @Getter
    private final ExecutorService executorService;

    @Getter
    private final ScheduledExecutorService scheduledExecutorService;
    @Getter
    private final List<PlayerInfo> players = new LinkedList<>();
    @Getter
    private String gameChatFormat;
    @Getter
    private List<Game> games;
    @Getter
    private List<String> words;
    @Getter
    private int signUpdateMillis;

    @Getter
    private int signUpdaterTask;

    public WhoIsTheSpy(JavaPlugin plugin, Config config, ExecutorService executorService, ScheduledExecutorService scheduledExecutorService) {
        instance = this;

        this.displayScoreboard = new DisplayScoreboard();
        this.displaySelectBeQuestionedInventory = new DisplaySelectBeQuestionedInventory();

        this.plugin = plugin;
        this.config = config;
        this.executorService = executorService;
        this.scheduledExecutorService = scheduledExecutorService;
    }

    public static void sendMessageToConsole(String message) {
        Bukkit.getConsoleSender().sendMessage(LOGGER_PREFIX + message);
    }

    public void load() {
        File file = new File(PLUGIN_FOLDER);

        if (!file.exists()) {
            if (!file.mkdirs()) {
                Bukkit.getConsoleSender().sendMessage(LOGGER_PREFIX + "Cannot create plugin folder");
            }
        }

        WhoIsTheSpyLocale.init();
        WhoIsTheSpyLocale.loadLocale(new File(PLUGIN_FOLDER + "Locale/" + config.getConfiguration().getString("Locale")));
    }

    public void reload() {
        config.reload();

        Class<?> itemTypeClazz = ItemType.class;
        Object[] itemTypeObjects = itemTypeClazz.getEnumConstants();

        try {
            Method itemTypeReload = itemTypeClazz.getMethod("reload");

            for (Object o : itemTypeObjects) {
                itemTypeReload.invoke(o);
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            sendMessageToConsole("Cannot load class ItemType.class");
        }

        this.games = this.loadAllGames();
        this.words = this.loadAllWords();

        for (Game game : games) {
            game.getGameDataManager().reload();
        }

        WhoIsTheSpyLocale.loadLocale(new File(PLUGIN_FOLDER + "Locale/" + config.getConfiguration().getString("Locale")));

        if (config.getConfiguration().isString("GameChatFormat")) {
            gameChatFormat = ChatColor.translateAlternateColorCodes('&', config.getConfiguration().getString("GameChatFormat"));
        } else {
            gameChatFormat = null;
        }

        if (config.getConfiguration().isInt("Sign.UpdateMillis")) {
            signUpdateMillis = config.getConfiguration().getInt("Sign.UpdateMillis");
        } else {
            signUpdateMillis = 500;
        }

        if (Bukkit.getScheduler().isCurrentlyRunning(signUpdaterTask)) {
            Bukkit.getScheduler().cancelTask(signUpdaterTask);
        }

        this.signUpdaterTask = Game.startUpdatingSigns(signUpdateMillis / 50);
    }

    public void enable() {
        this.reload();

        new PlayerJoinGameListener(this);
        new PlayerLeaveGameListener(this);
        new GameStartListener(this);
        new GameEndListener(this);
        new AsyncPlayerChatListener(this);
        new PlayerJoinListener(this);
        new PlayerQuitListener(this);
        new PlayerInteractListener(this);
        new SignChangeListener(this);
        new BlockBreakListener(this);
        new InventoryCloseListener(this);
        new InventoryClickListener(this);
        new UndercoverGuessWordListener(this);

        new WhoIsTheSpyCommand(this);
    }

    public void disable() {
        if (Bukkit.getScheduler().isCurrentlyRunning(this.signUpdaterTask)) {
            Bukkit.getScheduler().cancelTask(this.signUpdaterTask);
        }

        for (PlayerInfo playerInfo : players) {
            if (playerInfo.getPlayerData() != null) {
                playerInfo.getPlayerData().apply(playerInfo.getPlayer());
            }
        }

        Game.saveAll();
    }

    private List<Game> loadAllGames() {
        List<Game> games = new LinkedList<>();
        File folder = new File(PLUGIN_FOLDER + "Games/");

        if (!folder.exists()) {
            folder.mkdirs();
        }

        File[] files = folder.listFiles();

        if (files == null) {
            return games;
        }

        for (File file : files) {
            if (!file.isFile()) {
                continue;
            }

            try {
                Game game = Game.loadFromFile(FileUtils.removeExtension(file.getName()));
                games.add(game);
            } catch (Exception e) {
                e.printStackTrace();
                sendMessageToConsole(file.getName() + " loading failed");
            }
        }

        return games;
    }

    private List<String> loadAllWords() {
        return config.getConfiguration().getStringList("Word");
    }

    public PlayerInfo getPlayerInfo(Player player) {
        for (PlayerInfo playerInfo : players) {
            if (playerInfo.getPlayer() == player) {
                return playerInfo;
            }
        }

        return null;
    }
}
