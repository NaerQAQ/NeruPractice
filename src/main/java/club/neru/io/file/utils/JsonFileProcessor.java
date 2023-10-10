package club.neru.io.file.utils;

import club.neru.io.file.impl.JsonManager;
import club.neru.serialization.interfaces.SerializableInterface;
import de.leonhard.storage.Json;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.File;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

@Getter
@Setter
@Accessors(chain = true)
public class JsonFileProcessor {
    private String path;
    private String jsonKey;
    private Class<?> targetClass;

    @SuppressWarnings("unchecked")
    public <T> ConcurrentLinkedQueue<T> deserializeJsonAndConvertToQueue() {
        ConcurrentLinkedQueue<File> files = IOUtils.getFiles(path);

        return (ConcurrentLinkedQueue<T>) files.stream()
                .map(file -> {
                    JsonManager jsonManager = JsonManager.getInstance();
                    Json json = jsonManager.get(file);
                    String string = json.getString(jsonKey);
                    return SerializableInterface.fromJson(string, targetClass);
                })
                .collect(Collectors.toCollection(ConcurrentLinkedQueue::new));
    }
}
