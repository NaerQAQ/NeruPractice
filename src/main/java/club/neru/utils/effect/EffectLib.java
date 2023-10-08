package club.neru.utils.effect;

import club.neru.thread.Scheduler;
import lombok.experimental.UtilityClass;
import net.minecraft.server.v1_8_R3.EntityLightning;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityWeather;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

/**
 * 效果工具类。
 *
 * @author L1ncey
 * @version 1.0
 * @see Scheduler
 * @since 2023/10/8
 */
@UtilityClass
public class EffectLib {

    /**
     * 在指定的位置展示一个类似流行环绕的粒子。
     *
     * <p>
     * 请注意：该方法调用会产生相当大的资源占用，如有更好的方法，请代替我重写
     * </p>
     *
     * @param location 需要展示该效果的位置
     * @param delay 两次粒子展示的间隔
     */
    public void playCrystalEffect(Location location, int delay){
        World world = location.getWorld();

        Scheduler.timerAsync(new BukkitRunnable() {
            @Override
            public void run() {
                double t = 0;

                // 旋转角度
                t = t + Math.PI / 16;

               // 计算旋转后的坐标
                double x = 2 * Math.cos(t);
                double y = 2 * Math.exp(-0.1 * t) * Math.sin(t);
                double z = 2 * Math.sin(t);

                // 在新坐标创建特效
                location.add(x, y, z);
                world.playEffect(location, Effect.FIREWORKS_SPARK, 1, 1);

                // 如果现有特效旋转一圈则取消任务
                if (t > Math.PI * 20) {
                    this.cancel();
                }
            }
        }, delay, 1);
    }

    /**
     * 在指定的位置展示粒子四散的效果
     *
     * <p>
     * 请注意：该方法调用会产生相当大的资源占用，如有更好的方法，请代替我重写
     * </p>
     *
     * <p>
     * 请注意：该方法尚未测试运行，请谨慎使用
     * </p>
     *
     * @param location 需要展示该效果的位置
     * @param effect 要展示的粒子效果
     */
    public void createAuroraEffect(Location location, Effect effect) {
        World world = location.getWorld();

        Scheduler.timerAsync(new BukkitRunnable() {
            int a = 100;

            @Override
            public void run() {
                for (int i = 0; i < 360; i += 5) {
                    a++;

                    double angle = i * Math.PI / 180;
                    Vector vector = new Vector(Math.cos(angle) * 2, 0, Math.sin(angle) * 2);

                    Location effectLocation = location.clone().add(vector);
                    world.playEffect(effectLocation, effect, 1, 1);

                    if (a > 100) {
                        this.cancel();
                    }
                }
            }
        }, 10L, 0);
    }

    /**
     * 爆炸，方块四散的效果。
     *
     * @param location  需要展示该效果的位置
     * @param material  四散方块的类型
     * @param numBlocks 四散方块的数量
     */
    public void playCrystalExplodeEffect(Location location, Material material, int numBlocks) {
        World world = location.getWorld();

        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();

        // 爆炸
        world.createExplosion(x, y, z, .5f, false, false);

        // 方块四散
        for (int i = 0; i < numBlocks; i++) {
            // 创建一个 FallingBlock 实例
            @SuppressWarnings("deprecation")
            FallingBlock fallingBlock = world.spawnFallingBlock(location, material, (byte) 1);

            // 设置方块的速度，使其呈现抛物线散开的效果
            double vectorX = Math.random() * 2 - 1;
            double vectorY = Math.random() * 2;
            double vectorZ = Math.random() * 2 - 1;

            Vector vector = new Vector(vectorX, vectorY, vectorZ);

            fallingBlock.setVelocity(vector);
        }
    }

    /**
     * 参考特效：当玩家被击杀时展示。
     *
     * <p>
     * 效果：雷击在玩家死亡的地点，并伴随声音。
     * </p>
     *
     * @param location 需要展示该效果的位置
     */
    public void playLightingEffect(Player player, Location location) {
        WorldServer worldServer = ((CraftWorld) location.getWorld()).getHandle();

        EntityLightning lightning = new EntityLightning(worldServer, location.getX(), location.getY(), location.getZ(), true, true);
        lightning.setPosition(location.getX(), location.getY(), location.getZ());

        PacketPlayOutSpawnEntityWeather packet = new PacketPlayOutSpawnEntityWeather(lightning);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);

        location.getWorld().playSound(location, Sound.AMBIENCE_THUNDER, 1, 1);
    }
}
