package cn.gdrfgdrf.whoisthespy.Listener;

import cn.gdrfgdrf.whoisthespy.Events.GameStartEvent;
import cn.gdrfgdrf.whoisthespy.Game.Game;
import cn.gdrfgdrf.whoisthespy.Game.GameState;
import cn.gdrfgdrf.whoisthespy.Locale.WhoIsTheSpyLocale;
import cn.gdrfgdrf.whoisthespy.Player.PlayerHead;
import cn.gdrfgdrf.whoisthespy.Player.PlayerInfo;
import cn.gdrfgdrf.whoisthespy.Utils.InventorySet;
import cn.gdrfgdrf.whoisthespy.Utils.ItemType;
import cn.gdrfgdrf.whoisthespy.Utils.Util;
import cn.gdrfgdrf.whoisthespy.WhoIsTheSpy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static cn.gdrfgdrf.whoisthespy.Locale.WhoIsTheSpyLocale.COUNTDOWN_ABORTED;
import static cn.gdrfgdrf.whoisthespy.Locale.WhoIsTheSpyLocale.PREFIX;

public class GameStartListener implements Listener {
    private final WhoIsTheSpy whoIsTheSpy;

    public GameStartListener(WhoIsTheSpy whoIsTheSpy) {
        this.whoIsTheSpy = whoIsTheSpy;

        Bukkit.getPluginManager().registerEvents(this, whoIsTheSpy.getPlugin());
    }

    @EventHandler
    public void onGameStart(GameStartEvent event) {
        Game game = event.getGame();

        if (whoIsTheSpy.getWords() != null && !whoIsTheSpy.getWords().isEmpty()) {
            if (game.getPlayersInGame().size() < game.getMinPlayer()) {
                setGameState(game);
            } else {
                for (PlayerInfo playerInfo : game.getPlayersInGame()) {
                    Player player = playerInfo.getPlayer();
                    player.setLevel(0);
                    player.setExp(0);

                    player.getInventory().clear();
                }
                game.setGameState(GameState.PREPARING);

                PlayerInfo undercover = getUndercover(game);

                for (PlayerInfo playerInfo : game.getPlayersInGame()) {
                    if (playerInfo != undercover) {
                        game.getPlayerInfoAndVoteInventory().put(playerInfo, Game.getVote());
                    }
                }

                List<PlayerHead> playerHeads = new LinkedList<>();
                List<ItemStack> itemStackList = new LinkedList<>();

                for (int i = 0; i < game.getPlayersInGame().size(); i++) {
                    PlayerInfo playerInfo = game.getPlayersInGame().get(i);
                    PlayerHead playerHead = new PlayerHead(playerInfo.getPlayer());

                    playerHeads.add(playerHead);
                    itemStackList.add(playerHead.getItemStack());
                }

                for (Map.Entry<PlayerInfo, InventorySet> map :
                        game.getPlayerInfoAndVoteInventory().entrySet()) {
                    map.getValue().getPaginatedPane().populateWithItemStacks(itemStackList);
                }

                game.getQUESTIONER_SELECT_BE_QUESTIONED_PAGINATED_PANE()
                        .populateWithItemStacks(Util.PlayerHeadListToItemStackList(playerHeads));

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < game.getPlayersInGame().size(); i++) {
                            if (game.getPlayersInGame().size() < game.getMinPlayer()) {
                                setGameState(game, false);
                                resetPlayerInfoAndVoteInventoryMap(game);

                                break;
                            }

                            PlayerInfo playerInfo = game.getPlayersInGame().get(i);
                            ItemStack itemStack = Util.getHead(playerInfo.getPlayer());

                            PlayerHead playerHead = playerHeads.get(i);
                            playerHead.setItemStack(itemStack);

                            List<ItemStack> list = Util.PlayerHeadListToItemStackList(playerHeads);

                            for (Map.Entry<PlayerInfo, InventorySet> map :
                                    game.getPlayerInfoAndVoteInventory().entrySet()) {
                                map.getValue().getPaginatedPane().populateWithItemStacks(list);
                            }

                            game.getQUESTIONER_SELECT_BE_QUESTIONED_PAGINATED_PANE()
                                    .populateWithItemStacks(Util.PlayerHeadListToItemStackList(playerHeads));
                        }
                    }
                }.runTaskAsynchronously(whoIsTheSpy.getPlugin());

