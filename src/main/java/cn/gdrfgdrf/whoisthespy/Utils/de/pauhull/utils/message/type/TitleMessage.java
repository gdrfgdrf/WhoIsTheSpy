package cn.gdrfgdrf.whoisthespy.Utils.de.pauhull.utils.message.type;

import cn.gdrfgdrf.whoisthespy.Utils.de.pauhull.utils.message.NMSClasses;
import lombok.Getter;
import org.bukkit.entity.Player;

/**
 * Sends title message (upper text)
 *
 * @author pauhull
 * @version 1.0
 */
public class TitleMessage implements MessageType {

    private static final boolean SEND_TITLE;

    static {
        boolean sendTitle = false;
        try {
            Player.class.getMethod("sendTitle", String.class, String.class, int.class, int.class, int.class);
            sendTitle = true;
        } catch (NoSuchMethodException ignored) {
        }
        SEND_TITLE = sendTitle;
    }

    @Getter
    private String title;
    @Getter
    private String subTitle;
    @Getter
    private int fadeIn, stay, fadeOut;

    /**
     * Creates new TitleMessage from parameters
     *
     * @param title    The title (upper text)
     * @param subTitle The subtitle (lower text)
     * @param fadeIn   Fade in time in ticks
     * @param stay     Stay time in ticks
     * @param fadeOut  Fade out time in ticks
     */
    public TitleMessage(String title, String subTitle, int fadeIn, int stay, int fadeOut) {
        this.title = title;
        this.subTitle = subTitle;
        this.fadeIn = fadeIn;
        this.stay = stay;
        this.fadeOut = fadeOut;
    }

    /**
     * Sends title to player
     *
     * @param player The player
     */
    @Override
    public void send(Player player) {
        if (SEND_TITLE) {
            player.sendTitle(title, subTitle, fadeIn, stay, fadeOut);
        } else {
            NMSClasses.sendTitlesNMS(player, title, subTitle, fadeIn, stay, fadeOut);
        }
    }
}
