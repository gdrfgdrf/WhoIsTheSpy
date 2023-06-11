package cn.gdrfgdrf.whoisthespy.Game;

import cn.gdrfgdrf.whoisthespy.Events.PlayerJoinGameEvent;
import cn.gdrfgdrf.whoisthespy.Events.PlayerLeaveGameEvent;
import cn.gdrfgdrf.whoisthespy.Events.UndercoverGuessWordEvent;
import cn.gdrfgdrf.whoisthespy.Locale.WhoIsTheSpyLocale;
import cn.gdrfgdrf.whoisthespy.Phase.PhaseHandler;
import cn.gdrfgdrf.whoisthespy.Player.PlayerInfo;
import cn.gdrfgdrf.whoisthespy.Utils.InventorySet;
import cn.gdrfgdrf.whoisthespy.Utils.ItemType;
import cn.gdrfgdrf.whoisthespy.Utils.de.pauhull.utils.locale.storage.LocaleString;
import cn.gdrfgdrf.whoisthespy.WhoIsTheSpy;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import lombok.Getter;
import lombok.Setter;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.*;

import static cn.gdrfgdrf.whoisthespy.Locale.WhoIsTheSpyLocale.INVENTORY_TEXT_INPUT_GUESS_WORD_TITLE;

public class Game {

    public static final int MIN_PLAYER = 3;
    public static final int MAX_PLAYER = 10;
    public static final int COUNTDOWN = 30;
    public static final int GAME_DURATION = 600;
    public static final int SELECT_BE_QUESTIONED_DURATION = 15;
    public static final int ANSWER_DURATION = 15;
    public static final int VOTE_DURATION = 15;

    private WhoIsTheSpy whoIsTheSpy;

    @Getter
    private GameDataSet data;

    @Setter
    @Getter
    private GameState gameState;

    @Setter
    @Getter
    private List<PlayerInfo> playersInGame;

    @Setter
    @Getter
    private GameDataManager gameDataManager;

    @Setter
    @Getter
    private PhaseHandler phaseHandler;

    @Setter
    @Getter
    private PlayerInfo undercover;

    @Setter
    @Getter
    private PlayerInfo questioner;

    @Setter
    @Getter
    private PlayerInfo beQuestioned;

    @Setter
    @Getter
    private List<PlayerInfo> good;

    @Setter
    @Getter
    private String word;

    @Setter
    @Getter
    private List<PlayerInfo> initVotePlayers;

    @Setter
    @Getter
    private List<PlayerInfo> votedPlayers;

    @Setter
    @Getter
    private List<PlayerInfo> abstainVotePlayer;

    @Setter
    @Getter
    private HashMap<PlayerInfo, InventorySet> playerInfoAndVoteInventory;

    @Setter
    @Getter
    private HashMap<PlayerInfo, PlayerInfo> VOTER_AND_BE_VOTED;

    @Setter
    @Getter
    private String undercoverGuess;

    @Setter
    @Getter
    private AnvilGUI.Builder GUESS_WORD_GUI;

    @Setter
    @Getter
    private OutlinePane QUESTIONER_SELECT_BE_QUESTIONED_OUTLINE_PANE = new OutlinePane(0, 5, 9, 1);

    @Setter
    @Getter
    private PaginatedPane QUESTIONER_SELECT_BE_QUESTIONED_PAGINATED_PANE = new PaginatedPane(0, 0, 9, 5);

    @Setter
    @Getter
    private StaticPane QUESTIONER_SELECT_BE_QUESTIONED_NAVIGATION_STATIC_PANE = new StaticPane(0, 5, 9, 1);

    @Setter
    @Getter
    private ChestGui QUESTIONER_SELECT_BE_QUESTIONED;

