package cn.gdrfgdrf.whoisthespy.Game;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.file.FileConfiguration;

import java.lang.reflect.Field;
import java.util.List;

public class GameDataSet {
    @Setter
    @Getter
    SignList signs;

    @Setter
    @Getter
    private String name;

    @Setter
    @Getter
    private int minPlayer, maxPlayer, countdown, gameDuration, selectBeQuestionedDuration, answerDuration, voteDuration;

    @Setter
    @Getter
    private boolean enabled;

    public void loadAllValues(FileConfiguration fileConfiguration, Game game) {
        for (Field field : GameDataSet.class.getDeclaredFields()) {
            String path = getCapitalizedName(field.getName());
            Object value = load(fileConfiguration, path, field.getType(), game);

            try {
                field.set(this, value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public void setAllValues(FileConfiguration fileConfiguration) {
        try {
            for (Field field : GameDataSet.class.getDeclaredFields()) {
                String path = getCapitalizedName(field.getName());
                Object value = field.get(this);

                set(fileConfiguration, path, value);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public Object load(
            FileConfiguration fileConfiguration,
            String path,
            Class<?> type,
            Game game
    ) {
        if (!fileConfiguration.isSet(path)) {
            for (Field field : GameDataSet.class.getDeclaredFields()) {
                if (field.getName().equalsIgnoreCase(path)) {
                    try {
                        return field.get(game.getData());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            }
        }

        if (type == SignList.class) {
            List<String> locations = fileConfiguration.getStringList(path);
            return SignList.fromStringList(locations);
        }

        return fileConfiguration.get(path);
    }

    public void set(
            FileConfiguration configuration,
            String path,
            Object object
    ) {
        if (object instanceof SignList) {
            SignList signList = (SignList) object;
            configuration.set(path, signList.toStringList());
        } else {
            configuration.set(path, object);
        }
    }

    private String getCapitalizedName(String name) {
        return java.lang.Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }
}