                game.broadcast(WhoIsTheSpyLocale.PREFIX, WhoIsTheSpyLocale.GAME_STARTED);

                if (game.getPlayersInGame().size() < game.getMinPlayer()) {
                    setGameState(game);
                    resetPlayerInfoAndVoteInventoryMap(game);
                    return;
                }

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (game.getPlayersInGame().size() < game.getMinPlayer()) {
                            setGameState(game);
                            resetPlayerInfoAndVoteInventoryMap(game);
                            return;
                        }

                        game.broadcast(WhoIsTheSpyLocale.PREFIX, WhoIsTheSpyLocale.GET_RANDOM_CHARACTER);

                        game.setUndercover(undercover);
                        String word = getWord();
                        game.setWord(word);

                        for (PlayerInfo playerInfo : game.getPlayersInGame()) {
                            if (playerInfo != undercover) {
                                game.getGood().add(playerInfo);
                            }
                        }

                        if (game.getPlayersInGame().size() < game.getMinPlayer()) {
                            setGameState(game);
                            resetPlayerInfoAndVoteInventoryMap(game);
                            game.getGood().clear();
                            game.setUndercover(null);
                            game.setWord(null);

                            return;
                        }

                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (game.getPlayersInGame().size() < game.getMinPlayer()) {
                                    setGameState(game);
                                    resetPlayerInfoAndVoteInventoryMap(game);
                                    game.getGood().clear();
                                    game.setUndercover(null);
                                    game.setWord(null);

                                    return;
                                }

                                Util.sendTitle(
                                        undercover.getPlayer(),
                                        WhoIsTheSpyLocale.CHARACTER_TITLE.toString(),
                                        WhoIsTheSpyLocale.UNDERCOVER.toString()
                                );
                                Util.sendTitleForPlayerInfoList(
                                        game.getGood(),
                                        WhoIsTheSpyLocale.CHARACTER_TITLE.toString(),
                                        WhoIsTheSpyLocale.GOOD.toString()
                                );

                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        if (game.getPlayersInGame().size() < game.getMinPlayer()) {
                                            setGameState(game);
                                            resetPlayerInfoAndVoteInventoryMap(game);
                                            game.getGood().clear();
                                            game.setUndercover(null);
                                            game.setWord(null);

                                            return;
                                        }

                                        Util.sendTitleForPlayerInfoList(
                                                game.getGood(),
                                                WhoIsTheSpyLocale.WORD_TITLE.toString(),
                                                WhoIsTheSpyLocale.WORD.toString() + word
                                        );

