package cn.gdrfgdrf.whoisthespy.Game;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SignList implements Iterable<Location> {

    @Getter
    private List<Location> locations;

    public SignList(List<Location> signs) {
        this.locations = signs;
    }

    public SignList() {
        this(new ArrayList<>());
    }

    public static SignList fromStringList(List<String> stringList) {
        List<Location> signs = new ArrayList<>();
        for (String string : stringList) {
            signs.add(stringToLocation(string));
        }
        return new SignList(signs);
    }

    private static Location stringToLocation(String string) {
        String[] split = string.split(";");
        World world = Bukkit.getWorld(split[0]);
        double x = Double.valueOf(split[1]);
        double y = Double.valueOf(split[2]);
        double z = Double.valueOf(split[3]);
        float yaw = Float.valueOf(split[4]);
        float pitch = Float.valueOf(split[5]);
        return new Location(world, x, y, z, yaw, pitch);
    }

    private static String locationToString(Location location) {
        return location.getWorld().getName() + ";"
                + location.getX() + ";"
                + location.getY() + ";"
                + location.getZ() + ";"
                + location.getYaw() + ";"
                + location.getPitch();
    }

    public List<String> toStringList() {
        List<String> strings = new ArrayList<>();
        for (Location location : locations) {
            strings.add(locationToString(location));
        }
        return strings;
    }

    public void add(Location location) {
        locations.add(location);
    }

    public void remove(Location location) {
        locations.remove(location);
    }

    @Override
    public Iterator<Location> iterator() {
        return locations.iterator();
    }

}
