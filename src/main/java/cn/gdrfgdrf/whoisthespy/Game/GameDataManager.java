package cn.gdrfgdrf.whoisthespy.Game;

import cn.gdrfgdrf.whoisthespy.WhoIsTheSpy;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class GameDataManager {

    private final Game game;
    @Getter
    private FileConfiguration fileConfiguration;
    @Setter
    @Getter
    private File file;
    @Setter
    private boolean isRename = false;
    @Setter
    private File beforeRenameFile;

    public GameDataManager(Game game) {
        this.game = game;

        try {
            setup();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setup() throws IOException {
        this.file = new File(WhoIsTheSpy.PLUGIN_FOLDER + "Games/" + game.getName() + ".yml");

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }

        this.fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public void saveFileConfiguration() throws IOException {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }

        fileConfiguration.save(file);

        if (isRename) {
            isRename = false;

            beforeRenameFile.delete();

            beforeRenameFile = null;
        }
    }

    public void save(GameDataSet data) throws IOException {
        data.setAllValues(fileConfiguration);

        saveFileConfiguration();
    }

    public boolean delete() {
        return file.delete();
    }

    public void loadData() {
        game.getData().loadAllValues(fileConfiguration, game);
    }

    public void reload() {
        this.fileConfiguration = YamlConfiguration.loadConfiguration(file);
        this.loadData();
    }

}
