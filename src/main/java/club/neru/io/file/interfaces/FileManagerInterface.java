package club.neru.io.file.interfaces;

/**
 * 文件管理接口。
 *
 * <p>
 * 该接口定义了获取文件类型的管理类的方法。
 * </p>
 *
 * @param <T> 文件类型 (例如: {@link de.leonhard.storage.Yaml}, {@link de.leonhard.storage.Json}, etc.)
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/7/29
 */
public interface FileManagerInterface<T> {
    /**
     * 通过文件名与路径获取特定文件类型对象。
     *
     * @param name                    文件名
     * @param path                    文件路径
     * @param inputStreamFromResource 是否从资源内获取输入流
     * @return 特定文件类型对象
     */
    T get(String name, String path, boolean inputStreamFromResource);
}
