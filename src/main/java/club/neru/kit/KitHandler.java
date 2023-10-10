package club.neru.kit;

import club.neru.NeruPractice;

/**
 * 装备包处理程序。
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/10
 */
public class KitHandler {
    /**
     * 序列化后 Json 字符串所在的键值。
     */
    public static final String KIT_JSON_KEY = "kit";

    /**
     * 装备包路径。
     */
    public static final String KIT_PATH =
            NeruPractice.getDataFolderAbsolutePath() + "/kit";
}
