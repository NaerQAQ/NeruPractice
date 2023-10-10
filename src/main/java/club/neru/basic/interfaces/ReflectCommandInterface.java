package club.neru.basic.interfaces;

import java.lang.reflect.Method;

/**
 * 反射指令接口。
 *
 * <p>
 * 该接口的实现意为通过 {@link #execute(Object, String, Object)} 实现指令式调用 {@code setter} 方法。
 * </p>
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/10
 */
public interface ReflectCommandInterface {
    /**
     * 覆盖性写入更改。
     *
     * <p>
     * 该操作为覆盖性操作，即无论是否存在，均直接写入。
     * </p>
     */
    void write();

    /**
     * 执行方法。
     *
     * @param object     实现 {@link ReflectCommandInterface} 的对象
     * @param methodName 方法名 (忽略大小写)
     * @param value      值
     * @return 是否成功调用
     */
    @SuppressWarnings("unchecked")
    static boolean execute(Object object, String methodName, Object value) {
        if (object == null) {
            return false;
        }

        Class<? extends ReflectCommandInterface> clazz =
                (Class<? extends ReflectCommandInterface>) object.getClass();

        try {
            Method[] objectMethods = clazz.getDeclaredMethods();

            for (Method method : objectMethods) {
                String name = method.getName();
                int parameterCount = method.getParameterCount();

                if (name.equalsIgnoreCase(methodName) && parameterCount == 1) {
                    method.setAccessible(true);
                    method.invoke(object, value);
                    ReflectCommandInterface reflectCommandInterfaceObject =
                            (ReflectCommandInterface) object;
                    reflectCommandInterfaceObject.write();
                    return true;
                }
            }
        } catch (Exception ignore) {
            // ignore
        }

        return false;
    }
}
