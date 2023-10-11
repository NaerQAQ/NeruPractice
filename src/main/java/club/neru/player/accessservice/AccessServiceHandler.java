package club.neru.player.accessservice;

import club.neru.Mochi;
import club.neru.io.file.impl.JsonManager;
import club.neru.player.accessservice.data.AccessServiceData;
import club.neru.serialization.interfaces.SerializableInterface;
import club.neru.utils.common.QuickUtils;
import club.neru.utils.common.enums.ConsoleMessageTypeEnum;
import de.leonhard.storage.Json;

/**
 * 玩家进入服务处理程序。
 *
 * @author NaerQAQ
 * @version 1.0
 * @since 2023/10/11
 */
public class AccessServiceHandler {
    /**
     * 玩家进入服务数据文件名。
     */
    public static final String ACCESS_SERVICE_DATA_NAME = "access_service_data";

    /**
     * 玩家进入服务数据文件路径。
     */
    public static final String ACCESS_SERVICE_DATA_PATH =
            Mochi.getDataFolderAbsolutePath() + "/access_service";

    /**
     * 序列化后 Json 字符串所在的键值。
     */
    public static final String ACCESS_SERVICE_DATA_JSON_KEY = "access_service_data";

    /**
     * 获取玩家进入服务。
     *
     * @return {@link AccessServiceData}
     */
    public static AccessServiceData getAccessServiceData() {
        JsonManager jsonManager = JsonManager.getInstance();

        Json json = jsonManager.get(
                ACCESS_SERVICE_DATA_NAME,
                ACCESS_SERVICE_DATA_PATH,
                false
        );

        String accessServiceDataString =
                json.getString(ACCESS_SERVICE_DATA_JSON_KEY);

        if (accessServiceDataString.isEmpty()) {
            QuickUtils.sendMessageByKey(
                    ConsoleMessageTypeEnum.NO_PREFIX,
                    "access-service-data-null"
            );

            return null;
        }

        return SerializableInterface.fromJson(
                accessServiceDataString, AccessServiceData.class
        );
    }
}