                                        new BukkitRunnable() {
                                            @Override
                                            public void run() {
                                                PlayerInfo questioner = getQuestioner(game);
                                                game.setQuestioner(questioner);

                                                if (game.getPlayersInGame().size() < game.getMinPlayer()) {
                                                    setGameState(game);
                                                    resetPlayerInfoAndVoteInventoryMap(game);
                                                    game.getGood().clear();
                                                    game.setUndercover(null);
                                                    game.setWord(null);
                                                    game.setQuestioner(null);

                                                    return;
                                                }

                                                Util.sendTitleForPlayerInfoList(
                                                        game.getPlayersInGame(),
                                                        WhoIsTheSpyLocale.QUESTIONER_TITLE.toString(),
                                                        questioner.getPlayer().getPlayerListName()
                                                );

                                                for (PlayerInfo playerInfo : game.getGood()) {
                                                    if (whoIsTheSpy.getConfig().getConfiguration().isInt("ItemSlot.INIT_VOTE")) {
                                                        playerInfo.getPlayer().getInventory().setItem(
                                                                whoIsTheSpy.getConfig().getConfiguration().getInt("ItemSlot.INIT_VOTE"), ItemType.INIT_VOTE.getItemStack()
                                                        );
                                                    } else {
                                                        playerInfo.getPlayer().getInventory().setItem(8, ItemType.INIT_VOTE.getItemStack());
                                                    }
                                                }

                                                if (whoIsTheSpy.getConfig().getConfiguration().isInt("ItemSlot.INIT_GUESS_WORD")) {
                                                    game.getUndercover().getPlayer().getInventory().setItem(
                                                            whoIsTheSpy.getConfig().getConfiguration().getInt("ItemSlot.INIT_GUESS_WORD"), ItemType.INIT_GUESS_WORD.getItemStack()
                                                    );
                                                } else {
                                                    game.getUndercover().getPlayer().getInventory().setItem(8, ItemType.INIT_GUESS_WORD.getItemStack());
                                                }

                                                new BukkitRunnable() {
                                                    @Override
                                                    public void run() {
                                                        game.getQUESTIONER_SELECT_BE_QUESTIONED().show(questioner.getPlayer());
                                                    }
                                                }.runTask(whoIsTheSpy.getPlugin());

                                                game.getPhaseHandler().startGamePhase();
                                                game.setGameState(GameState.STARTED);
                                            }
                                        }.runTaskLaterAsynchronously(whoIsTheSpy.getPlugin(), 40);
                                    }
                                }.runTaskLaterAsynchronously(whoIsTheSpy.getPlugin(), 40);
                            }
                        }.runTaskLaterAsynchronously(whoIsTheSpy.getPlugin(), 20);
                    }
                }.runTaskLaterAsynchronously(whoIsTheSpy.getPlugin(), 20);
            }
        } else {
            game.getPhaseHandler().getGamePhase().finishGameForNotFoundWord();
        }
    }

    private void resetPlayerInfoAndVoteInventoryMap(Game game) {
        game.getPlayerInfoAndVoteInventory().clear();

        for (PlayerInfo playerInfo : game.getPlayersInGame()) {
            if (playerInfo != game.getUndercover()) {
                game.getPlayerInfoAndVoteInventory().put(playerInfo, Game.getVote());
            }
        }
    }

    private void setGameState(Game game) {
        setGameState(game, true);
    }

    private void setGameState(Game game, boolean showMessage) {
        game.setGameState(GameState.WAITING);

        if (whoIsTheSpy.getConfig().getConfiguration().isInt("ItemSlot.LEAVE_GAME")) {
            Util.initPlayerStatus(
                    game.getPlayersInGame(), whoIsTheSpy.getConfig().getConfiguration().getInt("ItemSlot.LEAVE_GAME")
            );
        } else {
            Util.initPlayerStatus(game.getPlayersInGame(), 8);
        }

        game.getPhaseHandler().startWaitingPhase();

        if (showMessage) {
            game.broadcast(PREFIX, COUNTDOWN_ABORTED);
        }

        for (PlayerInfo playerInfo : game.getPlayersInGame()) {
            Player player = playerInfo.getPlayer();
            player.setExp(0);
            player.setLevel(0);
        }
    }

    private PlayerInfo getQuestioner(Game game) {
        Random random = new Random();
        int i = random.nextInt(game.getPlayersInGame().size());

        return game.getPlayersInGame().get(i);
    }

    private PlayerInfo getUndercover(Game game) {
        Random random = new Random();
        int i = random.nextInt(game.getPlayersInGame().size());

        return game.getPlayersInGame().get(i);
    }

    private String getWord() {
        Random random = new Random();
        int i = random.nextInt(whoIsTheSpy.getWords().size());

        return whoIsTheSpy.getWords().get(i);
    }

}
