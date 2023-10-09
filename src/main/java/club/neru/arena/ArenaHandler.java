package club.neru.arena;

import club.neru.NeruPractice;

/**
 * 竞技场处理程序。
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/9
 */
public class ArenaHandler {
    /**
     * 序列化后 Json 字符串所在的键值。
     */
    public static final String ARENA_JSON_KEY = "arena";

    /**
     * 竞技场路径。
     */
    public static final String ARENA_PATH =
            NeruPractice.getDataFolderAbsolutePath() + "/arena";

    /**
     * 母竞技场所在的路径。
     */
    public static final String ARENA_PARENT_PATH = ARENA_PATH + "/parent";
}