    {
        QUESTIONER_SELECT_BE_QUESTIONED = new ChestGui(6, WhoIsTheSpyLocale.INVENTORY_QUESTIONER_SELECT_BE_QUESTIONED_TITLE.toString());

        QUESTIONER_SELECT_BE_QUESTIONED_OUTLINE_PANE.addItem(new GuiItem(ItemType.NAVIGATION_BACKGROUND.getItemStack()));
        QUESTIONER_SELECT_BE_QUESTIONED_OUTLINE_PANE.setRepeat(true);
        QUESTIONER_SELECT_BE_QUESTIONED_OUTLINE_PANE.setPriority(Pane.Priority.LOWEST);

        QUESTIONER_SELECT_BE_QUESTIONED_NAVIGATION_STATIC_PANE.addItem(new GuiItem(ItemType.NAVIGATION_PREVIOUS.getItemStack(), event -> {
            if (QUESTIONER_SELECT_BE_QUESTIONED_PAGINATED_PANE.getPage() > 0) {
                QUESTIONER_SELECT_BE_QUESTIONED_PAGINATED_PANE.setPage(QUESTIONER_SELECT_BE_QUESTIONED_PAGINATED_PANE.getPage() - 1);

                QUESTIONER_SELECT_BE_QUESTIONED.update();
            }
        }), 0, 0);

        QUESTIONER_SELECT_BE_QUESTIONED_NAVIGATION_STATIC_PANE.addItem(new GuiItem(ItemType.NAVIGATION_COUNTDOWN.getItemStack()), 4, 0);

        QUESTIONER_SELECT_BE_QUESTIONED_NAVIGATION_STATIC_PANE.addItem(new GuiItem(ItemType.NAVIGATION_NEXT.getItemStack(), event -> {
            if (QUESTIONER_SELECT_BE_QUESTIONED_PAGINATED_PANE.getPage() < QUESTIONER_SELECT_BE_QUESTIONED_PAGINATED_PANE.getPages() - 1) {
                QUESTIONER_SELECT_BE_QUESTIONED_PAGINATED_PANE.setPage(QUESTIONER_SELECT_BE_QUESTIONED_PAGINATED_PANE.getPage() + 1);

                QUESTIONER_SELECT_BE_QUESTIONED.update();
            }
        }), 8, 0);

        QUESTIONER_SELECT_BE_QUESTIONED.addPane(QUESTIONER_SELECT_BE_QUESTIONED_OUTLINE_PANE);
        QUESTIONER_SELECT_BE_QUESTIONED.addPane(QUESTIONER_SELECT_BE_QUESTIONED_PAGINATED_PANE);
        QUESTIONER_SELECT_BE_QUESTIONED.addPane(QUESTIONER_SELECT_BE_QUESTIONED_NAVIGATION_STATIC_PANE);
    }

    public Game(String name, WhoIsTheSpy whoIsTheSpy, boolean save) throws IOException {
        this.whoIsTheSpy = whoIsTheSpy;
        this.gameState = GameState.WAITING;
        this.data = new GameDataSet();
        this.playersInGame = new LinkedList<>();
        this.good = new LinkedList<>();
        this.initVotePlayers = new LinkedList<>();
        this.votedPlayers = new LinkedList<>();
        this.abstainVotePlayer = new LinkedList<>();
        this.VOTER_AND_BE_VOTED = new HashMap<>();
        this.playerInfoAndVoteInventory = new HashMap<>();

        GUESS_WORD_GUI = new AnvilGUI.Builder()
                .onClick((slot, stateSnapshot) -> {
                    if (slot == 2) {
                        if (this.gameState == GameState.STARTED) {
                            UndercoverGuessWordEvent undercoverGuessWordEvent = new UndercoverGuessWordEvent(this, PlayerInfo.getFromPlayer(stateSnapshot.getPlayer()), stateSnapshot.getText());
                            Bukkit.getPluginManager().callEvent(undercoverGuessWordEvent);
                        }

                        return Arrays.asList(AnvilGUI.ResponseAction.close());
                    } else {
                        return null;
                    }
                })
                .title(INVENTORY_TEXT_INPUT_GUESS_WORD_TITLE.toString())
                .plugin(whoIsTheSpy.getPlugin());

        ItemStack itemStack = ItemType.GUESS_WORD_GUI_PLACEHOLDER.getItemStack();
        GUESS_WORD_GUI.itemLeft(itemStack);

        this.data.setName(name);
        this.data.setEnabled(false);
        this.data.setCountdown(COUNTDOWN);
        this.data.setMinPlayer(MIN_PLAYER);
        this.data.setMaxPlayer(MAX_PLAYER);
        this.data.setGameDuration(GAME_DURATION);
        this.data.setSelectBeQuestionedDuration(SELECT_BE_QUESTIONED_DURATION);
        this.data.setAnswerDuration(ANSWER_DURATION);
        this.data.setVoteDuration(VOTE_DURATION);
        this.data.setSigns(new SignList());

        this.gameDataManager = new GameDataManager(this);
        this.phaseHandler = new PhaseHandler(whoIsTheSpy, this);

        if (save) {
            this.saveData();
        }
    }

