package cn.gdrfgdrf.whoisthespy.Utils.de.pauhull.utils.particle;

import cn.gdrfgdrf.whoisthespy.Utils.de.pauhull.utils.misc.MinecraftVersion;
import cn.gdrfgdrf.whoisthespy.Utils.de.pauhull.utils.misc.Reflection;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Utility to play particles with NMS
 *
 * @author pauhull
 * @version 1.1
 */
class NmsParticlePlayer implements ParticlePlayer {

    //region NMS
    private static Class<?> packetPlayOutWorldParticlesClass;
    private static Class<?> enumParticleClass;
    private static Class<?> particleParamClass;

    private static Constructor<?> packetConstructor;

    private static Method valueOf;

    static { // Get classes

        packetPlayOutWorldParticlesClass = Reflection.getNMSClass("PacketPlayOutWorldParticles");

        if (MinecraftVersion.CURRENT_VERSION.isLower(MinecraftVersion.v1_13)) {
            enumParticleClass = Reflection.getNMSClass("EnumParticle");

            try {
                valueOf = enumParticleClass.getMethod("valueOf", String.class);
                packetConstructor = packetPlayOutWorldParticlesClass.getConstructor(enumParticleClass, boolean.class, float.class, float.class, float.class, float.class, float.class, float.class, float.class, int.class, int[].class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        } else if (MinecraftVersion.CURRENT_VERSION.isLower(MinecraftVersion.v1_15)) {
            particleParamClass = Reflection.getNMSClass("ParticleParam");

            try {
                packetConstructor = packetPlayOutWorldParticlesClass.getConstructor(particleParamClass, boolean.class, float.class, float.class, float.class, float.class, float.class, float.class, float.class, int.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        } else {
            particleParamClass = Reflection.getNMSClass("ParticleParam");

            try {
                packetConstructor = packetPlayOutWorldParticlesClass.getConstructor(particleParamClass, boolean.class, double.class, double.class, double.class, float.class, float.class, float.class, float.class, int.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }
    //endregion

    private Object particle;

    /**
     * Creates new instance of ParticlePlayer
     *
     * @param particle Type of particle to be displayed
     */
    NmsParticlePlayer(Particles particle) {
        try {
            if (MinecraftVersion.CURRENT_VERSION.isLower(MinecraftVersion.v1_13)) {
                this.particle = valueOf.invoke(null, particle.name());
            } else {
                this.particle = particle.get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void play(Location location, int amount, Player... players) {
        Object packet = createPacket(particle, (float) location.getX(), (float) location.getY(), (float) location.getZ(), 0f, 0f, 0f, 0f, amount);

        for (Player player : players) {
            Reflection.sendPacket(player, packet);
        }
    }

    @Override
    public void play(Location location, int amount) {
        List<Player> players = location.getWorld().getPlayers();
        play(location, amount, players.toArray(new Player[players.size()]));
    }

    /**
     * Creates packet from arguments
     *
     * @param particle Particle to play
     * @param x        X location
     * @param y        Y location
     * @param z        Z location
     * @param xOffset  X offset
     * @param yOffset  Y offset
     * @param zOffset  Z offset
     * @param data     Data
     * @param amount   Amount of particles
     * @return The packet
     */
    private Object createPacket(Object particle, float x, float y, float z, float xOffset, float yOffset, float zOffset, float data, int amount) {
        try {
            if (MinecraftVersion.CURRENT_VERSION.isLower(MinecraftVersion.v1_13)) {
                return packetConstructor.newInstance(particle, true, x, y, z, xOffset, yOffset, zOffset, data, amount, null);
            } else if (MinecraftVersion.CURRENT_VERSION.isLower(MinecraftVersion.v1_15)) {
                return packetConstructor.newInstance(particle, true, x, y, z, xOffset, yOffset, zOffset, data, amount);
            } else {
                return packetConstructor.newInstance(particle, true, (double) x, (double) y, (double) z, xOffset, yOffset, zOffset, data, amount);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
