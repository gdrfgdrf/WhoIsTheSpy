package cn.gdrfgdrf.whoisthespy.Player;

import cn.gdrfgdrf.whoisthespy.Game.Character;
import cn.gdrfgdrf.whoisthespy.Game.Game;
import cn.gdrfgdrf.whoisthespy.WhoIsTheSpy;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

public class PlayerInfo {
    @Setter
    @Getter
    private Player player;

    @Setter
    @Getter
    private PlayerState playerState;

    @Setter
    @Getter
    private PlayerData playerData;

    @Setter
    @Getter
    private Character character;

    @Setter
    @Getter
    private Game currentGame;

    public PlayerInfo(Player player) {
        this.player = player;
        this.playerState = PlayerState.DEFAULT;
    }

    public static boolean isInGame(PlayerInfo playerInfo) {
        return playerInfo != null && playerInfo.isInGame();
    }

    public static PlayerInfo getFromPlayer(Player player) {
        for (PlayerInfo playerInfo : WhoIsTheSpy.getInstance().getPlayers()) {
            if (playerInfo.getPlayer() == player) {
                return playerInfo;
            }
        }

        return null;
    }

    public static boolean isInGame(Player player) {
        return isInGame(getFromPlayer(player));
    }

    public boolean isInGame() {
        return currentGame != null && playerState == PlayerState.INGAME;
    }

    @Override
    public String toString() {
        return "PlayerInfo{" +
                "player=" + player.getName() +
                ", character=" + character +
                ", currentGame=" + currentGame +
                '}';
    }
}