    public static InventorySet getVote() {
        InventorySet inventorySet = new InventorySet();

        ChestGui VOTE = new ChestGui(6, WhoIsTheSpyLocale.INVENTORY_SELECT_BE_VOTE_TITLE.toString());
        OutlinePane VOTE_OUTLINE_PANE = new OutlinePane(0, 5, 9, 1);
        PaginatedPane VOTE_PAGINATED_PANE = new PaginatedPane(0, 0, 9, 5);
        StaticPane VOTE_NAVIGATION_STATIC_PANE = new StaticPane(0, 5, 9, 1);

        addVoteItem(VOTE, VOTE_OUTLINE_PANE, VOTE_PAGINATED_PANE, VOTE_NAVIGATION_STATIC_PANE);

        inventorySet.setChestGui(VOTE);
        inventorySet.setOutlinePane(VOTE_OUTLINE_PANE);
        inventorySet.setPaginatedPane(VOTE_PAGINATED_PANE);
        inventorySet.setStaticPane(VOTE_NAVIGATION_STATIC_PANE);

        return inventorySet;
    }

    public static void addVoteItem(ChestGui VOTE, OutlinePane VOTE_OUTLINE_PANE, PaginatedPane VOTE_PAGINATED_PANE, StaticPane VOTE_NAVIGATION_STATIC_PANE) {
        VOTE_OUTLINE_PANE.addItem(new GuiItem(ItemType.NAVIGATION_BACKGROUND.getItemStack()));
        VOTE_OUTLINE_PANE.setRepeat(true);
        VOTE_OUTLINE_PANE.setPriority(Pane.Priority.LOWEST);

        VOTE_NAVIGATION_STATIC_PANE.addItem(new GuiItem(ItemType.NAVIGATION_PREVIOUS.getItemStack(), event -> {
            if (VOTE_PAGINATED_PANE.getPage() > 0) {
                VOTE_PAGINATED_PANE.setPage(VOTE_PAGINATED_PANE.getPage() - 1);

                VOTE.update();
            }
        }), 0, 0);

        VOTE_NAVIGATION_STATIC_PANE.addItem(new GuiItem(ItemType.NAVIGATION_COUNTDOWN_VOTE.getItemStack()), 4, 0);

        VOTE_NAVIGATION_STATIC_PANE.addItem(new GuiItem(ItemType.NAVIGATION_NEXT.getItemStack(), event -> {
            if (VOTE_PAGINATED_PANE.getPage() < VOTE_PAGINATED_PANE.getPages() - 1) {
                VOTE_PAGINATED_PANE.setPage(VOTE_PAGINATED_PANE.getPage() + 1);

                VOTE.update();
            }
        }), 8, 0);

        VOTE.addPane(VOTE_OUTLINE_PANE);
        VOTE.addPane(VOTE_PAGINATED_PANE);
        VOTE.addPane(VOTE_NAVIGATION_STATIC_PANE);
    }

    public static void saveAll() {
        for (Game game : WhoIsTheSpy.getInstance().getGames()) {
            try {
                game.saveData();
            } catch (IOException e) {
                e.printStackTrace();
                WhoIsTheSpy.sendMessageToConsole(game.getName() + " saving failed");
            }
        }
    }

