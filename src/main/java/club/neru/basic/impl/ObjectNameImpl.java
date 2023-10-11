package club.neru.basic.impl;

import club.neru.basic.interfaces.ObjectNameInterface;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ObjectNameImpl implements ObjectNameInterface {
    private String name;

    public <T> T to(Class<T> clazz) {
        return clazz.cast(this);
    }
}
