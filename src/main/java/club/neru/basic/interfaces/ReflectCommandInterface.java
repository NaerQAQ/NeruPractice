package club.neru.basic.interfaces;

import club.neru.io.file.interfaces.JsonPersistableInterface;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 反射指令接口。
 *
 * <p>
 * 该接口的实现意为通过 {@link #execute(Player, Object, String, Object)} 实现指令式调用  方法。
 * </p>
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/10
 */
public interface ReflectCommandInterface {
    /**
     * 使用反射在对象上执行方法。
     *
     * <p>
     * 若传入的对象实现了 {@link JsonPersistableInterface} 接口，则会完成后调用 {@link JsonPersistableInterface#write()} 方法。
     * </p>
     *
     * @param player     执行的玩家对象
     * @param object     实现 {@link ReflectCommandInterface} 的对象
     * @param methodName 方法名 (忽略大小写)
     * @param value      值
     * @return 是否成功调用
     */
    @SuppressWarnings("unchecked")
    static boolean execute(Player player, Object object, String methodName, Object value) {
        if (object == null) {
            return false;
        }

        Class<?> objectClass = object.getClass();

        // ReflectCommandInterface 应为 objectClass 实现的接口
        Class<? extends ReflectCommandInterface> ReflectCommandInterfaceClazz =
                (Class<? extends ReflectCommandInterface>) objectClass;

        Object finalValue = getFinalValue(player, value);

        try {
            Method[] objectMethods =
                    ReflectCommandInterfaceClazz.getMethods();

            for (Method method : objectMethods) {
                String name = method.getName();
                int parameterCount = method.getParameterCount();

                // 名称与参数匹配
                if (!name.equalsIgnoreCase(methodName) || parameterCount != 1) {
                    return false;
                }

                // 可见性
                method.setAccessible(true);
                method.invoke(object, finalValue);

                // 如果实现了 JsonPersistableInterface 接口则调用 write 方法
                if (JsonPersistableInterface.class.isAssignableFrom(objectClass)) {
                    JsonPersistableInterface jsonPersistableInterfaceObject =
                            (JsonPersistableInterface) object;
                    jsonPersistableInterfaceObject.write();
                }

                return true;
            }
        } catch (Exception ignore) {
            // ignore
        }

        return false;
    }

    /**
     * 获取最终值。
     *
     * <p>
     * 该方法将用来处理值内存在的特殊占位符或数字等。
     * </p>
     *
     * @param player 执行的玩家对象
     * @param value  值
     * @return 最终值
     */
    static Object getFinalValue(Player player, Object value) {
        if (!(value instanceof String)) {
            return value;
        }

        String valueString = (String) value;

        // 数字处理
        try {
            return Integer.parseInt(valueString);
        } catch (Exception ignore) {
            // ignore
        }

        // boolean
        try {
            return Boolean.parseBoolean(valueString);
        } catch (Exception ignore) {
            // ignore
        }

        // 玩家匹配
        Pattern pattern =
                Pattern.compile("<object_player_(.+?)>");
        Matcher matcher = pattern.matcher(valueString);

        if (matcher.find()) {
            String playerName = matcher.group(1);

            if (playerName.equalsIgnoreCase("me")) {
                return player;
            }

            Player targetPlayer = Bukkit.getPlayerExact(playerName);

            if (targetPlayer != null) {
                return targetPlayer;
            }
        }

        // 其余
        switch (valueString.toLowerCase()) {
            case "<object_location>":
                return player.getLocation();

            case "<object_block_location>":
                Location location = player.getLocation();

                World world = location.getWorld();

                int blockX = location.getBlockX();
                int blockY = location.getBlockY();
                int blockZ = location.getBlockZ();

                return new Location(
                        world, blockX, blockY, blockZ
                );
        }

        return value;
    }
}
