package cn.gdrfgdrf.whoisthespy;

import cn.gdrfgdrf.whoisthespy.Data.Config;
import cn.gdrfgdrf.whoisthespy.Utils.de.pauhull.utils.scheduler.Scheduler;
import lombok.Getter;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;


public class Plugin extends JavaPlugin {
    @Getter
    private Metrics metrics;

    private WhoIsTheSpy whoIsTheSpy;

    private void init() {
        metrics = new Metrics(this, 18580);
        File configFile = new File(WhoIsTheSpy.PLUGIN_FOLDER + "config.yml");

        if (WhoIsTheSpy.DEBUG) {
            configFile.delete();
        }

        saveDefaultConfig();

        Config config = new Config(configFile, true);
        whoIsTheSpy = new WhoIsTheSpy(this, config);
    }

    @Override
    public void onLoad() {
        this.init();
        whoIsTheSpy.load();
    }

    @Override
    public void onEnable() {
        whoIsTheSpy.enable();
    }

    @Override
    public void onDisable() {
        whoIsTheSpy.disable();
    }
}
