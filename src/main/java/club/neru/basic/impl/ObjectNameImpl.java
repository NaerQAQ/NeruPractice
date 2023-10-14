package club.neru.basic.impl;

import club.neru.basic.interfaces.ObjectNameInterface;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

/**
 * 对象名称实现的基础类。
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/11
 */
@Getter
@Setter
@Accessors(chain = true)
public class ObjectNameImpl implements ObjectNameInterface {
    /**
     * 对象的名称。
     */
    private String name = UUID.randomUUID().toString();

    /**
     * 将当前对象转换为指定类型。
     *
     * @param clazz 要转换成的目标类型
     * @param <T>   泛型参数
     * @return 转换后的对象
     */
    public <T> T to(Class<T> clazz) {
        return clazz.cast(this);
    }
}
