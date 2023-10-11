package club.neru.basic.interfaces;

import club.neru.io.file.interfaces.JsonPersistableInterface;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;

/**
 * 反射指令接口。
 *
 * <p>
 * 该接口的实现意为通过 {@link #execute(Player, Object, String, Object)} 实现指令式调用 {@code setter} 方法。
 * </p>
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/10
 */
public interface ReflectCommandInterface {
    /**
     * 执行方法。
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
        Class<? extends ReflectCommandInterface> clazz =
                (Class<? extends ReflectCommandInterface>) objectClass;

        Object finalValue = getFinalValue(player, value);

        try {
            Method[] objectMethods = clazz.getMethods();

            for (Method method : objectMethods) {
                String name = method.getName();
                int parameterCount = method.getParameterCount();

                if (name.equalsIgnoreCase(methodName) && parameterCount == 1) {
                    method.setAccessible(true);
                    method.invoke(object, finalValue);

                    if (JsonPersistableInterface.class.isAssignableFrom(objectClass)) {
                        JsonPersistableInterface jsonPersistableInterfaceObject =
                                (JsonPersistableInterface) object;
                        jsonPersistableInterfaceObject.write();
                    }

                    return true;
                }
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
        Object finalValue = null;

        if (value instanceof String) {
            String valueString = (String) value;

            try {
                finalValue = Integer.parseInt(valueString);
            } catch (Exception exception) {
                // ignore
            }

            switch (valueString.toLowerCase()) {
                case "<boolean_true>":
                    finalValue = true;
                    break;

                case "<object_block_location>":
                    Location location = player.getLocation();

                    World world = location.getWorld();
                    int blockX = location.getBlockX();
                    int blockY = location.getBlockY();
                    int blockZ = location.getBlockZ();

                    finalValue = new Location(world, blockX, blockY, blockZ);
                    break;
            }
        }

        return finalValue == null ? value : finalValue;
    }
}
