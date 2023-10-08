package club.neru.utils.serialization;

import com.google.gson.GsonBuilder;

public interface SerializableInterface {
    default String toJson() {
        return new GsonBuilder().create().toJson(this);
    }

    static <T> T fromJson(String json, Class<T> clazz) {
        return new GsonBuilder().create().fromJson(json, clazz);
    }
}