    public static int startUpdatingSigns(int ticks) {
        return Bukkit.getScheduler().scheduleSyncRepeatingTask(WhoIsTheSpy.getInstance().getPlugin(), () -> {
            WhoIsTheSpy.getInstance().getGames().forEach(game -> {
                try {
                    game.updateSigns();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }, 0, ticks);
    }

    public static boolean isLoaded(String name) {
        if (WhoIsTheSpy.getInstance().getGames() == null) {
            return false;
        }

        for (Game game : WhoIsTheSpy.getInstance().getGames()) {
            if (game.getName().equals(name)) {
                return true;
            }
        }

        return false;
    }

    public static Game getByName(String name) {
        for (Game game : WhoIsTheSpy.getInstance().getGames()) {
            if (game.getName().equals(name)) {
                return game;
            }
        }

        return null;
    }

    public static Game loadFromFile(String name) throws IOException {
        WhoIsTheSpy whoIsTheSpy = WhoIsTheSpy.getInstance();
        Game game;

        if (Game.isLoaded(name)) {
            game = Game.getByName(name);
        } else {
            game = new Game(name, whoIsTheSpy, false);
            game.gameDataManager.loadData();
        }

        return game;
    }

    public static boolean create(String name) throws IOException {
        if (isLoaded(name)) {
            return false;
        }

        WhoIsTheSpy whoIsTheSpy = WhoIsTheSpy.getInstance();
        Game game = new Game(name, whoIsTheSpy, true);
        whoIsTheSpy.getGames().add(game);

        return true;
    }

    public String getName() {
        return data.getName();
    }

    public void setName(String name) {
        data.setName(name);
    }

    public int getMinPlayer() {
        return data.getMinPlayer();
    }

    public void setMinPlayer(int minPlayer) {
        data.setMinPlayer(minPlayer);
    }

    public int getMaxPlayer() {
        return data.getMaxPlayer();
    }

    public void setMaxPlayer(int maxPlayer) {
        data.setMaxPlayer(maxPlayer);
    }

    public int getCountdown() {
        return data.getCountdown();
    }

    public void setCountdown(int countdown) {
        data.setCountdown(countdown);
    }

    public int getGameDuration() {
        return data.getGameDuration();
    }

    public void setGameDuration(int gameDuration) {
        data.setGameDuration(gameDuration);
    }

    public int getSelectBeQuestionedDuration() {
        return data.getSelectBeQuestionedDuration();
    }

    public void setSelectBeQuestionedDuration(int selectBeQuestionedDuration) {
        data.setSelectBeQuestionedDuration(selectBeQuestionedDuration);
    }

    public int getAnswerDuration() {
        return data.getAnswerDuration();
    }

    public void setAnswerDuration(int answerCountdown) {
        data.setAnswerDuration(answerCountdown);
    }

    public int getVoteDuration() {
        return data.getVoteDuration();
    }

    public void setVoteDuration(int voteCountdown) {
        data.setVoteDuration(voteCountdown);
    }

    public SignList getSigns() {
        return data.signs;
    }

    public boolean isEnabled() {
        return data.isEnabled();
    }

    public void setEnabled(boolean enabled) {
        data.setEnabled(enabled);
    }

    public PlayerJoinGameEvent addPlayer(Player player) {
        PlayerInfo playerInfo = PlayerInfo.getFromPlayer(player);

        if (playerInfo == null) {
            whoIsTheSpy.getPlayers().add(new PlayerInfo(player));
            playerInfo = PlayerInfo.getFromPlayer(player);
        }

        if (!whoIsTheSpy.getPlayers().contains(playerInfo)) {
            whoIsTheSpy.getPlayers().add(playerInfo);
        }

        PlayerJoinGameEvent event = new PlayerJoinGameEvent(this, player, playerInfo);
        Bukkit.getPluginManager().callEvent(event);

        return event;
    }

    public PlayerLeaveGameEvent callPlayerLeaveGameEvent(PlayerInfo playerInfo) {
        PlayerLeaveGameEvent event = new PlayerLeaveGameEvent(this, playerInfo.getPlayer(), playerInfo);
        Bukkit.getPluginManager().callEvent(event);

        return event;
    }

    public PlayerLeaveGameEvent removePlayer(PlayerInfo playerInfo) {
        playersInGame.remove(playerInfo);

        return callPlayerLeaveGameEvent(playerInfo);
    }

    public void broadcast(LocaleString prefix, LocaleString message, String... placeholder) {
        for (PlayerInfo playerInfo : playersInGame) {
            message.message(prefix, playerInfo.getPlayer(), placeholder);
        }
    }

    public void addSign(Location location) throws IOException {
        if (data.signs == null) {
            data.signs = new SignList();
        }

        data.signs.add(location);
        gameDataManager.save(data);
    }

    public void saveData() throws IOException {
        gameDataManager.save(data);
    }

    public void delete() {
        Iterator<PlayerInfo> iterator = playersInGame.stream().iterator();
        while (iterator.hasNext()) {
            PlayerInfo playerInfo = iterator.next();
            callPlayerLeaveGameEvent(playerInfo);
            iterator.remove();
        }

        whoIsTheSpy.getGames().remove(this);
        gameDataManager.delete();
    }

    public void updateSigns() throws IOException {
        Iterator<Location> iterator = data.signs.getLocations().iterator();
        boolean save = false;

        while (iterator.hasNext()) {
            Location location = iterator.next();
            Block block = location.getBlock();

            if (block.getType().name().contains("SIGN")) {
                Sign sign = (Sign) block.getState();
                String[] lines = new String[4];

                if (!isEnabled()) {
                    lines = WhoIsTheSpyLocale.SIGN_DISABLED.getValues();
                } else {
                    switch (gameState) {
                        case WAITING:
                            if (getPlayersInGame().size() >= getMaxPlayer()) {
                                lines = WhoIsTheSpyLocale.SIGN_FULL.getValues();
                            } else {
                                lines = WhoIsTheSpyLocale.SIGN_WAITING.getValues();
                            }
                            break;

                        case PREPARING:
                            lines = WhoIsTheSpyLocale.SIGN_PREPARING.getValues();
                            break;

                        case STARTED:
                            lines = WhoIsTheSpyLocale.SIGN_STARTED.getValues();
                            break;
                    }
                }

                for (int i = 0; i < 4; i++) {
                    sign.setLine(i, ChatColor.translateAlternateColorCodes('&', lines[i].replace("%GAME%", getName())
                            .replace("%CURRENT_PLAYER%", String.valueOf(getPlayersInGame().size()))
                            .replace("%MAX_PLAYER%", String.valueOf(getMaxPlayer()))));
                }

                sign.update();
            } else {
                iterator.remove();
                save = true;
            }
        }

        if (save) {
            gameDataManager.save(data);
        }
    }

    public void reset() {
        this.gameState = GameState.WAITING;
        this.phaseHandler.cancelWaitingPhase();
        this.phaseHandler.cancelGamePhase();
        this.phaseHandler = new PhaseHandler(whoIsTheSpy, this);
        this.phaseHandler.startWaitingPhase();

        this.setUndercover(null);
        this.setQuestioner(null);
        this.setBeQuestioned(null);
        this.setWord(null);
        this.setUndercoverGuess(null);
        this.setUndercover(null);
        this.getGood().clear();
        this.playersInGame.clear();
        this.initVotePlayers.clear();
        this.votedPlayers.clear();
        this.VOTER_AND_BE_VOTED.clear();
        this.abstainVotePlayer.clear();
        this.playerInfoAndVoteInventory.clear();

        GUESS_WORD_GUI = new AnvilGUI.Builder()
                .onClick((slot, stateSnapshot) -> {
                    if (slot == 2) {
                        if (this.gameState == GameState.STARTED) {
                            UndercoverGuessWordEvent undercoverGuessWordEvent = new UndercoverGuessWordEvent(this, PlayerInfo.getFromPlayer(stateSnapshot.getPlayer()), stateSnapshot.getText());
                            Bukkit.getPluginManager().callEvent(undercoverGuessWordEvent);
                        }

                        return Arrays.asList(AnvilGUI.ResponseAction.close());
                    } else {
                        return null;
                    }
                })
                .title(INVENTORY_TEXT_INPUT_GUESS_WORD_TITLE.toString())
                .plugin(whoIsTheSpy.getPlugin());

        ItemStack itemStack = ItemType.GUESS_WORD_GUI_PLACEHOLDER.getItemStack();
        GUESS_WORD_GUI.itemLeft(itemStack);

        QUESTIONER_SELECT_BE_QUESTIONED = new ChestGui(6, WhoIsTheSpyLocale.INVENTORY_QUESTIONER_SELECT_BE_QUESTIONED_TITLE.toString());
        QUESTIONER_SELECT_BE_QUESTIONED_OUTLINE_PANE = new OutlinePane(0, 5, 9, 1);
        QUESTIONER_SELECT_BE_QUESTIONED_PAGINATED_PANE = new PaginatedPane(0, 0, 9, 5);
        QUESTIONER_SELECT_BE_QUESTIONED_NAVIGATION_STATIC_PANE = new StaticPane(0, 5, 9, 1);

        QUESTIONER_SELECT_BE_QUESTIONED_OUTLINE_PANE.addItem(new GuiItem(ItemType.NAVIGATION_BACKGROUND.getItemStack()));
        QUESTIONER_SELECT_BE_QUESTIONED_OUTLINE_PANE.setRepeat(true);
        QUESTIONER_SELECT_BE_QUESTIONED_OUTLINE_PANE.setPriority(Pane.Priority.LOWEST);

        QUESTIONER_SELECT_BE_QUESTIONED_NAVIGATION_STATIC_PANE.addItem(new GuiItem(ItemType.NAVIGATION_PREVIOUS.getItemStack(), event -> {
            if (QUESTIONER_SELECT_BE_QUESTIONED_PAGINATED_PANE.getPage() > 0) {
                QUESTIONER_SELECT_BE_QUESTIONED_PAGINATED_PANE.setPage(QUESTIONER_SELECT_BE_QUESTIONED_PAGINATED_PANE.getPage() - 1);

                QUESTIONER_SELECT_BE_QUESTIONED.update();
            }
        }), 0, 0);

        QUESTIONER_SELECT_BE_QUESTIONED_NAVIGATION_STATIC_PANE.addItem(new GuiItem(ItemType.NAVIGATION_COUNTDOWN.getItemStack()), 4, 0);

        QUESTIONER_SELECT_BE_QUESTIONED_NAVIGATION_STATIC_PANE.addItem(new GuiItem(ItemType.NAVIGATION_NEXT.getItemStack(), event -> {
            if (QUESTIONER_SELECT_BE_QUESTIONED_PAGINATED_PANE.getPage() < QUESTIONER_SELECT_BE_QUESTIONED_PAGINATED_PANE.getPages() - 1) {
                QUESTIONER_SELECT_BE_QUESTIONED_PAGINATED_PANE.setPage(QUESTIONER_SELECT_BE_QUESTIONED_PAGINATED_PANE.getPage() + 1);

                QUESTIONER_SELECT_BE_QUESTIONED.update();
            }
        }), 8, 0);

        QUESTIONER_SELECT_BE_QUESTIONED.addPane(QUESTIONER_SELECT_BE_QUESTIONED_PAGINATED_PANE);
        QUESTIONER_SELECT_BE_QUESTIONED.addPane(QUESTIONER_SELECT_BE_QUESTIONED_OUTLINE_PANE);
        QUESTIONER_SELECT_BE_QUESTIONED.addPane(QUESTIONER_SELECT_BE_QUESTIONED_NAVIGATION_STATIC_PANE);
    }

    @Override
    public String toString() {
        return getName();
    }
}
