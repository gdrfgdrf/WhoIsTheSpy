package cn.gdrfgdrf.whoisthespy.Data;

import cn.gdrfgdrf.whoisthespy.Utils.de.pauhull.utils.file.FileUtils;
import cn.gdrfgdrf.whoisthespy.WhoIsTheSpy;
import com.google.common.io.Files;
import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Config {
    @Getter
    private final boolean utf8;
    @Getter
    private File file;
    @Getter
    private FileConfiguration configuration;

    public Config(File file, boolean utf8) {
        this.utf8 = utf8;
        this.file = file;

        load(file);
    }

    public static void copy(String resource) {
        FileUtils.copy(resource, WhoIsTheSpy.PLUGIN_FOLDER + resource);
    }

    public void reload() {
        load(file);
    }

    private void load(File file) {

        if (!file.getName().equals("config.yml")) {
            copy(file.getName());
        }

        if (utf8) {
            try {
                String s = Files.toString(file, StandardCharsets.UTF_8);
                configuration = new YamlConfiguration();
                configuration.loadFromString(s);
            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }
        } else {
            configuration = YamlConfiguration.loadConfiguration(file);
        }
    }
}
