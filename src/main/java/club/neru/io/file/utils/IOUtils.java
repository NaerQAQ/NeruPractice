package club.neru.io.file.utils;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * I/O 工具类。
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/9
 */
public class IOUtils {
    /**
     * 获取指定文件夹内的所有文件。
     *
     * <p>
     * 不包括子文件夹。
     * </p>
     *
     * @param folderPath 文件夹路径
     * @return 包含所有文件的并发链接队列
     */
    public static ConcurrentLinkedQueue<File> getFiles(String folderPath) {
        File folder = new File(folderPath);

        if (!folder.isDirectory()) {
            return new ConcurrentLinkedQueue<>();
        }

        File[] files = folder.listFiles();

        if (files == null) {
            return new ConcurrentLinkedQueue<>();
        }

        return Arrays.stream(files)
                .filter(File::isFile)
                .collect(ConcurrentLinkedQueue::new, ConcurrentLinkedQueue::offer, ConcurrentLinkedQueue::addAll);
    }
}
