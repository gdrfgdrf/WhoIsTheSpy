package cn.gdrfgdrf.whoisthespy;

import cn.gdrfgdrf.whoisthespy.Data.Config;
import cn.gdrfgdrf.whoisthespy.Utils.de.pauhull.utils.scheduler.Scheduler;
import lombok.Getter;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;


public class Plugin extends JavaPlugin {
    @Getter
    private Metrics metrics;

    private WhoIsTheSpy whoIsTheSpy;
    private Config config;
    private ExecutorService executorService;
    private ScheduledExecutorService scheduledExecutorService;

    private void init() {

        metrics = new Metrics(this, 18580);

        if (WhoIsTheSpy.DEBUG) {
            new File(WhoIsTheSpy.PLUGIN_FOLDER + "config.yml").delete();
        }

        saveDefaultConfig();

        config = new Config(new File(WhoIsTheSpy.PLUGIN_FOLDER + "config.yml"), true);
        executorService = Scheduler.createExecutorService();
        scheduledExecutorService = Scheduler.createScheduledExecutorService();
        whoIsTheSpy = new WhoIsTheSpy(this, config, executorService, scheduledExecutorService);
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
        executorService.shutdown();
        scheduledExecutorService.shutdown();
    }
}
