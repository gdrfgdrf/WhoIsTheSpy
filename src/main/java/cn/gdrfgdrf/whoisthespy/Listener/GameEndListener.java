package cn.gdrfgdrf.whoisthespy.Listener;

import cn.gdrfgdrf.whoisthespy.Events.GameEndEvent;
import cn.gdrfgdrf.whoisthespy.Game.Game;
import cn.gdrfgdrf.whoisthespy.Player.PlayerInfo;
import cn.gdrfgdrf.whoisthespy.WhoIsTheSpy;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cn.gdrfgdrf.whoisthespy.Locale.WhoIsTheSpyLocale.PREFIX;

public class GameEndListener implements Listener {

    private final WhoIsTheSpy whoIsTheSpy;

    public GameEndListener(WhoIsTheSpy whoIsTheSpy) {
        this.whoIsTheSpy = whoIsTheSpy;

        Bukkit.getPluginManager().registerEvents(this, whoIsTheSpy.getPlugin());
    }

    @EventHandler
    public void onGameEnd(GameEndEvent event) {
        Game game = event.getGame();

        for (PlayerInfo playerInfo : game.getPlayersInGame()) {
            playerInfo.getPlayer().closeInventory();
        }

        if (event.isCalculateVote()) {
            PlayerInfo mostVote = calculate(game);

            if (game.getUndercover() == mostVote) {
                game.getPhaseHandler().getGamePhase().finishGameForGoodWin();
            } else {
                game.getPhaseHandler().getGamePhase().finishGameForGoodLose();
            }

            Iterator<PlayerInfo> iterator = game.getPlayersInGame().iterator();
            while (iterator.hasNext()) {
                PlayerInfo playerInfo = iterator.next();
                game.callPlayerLeaveGameEvent(playerInfo);
                iterator.remove();

                event.getReason().message(PREFIX, playerInfo.getPlayer(), event.getPlaceholder());
            }
        }

        game.reset();
    }

    public PlayerInfo calculate(Game game) {
        HashMap<PlayerInfo, PlayerInfo> voteMap = game.getVOTER_AND_BE_VOTED();
        HashMap<String, PlayerInfo> stringAndPlayerInfo = new HashMap<>();
        HashMap<String, Integer> voteCountMap = new HashMap<>();
        List<String> beVotedList = new LinkedList<>();

        for (Map.Entry<PlayerInfo, PlayerInfo> map : voteMap.entrySet()) {
            beVotedList.add(map.getValue().getPlayer().getUniqueId().toString());
            stringAndPlayerInfo.put(map.getValue().getPlayer().getUniqueId().toString(), map.getValue());
        }

        Pattern pattern;
        Matcher matcher;

        String tmp = "";
        String regex;
        String tot_str = beVotedList.toString();
        String max_str = null;

        int max_cnt = 0;

        for (String str : beVotedList) {
            if (tmp.equals(str)) {
                continue;
            }

            tmp = str;
            regex = str;
            pattern = Pattern.compile(regex);
            matcher = pattern.matcher(tot_str);

            int cnt = 0;

            while (matcher.find()) {
                cnt++;
            }

            voteCountMap.put(str, cnt);

            if (cnt > max_cnt) {
                max_cnt = cnt;
                max_str = str;
            }
        }

        for (Map.Entry<String, Integer> map : voteCountMap.entrySet()) {
            if (!map.getKey().equals(max_str) && map.getValue() == max_cnt) {
                return null;
            }
        }

        return stringAndPlayerInfo.get(max_str);
    }
}
