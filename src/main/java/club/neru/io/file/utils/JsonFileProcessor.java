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

/**
 * 批量文件流处理程序。
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/10
 */
@Getter
@Setter
@Accessors(chain = true)
public class JsonFileProcessor {
    /**
     * 文件路径。
     */
    private String path;

    /**
     * 反序列化内容所在的 Json 键值。
     */
    private String jsonKey;

    /**
     * 反序列化的目标类。
     */
    private Class<?> targetClass;

    /**
     * 反序列化并将结果转换为指定类对象的队列。
     *
     * @param <T> 元素类型
     * @return 指定反序列化后的元素队列
     */
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
